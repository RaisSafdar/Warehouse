package com.example.warehouse.adapters;

import android.content.Context;
import android.util.Log;
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

public class FulfilAdapterTotal extends RecyclerView.Adapter<FulfilAdapterTotal.myviewholder>{
    List<FulfilProductModel> list;

    public FulfilAdapterTotal(List<FulfilProductModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    Context context;
    @NonNull
    @Override
    public FulfilAdapterTotal.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fulfiil_total_inventory_card,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FulfilAdapterTotal.myviewholder holder, int position) {
        FulfilProductModel deliveryModel = list.get(position);
        Glide.with(context).load(deliveryModel.getImage()).placeholder(R.drawable.ic_baseline_image_24).into(holder.imageView);
        holder.qtty.setText(deliveryModel.getQty());
        holder.pname.setText(deliveryModel.getName());


        holder.qty = Integer.parseInt(deliveryModel.getQty());
        holder.price = Integer.parseInt(deliveryModel.getRate());


        holder.tamount = holder.qty* holder.price;
        holder.rate.setText(deliveryModel.getRate());
        holder.amount.setText(String.valueOf(holder.tamount));

        Log.d("price", "onBindViewHolder: "+holder.qty);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView pname,qtty,rate,amount;
        int qty,price,tamount;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemcardimgfulfil);
            pname = itemView.findViewById(R.id.itemcarditemnamefulfil);
            qtty = itemView.findViewById(R.id.itemcarditemqttyfil);
            rate = itemView.findViewById(R.id.rate);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
