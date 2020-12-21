package com.ntzk.freebooks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.LogDescriptor;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class ContactFragment extends Fragment {
    private  final long DAY=86400000L;
    private static final String TAG = "ContactFragment";
    private Spinner spinner;
    private Button sendBtn;
    private EditText nume,descriere;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final SharedPreferences sp=getActivity().getSharedPreferences("ContactTime",Context.MODE_PRIVATE);
        long lastMessage=sp.getLong("LastMessage",0);


        spinner=view.findViewById(R.id.spinner);
        nume=view.findViewById(R.id.editTextTextPersonName);
        descriere=view.findViewById(R.id.editTextTextMultiLine);
        sendBtn=view.findViewById(R.id.sendButton);
        if(lastMessage+DAY>System.currentTimeMillis())
            sendBtn.setEnabled(false);
        final Context context=getContext();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nume.getText().toString().equals("") && descriere.getText().toString().equals(""))
                {
                    Log.d(TAG, "onClick: eroare");
                }
                else
                {
                    sendBtn.setEnabled(false);
                    Map<String ,String> mesaje=new HashMap<>();
                    mesaje.put("Nume" ,nume.getText().toString());
                    mesaje.put("Subiect",spinner.getSelectedItem().toString());
                    mesaje.put("Descriere",descriere.getText().toString());
                    FirebaseFirestore.getInstance().collection("mesaje").add(mesaje).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(context, "Mesaj trimis cu succes.", Toast.LENGTH_SHORT).show();

                            sp.edit().putLong("LastMessage",System.currentTimeMillis()).commit();

                        }
                    });
                }
            }
        });
    }
}