package com.example.navigationdrawerexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class WishListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater lInflater;
    private List<WishList> WishlistStorage;

    public WishListAdapter(Context context, List<WishList> customizedListView) {

        this.context = context;

        lInflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        WishlistStorage = customizedListView;
    }


    @Override
    public int getCount() {
        return WishlistStorage.size();
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
                WishlistStorage.get(position).getTitle());
        bookGenre.setText("Book Genre: " +
                WishlistStorage.get(position).getGenre());
        bookAuthor.setText("Book Author: " +
                WishlistStorage.get(position).getAuthor());
        bookRatings.setRating((WishlistStorage.get(position).getRatings()));

        ivPosterImage = (ImageView) convertView.findViewById(R.id.ivPosterImage);
        String url_text = WishlistStorage.get(position).getBookCover();
        Glide.with(context).load(getImage(url_text)).into(ivPosterImage);


        return convertView;
    }


}
