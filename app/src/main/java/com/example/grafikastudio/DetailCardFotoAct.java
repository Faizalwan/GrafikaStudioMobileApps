package com.example.grafikastudio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DetailCardFotoAct extends AppCompatActivity {

    ImageView btn_back,thumbnail_paket;
    LinearLayout card_paket;

    TextView title_paket,nama_paket,harga_paket,jml_org;

    DatabaseReference reference,reference1;

    RecyclerView recyclepaket2;
    ArrayList<row_paket2> listpaket;
    row_paket_adapter2 adapterpaket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_card_foto);

        title_paket = findViewById(R.id.title_paket);
        nama_paket = findViewById(R.id.nama_paket);
        harga_paket = findViewById(R.id.harga_paket);
        jml_org = findViewById(R.id.jml_org);

        thumbnail_paket = findViewById(R.id.thumbnail_paket);

        card_paket = findViewById(R.id.card_paket);

        recyclepaket2=findViewById(R.id.recyclepaket2);
        recyclepaket2.setLayoutManager(new LinearLayoutManager(this));
        listpaket = new ArrayList<row_paket2>();
        String url;

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        String kode_katalog = bundle.getString("kode_katalog");
        //Toast.makeText(getApplicationContext(), "Jenis Paket :  "+jenis_paket_baru, Toast.LENGTH_SHORT).show();
        reference = FirebaseDatabase.getInstance().getReference().child("katalog").child(kode_katalog);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                title_paket.setText("Paket "+datasnapshot.child("nama_katalog").getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        reference1= FirebaseDatabase.getInstance().getReference().child("paket");
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                for (DataSnapshot dataSnapshot1: dataSnapshot2.getChildren()){
                    String temp = dataSnapshot1.child("kode_katalog").getValue().toString();
                    if(temp.equals(kode_katalog)) {
                        row_paket2 p = dataSnapshot1.getValue(row_paket2.class);
                        listpaket.add(p);
                    }

                }
                adapterpaket = new row_paket_adapter2(DetailCardFotoAct.this,listpaket);
                recyclepaket2.setAdapter(adapterpaket);
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

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(Double.parseDouble(amount));
    }
}
