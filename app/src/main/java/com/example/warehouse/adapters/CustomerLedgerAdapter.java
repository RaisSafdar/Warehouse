package com.example.warehouse.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse.R;
import com.example.warehouse.model.CustomerLedgerModel;

import java.util.List;

public class CustomerLedgerAdapter extends RecyclerView.Adapter<CustomerLedgerAdapter.myviewholder>{
    List<CustomerLedgerModel> list;
    Context context;

    public CustomerLedgerAdapter(List<CustomerLedgerModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_ledger_card, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        CustomerLedgerModel model = list.get(position);
        holder.date.setText(model.getDate());
        holder.details.setText(model.getOrderid());
        holder.credit.setText(model.getCredit());
        holder.debit.setText(model.getDebit());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView id,date,details,credit,debit;
        public myviewholder(@NonNull View itemView) {
                super(itemView);
                date = itemView.findViewById(R.id.datetxt);
                details = itemView.findViewById(R.id.detailstxt);
                credit = itemView.findViewById(R.id.credittxt);
                debit = itemView.findViewById(R.id.debittxt);

        }
    }
}
