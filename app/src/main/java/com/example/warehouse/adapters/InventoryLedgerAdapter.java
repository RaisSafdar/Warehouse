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

import java.util.List;

public class InventoryLedgerAdapter extends RecyclerView.Adapter<InventoryLedgerAdapter.myviewholder>{
    List<InventoryLedgerModel> list;
    Context context;

    public InventoryLedgerAdapter(List<InventoryLedgerModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ledger_card, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        InventoryLedgerModel model = list.get(position);

        holder.date.setText(model.getDate());
        holder.name.setText(model.getProduct_name());
        holder.saleqty.setText(model.getSale_quantity());
        holder.saleprice.setText(model.getSale_price());
        holder.recqty.setText(model.getReceived_quantity());
        holder.recamt.setText(model.getReceived_amount());
        holder.orderid.setText(model.getOrderid());
        holder.itemprice.setText(model.getProduct_price());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView date,name,saleqty,saleprice,recqty,recamt,orderid,itemprice;
        public myviewholder(@NonNull View itemView) {
                super(itemView);

                date = itemView.findViewById(R.id.datetxt);
                name = itemView.findViewById(R.id.pname);
            saleqty = itemView.findViewById(R.id.saleqty);
            saleprice = itemView.findViewById(R.id.saleprice);
            recqty = itemView.findViewById(R.id.recievedqty);
            recamt = itemView.findViewById(R.id.receivedamount);
            orderid = itemView.findViewById(R.id.orderid);
            itemprice = itemView.findViewById(R.id.itemprice);


        }
    }
}
