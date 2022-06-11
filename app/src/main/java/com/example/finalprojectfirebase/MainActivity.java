package com.example.finalprojectfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {
    boolean isAnyUserHere;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = getIntent();
        isAnyUserHere = intent.getBooleanExtra("LoginState", false);
        Log.d("LoginState", String.valueOf(isAnyUserHere));
        SignupFragment SF = new SignupFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.myfrag, SF);
        if (isAnyUserHere) {
            Intent goToStoreIntent = new Intent(MainActivity.this, Storeuser.class);
            startActivity(goToStoreIntent);
        } else {
            ft.replace(R.id.myfrag, SF);
        }
        ft.commit();
    }
}