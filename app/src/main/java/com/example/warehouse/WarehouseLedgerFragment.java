package com.example.warehouse;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.warehouse.adapters.InventoryLedgerAdapter;
import com.example.warehouse.adapters.VendorAdapter;
import com.example.warehouse.adapters.VendorAdapter2;
import com.example.warehouse.adapters.WarehouseLedgerAdapter;
import com.example.warehouse.model.InventoryLedgerModel;
import com.example.warehouse.model.VendorsModel;
import com.example.warehouse.model.WarehouseLedgerModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WarehouseLedgerFragment extends Fragment {
    RecyclerView recyclerView;
    JSONObject server_responce;
    VendorAdapter2 adapter;
    List<VendorsModel> list;
    UserInfo userInfo;
    String user_id,name,vendor_id;
    ProgressDialog progressDialog;
    Dialog builder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_warehouse_ledger, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        recyclerView = view.findViewById(R.id.vendorerec);
        list = new ArrayList<>();
        userInfo = new UserInfo(getActivity());
        user_id = userInfo.getKeyId();
        builder = new Dialog(getActivity());



        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressDialog.show();
        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.MyVendors,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Responsezero", response);

                        try {
                            JSONObject obj = new JSONObject(response);

                            //we have the array named pkwholesales inside the object
                            //so here we are getting that json array
                            JSONArray jsonArray = obj.getJSONArray("pkwholesales");

                            Log.d("1212", "onResponse: "+jsonArray.length());


                            for (int i = 0; i < jsonArray.length(); i++) {


                                server_responce = jsonArray.getJSONObject(i);


                                Boolean error = server_responce.getBoolean("error");


                                //Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();

                                if (error) {
                                    progressDialog.dismiss();
                                    Log.d("ee", "onResponse: "+error);



                                } else {
                                    progressDialog.dismiss();



                                    vendor_id = server_responce.getString("vendor_id");
                                    name = server_responce.getString("name");





                                    VendorsModel listData = new VendorsModel(name,vendor_id);
                                    list.add(listData);
                                }

                            }
                            adapter=new VendorAdapter2(list,getActivity(),builder);

                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            progressDialog.dismiss();


                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getActivity(), "Internet Error", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {


                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("warehouse_id", user_id);
                return params;


            }

        };
        Singleton.getInstance (getActivity()).addToRequestQueue (postRequest );

        return view;
    }
}