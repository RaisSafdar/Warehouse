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
import com.example.warehouse.adapters.CustomerLedgerAdapter;
import com.example.warehouse.model.CustomerLedgerModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendorLedger extends AppCompatActivity {
    RecyclerView recyclerView;
    JSONObject server_responce;
    CustomerLedgerAdapter adapter;
    List<CustomerLedgerModel> list;
    ProgressDialog progressDialog;
    String vid;
    FloatingActionButton floatingActionButton;
    String user_id;
    UserInfo userInfo;
    TextView tcredit, tdebit, nbalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_ledger);
        recyclerView = findViewById(R.id.adminledrec);
        tcredit = findViewById(R.id.totalcredit);
        tdebit = findViewById(R.id.totaldebit);
        nbalance = findViewById(R.id.netbalance);

        Intent intent = getIntent();
        vid = intent.getStringExtra("vendor_id");
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        userInfo = new UserInfo(getApplicationContext());
        user_id = userInfo.getKeyId();
        progressDialog = new ProgressDialog(VendorLedger.this);
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        list = new ArrayList<>();
        progressDialog.show();
        loadData();

    }
    private void loadData() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.vendor_ledger,
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
                                    int x = jsonArray.length() - 1;

                                    if (i == x) {

                                        int totalcredit = server_responce.getInt("total_credit");

                                        int totaldebit = server_responce.getInt("total_debit");
                                        int netbalance = server_responce.getInt("net_balance");
                                        tcredit.setText(String.valueOf(totalcredit));
                                        tdebit.setText(String.valueOf(totaldebit));
                                        nbalance.setText(String.valueOf(netbalance));
                                        //  Toast.makeText(getActivity(), "abc"+total, Toast.LENGTH_SHORT).show();

                                    }
                                    progressDialog.dismiss();
                                    String id = server_responce.getString("id");
                                    String date = server_responce.getString("date");
                                    String debit = server_responce.getString("total_payment");
                                    String credit = server_responce.getString("payment_received");
                                    String orderid = server_responce.getString("order_id");
                                    String vendorid = server_responce.getString("vendor_id");


                                    CustomerLedgerModel model = new CustomerLedgerModel(id, date, orderid, credit, debit
                                            , vendorid);
                                    list.add(model);


                                }
                            }

                            adapter = new CustomerLedgerAdapter(list, getApplicationContext());

                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            progressDialog.dismiss();


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Internet issue", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() {


                // Posting parameters to login url


                Map<String, String> params = new HashMap<>();

                UserInfo info = new UserInfo(getApplicationContext());


                params.put("vendor_id", vid);


                return params;


            }


        };
        Singleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest);


    }

}