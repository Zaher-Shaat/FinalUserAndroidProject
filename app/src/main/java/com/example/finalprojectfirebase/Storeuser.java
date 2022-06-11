package com.example.finalprojectfirebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Storeuser extends AppCompatActivity {
    private static final String TAG = "store";
    ArrayList<item> items = new ArrayList<>();
    FloatingActionButton signOutBtn;
    private RecyclerView store_clothes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeuser);
        store_clothes = findViewById(R.id.store_clothes);
        signOutBtn = findViewById(R.id.signOutBtn);
        store_clothes.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        store_clothes.setLayoutManager(gridLayoutManager);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        readData();
    }

    private void readData() {
        items.clear();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("items");
        Task<DataSnapshot> task = ref.get();
        task.addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Iterable<DataSnapshot> data = task.getResult().getChildren();
                    for (DataSnapshot snap : data) {
                        item i = snap.getValue(item.class);
                        items.add(i);

                    }
                    clothesAdapter adapter = new clothesAdapter(items);
                    store_clothes.setAdapter(adapter);


                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(Storeuser.this, "failed " + error, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void signOut() {


        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        SharedPreferences sharedPreferences = getSharedPreferences("loginState", Context.MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
        myEdit.putBoolean("loginState", false);
        myEdit.commit();
        Intent signOutIntent = new Intent(Storeuser.this, MainActivity.class);
        signOutIntent.putExtra("LoginState", false);
        startActivity(signOutIntent);
        finish();
    }
}