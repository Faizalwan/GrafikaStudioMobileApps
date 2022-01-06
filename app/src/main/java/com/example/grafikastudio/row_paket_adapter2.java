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

public class row_paket_adapter2 extends RecyclerView.Adapter<row_paket_adapter2.MyViewHolder>{

    Context context1;
    ArrayList<row_paket2> rowpaket2;

    public row_paket_adapter2(Context c1, ArrayList<row_paket2> p1){
        context1 =  c1;
        rowpaket2 =  p1;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context1).inflate(R.layout.row_paket2, parent, false));

    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.nama_paket.setText(rowpaket2.get(position).getNama_paket());
        holder.harga.setText(currencyFormat(rowpaket2.get(position).getHarga()));
        holder.jml_orang.setText(rowpaket2.get(position).getJml_orang());
        Picasso.get().load(rowpaket2.get(position).getUrl_pic()).fit().centerCrop().into(holder.thumbnail_paket);

        final String kode = rowpaket2.get(position).getKode_paket();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(context1, DeskripsiPaketAct.class);
                go.putExtra("kode_paket" , kode);
                context1.startActivity(go);
            }
        });



    }
    @Override
    public int getItemCount() {
        return rowpaket2.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nama_paket,harga,jml_orang;
        ImageView thumbnail_paket;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_paket=itemView.findViewById(R.id.nama_paket);
            harga=itemView.findViewById(R.id.harga_paket);
            jml_orang=itemView.findViewById(R.id.jml_org);
            thumbnail_paket=itemView.findViewById(R.id.thumbnail_paket);
        }
    }

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(Double.parseDouble(amount));
    }
}