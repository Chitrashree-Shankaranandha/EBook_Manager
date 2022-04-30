package com.example.navigationdrawerexample;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class WishlistFragment extends Fragment  {
    private Context context;

    public WishlistFragment() {

        super(R.layout.fragment_wishlist);
        //this.context = context;
        // Required empty public constructor
    }

    FirebaseAuth Auth;
    FirebaseUser currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    private List<WishList> wishlistStorage;
    public static final String BOOK_DETAIL_KEY = "wishlist";
    AdapterView PARENT;
    private ListView lvWishList;
    private WishListAdapter adapterWishList;
    ArrayList<WishList> arrayList;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;




    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        super.onViewCreated(view, savedInstanceState);
        // below line is used to get the instance
        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get
        // reference for our database.
        lvWishList = (ListView) view.findViewById(R.id.listView);
//        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://databaseauth-19365-default-rtdb.firebaseio.com/Wishlist");
//        databaseReference = FirebaseDatabase.getInstance().getReference("Wishlist");
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://navigationdrawerexample-e5fc6-default-rtdb.firebaseio.com/WishList");

        getdata();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_wishlist, container, false);

        setHasOptionsMenu(true);
        Auth = FirebaseAuth.getInstance();
        currentUser = Auth.getCurrentUser();
        System.out.print("currentUser"+currentUser.getEmail());
        return rootView;

    }


    private void getdata() {
        // calling add value event listener method
        // for getting the values from database.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
               // String value = snapshot.getValue(String.class);
                arrayList = new ArrayList<WishList>();
                for(DataSnapshot ds : snapshot.getChildren()) {
                        WishList obj = ds.getValue(WishList.class);
                        if(obj.getUsername().equals(currentUser.getEmail())) {
                            System.out.println("Author Name" + obj.toString());
                            arrayList.add(obj);
                        }

                }

                adapterWishList = new WishListAdapter(getActivity(),arrayList);
                lvWishList.setAdapter(adapterWishList);
                System.out.println("onDataChange");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(getContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

    }


}