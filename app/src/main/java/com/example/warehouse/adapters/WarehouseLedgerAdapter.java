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
import com.example.warehouse.model.WarehouseLedgerModel;

import java.util.List;

public class WarehouseLedgerAdapter extends RecyclerView.Adapter<WarehouseLedgerAdapter.myviewholder>{
    List<WarehouseLedgerModel> list;
    Context context;

    public WarehouseLedgerAdapter(List<WarehouseLedgerModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.warehouse_ledger_card, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        WarehouseLedgerModel model = list.get(position);

        holder.date.setText(model.getDate());
        holder.name.setText(model.getVendorname());

        holder.saleprice.setText(model.getCredit());
        holder.recqty.setText(model.getDebit());

        holder.orderid.setText(model.getOrderid());




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView date,name,saleprice,recqty,orderid;
        public myviewholder(@NonNull View itemView) {
                super(itemView);

                date = itemView.findViewById(R.id.datetxt);
                name = itemView.findViewById(R.id.vendorname);
            saleprice = itemView.findViewById(R.id.credittxt);
            recqty = itemView.findViewById(R.id.debittxt);
            orderid = itemView.findViewById(R.id.detailstxt);



        }
    }
}
