package com.example.grafikastudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderTrackingAct extends AppCompatActivity {
    ImageView img_booking,img_editing,img_cetak,img_selesai,btn_back;
    Button btn_lihatfoto;
    TextView status;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);
        getUsernameLocal();

        btn_back=findViewById(R.id.btn_back);
        status = findViewById(R.id.status);

        img_booking=findViewById(R.id.img_booking);
        img_editing=findViewById(R.id.img_editing);
        img_cetak=findViewById(R.id.img_cetak);
        img_selesai=findViewById(R.id.img_selesai);
        btn_lihatfoto=findViewById(R.id.btn_lihat);
        Bundle bundle = getIntent().getExtras();
        String kode_transaksi = bundle.getString("kode_transaksi");
        btn_lihatfoto.setVisibility(View.GONE);



        reference = FirebaseDatabase.getInstance().getReference().child("proses").child(kode_transaksi);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                String proses = datasnapshot.child("status").getValue().toString();
                if (proses.equals("Tahap 1") || proses.equals("Tahap 2") || proses.equals("Tahap 3")){
                    img_editing.setColorFilter(getResources().getColor(R.color.abui));
                    status.setText("Editing");
                }else if (proses.equals("Transaksi Baru") || proses.equals("Selesai Foto") ){
                    img_booking.setColorFilter(getResources().getColor(R.color.abui));
                    status.setText("Booking");
                }else if (proses.equals("Proses Cetak") ){
                    img_cetak.setColorFilter(getResources().getColor(R.color.abui));
                    status.setText("Pencetakan");
                }else if (proses.equals("Selesai")){
                    img_selesai.setColorFilter(getResources().getColor(R.color.abui));
                    btn_lihatfoto.setVisibility(View.VISIBLE);
                    status.setText("Selesai");
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        btn_lihatfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(OrderTrackingAct.this, HasilFotoAct.class);
                go.putExtra("kode_transaksi", kode_transaksi);
                startActivity(go);
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