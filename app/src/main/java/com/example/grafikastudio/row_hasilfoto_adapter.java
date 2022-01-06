package com.example.grafikastudio;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class row_hasilfoto_adapter extends RecyclerView.Adapter<row_hasilfoto_adapter.MyViewHolder>{

    Context context;
    ArrayList<row_hasilfoto> rowhasilfoto;

    public row_hasilfoto_adapter(Context c, ArrayList<row_hasilfoto> p){
        context =  c;
        rowhasilfoto =  p;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_hasilfoto, parent, false));

    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.item_hasilfoto.setText(rowhasilfoto.get(position).getKode_transaksi());

        final String kode = rowhasilfoto.get(position).getKode_transaksi();

        holder.item_hasilfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(context, OrderTrackingAct.class);
                go.putExtra("kode_transaksi" , kode);
                context.startActivity(go);
            }
        });



    }
    @Override
    public int getItemCount() {
        return rowhasilfoto.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        Button item_hasilfoto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_hasilfoto=itemView.findViewById(R.id.item_hasilfoto);
        }
    }
}