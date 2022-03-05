package com.example.warehouse.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse.R;
import com.example.warehouse.model.InventoryLedgerModel;
import com.example.warehouse.model.VendorInventoryLedgerModel;

import java.util.List;

public class VendorInventoryLedgerAdapter extends RecyclerView.Adapter<VendorInventoryLedgerAdapter.myviewholder>{
    List<VendorInventoryLedgerModel> list;
    Context context;

    public VendorInventoryLedgerAdapter(List<VendorInventoryLedgerModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_purchase_history, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        VendorInventoryLedgerModel model = list.get(position);

        holder.name.setText(model.getProduct_name());
        holder.saleprice.setText(model.getSale_price());
        holder.recqty.setText(model.getReceived_quantity());
        holder.itemprice.setText(model.getProduct_price());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView name,saleqty,saleprice,recqty,orderid,itemprice;
        public myviewholder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.pname);
            saleprice = itemView.findViewById(R.id.saleprice);
            recqty = itemView.findViewById(R.id.recievedqty);
            itemprice = itemView.findViewById(R.id.itemprice);


        }
    }
}
