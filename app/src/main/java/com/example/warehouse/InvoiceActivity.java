package com.example.warehouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.warehouse.adapters.DateAdapter;
import com.example.warehouse.adapters.DateAdapter2;
import com.example.warehouse.model.DateModel;
import com.example.warehouse.model.DateModel2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    JSONObject server_responce;
    DateAdapter2 adapter;
    List<DateModel2> list;
    ProgressDialog progressDialog;
    String user_id,vid,vname;
    UserInfo userInfo;
    TextView vendorname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        vendorname = findViewById(R.id.protv);
        recyclerView = findViewById(R.id.vendordaterec);

        Intent intent = getIntent();
        vid = intent.getStringExtra("vendor_id");
        vname = intent.getStringExtra("vendor_name");
        vendorname.setText(vname);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        userInfo = new UserInfo(getApplicationContext());
        user_id = userInfo.getKeyId();

        progressDialog = new ProgressDialog(InvoiceActivity.this);
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        list = new ArrayList<>();
        progressDialog.show();
        loadData();
    }
    private void loadData() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.Warehouse_ledger_date,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Responseprod", response);

                        try {
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray jsonArray = obj.getJSONArray("pkwholesales");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                server_responce = jsonArray.getJSONObject(i);


                                String error = server_responce.getString("error");

                                //Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();

                                if (error.equals("true")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "No Result Found", Toast.LENGTH_SHORT).show();
                                } else {

                                    progressDialog.dismiss();
                                    String id = server_responce.getString("id");
                                    String vendorid = server_responce.getString("vendor_id");
                                    String date = server_responce.getString("date");
                                    String vname = server_responce.getString("vname");
                                    String status = server_responce.getString("status");


                                    DateModel2 model = new DateModel2(id,vendorid ,date, vname,status);
                                    list.add(model);


                                }
                            }

                            adapter = new DateAdapter2(list, getApplicationContext());

                            recyclerView.setAdapter(adapter);
                            if (recyclerView.getAdapter().getItemCount()==0){
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "No Records Available", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                progressDialog.dismiss();

                            }

                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();


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

                UserInfo info = new UserInfo(getApplicationContext());


                params.put("vender_id", vid);


                return params;


            }


        };
        Singleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }

}