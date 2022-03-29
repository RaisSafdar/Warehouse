package com.example.warehouse.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse.DateActivity;
import com.example.warehouse.InventoryActivity;
import com.example.warehouse.R;
import com.example.warehouse.VendorLedger;
import com.example.warehouse.VendorPurchaseHistory;
import com.example.warehouse.WarehouseLedger;
import com.example.warehouse.model.VendorsModel;

import java.util.List;

public class VendorAdapter2 extends RecyclerView.Adapter<VendorAdapter2.myviewholder>{
    List<VendorsModel> list;
    Dialog builder;

    public VendorAdapter2(List<VendorsModel> list, Context context, Dialog builder) {
        this.list = list;
        this.context = context;
        this.builder = builder;
    }

    Context context;
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myvendorsorders,parent,false);
        myviewholder viewHolder = new myviewholder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        VendorsModel myOrdersModel = list.get(position);
        holder.textView.setText(myOrdersModel.getName());
        holder.vendor_id = myOrdersModel.getStorename();
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DateActivity.class);
                intent.putExtra("vendor_id",holder.vendor_id);
                intent.putExtra("vendor_name",holder.textView.getText().toString());

                context.startActivity(intent);

            }
        });


    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        String vendor_id;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.vendorname);
            imageView = itemView.findViewById(R.id.arrowv);
        }
    }
}
