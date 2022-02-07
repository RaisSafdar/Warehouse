package com.example.warehouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.warehouse.adapters.HistoryAdapter;
import com.example.warehouse.model.HistoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrderHistory extends AppCompatActivity {
    RecyclerView recyclerView;
    JSONObject server_responce;
    HistoryAdapter adapter;
    List<HistoryModel> list;
    ProgressDialog progressDialog;
    UserInfo userInfo;
    String user_id;
    TextView nohistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        recyclerView = findViewById(R.id.historyrec);
        nohistory = findViewById(R.id.nohistory);
        progressDialog = new ProgressDialog(OrderHistory.this);
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        list = new ArrayList<>();
        userInfo = new UserInfo(getApplicationContext());
        user_id = userInfo.getKeyId();

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        progressDialog.show();
        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.history,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Responsehistory", response);

                        try {
                            JSONObject obj = new JSONObject(response);

                            //we have the array named pkwholesales inside the object
                            //so here we are getting that json array
                            JSONArray jsonArray = obj.getJSONArray("pkwholesales");

                            Log.d("1212", "onResponse: "+jsonArray.length());


                            for (int i = 0; i < jsonArray.length(); i++) {


                                server_responce = jsonArray.getJSONObject(i);


                                String error = server_responce.getString("error");


                                //Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();

                                if (error.equals("true")) {
                                    progressDialog.dismiss();


                                    Toast.makeText(getApplicationContext(), "No History Yet", Toast.LENGTH_LONG).show();



                                } else {
                                    progressDialog.dismiss();



                                    String id = server_responce.getString("order_id");
                                    String date = server_responce.getString("date");
                                    String price = server_responce.getString("subtotal");
                                    // String paidupto = server_responce.getString("paidupto");
                                    // String paid_status = server_responce.getString("paid_status");
                                    String delivery_status = server_responce.getString("delivery_status");
                                    //  String orderdc = server_responce.getString("totaldc");
                                    //  String ordertotal = server_responce.getString("total");




                                    //Toast.makeText(getApplicationContext(),date,Toast.LENGTH_LONG).show();



                                    HistoryModel listData = new HistoryModel(id,
                                            date,price,delivery_status,"0");
                                    list.add(listData);
                                }

                            }
                            adapter=new HistoryAdapter(list,getApplicationContext());

                            recyclerView.setAdapter(adapter);
                            if (recyclerView.getAdapter().getItemCount()==0){
                                progressDialog.dismiss();
                                nohistory.setVisibility(View.VISIBLE);
                            }
                            else {
                                progressDialog.dismiss();
                                nohistory.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {

                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Internet issue1", Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Internet issue11", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {


                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                // is id se vendor lene hain or unke products show krwany hain
                params.put("warehouse_id", user_id);
                return params;


            }

        };
        Singleton.getInstance (getApplicationContext()).addToRequestQueue (postRequest );
    }
}