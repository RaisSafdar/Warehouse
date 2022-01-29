package com.example.warehouse.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.warehouse.R;
import com.example.warehouse.model.FulfilProductModel;

import java.util.List;

public class Fulfil_Adapter extends RecyclerView.Adapter<Fulfil_Adapter.myviewholder> {
    List<FulfilProductModel> list;
    Context context;

    public Fulfil_Adapter(List<FulfilProductModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fulfil_product_card,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        FulfilProductModel deliveryModel = list.get(position);
        Glide.with(context).load(deliveryModel.getImage()).placeholder(R.drawable.ic_baseline_image_24).into(holder.imageView);
        holder.qtty.setText(deliveryModel.getQty());
        holder.pname.setText(deliveryModel.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView pname,qtty;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemcardimgfulfil);
            pname = itemView.findViewById(R.id.itemcarditemnamefulfil);
            qtty = itemView.findViewById(R.id.itemcarditemqttyfil);
        }
    }
}

