package com.example.grafikastudio.ui.booking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.grafikastudio.DeskripsiBookingAct;
import com.example.grafikastudio.DetailCardFotoAct;
import com.example.grafikastudio.HasilFotoAct;
import com.example.grafikastudio.R;
import com.example.grafikastudio.row_booking;
import com.example.grafikastudio.row_booking_adapter;
import com.example.grafikastudio.row_paket;
import com.example.grafikastudio.row_paket2;
import com.example.grafikastudio.row_paket_adapter2;
import com.example.grafikastudio.row_paket_adapterproduk;
import com.example.grafikastudio.row_paketproduk;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class BookingFragment extends Fragment {

    RecyclerView recyclebooking;
    LinearLayout empty_states;
    ArrayList<row_booking> listbooking;
    row_booking_adapter adapter;

    DatabaseReference reference,reference1;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_booking, container, false);
        getUsernameLocal();


        recyclebooking=root.findViewById(R.id.recyclebooking);
        empty_states=root.findViewById(R.id.empty_states);
        recyclebooking.setLayoutManager(new LinearLayoutManager(getContext()));
        listbooking = new ArrayList<row_booking>();

        reference1= FirebaseDatabase.getInstance().getReference().child("Booking").child(username_key_new).child("detailbooking");
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    empty_states.setVisibility(View.GONE);
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        row_booking p = dataSnapshot1.getValue(row_booking.class);
                        listbooking.add(p);


                    }
                    adapter = new row_booking_adapter(getContext(), listbooking);
                    recyclebooking.setAdapter(adapter);
                }else {
                    recyclebooking.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"Database Error !!" + databaseError ,Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
