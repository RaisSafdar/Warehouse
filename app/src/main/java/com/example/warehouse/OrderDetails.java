package com.example.warehouse;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.warehouse.adapters.OrderDetailAdapter;
import com.example.warehouse.model.OrderDetailModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetails extends AppCompatActivity {
//    RecyclerView recyclerView;
//    JSONObject server_responce;
//    OrderDetailAdapter adapter;
//    List<OrderDetailModel> list;
//    UserInfo userInfo;
//    AlertDialog.Builder builder;
//    AlertDialog.Builder builder2;
//    String user_id,ordrid,status,vendorid;
//    ProgressDialog progressDialog;
//    TextView Total,ready,city,items,qtty;
//    QuantityViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
//        builder = new AlertDialog.Builder(this);
//        builder2 = new AlertDialog.Builder(this);
//        city = findViewById(R.id.citytv);
//        progressDialog = new ProgressDialog(OrderDetails.this);
//        progressDialog.setMessage("Loading...Please wait");
//        progressDialog.setCanceledOnTouchOutside(false);
//        Intent intent = getIntent();
//        ordrid = intent.getStringExtra("orderid");
//        vendorid = intent.getStringExtra("vendorid");
//        status =intent.getStringExtra("status");
//        recyclerView = findViewById(R.id.rviewmyorders1);
//        items = findViewById(R.id.itemstv);
//        qtty = findViewById(R.id.qttytv);
//        Total = findViewById(R.id.deliverycharges);
//
////        viewModel = new ViewModelProvider(this).get(QuantityViewModel.class);
////        LiveData<Integer> liveData = viewModel.getCounter(this,ordrid);
////        liveData.observe(this, new Observer<Integer>() {
////            @Override
////            public void onChanged(Integer ss) {
////                qtty.setText(String.valueOf(ss));
////             //viewModel.loadData(getApplicationContext(),ordrid);
////                Log.d("sdsdadf", "onChanged: "+ss);
////            }
////        });
////
////        viewModel = new ViewModelProvider(this).get(QuantityViewModel.class);
////        LiveData<String> liveData1 = viewModel.getTotal(this,ordrid);
////        liveData1.observe(this, new Observer<String>() {
////            @Override
////            public void onChanged(String ss) {
////                Total.setText(String.valueOf(ss));
////            }
////        });
//
//        ready = findViewById(R.id.cout);
//        if (status.equals("Pending")){
//            ready.setVisibility(View.VISIBLE);
//        }else {
//            ready.setVisibility(View.GONE);
//        }
//
//        list = new ArrayList<>();
//
//        userInfo = new UserInfo(getApplicationContext());
//        user_id = userInfo.getKeyId();
//
//        recyclerView.hasFixedSize();
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//
//        progressDialog.show();
//        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.OrderDetails,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        Log.d("Response", response);
//
//                        try {
//                            JSONObject obj = new JSONObject(response);
//
//                            //we have the array named pkwholesales inside the object
//                            //so here we are getting that json array
//                            JSONArray jsonArray = obj.getJSONArray("pkwholesales");
//
//                            Log.d("1212", "onResponse: "+jsonArray.length());
//
//
//                            for (int i = 0; i < jsonArray.length(); i++) {
//
//
//                                server_responce = jsonArray.getJSONObject(i);
//
//
//                                Boolean error = server_responce.getBoolean("error");
//
//
//                                //Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
//
//                                if (error) {
//                                    progressDialog.dismiss();
//
//
//
//                                } else {
//                                    progressDialog.dismiss();
//
//
//
//
//                                    String id = server_responce.getString("id");
//                                    String order_id = server_responce.getString("order_id");
//                                    String product_name = server_responce.getString("product_name");
//                                    String product_image = server_responce.getString("product_image");
//                                    String total = server_responce.getString("total");
//                                    Total.setText(total);
//                                    String purchase_price = server_responce.getString("purchase_price");
//                                    String quantity = server_responce.getString("quantity");
//
//                                    String product_images = Utils.PimageUrl + product_image;
//
//
//                                    //Toast.makeText(getActivity(),image,Toast.LENGTH_LONG).show();
//
//
//
//                                    OrderDetailModel listData = new OrderDetailModel(id,order_id,product_name,product_images,total,quantity
//                                    ,purchase_price,status);
//                                    list.add(listData);
//                                }
//
//                            }
//                            adapter=new OrderDetailAdapter(list,getApplicationContext(),
//                                    Total,OrderDetails.this,builder2,"");
//
//                            recyclerView.setAdapter(adapter);
//                            String itemsa = String.valueOf(recyclerView.getAdapter().getItemCount());
//                            items.setText(itemsa);
//
//                        } catch (JSONException e) {
//
//                            e.printStackTrace();
//                            progressDialog.dismiss();
//
//
//                        }
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        progressDialog.dismiss();
//                        Toast.makeText(getApplicationContext(), "Internet issue", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//
//
//                // Posting parameters to login url
//                Map<String, String> params = new HashMap<>();
//                params.put("order_id", ordrid);
//                params.put("warehouse_id", user_id);
//                params.put("vendor_id", vendorid);
//                return params;
//
//
//            }
//
//        };
//        Singleton.getInstance (getApplicationContext()).addToRequestQueue (postRequest );
//
//
//        ready.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                builder.setMessage("Are you Sure?");
//                builder.setTitle("Ready to collect");
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        StringRequest stringRequest=new StringRequest( Request.Method.POST ,
//
//
//                                Utils.OrderReady, new Response.Listener <String> () {
//
//
//                            @Override
//                            public void onResponse(String response) {
//                                Log.d("testerror",response);
//                                try {
//                                    JSONObject jObj = new JSONObject(response);
//                                    boolean error = jObj.getBoolean("error");
//                                    String error_msg = jObj.getString("msg");
//
//
//                                    if (!error) {
//
//                                        progressDialog.hide();
//
//                                        Toast.makeText(getApplicationContext(),error_msg,Toast.LENGTH_SHORT).show();
//                                        Intent intent1 = new Intent(OrderDetails.this,MainActivity.class);
//                                        startActivity(intent1);
//                                        finish();
//
//
//                                    }else {
//
//                                        progressDialog.dismiss();
//                                        Toast.makeText(getApplicationContext(),error_msg,Toast.LENGTH_SHORT).show();
//
//
//                                    }
//
//                                } catch (JSONException e) {
//                                    progressDialog.hide();
//                                    // JSON error
//                                    e.printStackTrace();
//
//
//                                    Toast.makeText (getApplicationContext(),"Internet Error",Toast.LENGTH_SHORT ).show ();
//                                }
//
//
//
//                            }
//                        } , new Response.ErrorListener () {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                progressDialog.hide();
//                                //Log.e(TAG, "Login Error: " + error.getMessage());
//
//
//
//                            }
//                        }) {
//
//                            @Override
//                            protected Map<String, String> getParams() {
//
//
//                                // Posting parameters to login url
//
//
//                                Map<String, String> params = new HashMap<>();
//
//                                params.put("order_id", ordrid);
//                                params.put("warehouse_id", user_id);
//                                String ttl = Total.getText().toString();
//                                params.put("totalrupees", ttl);
//                                Log.d("ttl", "getParams: "+ttl);
//
//
//                                return params;
//
//
//                            }
//
//                        };
//                        Singleton.getInstance( getApplicationContext() ).addToRequestQueue(stringRequest);
//
//                    }
//                });
//                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                    }
//                });
//                builder.show();
//
//
//
//            }
//        });

    }
}