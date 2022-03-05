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
import com.example.warehouse.adapters.InventoryLedgerAdapter;
import com.example.warehouse.adapters.VendorInventoryLedgerAdapter;
import com.example.warehouse.model.InventoryLedgerModel;
import com.example.warehouse.model.VendorInventoryLedgerModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendorPurchaseHistory extends AppCompatActivity {
    RecyclerView recyclerView;
    JSONObject server_responce;
    VendorInventoryLedgerAdapter adapter;
    List<VendorInventoryLedgerModel> list;
    ProgressDialog progressDialog;
    String vid,date,vnames;
    FloatingActionButton floatingActionButton;
    String user_id;
    UserInfo userInfo;
    TextView datetv,vname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_purchase_history);
        recyclerView =findViewById(R.id.adminledrec);
        datetv =findViewById(R.id.Datetxt);
        vname =findViewById(R.id.ycompl);

        Intent intent =getIntent();
        vid = intent.getStringExtra("vendor_id");
        date = intent.getStringExtra("date");
        vnames = intent.getStringExtra("vname");


        datetv.setText("("+ date +")");
        vname.setText(vnames);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        userInfo = new UserInfo(getApplicationContext());
        user_id = userInfo.getKeyId();
        progressDialog = new ProgressDialog(VendorPurchaseHistory.this);
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        list = new ArrayList<>();
        progressDialog.show();
        loadData();
    }
    private void loadData() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.Vendor_Inventory_ledger,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Responseprodfg", response);

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
                                    String pid = server_responce.getString("product_id");
                                    String date = server_responce.getString("date");
                                    String received_quantity = server_responce.getString("received_quantity");
                                    String product_price = server_responce.getString("product_price");
                                    String sale_price = server_responce.getString("sale_price");
                                    String product_name = server_responce.getString("product_name");
                                    String vendorid = server_responce.getString("vendor_id");


                                    VendorInventoryLedgerModel model = new VendorInventoryLedgerModel(id,date,product_name,sale_price
                                            ,received_quantity,product_price);
                                    list.add(model);


                                }
                            }

                            adapter = new VendorInventoryLedgerAdapter(list, getApplicationContext());

                            recyclerView.setAdapter(adapter);

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
                params.put("date", date);


                return params;


            }


        };
        Singleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }

}