package com.example.warehouse;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderVendorAdapter extends RecyclerView.Adapter<OrderVendorAdapter.myviewholder>{
    List<VendorsModel> list;

    public OrderVendorAdapter(List<VendorsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    Context context;
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendorscard, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        VendorsModel vendorsModel = list.get(position);
        holder.vendorcode.setText(vendorsModel.getVendorCode());
        holder.orderstatus.setText(vendorsModel.getStatus());
        holder.vendoritems.setText(vendorsModel.getVendoritems());
        holder.vendor = vendorsModel.getVendor_id().toString();
        holder.ostatus = vendorsModel.getStatus();



        if (holder.ostatus.equals("Delivered")){
            holder.relativeLayout.setBackgroundResource(R.drawable.rell);
        }else if (holder.ostatus.equals("Ready")){
            holder.relativeLayout.setBackgroundResource(R.drawable.pending);
        }else if (holder.ostatus.equals("Dispatch")){
            holder.relativeLayout.setBackgroundResource(R.drawable.dispatch);
        }else if (holder.ostatus.equals("Cancelled")){
            holder.relativeLayout.setBackgroundResource(R.drawable.cancelled);
        }else if (holder.ostatus.equals("Pending")){
            holder.relativeLayout.setBackgroundResource(R.drawable.pending);
        }

        holder.vendoritems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,OrderDetails.class);
                intent.putExtra("orderid",vendorsModel.getOrderid());
                intent.putExtra("status",vendorsModel.getStatus());
                intent.putExtra("vendorid",holder.vendor);
                //Toast.makeText(context, ""+holder.vendor, Toast.LENGTH_SHORT).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.vi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,OrderDetails.class);
                intent.putExtra("orderid",vendorsModel.getOrderid());
                intent.putExtra("vendorid",holder.vendor);
                intent.putExtra("status",vendorsModel.getStatus());
                //Toast.makeText(context, ""+holder.vendor, Toast.LENGTH_SHORT).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView vendorcode;
        TextView orderstatus;
        TextView vendoritems,vi2;
        String vendor,ostatus;
        RelativeLayout relativeLayout;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            vendorcode = itemView.findViewById(R.id.vendorcode);
            orderstatus = itemView.findViewById(R.id.orderstatus);
            vendoritems = itemView.findViewById(R.id.vendoritemstxt);
            vi2 = itemView.findViewById(R.id.vendoritems);
            relativeLayout = itemView.findViewById(R.id.rl);
        }
    }
}
