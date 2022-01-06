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

public class row_rewardsaya_adapter extends RecyclerView.Adapter<row_rewardsaya_adapter.MyViewHolder>{

    Context context;
    ArrayList<row_rewardsaya> rowrewardsaya;

    public row_rewardsaya_adapter(Context c, ArrayList<row_rewardsaya> p){
        context =  c;
        rowrewardsaya =  p;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_rewardsaya, parent, false));

    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.judul_reward.setText(rowrewardsaya.get(position).getJudul_reward());
        holder.poin_reward.setText(rowrewardsaya.get(position).getPoin_reward()+" poin");
        Picasso.get().load(rowrewardsaya.get(position).getUrl_reward()).fit().centerCrop().into(holder.thumbnail_reward);

        final String kode = rowrewardsaya.get(position).getKode_reward();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(context, DetailRewardAct.class);
                go.putExtra("kode_reward" , kode);
                go.putExtra("petunjuk" , "user");
                context.startActivity(go);
            }
        });



    }
    @Override
    public int getItemCount() {
        return rowrewardsaya.size();
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