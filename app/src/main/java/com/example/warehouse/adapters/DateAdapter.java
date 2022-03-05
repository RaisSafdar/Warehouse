package com.example.warehouse.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse.R;
import com.example.warehouse.VendorPurchaseHistory;
import com.example.warehouse.WarehouseLedger;
import com.example.warehouse.model.DateModel;

import java.util.List;

public class DateAdapter  extends RecyclerView.Adapter<DateAdapter.myviewholder>{
    List<DateModel> list;
    Context context;

    public DateAdapter(List<DateModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public DateAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_layout,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateAdapter.myviewholder holder, int position) {

        DateModel model = list.get(position);
        holder.date.setText(model.getDate());
        holder.vid = model.getVid();
        holder.vname = model.getVname();

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VendorPurchaseHistory.class);
                intent.putExtra("date",holder.date.getText().toString());
                intent.putExtra("vendor_id",holder.vid);
                intent.putExtra("vname",holder.vname);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView date;
        ConstraintLayout constraintLayout;
        String vid,vname;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.datessstxt);
            constraintLayout = itemView.findViewById(R.id.constratintlayouts);
        }
    }
}
