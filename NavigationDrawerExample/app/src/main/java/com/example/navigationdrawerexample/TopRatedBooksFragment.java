package com.example.navigationdrawerexample;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;

import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TopRatedBooksFragment extends Fragment{

    private ListView playList;
    private RatingBar mRatingBar;
    private int titleCount = 0;
    private double maxPrice = Integer.MIN_VALUE;

    public TopRatedBooksFragment() {
        super(R.layout.fragment_top_e_books);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playList = view.findViewById(R.id.listView);
        mRatingBar = (RatingBar) view.findViewById(R.id.ratingBar);

        System.out.println("Hello inside onViewCreated");

        StringBuilder jsonResult = new StringBuilder();
        try {
            InputStream in = getActivity().getAssets().open("book.json");
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                jsonResult.append(line);
            }

            System.out.println(jsonResult.toString());

        } catch (Exception e) {
            System.out.println("Err: " + e);
        }
        List<Books> fragbooklist =
                returnParsedJsonObject(jsonResult.toString());
        BookAdapter jsonBookAdapter = new
                BookAdapter(getContext(), fragbooklist);

        playList.setAdapter(jsonBookAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AsyncDataClass().execute();

    }
    private List<Books> listStorage;
    public static final String BOOK_DETAIL_KEY = "book";
    AdapterView PARENT;
    private ListView lvMovies;
    private BookAdapter adapterBooks;
    ArrayList<Books> arrayList = new ArrayList<Books>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


            final View rootView = inflater.inflate(R.layout.fragment_top_e_books, container, false);


       // return inflater.inflate(R.layout.fragment_top_e_books, container, false);
     return rootView;
    }

    private class AsyncDataClass extends AsyncTask<String, Void,
            String> {
        //  HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(String... params) {
            StringBuilder jsonResult = new StringBuilder();
            try {
                InputStream in = getActivity().getAssets().open("book.json");
                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonResult.append(line);
                }
                System.out.println("Returned Json url object " +
                        jsonResult.toString());
            } catch (Exception e) {
                System.out.println("Err: " + e);
            }
            return jsonResult.toString();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println("onExecute");
            List<Books> parsedObject =
                    returnParsedJsonObject(result);
            BookAdapter jsonBookAdapter = new
                    BookAdapter(getContext(), parsedObject);

            playList.setAdapter(jsonBookAdapter);
        }
    } //end AsyncDataClass class

    private List<Books> returnParsedJsonObject(String result) {
        List<Books> jsonObject = new ArrayList<Books>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        Books newItemObject = null; //interior object holder

        try {
            resultObject = new JSONObject(result);
            System.out.println("Preparsed JSON object " +
                    resultObject.toString());
            // set up json Array to be parse
            jsonArray = resultObject.optJSONArray("Kindle_Top15");
            // System.out.println("Json array"+jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonChildNode = null;
            try {
                jsonChildNode = jsonArray.getJSONObject(i);
                //get all data from stream
                String bookTitle = jsonChildNode.getString("NAME");
                String bookAuthor =
                        jsonChildNode.getString("AUTHOR");
                String bookRating =
                        jsonChildNode.getString("RATING");

                String bookImage =
                        jsonChildNode.getString("PHOTO");
                String bookGenre=
                        jsonChildNode.getString("GENRE");
                newItemObject = new Books(bookTitle, bookAuthor, Float.parseFloat(bookRating), bookImage,bookGenre);
                jsonObject.add(newItemObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    } //end method

}