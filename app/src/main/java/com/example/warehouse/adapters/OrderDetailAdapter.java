package com.example.warehouse.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.warehouse.QuantityViewModel;
import com.example.warehouse.R;
import com.example.warehouse.Singleton;
import com.example.warehouse.UserInfo;
import com.example.warehouse.Utils;
import com.example.warehouse.VendorNames;
import com.example.warehouse.model.OrderDetailModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.myviewholder>{
    List<OrderDetailModel> list;
    Context context;
    TextView textView,textView1;
    int all_total=0;
    String vid,s1;
    QuantityViewModel viewModel;
    AlertDialog.Builder builder;
    FragmentActivity activity;





    public OrderDetailAdapter(List<OrderDetailModel> list, Context context, TextView textView,
                              FragmentActivity activity, AlertDialog.Builder builder, String vid) {
        this.list = list;
        this.context = context;
        this.textView = textView;
        this.builder = builder;
        this.vid = vid;
        this.activity = activity;

        viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(QuantityViewModel.class);

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitems,parent,false);

        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

      //  QuantityViewModel viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(QuantityViewModel.class);
        OrderDetailModel orderDetailModel = list.get(position);
        holder.image = orderDetailModel.getProduct_image().toString();

            Glide.with(context)
                    .load(holder.image).placeholder(R.drawable.ic_baseline_image24_foreground)
                    .into(holder.pimage);

        holder.name.setText(orderDetailModel.getProduct_name());
        holder.price.setText(orderDetailModel.getPurchase_price());

        holder.qnty.setText(orderDetailModel.getQuantity());
        holder.quantity.setText(orderDetailModel.getQuantity());
        holder.total= Integer.parseInt(orderDetailModel.getTotal());
        if (!orderDetailModel.getStatus().equals("Pending")){
            holder.dec.setVisibility(View.GONE);
        }else
        {
            holder.dec.setVisibility(View.VISIBLE);
        }
        holder.dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.pid = orderDetailModel.getId();

                final EditText edittext = new EditText(context);
                edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setMessage("Enter Your Quantity");

                builder.setTitle("Quantity");
                builder.setView(edittext);
                edittext.setText(holder.qnty.getText().toString());

                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        if (TextUtils.isEmpty(edittext.getText().toString())){
                            edittext.setError("Please Enter Quantity");
                            // Toast.makeText(context, "Please Enter Quantity", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int q1 = Integer.parseInt(holder.qnty.getText().toString());
                        int q2 = Integer.parseInt(edittext.getText().toString());

                        if (q2 <= 0) {
                            holder.qnty.setText("0");
                            orderDetailModel.setQuantity("0");
                            notifyDataSetChanged();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {


                                    StringRequest stringRequest = new StringRequest(Request.Method.POST,


                                            Utils.inc_dec, new Response.Listener<String>() {


                                        @Override
                                        public void onResponse(String response) {
                                            Log.d("testerror", response);
                                            try {
                                                JSONObject jObj = new JSONObject(response);
                                                boolean error = jObj.getBoolean("error");
                                                String error_msg = jObj.getString("msg");


                                                if (!error) {
                                                    viewModel.loadData(context, orderDetailModel.getOrder_id(),vid);



                                                } else {

                                                    Toast.makeText(context, "Quantity Decreasing Failed", Toast.LENGTH_SHORT).show();

                                                }
                                            } catch (JSONException e) {
                                                // JSON error
                                                e.printStackTrace();


                                                Toast.makeText(context, "Internet Error", Toast.LENGTH_SHORT).show();
                                            }


                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(context, "Internet Error", Toast.LENGTH_SHORT).show();
                                            //Log.e(TAG, "Login Error: " + error.getMessage());


                                        }
                                    }) {

                                        @Override
                                        protected Map<String, String> getParams() {


                                            // Posting parameters to login url


                                            Map<String, String> params = new HashMap<>();

                                            params.put("order_id", orderDetailModel.getOrder_id());
                                            params.put("product_quantity", "0");
                                            params.put("product_id", holder.pid);


                                            return params;


                                        }

                                    };

                                    // Adding request to request queue
                                    Singleton.getInstance(context).addToRequestQueue(stringRequest);
                                }
                            }).start();
                        } else {


                            if (Integer.parseInt(edittext.getText().toString())<q1) {


                                orderDetailModel.setQuantity(String.valueOf(q2));
                                holder.qnty.setText(String.valueOf(q2));
                                notifyDataSetChanged();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {


                                        StringRequest stringRequest = new StringRequest(Request.Method.POST,


                                                Utils.inc_dec, new Response.Listener<String>() {


                                            @Override
                                            public void onResponse(String response) {
                                                Log.d("testerror", response);
                                                try {
                                                    JSONObject jObj = new JSONObject(response);
                                                    boolean error = jObj.getBoolean("error");
                                                    String error_msg = jObj.getString("msg");


                                                    if (!error) {
                                                       viewModel.loadData(context, orderDetailModel.getOrder_id(),vid);

                                            //  Toast.makeText(context, holder.qnty.getText().toString(), Toast.LENGTH_LONG).show();


                                                    } else {

                                                        Toast.makeText(context, "Quantity Decreasing Failed", Toast.LENGTH_SHORT).show();

                                                    }
                                                } catch (JSONException e) {
                                                    // JSON error
                                                    e.printStackTrace();


                                                    Toast.makeText(context, "Internet Error", Toast.LENGTH_SHORT).show();
                                                }


                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(context, "Internet Error", Toast.LENGTH_SHORT).show();
                                                //Log.e(TAG, "Login Error: " + error.getMessage());


                                            }
                                        }) {

                                            @Override
                                            protected Map<String, String> getParams() {


                                                // Posting parameters to login url


                                                Map<String, String> params = new HashMap<>();

                                                params.put("order_id", orderDetailModel.getOrder_id());
                                                params.put("product_quantity", holder.qnty.getText().toString());
                                                params.put("product_id", holder.pid);


                                                return params;


                                            }

                                        };

                                        // Adding request to request queue
                                        Singleton.getInstance(context).addToRequestQueue(stringRequest);

                                    }
                                }).start();
                            } else {
                                Toast.makeText(context, "Value is Greater Than Original Quantity", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();




            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class myviewholder extends RecyclerView.ViewHolder {
        TextView qnty;
        TextView name, quantity,price;
        ImageView pimage;
        String image,increment,completeNewVersion,pid;
        ImageButton dec;
        int total;
        public myviewholder(View view) {
            super(view);
            qnty = itemView.findViewById(R.id.qntttty);
            name = itemView.findViewById(R.id.cartitemname);
            quantity = itemView.findViewById(R.id.cartitemqauntity);
            price = itemView.findViewById(R.id.cartitemprice);
            pimage = itemView.findViewById(R.id.cartitemimage);

            dec = itemView.findViewById(R.id.decrease);
        }
    }
}
