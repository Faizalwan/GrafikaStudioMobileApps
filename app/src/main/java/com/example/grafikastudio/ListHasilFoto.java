package com.example.grafikastudio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListHasilFoto extends AppCompatActivity {

    ImageView btn_back;
    RecyclerView list_hasilfoto;
    ArrayList <row_hasilfoto> rowhasilfoto;
    row_hasilfoto_adapter adapter;

    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hasil_foto);

        getUsernameLocal();

        btn_back = findViewById(R.id.btn_back);
        list_hasilfoto = findViewById(R.id.list_hasilfoto);
        list_hasilfoto.setLayoutManager(new LinearLayoutManager(this));
        rowhasilfoto = new ArrayList<row_hasilfoto>();

        reference= FirebaseDatabase.getInstance().getReference().child("transaksiUser").child(username_key_new).child("detailtransaksi");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    row_hasilfoto p = dataSnapshot1.getValue(row_hasilfoto.class);
                    rowhasilfoto.add(p);

                }
                adapter = new row_hasilfoto_adapter(ListHasilFoto.this, rowhasilfoto);
                list_hasilfoto.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Database Error !!" + databaseError ,Toast.LENGTH_SHORT).show();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}