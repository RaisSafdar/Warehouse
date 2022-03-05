package com.example.warehouse.adapters;

import android.app.AlertDialog;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse.InventoryActivity;
import com.example.warehouse.R;
import com.example.warehouse.VendorLedger;
import com.example.warehouse.VendorPurchaseHistory;
import com.example.warehouse.model.VendorsModel;

import java.util.List;

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.myviewholder>{
    List<VendorsModel> list;
    Dialog builder;

    public VendorAdapter(List<VendorsModel> list, Context context, Dialog builder) {
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

                opendialog(holder.vendor_id,holder.textView.getText().toString());

            }
        });


    }

    private void opendialog(String vid,String vname) {

       // Toast.makeText(context, "dialog open", Toast.LENGTH_SHORT).show();

        builder.setContentView(R.layout.dialog_layout);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView ledger = builder.findViewById(R.id.ledger);
        ImageView inventory = builder.findViewById(R.id.inventary);
        ImageView close = builder.findViewById(R.id.closeimage);


        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InventoryActivity.class);
                intent.putExtra("vendor_id",vid);
              intent.putExtra("vendor_name",vname);

                context.startActivity(intent);
            }
        });

        ledger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VendorLedger.class);
                intent.putExtra("vendor_id",vid);
                intent.putExtra("vendor_name",vname);

                context.startActivity(intent);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        builder.show();
    };


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
