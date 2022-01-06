package com.example.grafikastudio.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.grafikastudio.DetailCardFotoAct;
import com.example.grafikastudio.EditProfileAct;
import com.example.grafikastudio.R;
import com.example.grafikastudio.row_paket;
import com.example.grafikastudio.row_paket_adapterpopuler;
import com.example.grafikastudio.row_paket_adapterproduk;
import com.example.grafikastudio.row_paketpopuler;
import com.example.grafikastudio.row_paketproduk;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.rivan.dprd.recycleview.row_paket_adapter;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    ImageSlider imageSlider;
    LinearLayout paket_funtime,
            paket_foto_makanan,paket_fashion,paket_creative_desk,
            paket_populer1,paket_populer2,paket_populer3,paket_populer4;


    ImageView photo_home_user;
    TextView user_poin, nama_lengkap;

    RecyclerView recyclepaket;
    ArrayList<row_paket> listpaket;
    row_paket_adapter adapterpaket;

    RecyclerView recyclepaketproduk;
    ArrayList<row_paketproduk> listpaketproduk;
    row_paket_adapterproduk adapterpaketproduk;

    RecyclerView recyclepaketpopuler;
    ArrayList<row_paketpopuler> listpaketpopuler;
    row_paket_adapterpopuler adapterpaketpopuler;
    ArrayList<SlideModel> images = new ArrayList<>();


    DatabaseReference reference,reference1,reference2,reference3;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    String url;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        getUsernameLocal();

        imageSlider = root.findViewById(R.id.image_slider);

        reference = FirebaseDatabase.getInstance().getReference().child("banner");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    url = snapshot.child("url").getValue().toString();
                    images.add(new SlideModel( url, null));
                    imageSlider.setImageList(images, ScaleTypes.CENTER_CROP);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        photo_home_user = root.findViewById(R.id.photo_home_user);
        nama_lengkap = root.findViewById(R.id.nama_lengkap);
        user_poin = root.findViewById(R.id.user_poin);

        recyclepaket=root.findViewById(R.id.recyclepaket);
        recyclepaket.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        listpaket = new ArrayList<row_paket>();

        recyclepaketproduk=root.findViewById(R.id.recyclepaketproduk);
        recyclepaketproduk.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        listpaketproduk = new ArrayList<row_paketproduk>();

        recyclepaketpopuler=root.findViewById(R.id.recyclepaketpopuler);
        recyclepaketpopuler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        listpaketpopuler = new ArrayList<row_paketpopuler>();

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                nama_lengkap.setText(datasnapshot.child("nama_lengkap").getValue().toString());
                user_poin.setText(datasnapshot.child("user_poin").getValue().toString()+" poin");
                String url_photo =datasnapshot.child("url_photo_profile").getValue().toString();
                if(url_photo.matches("")){

                }else{
                    Picasso.get().load(datasnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_home_user);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        reference1= FirebaseDatabase.getInstance().getReference().child("katalog");
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                        String temp = dataSnapshot1.child("kategori").getValue().toString();
                        if(temp.equals("Foto Studio")){
                            row_paket p = dataSnapshot1.getValue(row_paket.class);
                            listpaket.add(p);
                        }
                }
                adapterpaket = new row_paket_adapter(getContext(),listpaket);
                recyclepaket.setAdapter(adapterpaket);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"Database Error !!" + databaseError ,Toast.LENGTH_SHORT).show();
            }
        });

        reference2= FirebaseDatabase.getInstance().getReference().child("katalog");
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    String temp = dataSnapshot1.child("kategori").getValue().toString();
                    if(temp.equals("Foto Produk")){
                        row_paketproduk p1 = dataSnapshot1.getValue(row_paketproduk.class);
                        listpaketproduk.add(p1);
                    }

                }
                adapterpaketproduk = new row_paket_adapterproduk(getContext(),listpaketproduk);
                recyclepaketproduk.setAdapter(adapterpaketproduk);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"Database Error !!" + databaseError ,Toast.LENGTH_SHORT).show();
            }
        });

        reference3= FirebaseDatabase.getInstance().getReference().child("populer");
        reference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    row_paketpopuler p1 = dataSnapshot1.getValue(row_paketpopuler.class);
                    listpaketpopuler.add(p1);
                }
                adapterpaketpopuler = new row_paket_adapterpopuler(getContext(),listpaketpopuler);
                recyclepaketpopuler.setAdapter(adapterpaketpopuler);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"Database Error !!" + databaseError ,Toast.LENGTH_SHORT).show();
            }
        });

        photo_home_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goprofile = new Intent(getActivity(), EditProfileAct.class);
                startActivity(goprofile);
            }
        });

        //kirim id paket


        return root;
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }

    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

}
