package com.example.navigationdrawerexample;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BookAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater lInflater;
    private List<Books> listStorage;

    public BookAdapter(Context context, List<Books> customizedListView) {

        this.context = context;

        lInflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }


    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        return drawableResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //View currentItemView = convertView;

        TextView bookTitle = null;
        TextView bookGenre = null;
        TextView bookAuthor = null;
        RatingBar bookRatings = null;
        ImageView ivPosterImage = null;


        if (convertView == null) {
            convertView = lInflater.inflate(R.layout.list, parent,
                    false);
        }
        bookTitle = convertView.findViewById(R.id.textView);
        bookGenre = convertView.findViewById(R.id.textView3);
        bookAuthor = convertView.findViewById(R.id.textView2);
        bookRatings = convertView.findViewById(R.id.ratingBar);

        bookTitle.setText("Book Title: " +
                listStorage.get(position).getTitle());
        bookGenre.setText("Book Genre: " +
                listStorage.get(position).getGenre());
        bookAuthor.setText("Book Author: " +
                listStorage.get(position).getAuthor());
        bookRatings.setRating((listStorage.get(position).getRatings()));

        ivPosterImage = (ImageView) convertView.findViewById(R.id.ivPosterImage);
        String url_text = listStorage.get(position).getBookCover();
        Glide.with(context).load(getImage(url_text)).into(ivPosterImage);

        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent switchActivityIntent = new Intent(context, DetailActivity.class);
                switchActivityIntent.putExtra("Image",listStorage.get(position).getBookCover());
                switchActivityIntent.putExtra("Title",listStorage.get(position).getTitle());
                switchActivityIntent.putExtra("Author",listStorage.get(position).getAuthor());
                switchActivityIntent.putExtra("Genre",listStorage.get(position).getGenre());
                switchActivityIntent.putExtra("Rating",listStorage.get(position).getRatings());

                context.startActivity(switchActivityIntent);
            }
            });

        return convertView;
    }
}
