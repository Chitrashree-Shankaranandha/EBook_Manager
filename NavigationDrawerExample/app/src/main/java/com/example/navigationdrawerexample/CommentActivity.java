package com.example.navigationdrawerexample;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    EditText edit_comment;
    ImageView send;
    RecyclerView recyclerView;
    List<Comment> List;
    CommentAdapter adapter;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    String name;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private String  bookID;
    FirebaseAuth Auth;
    FirebaseUser currentUser;
    String mTitle;
    String comment_value;
    String bookname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        edit_comment = findViewById(R.id.input_comment);
        send = findViewById(R.id.send);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        Bundle i = getIntent().getExtras();
         mTitle = i.getString("name");
         bookname= i.getString("bookname");
        comment_value=edit_comment.getText().toString();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_comment.getText().toString().isEmpty())
                {
                    Toast.makeText(CommentActivity.this, "Comment can't be empty", Toast.LENGTH_SHORT).show();
                }else
                {
                    addComment();
                }

                edit_comment.getText().clear();
            }
        });

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

       getComments();

    }

    private void getComments() {

        List = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://navigationdrawerexample-e5fc6-default-rtdb.firebaseio.com/Comments");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Comment commentObj = dataSnapshot.getValue(Comment.class);

                    if(bookname.equals(commentObj.getBookname())){

                        List.add(commentObj);
                    }

                }
              adapter = new CommentAdapter(getApplicationContext(), List);  recyclerView.setAdapter(adapter);
               adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(CommentActivity.this, "Error while load comments", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addComment() {

        comment_value=edit_comment.getText().toString();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://navigationdrawerexample-e5fc6-default-rtdb.firebaseio.com/Comments");
        Auth = FirebaseAuth.getInstance();
        currentUser = Auth.getCurrentUser();

        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("dd-M-yyyy hh:mm a");
        String currentDate=format.format(date);

        Comment commentObj= new Comment(comment_value, mTitle, bookname,currentDate) ;
        String commentName = comment_value;

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                databaseReference.child(commentName).setValue(commentObj);
                Toast.makeText(CommentActivity.this, "Comment is added", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}
