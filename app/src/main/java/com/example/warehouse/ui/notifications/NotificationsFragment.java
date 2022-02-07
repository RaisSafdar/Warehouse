package com.example.warehouse.ui.notifications;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.warehouse.R;
import com.example.warehouse.Singleton;
import com.example.warehouse.UserInfo;
import com.example.warehouse.Utils;
import com.example.warehouse.adapters.InventoryLedgerAdapter;
import com.example.warehouse.model.InventoryLedgerModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationsFragment extends Fragment {
    RecyclerView recyclerView;
    JSONObject server_responce;
    InventoryLedgerAdapter adapter;
    List<InventoryLedgerModel> list;
    ProgressDialog progressDialog;
    String cid;
    FloatingActionButton floatingActionButton;
    String user_id;
    UserInfo userInfo;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        recyclerView =view.findViewById(R.id.adminledrec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userInfo = new UserInfo(getActivity());
        user_id = userInfo.getKeyId();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        list = new ArrayList<>();
        progressDialog.show();
        loadData();
        return view;
    }
    private void loadData() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.Inventory_ledger,
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
                                    Toast.makeText(getActivity(), "No Result Found", Toast.LENGTH_SHORT).show();
                                } else {



                                    progressDialog.dismiss();
                                    String id = server_responce.getString("id");
                                    String pid = server_responce.getString("product_id");
                                    String date = server_responce.getString("date");
                                    String received_quantity = server_responce.getString("received_quantity");
                                    String sale_quantity = server_responce.getString("sale_quantity");
                                    String orderid = server_responce.getString("order_id");
                                    String product_price = server_responce.getString("product_price");
                                    String sale_price = server_responce.getString("sale_price");
                                    String product_name = server_responce.getString("product_name");
                                    String vendorid = server_responce.getString("vendor_id");


                                    InventoryLedgerModel model = new InventoryLedgerModel(id,date,orderid,product_name,sale_price,
                                            sale_quantity,received_quantity,"0",product_price);
                                    list.add(model);


                                }
                            }

                            adapter = new InventoryLedgerAdapter(list, getActivity());

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

                        Toast.makeText(getActivity(), "Internet issue", Toast.LENGTH_SHORT).show();

                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() {


                // Posting parameters to login url


                Map<String, String> params = new HashMap<>();

                UserInfo info = new UserInfo(getActivity());


                params.put("warehouse_id", user_id);


                return params;


            }


        };
        Singleton.getInstance(getActivity()).addToRequestQueue(postRequest);
    }


}