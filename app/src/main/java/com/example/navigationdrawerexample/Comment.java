
package com.example.navigationdrawerexample;

public class Comment {
    String comment;
    String publisher;
    String time;
    String bookname;


    public Comment(String comment, String publisher , String bookname,String time) {
        this.comment = comment;
        this.publisher = publisher;
        this.time = time;
        this.bookname=bookname;
    }

    public Comment()
    {

    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }
}