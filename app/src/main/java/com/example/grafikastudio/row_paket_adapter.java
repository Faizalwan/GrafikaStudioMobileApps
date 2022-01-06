package com.rivan.dprd.recycleview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grafikastudio.DetailCardFotoAct;
import com.example.grafikastudio.R;
import com.example.grafikastudio.row_paket;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class row_paket_adapter extends RecyclerView.Adapter<row_paket_adapter.MyViewHolder>{

    Context context;
    ArrayList<row_paket> rowpaket;

    public row_paket_adapter(Context c, ArrayList<row_paket> p){
        context =  c;
        rowpaket =  p;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_paket, parent, false));

    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.titlepaket.setText(rowpaket.get(position).getNama_Katalog());
        Picasso.get().load(rowpaket.get(position).getUrl_pic()).fit().centerCrop().into(holder.foto);

        final String kode = rowpaket.get(position).getKode_Katalog();

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
        return rowpaket.size();
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