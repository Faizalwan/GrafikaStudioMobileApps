package com.example.grafikastudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class DeskripsiBookingAct extends AppCompatActivity {

    ImageView btn_back,thumbnail_paket;

    DatabaseReference reference;

    Button btn_reschedule, btn_batalbooking;

    TextView kode_trans,nama_paket, harga_paket, jml_org, nama_lengkap, no_wa, email, akun_ig, alamat_rumah;
    TextView extras, tgl_booking, jam_booking;
    ArrayList<String> deskripsi;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deskripsi_booking);
        getUsernameLocal();

        kode_trans = findViewById(R.id.kode_transaksi);
        thumbnail_paket = findViewById(R.id.thumbnail_paket);
        nama_paket = findViewById(R.id.nama_paket);
        harga_paket = findViewById(R.id.harga_paket);
        jml_org = findViewById(R.id.jml_org);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        no_wa = findViewById(R.id.no_wa);
        email = findViewById(R.id.email);
        akun_ig = findViewById(R.id.akun_ig);
        alamat_rumah = findViewById(R.id.alamat_rumah);
        extras = findViewById(R.id.extras);
        tgl_booking = findViewById(R.id.tgl_booking);
        jam_booking = findViewById(R.id.jam_booking);
        btn_reschedule = findViewById(R.id.btn_reschedule);
        btn_batalbooking = findViewById(R.id.btn_batalbooking);


        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        String kode_transaksi = bundle.getString("kode_transaksi");

        //mengambil data dari firebase berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Booking").child(username_key_new).child("detailbooking").child(kode_transaksi);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                //menimpa data yang ada dengan data yg baru
                Picasso.get().load(datasnapshot.child("url_pic").getValue().toString()).centerCrop().fit().into(thumbnail_paket);
                kode_trans.setText(datasnapshot.child("kode_transaksi").getValue().toString());
                nama_paket.setText(datasnapshot.child("nama_paket").getValue().toString());
                String harga = datasnapshot.child("harga").getValue().toString();
                harga_paket.setText(currencyFormat(harga));
                jml_org.setText(datasnapshot.child("jml_orang").getValue().toString());
                nama_lengkap.setText(datasnapshot.child("nama_lengkap").getValue().toString());
                no_wa.setText(datasnapshot.child("no_wa").getValue().toString());
                email.setText(datasnapshot.child("email").getValue().toString());
                akun_ig.setText(datasnapshot.child("akun_ig").getValue().toString());
                alamat_rumah.setText(datasnapshot.child("alamat_rumah").getValue().toString());
                extras.setText(datasnapshot.child("extras").getValue().toString());
                tgl_booking.setText(datasnapshot.child("tgl_booking").getValue().toString());
                jam_booking.setText(datasnapshot.child("jam_booking").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        btn_reschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goform = new Intent(DeskripsiBookingAct.this, FormBookingAct.class);
                goform.putExtra("kode", kode_transaksi);
                goform.putExtra("aksi", "reschedule");
                startActivity(goform);
            }
        });

        btn_batalbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference().child("Booking").child(username_key_new).child("detailbooking").child(kode_transaksi);
                reference.removeValue();
                Toast.makeText(getApplicationContext(), "Booking Berhasil Dibatalkan", Toast.LENGTH_SHORT).show();
                Intent gosplash = new Intent(DeskripsiBookingAct.this, SplashAct.class);
                startActivity(gosplash);
            }
        });

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gohome = new Intent(DeskripsiBookingAct.this, MainActivity.class);
                startActivity(gohome);
            }
        });

    }

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(Double.parseDouble(amount));
    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}