package com.example.navigationdrawerexample;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class WishlistFragment extends Fragment  {
    private Context context;

    public WishlistFragment() {

        super(R.layout.fragment_wishlist);
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
        super.onViewCreated(view, savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        lvWishList = (ListView) view.findViewById(R.id.wishlistview);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://navigationdrawerexample-e5fc6-default-rtdb.firebaseio.com/WishList");
        getdata();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_wishlist, container, false);

        setHasOptionsMenu(true);
        Auth = FirebaseAuth.getInstance();
        currentUser = Auth.getCurrentUser();
        return rootView;

    }

    private void getdata() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}