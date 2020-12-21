package com.ntzk.freebooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PDFActivity extends AppCompatActivity {
private  TextView errorTv;
private ProgressBar progressBar;
private PDFView pdfView;
private SharedPreferences sp;
private String pdf;
private boolean nightMode;
    private static final String TAG = "PDFActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp=getSharedPreferences("PageNumber",MODE_PRIVATE);
        setContentView(R.layout.activity_p_d_f);
        progressBar=findViewById(R.id.pdfProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        pdf=getIntent().getStringExtra("pdf");
        nightMode=getIntent().getBooleanExtra("nightMode",false);
        final int pageNumber=sp.getInt(pdf,0);
        pdfView=findViewById(R.id.pdfview);
        StorageReference pdfRef= FirebaseStorage.getInstance().getReferenceFromUrl(pdf);
        pdfRef.getBytes(20*1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                progressBar.setVisibility(View.GONE);
                pdfView.fromBytes(bytes).defaultPage(pageNumber).nightMode(nightMode).load();
            }

        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: ");
                        progressBar.setVisibility(View.GONE);
                        errorTv=findViewById(R.id.pdfErrorTv);
                        errorTv.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        sp.edit().putInt(pdf,pdfView.getCurrentPage()).commit();
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        sp.edit().putInt(pdf,pdfView.getCurrentPage()).commit();
        super.onStop();
    }
}