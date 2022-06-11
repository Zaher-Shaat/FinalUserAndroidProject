package com.example.finalprojectfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Storeuser extends AppCompatActivity {
    private RecyclerView store_clothes;

    ArrayList<item> items = new ArrayList<>();
    private static final String TAG = "store";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeuser);
        store_clothes = findViewById(R.id.store_clothes);
        store_clothes.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        store_clothes.setLayoutManager(gridLayoutManager);
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
                if(task.isSuccessful()){
                    Iterable<DataSnapshot> data = task.getResult().getChildren();
                    for (DataSnapshot snap:data){
                        item i =snap.getValue(item.class);
                        items.add(i);

                    }
                    clothesAdapter adapter=new clothesAdapter(items);
                    store_clothes.setAdapter(adapter);


                }else{
                    String error = task.getException().getMessage();
                    Toast.makeText(Storeuser.this, "failed " +error, Toast.LENGTH_SHORT).show();

                }   }
        });
    }
}