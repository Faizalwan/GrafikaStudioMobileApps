package com.example.grafikastudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class DeskripsiPaketAct extends AppCompatActivity {

    ImageView btn_back,thumbnail_paket;
    Button btn_booking;

    TextView nama_paket,harga_paket,jml_org,deskripsi_paket;
    ArrayList<String> deskripsi;

    DatabaseReference reference,reference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deskripsi_paket);

        nama_paket = findViewById(R.id.nama_paket);
        harga_paket = findViewById(R.id.harga_paket);
        jml_org = findViewById(R.id.jml_org);
        deskripsi_paket = findViewById(R.id.deskripsi_paket);

        thumbnail_paket = findViewById(R.id.thumbnail_paket);

        btn_back = findViewById(R.id.btn_back);
        btn_booking = findViewById(R.id.btn_booking);

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        String kode_paket = bundle.getString("kode_paket");

        //mengambil data dari firebase berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("paket").child(kode_paket);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                //menimpa data yang ada dengan data yg baru
                nama_paket.setText(datasnapshot.child("nama_paket").getValue().toString());
                String harga = datasnapshot.child("harga").getValue().toString();
                harga_paket.setText(currencyFormat(harga));
                jml_org.setText(datasnapshot.child("jml_orang").getValue().toString());
                deskripsi_paket.setText(datasnapshot.child("deskripsi").getValue().toString());
                Picasso.get().load(datasnapshot.child("url_pic").getValue().toString()).centerCrop().fit().into(thumbnail_paket);

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

        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goform = new Intent(DeskripsiPaketAct.this, FormBookingAct.class);
                goform.putExtra("kode", kode_paket);
                goform.putExtra("aksi", "baru");
                startActivity(goform);
            }
        });

    }

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(Double.parseDouble(amount));
    }
}
