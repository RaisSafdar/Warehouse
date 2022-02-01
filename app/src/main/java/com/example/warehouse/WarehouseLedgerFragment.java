package com.example.warehouse;

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
import com.example.warehouse.adapters.WarehouseLedgerAdapter;
import com.example.warehouse.model.InventoryLedgerModel;
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
    WarehouseLedgerAdapter adapter;
    List<WarehouseLedgerModel> list;
    ProgressDialog progressDialog;
    String user_id;
    UserInfo userInfo;
    TextView noresult;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_warehouse_ledger, container, false);

        recyclerView = view.findViewById(R.id.adminledrec);
        noresult = view.findViewById(R.id.noresult);

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
        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.Warehouse_ledger,
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
                                    Toast.makeText(getActivity(), "No Result Found", Toast.LENGTH_SHORT).show();
                                } else {

                                    progressDialog.dismiss();
                                    String id = server_responce.getString("id");
                                    String date = server_responce.getString("date");
                                    String debit = server_responce.getString("total_payment");
                                    String credit = server_responce.getString("payment_received");
                                    String orderid = server_responce.getString("order_id");
                                    String vendorid = server_responce.getString("vendor_id");
                                    String vendorname = server_responce.getString("venname");


                                    WarehouseLedgerModel model = new WarehouseLedgerModel(id, date, orderid, credit, debit
                                            , vendorid,vendorname);
                                    list.add(model);


                                }
                            }

                            adapter = new WarehouseLedgerAdapter(list, getActivity());

                            recyclerView.setAdapter(adapter);
                            if (recyclerView.getAdapter().getItemCount()==0){
                                progressDialog.dismiss();
                                noresult.setVisibility(View.VISIBLE);
                            }
                            else {
                                progressDialog.dismiss();
                                noresult.setVisibility(View.GONE);
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
                        Toast.makeText(getActivity(), "Internet Issue", Toast.LENGTH_SHORT).show();
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