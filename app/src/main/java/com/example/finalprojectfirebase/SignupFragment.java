package com.example.finalprojectfirebase;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignupFragment extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private EditText ed_name, ed_email, ed_pass;
    private Button btn_signup;
    private TextView tv_login;

    public SignupFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View V= inflater.inflate(R.layout.fragment_signup, container, false);
        ed_name = V.findViewById(R.id.ed_name);
        ed_email =V.findViewById(R.id.ed_email);
        ed_pass = V.findViewById(R.id.ed_pass);
        btn_signup = V.findViewById(R.id.btn_signup);
        tv_login = V.findViewById(R.id.tv_login);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();

            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity ma = (MainActivity) getActivity();
                FragmentManager f = ma.getSupportFragmentManager();
                FragmentTransaction ft = f.beginTransaction();
                loginFragment lf = new loginFragment();
                ft.replace(R.id.myfrag, lf);
                ft.commit();
            }
        });
       return V;
    }
    private void signup() {
        String fullName = ed_name.getText().toString();
        String email = ed_email.getText().toString();
        String password = ed_pass.getText().toString();
        if (fullName.isEmpty()) {
            Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty()) {
            Toast.makeText(getActivity(), "email can not be empty", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(getActivity(), "password can not be less than 6 digits", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "created account", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            String uid = user.getUid();
                            HashMap<String, String> data = new HashMap<>();
                            data.put("uid", uid);
                            data.put("fullName", fullName);
                            data.put("email", email);
                            firebaseFirestore.collection("users")
                                    .add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()) {
                                                System.out.println("insert user to Firestore");
                                            } else {
                                                System.out.println("insert failed");
                                            }
                                        }
                                    });

                        }


                        startActivity(new Intent(getActivity(), Storeuser.class));

                    } else {
                        Toast.makeText(getActivity(), "Error while create new account", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

    }
}