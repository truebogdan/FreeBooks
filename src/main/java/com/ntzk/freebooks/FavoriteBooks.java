package com.ntzk.freebooks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteBooks#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteBooks extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoriteBooks() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteBooks.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteBooks newInstance(String param1, String param2) {
        FavoriteBooks fragment = new FavoriteBooks();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final List<String> favs=((MainActivity)this.getActivity()).getFavorite();
        View view= inflater.inflate(R.layout.fragment_favorite_books, container, false);
        final TextView emptyTv=view.findViewById(R.id.emptyListTV);
        final ProgressBar favProgressBar=view.findViewById(R.id.favListProgressBar);
        final RecyclerView recyclerView=view.findViewById(R.id.FavBooksRecView);
        final TextView favError=view.findViewById(R.id.faverrorTv);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this.getContext());
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        final ArrayList<Book> favsBooks=new ArrayList<>();
        if(favs.isEmpty())
            emptyTv.setVisibility(View.VISIBLE);
        else
        {
            favProgressBar.setVisibility(View.VISIBLE);
            firestore.collection("books").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful())
                    {
                        ArrayList<Book> favBooks=new ArrayList<>();
                        for (QueryDocumentSnapshot q:task.getResult())
                        {
                            Book book=q.toObject(Book.class);
                            if(favs.contains(book.getTitlu()))
                                favBooks.add(book);

                        }

                        recyclerView.setAdapter(new BookListAdapter(favBooks,FavoriteBooks.this));
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        favProgressBar.setVisibility(View.GONE);
                    }
                    else
                    {
                        favError.setVisibility(View.VISIBLE);
                    }

                }
            });
        }

        return view;
    }
}