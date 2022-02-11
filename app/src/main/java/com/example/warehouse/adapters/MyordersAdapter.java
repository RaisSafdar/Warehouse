package com.example.warehouse.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse.R;
import com.example.warehouse.VendorsInOrders;
import com.example.warehouse.VendorsTab;
import com.example.warehouse.model.MyOrdersModel;

import java.util.List;

public class MyordersAdapter extends RecyclerView.Adapter<MyordersAdapter.myviewholder> {
    List<MyOrdersModel> list;
    Context context;

    public MyordersAdapter(List<MyOrdersModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorderscard,parent,false);
        myviewholder viewHolder = new myviewholder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        MyOrdersModel myOrdersModel = list.get(position);
        holder.t1.setText(myOrdersModel.getDate());
        holder.t2.setText(myOrdersModel.getId());
        holder.s1 = myOrdersModel.getDelivery();
        holder.t3.setText(myOrdersModel.getDelivery());
        holder.t4.setText(" "+myOrdersModel.getPercentage()+"%");
        String city = myOrdersModel.getCity();

        if (holder.s1.equals("Pending")) {
            holder.btn.setBackgroundResource(R.drawable.pending);
        }else if (holder.s1.equals("Cancelled")){
            holder.btn.setBackgroundResource(R.drawable.cancel);
        }else if (holder.s1.equals("Delivered")){
            holder.btn.setBackgroundResource(R.drawable.rell);
        }else if (holder.s1.equals("Ready")){
            holder.btn.setBackgroundResource(R.drawable.process);
        }else if (holder.s1.equals("Dispatch")){
            holder.btn.setBackgroundResource(R.drawable.dispatched);
        }
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VendorsTab.class);
                intent.putExtra("ordrid",holder.t2.getText());
                intent.putExtra("status",holder.t3.getText());
                intent.putExtra("city",city);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });




    } @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3,t4;
        RelativeLayout btn;
        String s1,s2;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.date);
            t2 = itemView.findViewById(R.id.oid);
            t3 = itemView.findViewById(R.id.status);
            t4 = itemView.findViewById(R.id.percentage);
            btn = itemView.findViewById(R.id.rl);


        }
    }
}
