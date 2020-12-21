package com.ntzk.freebooks;

import androidx.annotation.NonNull;

public class Book {
    private String Titlu;
    private String Autor;
    private String An;
    private String Rating;
    private String Cover;
    private String Pdf;
    private String Gen;

    public Book(String titlu, String autor, String an,String rating,String cover,String pdf) {
        this.Titlu = titlu;
        this.Autor = autor;
        this.An = an;
        this.Rating=rating;
        this.Cover=cover;
        this.Pdf=pdf;
    }

    public Book() {
    }

    public String getGen() {
        return Gen;
    }

    public void setGen(String gen) {
        Gen = gen;
    }

    public String getCover() {
        return Cover;
    }

    public String getPdf() {
        return Pdf;
    }

    public String getTitlu() {
        return Titlu;
    }

    public void setTitlu(String titlu) {
        this.Titlu = titlu;
    }

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String autor) {
        this.Autor = autor;
    }

    public String getAn() {
        return An;
    }
    public String getRating() {return  Rating;}

    public void setAn(String an) {
        this.An = an;
    }

    @NonNull
    @Override
    public String toString() {
        return Titlu +" "+Autor;
    }
}
