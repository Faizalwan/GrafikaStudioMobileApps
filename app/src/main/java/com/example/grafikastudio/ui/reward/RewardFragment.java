package com.example.grafikastudio.ui.reward;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grafikastudio.DetailRewardAct;
import com.example.grafikastudio.EditProfileAct;
import com.example.grafikastudio.R;
import com.example.grafikastudio.RewardSayaAct;
import com.example.grafikastudio.row_paket;
import com.example.grafikastudio.row_reward;
import com.example.grafikastudio.row_reward_adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class RewardFragment extends Fragment {

    LinearLayout reward1;
    Button btn_rewardsaya;

    TextView user_poin;

    RecyclerView recyclereward;
    ArrayList<row_reward> listreward;
    row_reward_adapter adapterreward;

    DatabaseReference reference,reference1;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_reward, container, false);
        getUsernameLocal();


        user_poin = root.findViewById(R.id.user_poin);
        reference1 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                String poin = datasnapshot.child("user_poin").getValue().toString()+" poin";
                user_poin.setText(poin);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        recyclereward=root.findViewById(R.id.recyclereward);
        recyclereward.setLayoutManager(new LinearLayoutManager(getContext()));
        listreward = new ArrayList<row_reward>();


        reference= FirebaseDatabase.getInstance().getReference().child("Reward");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    row_reward p = dataSnapshot1.getValue(row_reward.class);
                    listreward.add(p);
                }
                adapterreward = new row_reward_adapter(getContext(),listreward);
                recyclereward.setAdapter(adapterreward);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"Database Error !!" + databaseError ,Toast.LENGTH_SHORT).show();
            }
        });

        btn_rewardsaya = root.findViewById(R.id.btn_rewardsaya);
        btn_rewardsaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gorewardsaya = new Intent(getActivity(), RewardSayaAct.class);
                startActivity(gorewardsaya);
            }
        });
        return root;
    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
