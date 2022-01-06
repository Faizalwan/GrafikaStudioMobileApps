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

import java.text.DecimalFormat;
import java.util.ArrayList;

public class row_booking_adapter extends RecyclerView.Adapter<row_booking_adapter.MyViewHolder>{

    Context context1;
    ArrayList<row_booking> rowboking;

    public row_booking_adapter(Context c1, ArrayList<row_booking> p){
        context1 =  c1;
        rowboking =  p;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context1).inflate(R.layout.row_booking, parent, false));

    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.nama_paket.setText(rowboking.get(position).getNama_paket());
        holder.harga.setText("Rp. "+currencyFormat(rowboking.get(position).getHarga()));
        holder.jml_orang.setText(rowboking.get(position).getJml_orang());
        holder.kode_transaksi.setText(rowboking.get(position).getKode_transaksi());
        Picasso.get().load(rowboking.get(position).getUrl_pic()).fit().centerCrop().into(holder.thumbnail_paket);

        final String kode = rowboking.get(position).getKode_transaksi();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(context1, DeskripsiBookingAct.class);
                go.putExtra("kode_transaksi" , kode);
                context1.startActivity(go);
            }
        });



    }
    @Override
    public int getItemCount() {
        return rowboking.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nama_paket,harga,jml_orang,kode_transaksi;
        ImageView thumbnail_paket;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_paket=itemView.findViewById(R.id.nama_paket);
            harga=itemView.findViewById(R.id.harga_paket);
            jml_orang=itemView.findViewById(R.id.jml_org);
            thumbnail_paket=itemView.findViewById(R.id.thumbnail_paket);
            kode_transaksi=itemView.findViewById(R.id.kode_transaksi);
        }
    }

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(Double.parseDouble(amount));
    }
}