package com.example.firebase_test;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import models.Users;

public class MainActivity extends AppCompatActivity {
    MainAdapter mainAdapter;
    FirebaseAuth auth;
    Button btn_Logout;
    FirebaseUser user;
//    TextView txt_welcome;
    RecyclerView recyclerView;
    FloatingActionButton fab_add;
    Users users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        btn_Logout=findViewById(R.id.btnLogout);
        fab_add = (FloatingActionButton)findViewById(R.id.fab_add);
        recyclerView = findViewById(R.id.rcv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        user = auth.getCurrentUser();
        if(user == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
            Log.d(TAG, "onCreate: khong co thong tin nguoi dung");
        }
        else{
            Log.d(TAG, "Da goi ham nay ");
            Log.d(TAG, "id nguoi dung: "+user.getUid());
            Log.d(TAG, "email người dùng: "+ user.getEmail());
            FirebaseRecyclerOptions<Users> options =
                    new FirebaseRecyclerOptions.Builder<Users>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), Users.class)
                            .build();
            mainAdapter = new MainAdapter(options);
            mainAdapter.startListening();
            recyclerView.setAdapter(mainAdapter);

        }

        btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddActivity.class));

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String search) {
                searchByName(search);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String search) {
                searchByName(search);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void searchByName(String search_key){
        String endKey = search_key + "\uf8ff";
        Query query = FirebaseDatabase.getInstance().getReference().child("users")
                .orderByChild("name")
                .startAt(search_key)
                .endAt(endKey);

        FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(query, Users.class)
                .build();
        mainAdapter = new MainAdapter(options);
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }
//private void searchByName(String searchKey) {
//    Query query = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("name");
//
//    query.addListenerForSingleValueEvent(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            List<Users> searchResults = new ArrayList<>();
//
//            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                Users user = snapshot.getValue(Users.class);
//                if (user != null && user.getName().toLowerCase().contains(searchKey.toLowerCase())) {
//                    searchResults.add(user);
//                }
//            }
//
//            // Hiển thị kết quả searchResults ở đây (có thể thông qua adapter, RecyclerView, ListView, etc.)
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//            // Xử lý nếu có lỗi
//        }
//    });
//}
}