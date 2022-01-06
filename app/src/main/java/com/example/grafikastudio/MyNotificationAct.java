package com.example.grafikastudio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyNotificationAct extends AppCompatActivity {

    ImageView btn_back;
    TextView title_notif, text_notif;
    RecyclerView recycle_notif;
    ArrayList <row_notif> list;
    row_notif_adapter adapter;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notification);



        btn_back = findViewById(R.id.btn_back);
        recycle_notif = findViewById(R.id.recycle_notif);
        title_notif = findViewById(R.id.title_notif);
        text_notif = findViewById(R.id.text_notif);

        recycle_notif.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<row_notif>();

        reference = FirebaseDatabase.getInstance().getReference().child("NotifikasiUser");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
               for(DataSnapshot dataSnapshot1:datasnapshot.getChildren()){
                   row_notif notif = dataSnapshot1.getValue(row_notif.class);
                   list.add(notif);
               }
               adapter = new row_notif_adapter(MyNotificationAct.this, list);
                recycle_notif.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}