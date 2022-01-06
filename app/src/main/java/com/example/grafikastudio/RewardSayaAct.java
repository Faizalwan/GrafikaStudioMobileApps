package com.example.grafikastudio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.grafikastudio.ui.profile.ProfileFragment;
import com.example.grafikastudio.ui.reward.RewardFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RewardSayaAct extends AppCompatActivity {

    ImageView btn_back;
    RecyclerView recyclerewardsaya;
    ArrayList<row_rewardsaya> listreward;
    row_rewardsaya_adapter adapterreward;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_saya);
        getUsernameLocal();

        recyclerewardsaya=findViewById(R.id.recyclerewardsaya);
        recyclerewardsaya.setLayoutManager(new LinearLayoutManager(this));
        listreward = new ArrayList<row_rewardsaya>();

        reference= FirebaseDatabase.getInstance().getReference().child("RewardUser").child(username_key_new).child("detailreward");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    row_rewardsaya p2 = dataSnapshot1.getValue(row_rewardsaya.class);
                    listreward.add(p2);
                }
                adapterreward = new row_rewardsaya_adapter(RewardSayaAct.this,listreward);
                recyclerewardsaya.setAdapter(adapterreward);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Database Error !!" + databaseError ,Toast.LENGTH_SHORT).show();
            }
        });

        btn_back = findViewById(R.id.btn_back);

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
