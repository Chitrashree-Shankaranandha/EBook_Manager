package com.example.navigationdrawerexample;

import android.content.Context;
import android.content.Intent;
import android.net.TrafficStats;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WishListAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater lInflater;
    private List<WishList> WishlistStorage;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser currentUser;
    FirebaseAuth Auth;


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
        ImageView wishlistDeleteIcon=null;


        if (convertView == null) {

            convertView = lInflater.inflate(R.layout.wishlist_layout, parent,
                            false);
        }

        bookTitle = convertView.findViewById(R.id.textView);
        bookGenre = convertView.findViewById(R.id.textView3);
        bookAuthor = convertView.findViewById(R.id.textView2);
        bookRatings = convertView.findViewById(R.id.ratingBar);
        wishlistDeleteIcon = convertView.findViewById(R.id.deleteImage);
        wishlistDeleteIcon.setTag(position);

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

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://navigationdrawerexample-e5fc6-default-rtdb.firebaseio.com/WishList");
        Auth = FirebaseAuth.getInstance();
        currentUser = Auth.getCurrentUser();

        wishlistDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int pos = (int)view.getTag();
                WishlistStorage.remove(pos);
                Toast.makeText(context,"Wishlist Deleted",Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }

        });

        return convertView;
    }

    private void removeItem(int position) {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()) {
                    WishList obj = ds.getValue(WishList.class);
                    if(obj.getUsername().equals(currentUser.getEmail())) {
                        System.out.println("Remove wishlist for the current user"+obj.getTitle()+" "+obj.getUsername());
                       //  ds.getRef().removeValue();

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(context, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
