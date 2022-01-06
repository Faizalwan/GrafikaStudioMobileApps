package com.example.grafikastudio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
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

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DetailRewardAct extends AppCompatActivity {

    ImageView btn_back,thumbnail_reward;
    Dialog myDialog;
    Button btn_tukar;

    TextView judul_reward,poin_reward,masa_berlaku;

    DatabaseReference reference,reference1,reference2;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    Integer poin,berlaku;
    String kodereward,judulreward,poinreward,urlreward,masaBerlaku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reward);
        getUsernameLocal();

        btn_back = findViewById(R.id.btn_back);
        myDialog = new Dialog(this);

        judul_reward = findViewById(R.id.judul_reward);
        poin_reward = findViewById(R.id.poin_reward);
        thumbnail_reward = findViewById(R.id.thumbnail_reward);
        masa_berlaku = findViewById(R.id.masa_berlaku);
        btn_tukar = findViewById(R.id.btn_tukar);


        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        String kode_reward = bundle.getString("kode_reward");
        String aksi = bundle.getString("petunjuk");


        //mengambil data dari firebase berdasarkan intent
        if(aksi.equals("cek")) {
            reference = FirebaseDatabase.getInstance().getReference().child("Reward").child(kode_reward);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot datasnapshot) {

                    kodereward = datasnapshot.child("kode_reward").getValue().toString();
                    urlreward = datasnapshot.child("url_reward").getValue().toString();

//                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.US);
//                tglMulai = datasnapshot.child("tgl_mulai").getValue().toString();
//                String dateMulai = sdf.format(Date.valueOf(tglMulai));
//                tgl_mulai.setText(dateMulai);


                    //menimpa data yang ada dengan data yg baru
                    judul_reward.setText(datasnapshot.child("judul_reward").getValue().toString());
                    masa_berlaku.setText(datasnapshot.child("masa_berlaku").getValue().toString() + " Bulan");
                    poin_reward.setText(datasnapshot.child("poin_reward").getValue().toString() + " poin");
                    poin = Integer.valueOf(datasnapshot.child("poin_reward").getValue().toString());
                    berlaku = Integer.valueOf(datasnapshot.child("masa_berlaku").getValue().toString());
                    Picasso.get().load(datasnapshot.child("url_reward").getValue().toString()).centerCrop().fit().into(thumbnail_reward);


                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }else if(aksi.equals("user")){
            reference = FirebaseDatabase.getInstance().getReference().child("RewardUser").child(username_key_new).child("detailreward").child(kode_reward);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot datasnapshot) {

                    kodereward = datasnapshot.child("kode_reward").getValue().toString();
                    urlreward = datasnapshot.child("url_reward").getValue().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.US);
                masaBerlaku = datasnapshot.child("masa_berlaku").getValue().toString();
                    String dateMulai = sdf.format(Date.valueOf(masaBerlaku));


                    //menimpa data yang ada dengan data yg baru
                    judul_reward.setText(datasnapshot.child("judul_reward").getValue().toString());
                    masa_berlaku.setText(dateMulai);
                    poin_reward.setText(datasnapshot.child("poin_reward").getValue().toString() + " poin");
                    Picasso.get().load(datasnapshot.child("url_reward").getValue().toString()).centerCrop().fit().into(thumbnail_reward);

                    btn_tukar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    public void ShowPopUpReward(View view){
        Button btn_lihatreward;
        myDialog.setContentView(R.layout.activity_pop_up_reward);
        btn_lihatreward = (Button) myDialog.findViewById(R.id.btn_lihatreward);

        reference2 = FirebaseDatabase.getInstance().getReference().child("RewardUser").child(username_key_new).child("detailreward").child(kodereward);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                //menimpa data yang ada dengan data yg baru
                if(datasnapshot.exists()){
                    Toast.makeText(getApplicationContext(), " Reward Telah Dimiliki !", Toast.LENGTH_SHORT).show();

                }else {
                    reference1 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot datasnapshot1) {
                            //menimpa data yang ada dengan data yg baru
                            Integer poin_user = Integer.valueOf(datasnapshot1.child("user_poin").getValue().toString());
                            if(poin_user < poin){
                                Toast.makeText(getApplicationContext(), "Poin Tidak Cukup !", Toast.LENGTH_SHORT).show();
                            }else {
                                reference = FirebaseDatabase.getInstance().getReference().child("RewardUser").child(username_key_new);
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot datasnapshot) {
                                        //menimpa data yang ada dengan data yg baru
                                        datasnapshot.getRef().child("username").setValue(username_key_new);
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError error) {

                                    }
                                });
                                poin_user -= poin;
                                Calendar cal = Calendar.getInstance();
                                cal.add(Calendar.MONTH,berlaku);
                                cal.getTime();
                                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.getTime());
                                String tgl_reedem = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new java.util.Date());
                                String jam_reedem = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new java.util.Date());
                                datasnapshot1.getRef().child("user_poin").setValue(poin_user);

                                datasnapshot.getRef().child("kode_reward").setValue(kodereward);
                                datasnapshot.getRef().child("judul_reward").setValue(judul_reward.getText().toString());
                                datasnapshot.getRef().child("poin_reward").setValue(String.valueOf(poin));
                                datasnapshot.getRef().child("url_reward").setValue(urlreward);
                                datasnapshot.getRef().child("masa_berlaku").setValue(date);
                                datasnapshot.getRef().child("tgl_reedem").setValue(tgl_reedem);
                                datasnapshot.getRef().child("jam_reedem").setValue(jam_reedem);

                                btn_lihatreward.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(DetailRewardAct.this, RewardSayaAct.class));
                                        finish();
                                    }
                                });
                                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                myDialog.setCancelable(false);
                                myDialog.show();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }

//
//    public static String dateFormatter(String date) {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
//        String date = formatter.format(Date.parse("Your date string"));
//    }
}
