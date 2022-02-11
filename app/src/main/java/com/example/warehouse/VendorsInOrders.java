package com.example.warehouse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class VendorsInOrders extends AppCompatActivity {
RecyclerView recyclerView;
    JSONObject server_responce,server_responce1;
    OrderVendorAdapter adapter;
    List<VendorsModel> list;
    UserInfo userInfo;
    String user_id,order_id,status,vendoritems;
    ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendors_in_orders);

        userInfo = new UserInfo(this);
        user_id = userInfo.getKeyId();
        Intent intent = getIntent();
        order_id = intent.getStringExtra("ordrid");
        status = intent.getStringExtra("status");



        recyclerView = findViewById(R.id.vendorecs);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        progressDialog = new ProgressDialog(VendorsInOrders.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        list = new ArrayList<>();


        progressDialog.show();
        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.ordervendor,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Responseorders1212", response);

                        try {
                            JSONObject obj = new JSONObject(response);

                            //we have the array named pkwholesales inside the object
                            //so here we are getting that json array
                            JSONArray jsonArray = obj.getJSONArray("pkwholesales");

                            Log.d("1212", "onResponse: " + jsonArray.length());


                            for (int i = 0; i < jsonArray.length(); i++) {


                                server_responce = jsonArray.getJSONObject(i);


                                String error = server_responce.getString("error");


                                //Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();

                                if (error.equals("true")) {
                                    progressDialog.dismiss();


                                    Toast.makeText(getApplicationContext(), "No Data To Show", Toast.LENGTH_LONG).show();


                                } else {
                                    progressDialog.dismiss();


                                    String vcode = server_responce.getString("vendor_code");
                                    String orderstatus = server_responce.getString("delivery_status");
                                    String Orderss = server_responce.getString("order_id");
                                    String vid = server_responce.getString("vendor_id");
                                     vendoritems = server_responce.getString("totalitems");







                                   // Toast.makeText(getApplicationContext(),vid,Toast.LENGTH_LONG).show();


                                    VendorsModel listData = new VendorsModel(vcode,orderstatus,Orderss,vid,vendoritems);
                                    list.add(listData);
                                }

                            }
                            Collections.reverse(list);
                            adapter = new OrderVendorAdapter(list, getApplicationContext());

                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {

                            e.printStackTrace();
                            Log.d("Error.Response", Objects.requireNonNull(e.getMessage()));


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Internet Issue", Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {


                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("order_id", order_id);
                params.put("warehouse_id", user_id);
                return params;


            }

        };
        Singleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }
}