package com.example.navigationdrawerexample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


public class AboutUsFragment extends Fragment {

    TextView txtview;

    public AboutUsFragment() {
        super(R.layout.fragment_about_us);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtview= view.findViewById(R.id.about_us);
        txtview.setText(" Ebook Manager is an android application for everyone who is facing issues with managing ebooks.\n" +
                "This is the application that enables the users to browse the top rated books from Kindle depending on the ratings given by other users.\n" +
                "Users can also make a wish list of their Books.\n" +
                "Users can interact with each other as well in the comment section. A real-time movie\n" +
                "managing application \n" +
                "It enables users to sign up and login using Facebook in different ways and a Real-\n" +
                "Time ebook managing application where users can provide their comment or reviews.\n" +
                "\n"
                );
    }


}