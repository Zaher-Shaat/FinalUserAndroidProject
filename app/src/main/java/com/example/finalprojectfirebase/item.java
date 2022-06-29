package com.example.finalprojectfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

public class item implements Serializable {
    private String id;
    private String code;
    private String price;
    private String image;
// malabsi User
    public item() {

    }

    public item(String id, String code, String price, String image) {
        this.id = id;
        this.code = code;
        this.price = price;
        this.image = image;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "item{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", price='" + price + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

}
