package com.example.grafikastudio.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.grafikastudio.EditProfileAct;
import com.example.grafikastudio.ListHasilFoto;
import com.example.grafikastudio.MyNotificationAct;
import com.example.grafikastudio.R;
import com.example.grafikastudio.SplashAct;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    LinearLayout btn_editprofile, btn_hasilfoto;
    FrameLayout btn_notif;
    TextView nama_lengkap,username,badge_counter;
    Button btn_keluar;
    ImageView photo_user;

    DatabaseReference reference,reference1;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        getUsernameLocal();

        btn_notif = root.findViewById(R.id.btn_notif);
        badge_counter = root.findViewById(R.id.badge_counter);
        btn_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gomynotif = new Intent(getActivity(), MyNotificationAct.class);
                startActivity(gomynotif);
            }
        });

        nama_lengkap = root.findViewById(R.id.nama_lengkap);
        username = root.findViewById(R.id.username);
        photo_user = root.findViewById(R.id.photo_user);



        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                nama_lengkap.setText(datasnapshot.child("nama_lengkap").getValue().toString());
                username.setText(datasnapshot.child("username").getValue().toString());
                String url_photo =datasnapshot.child("url_photo_profile").getValue().toString();
                if(url_photo.matches("")){

                }else{
                    Picasso.get().load(datasnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_user);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        reference1 = FirebaseDatabase.getInstance().getReference().child("NotifikasiUser");
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {



                long count = datasnapshot.getChildrenCount();
                if(count==0){
                    badge_counter.setVisibility(View.GONE);
                    btn_notif.setEnabled(false);
                }
                badge_counter.setText(String.valueOf(count));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        btn_editprofile = root.findViewById(R.id.btn_editprofile);
        btn_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goeditprofile = new Intent(getActivity(), EditProfileAct.class);
                startActivity(goeditprofile);
            }
        });

        btn_hasilfoto = root.findViewById(R.id.btn_hasilfoto);
        btn_hasilfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gohasilfoto = new Intent(getActivity(), ListHasilFoto.class);
                startActivity(gohasilfoto);
            }
        });

        btn_keluar = root.findViewById(R.id.btn_keluar);
        btn_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menghapus isi / nilai / value dari username local
                // menyimpan data kepada local storage atau hp
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, null);
                editor.apply();

                Intent gosplash = new Intent(getActivity(), SplashAct.class);
                startActivity(gosplash);
                getActivity().finish();
            }
        });



        return root;
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }

}
