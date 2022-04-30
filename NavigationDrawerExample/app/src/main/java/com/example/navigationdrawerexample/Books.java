package com.example.navigationdrawerexample;

public class Books {
    String genre;
    String title;
    String author;
    float ratings;
    String votes;
    double price;
    String year;
    String bookCover;

    public Books(String genre, String title, String author, float ratings, String votes, double price, String year,
                 String bookCover) {
        this.genre = genre;
        this.title = title;
        this.author = author;
        this.ratings = ratings;
        this.votes = votes;
        this.price = price;
        this.year = year;
        this.bookCover=bookCover;
    }

    public Books(String title, String author, float ratings, String bookCover, String genre) {
        this.title = title;
        this.author = author;
        this.ratings = ratings;
        this.bookCover=bookCover;
        this.genre=genre;

    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getRatings() {
        return ratings;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public  String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }
}
