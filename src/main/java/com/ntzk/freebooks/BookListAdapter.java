package com.ntzk.freebooks;

import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

  List<Book> books;
  Fragment fragment;
  View view;

    public BookListAdapter(List<Book> books,Fragment fragment) {
        this.books = books;
        this.fragment=fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.booklayout,parent,false);
        this.view=view;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.getTitlu().setText(books.get(position).getTitlu());
        holder.getAutor().setText(books.get(position).getAutor());
        holder.getAn().setText(books.get(position).getAn());
        holder.getRating().setText(books.get(position).getRating());
        //StorageReference imageRef=FirebaseStorage.getInstance().getReferenceFromUrl("gs://freebooks-96cd9.appspot.com/books/darthplagueis.jpg");
        Glide.with(fragment).load(books.get(position).getCover()).into(holder.getCover());
        holder.getOpenBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(fragment.getActivity(),PDFActivity.class);
                intent.putExtra("pdf",books.get(position).getPdf());
                intent.putExtra("nightMode",((MainActivity)fragment.getActivity()).isNightModeOn());
                fragment.getActivity().startActivity(intent);
            }
        });
        final List<String> favList=((MainActivity)fragment.getActivity()).getFavorite();
        if (favList.contains(books.get(position).getTitlu()))
            holder.favBtn.setChecked(true);
        holder.getFavBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.favBtn.isChecked())
                favList.remove(books.get(position).getTitlu());
                else 
                    favList.add(books.get(position).getTitlu());
//
            }
        });



    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView titlu,autor,an,rating;
        private ImageView cover;
        private ImageButton openBtn;
        private ToggleButton favBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titlu=itemView.findViewById(R.id.titleTv);
            autor=itemView.findViewById(R.id.autorTv);
            an=itemView.findViewById(R.id.anTv);
            rating=itemView.findViewById(R.id.ratingtv);
            cover=itemView.findViewById(R.id.Bookcover);
            openBtn=itemView.findViewById(R.id.openBtn);
            favBtn=itemView.findViewById(R.id.favBtn);

        }

        public ImageButton getOpenBtn() {
            return openBtn;
        }

        public ToggleButton getFavBtn() {
            return favBtn;
        }

        public ImageView getCover() {return cover;}
        public TextView getRating() {return  rating;}
        public TextView getTitlu() {
            return titlu;
        }

        public TextView getAutor() {
            return autor;
        }

        public TextView getAn() {
            return an;
        }
    }
}
