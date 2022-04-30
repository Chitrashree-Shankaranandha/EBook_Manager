package com.example.navigationdrawerexample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DetailActivity extends AppCompatActivity {

    private ImageView ivPosterImage;
    private TextView bookTitle;
    private TextView tvPlot;
    private TextView bookAuthor;
    private TextView bookGenre;
    private RatingBar userRating;
    private RatingBar avgRating;
    private TextView tvImdbScore;
    private TextView ratingLabel;
    private ImageButton wishlistBtn;
    private ImageButton commentBtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private String  bookID;
    FirebaseAuth Auth;
    FirebaseUser currentUser;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String S_userId = "userId";
    private static final int THREAD_ID = 10000;

    ListView lvComment;
    //private int mPosition;
    BookAdapter booksAdater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://navigationdrawerexample-e5fc6-default-rtdb.firebaseio.com/WishList");
        Auth = FirebaseAuth.getInstance();
        currentUser = Auth.getCurrentUser();

        if(BuildConfig.DEBUG){

            StrictMode.enableDefaults();
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build();
            StrictMode.setThreadPolicy(policy);

           StrictMode.VmPolicy VmPolicy= new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build();
           StrictMode.setVmPolicy(VmPolicy);

        }
        setContentView(R.layout.detail_activity);

        // Fetch views
        ivPosterImage = (ImageView) findViewById(R.id.ivPosterImage);
        bookTitle = (TextView) findViewById(R.id.bookTitle);
        bookAuthor =  findViewById(R.id.authorName);
        userRating =  (RatingBar) findViewById(R.id.userRating);
        bookGenre =  findViewById(R.id.genreName);
        commentBtn = (ImageButton) findViewById(R.id.commentBtn);
        wishlistBtn = (ImageButton) findViewById(R.id.wishlistBtn);

        Bundle i = getIntent().getExtras();
                ;
        String mTitle = i.getString("Title");
        String mAuthor = i.getString("Author");
        String mImage = i.getString("Image");
        String mGenre = i.getString("Genre");
        Float mRatings=i.getFloat("Rating");

        Glide.with(this).load(getImage(mImage)).into(ivPosterImage);
        bookTitle.setText(mTitle);
        bookAuthor.setText(mAuthor);
        bookGenre.setText(mGenre);


        userRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float mRatings, boolean fromUser) {
                userRating.setRating(mRatings);
            }

        });

        wishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrafficStats.setThreadStatsTag(THREAD_ID);

                String wishListBookTitle = bookTitle.getText().toString();
                String wishListBookAuthor = bookAuthor.getText().toString();
                String wishListbookGenre = bookGenre.getText().toString();
                Float wishListuserRating = mRatings;
                bookID = wishListBookTitle;

                System.out.println("Inside wishlistBtn setOnClickListener"+currentUser.getEmail());

                WishList wishListObj = new WishList(wishListBookTitle, wishListBookAuthor,wishListuserRating,mImage , wishListbookGenre,currentUser.getEmail());

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // on below line we are setting data in our firebase database.
                        databaseReference.child(bookID).setValue(wishListObj);
                        // displaying a toast message.
                        Toast.makeText(DetailActivity.this, "WishList Added..", Toast.LENGTH_SHORT).show();
                        // starting a main activity.
                        startActivity(new Intent(DetailActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on below line.
                        Toast.makeText(DetailActivity.this, "Fail to add WishList..", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String wishListBookTitle = bookTitle.getText().toString();

                Intent intent = new Intent(DetailActivity.this, CommentActivity.class);
                intent.putExtra("name",currentUser.getEmail());
                intent.putExtra("bookname",wishListBookTitle);
                startActivity(intent);

            }
        });

    }

    public int getImage(String imageName) {

        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());

        return drawableResourceId;
    }
}