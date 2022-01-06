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

public class row_notif_adapter extends RecyclerView.Adapter<row_notif_adapter.MyViewHolder>{

    Context context;
    ArrayList<row_notif> row_notifs;

    public row_notif_adapter(Context c, ArrayList<row_notif> p){
        context =  c;
        row_notifs =  p;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_notification, parent, false));

    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.title_notif.setText(row_notifs.get(position).getTitle());
        holder.text_notif.setText(row_notifs.get(position).getText());
    }
    @Override
    public int getItemCount() {
        return row_notifs.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{


        TextView title_notif,text_notif;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_notif=itemView.findViewById(R.id.title_notif);
            text_notif=itemView.findViewById(R.id.text_notif);
        }
    }
}