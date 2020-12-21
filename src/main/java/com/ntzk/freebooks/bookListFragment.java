package com.ntzk.freebooks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.perfmark.Tag;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link bookListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class bookListFragment extends Fragment {
    private static final String TAG = "bookListFragment";
    private ProgressBar booklistProgressBar;
    private TextView errorTv;
    private ToggleButton thrillerBtn,SfBtn,FanBtn,jurnalBtn;
    private RecyclerView bookRecView;
    private List<String> filters= new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private  FirebaseFirestore db;

    public bookListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static bookListFragment newInstance(String param1, String param2) {
        bookListFragment fragment = new bookListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        db= FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.booklist, container, false);
        initializeView(view);
        final List<Book> books=new ArrayList<>();
        db.collection("books").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    booklistProgressBar.setVisibility(View.GONE);
                    for(QueryDocumentSnapshot documentSnapshots:task.getResult())
                    {
                        Book book=documentSnapshots.toObject(Book.class);
                        Log.d(TAG, "onComplete: OBJECT "+book.toString());
                        books.add(book);
                    }
                    bookRecView.setAdapter( new BookListAdapter(books,bookListFragment.this));
                    bookRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

                }
                else
                {
                    errorTv.setVisibility(View.VISIBLE);
                }
            }


        });


        return  view;
    }
    private void initializeView(View view)
    {
        bookRecView=view.findViewById(R.id.BookListRecView);
        booklistProgressBar=view.findViewById(R.id.booklistProgressBar);
        booklistProgressBar.setVisibility(View.VISIBLE);
        errorTv=view.findViewById(R.id.errorTv);
        thrillerBtn=view.findViewById(R.id.thBtn);
        SfBtn=view.findViewById(R.id.SFCat);
        FanBtn=view.findViewById(R.id.FanCat);
        jurnalBtn=view.findViewById(R.id.JurnalCat);
        filterListner();

    }
    private void filterListner()
    {
         View.OnClickListener filterListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query=db.collection("books");
                if(((ToggleButton)v).isChecked())
                {
                    filters.add(((ToggleButton) v).getText().toString());
                    query = query.whereIn("Gen",filters);
                    adaptFromQuery(query);
                }
                else
                {
                    filters.remove(((ToggleButton) v).getText().toString());
                    if(!filters.isEmpty())
                    {
                        query=query.whereIn("Gen",filters);
                    }
                    
                    adaptFromQuery(query);
                }


            }
        };
         thrillerBtn.setOnClickListener(filterListner);
         SfBtn.setOnClickListener(filterListner);
         FanBtn.setOnClickListener(filterListner);
         jurnalBtn.setOnClickListener(filterListner);


    }
    private void adaptFromQuery(Query query)
    {
        final List<Book> books=new ArrayList<>();
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    booklistProgressBar.setVisibility(View.GONE);
                    for(QueryDocumentSnapshot documentSnapshots:task.getResult())
                    {
                        Book book=documentSnapshots.toObject(Book.class);
                        Log.d(TAG, "onComplete: OBJECT "+book.toString());
                        books.add(book);
                    }
                    bookRecView.setAdapter( new BookListAdapter(books,bookListFragment.this));
                    bookRecView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
                else
                {
                    errorTv.setVisibility(View.VISIBLE);
                }

            }
        });
    }

}