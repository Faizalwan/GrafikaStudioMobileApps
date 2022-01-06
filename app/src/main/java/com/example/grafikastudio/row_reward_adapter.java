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

public class row_reward_adapter extends RecyclerView.Adapter<row_reward_adapter.MyViewHolder>{

    Context context;
    ArrayList<row_reward> rowreward;

    public row_reward_adapter(Context c, ArrayList<row_reward> p){
        context =  c;
        rowreward =  p;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_reward, parent, false));

    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.judul_reward.setText(rowreward.get(position).getJudul_reward());
        holder.poin_reward.setText(rowreward.get(position).getPoin_reward()+" poin");
        Picasso.get().load(rowreward.get(position).getUrl_reward()).fit().centerCrop().into(holder.thumbnail_reward);

        final String kode = rowreward.get(position).getKode_reward();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(context, DetailRewardAct.class);
                go.putExtra("kode_reward" , kode);
                go.putExtra("petunjuk" , "cek");
                context.startActivity(go);
            }
        });



    }
    @Override
    public int getItemCount() {
        return rowreward.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView judul_reward,poin_reward;
        ImageView thumbnail_reward;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            judul_reward=itemView.findViewById(R.id.judul_reward);
            poin_reward=itemView.findViewById(R.id.poin_reward);
            thumbnail_reward=itemView.findViewById(R.id.thumbnail_reward);
        }
    }
}