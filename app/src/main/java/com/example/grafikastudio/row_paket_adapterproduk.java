package com.example.grafikastudio;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class row_paket_adapterproduk extends RecyclerView.Adapter<row_paket_adapterproduk.MyViewHolder>{

    Context context;
    ArrayList<row_paketproduk> rowpaketproduk;

    public row_paket_adapterproduk(Context c, ArrayList<row_paketproduk> p){
        context =  c;
        rowpaketproduk =  p;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_paketproduk, parent, false));

    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.titlepaket.setText(rowpaketproduk.get(position).getNama_Katalog());
        Picasso.get().load(rowpaketproduk.get(position).getUrl_pic()).fit().centerCrop().into(holder.foto);

        final String kode = rowpaketproduk.get(position).getKode_Katalog();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(context, DetailCardFotoAct.class);
                go.putExtra("kode_katalog" , kode);
                context.startActivity(go);
            }
        });



    }
    @Override
    public int getItemCount() {
        return rowpaketproduk.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titlepaket;
        ImageView foto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titlepaket=itemView.findViewById(R.id.titlepaket);
            foto=itemView.findViewById(R.id.imgpaket);
        }
    }
}