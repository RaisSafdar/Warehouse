package com.example.warehouse;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import com.example.warehouse.adapters.OrderDetailAdapter;
import com.example.warehouse.model.OrderDetailModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VendorNames extends Fragment {
    String vid,status;
    RecyclerView recyclerView;
    JSONObject server_responce;
    OrderDetailAdapter adapter;
    List<OrderDetailModel> list;
    UserInfo userInfo;
    AlertDialog.Builder builder;
    AlertDialog.Builder builder2;
    String user_id,ordrid,strttl;
    ProgressDialog progressDialog;
    TextView Total,ready,city,items,qttys;
    QuantityViewModel viewModel;
    Context context;
    FragmentManager fragmentManager;



    FragmentActivity activity;

    public VendorNames(String vid, String status, String orderid, Context applicationContext) {
        this.vid = vid;
        this.status = status;
        this.ordrid = orderid;
        this.context = applicationContext;

    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_vendor_names, container, false);
        builder = new AlertDialog.Builder(getActivity());
        builder2 = new AlertDialog.Builder(getActivity());
        city = view.findViewById(R.id.citytv);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        //context = getActivity();
        recyclerView = view.findViewById(R.id.rviewmyorders1);
        items = view.findViewById(R.id.itemstv);
        qttys = view.findViewById(R.id.qttytv);
        Total = view.findViewById(R.id.deliverycharges);
        fragmentManager = getChildFragmentManager();




        viewModel = new ViewModelProvider(this).get(QuantityViewModel.class);

        viewModel.loadData(getActivity(),ordrid,vid,Total,qttys);

        LiveData<Integer> liveData = viewModel.getCounter(getActivity(),ordrid,vid,Total,qttys);
        liveData.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer ss) {
             //   qttys.setText(String.valueOf(ss));
               // viewModel.loadData(getApplicationContext(),ordrid);
                Log.d("sdsdadf", "onChanged: "+ss);
            }
        });

        viewModel = new ViewModelProvider(this).get(QuantityViewModel.class);
        LiveData<String> liveData1 = viewModel.getTotal(getActivity(),ordrid,vid,Total,qttys);
        liveData1.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String ss1) {
               // Total.setText(String.valueOf(ss1));
            }
        });









        ready = view.findViewById(R.id.cout);
        if (status.equals("Pending")){
            ready.setVisibility(View.VISIBLE);
        }else {
            ready.setVisibility(View.GONE);
        }

        list = new ArrayList<>();

        userInfo = new UserInfo(getActivity());
        user_id = userInfo.getKeyId();

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressDialog.show();
        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.OrderDetails,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

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



                                } else {
                                    progressDialog.dismiss();




                                    String id = server_responce.getString("id");
                                    String order_id = server_responce.getString("order_id");
                                    String product_name = server_responce.getString("product_name");
                                    String product_image = server_responce.getString("product_image");
                                    String total = server_responce.getString("total");
                                    Total.setText(total);
                                    String purchase_price = server_responce.getString("purchase_price");
                                    String quantity = server_responce.getString("quantity");
                                    String qty = server_responce.getString("qty");
                                    qttys.setText(qty);

                                    String product_images = Utils.PimageUrl + product_image;


                                    //Toast.makeText(getActivity(),image,Toast.LENGTH_LONG).show();



                                    OrderDetailModel listData = new OrderDetailModel(id,order_id,product_name,product_images,total,quantity
                                            ,purchase_price,status);
                                    list.add(listData);
                                }

                            }
                            adapter=new OrderDetailAdapter(list,getActivity(),
                                    Total,getActivity(),builder2,vid,qttys);

                            recyclerView.setAdapter(adapter);
                            String itemsa = String.valueOf(recyclerView.getAdapter().getItemCount());
                            items.setText(itemsa);

                        } catch (JSONException e) {

                            e.printStackTrace();
                            progressDialog.dismiss();


                        }
                    }
                },
                new Response.ErrorListener()
                {
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
                params.put("order_id", ordrid);
                params.put("warehouse_id", user_id);
                params.put("vendor_id", vid);
                return params;


            }

        };
        Singleton.getInstance (getActivity()).addToRequestQueue (postRequest );


        ready.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                builder.setMessage("Are you Sure?");
                builder.setTitle("Ready to collect");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringRequest stringRequest=new StringRequest( Request.Method.POST ,


                                Utils.OrderReady, new Response.Listener <String> () {


                            @Override
                            public void onResponse(String response) {
                                Log.d("testerror",response);
                                try {
                                    JSONObject jObj = new JSONObject(response);
                                    boolean error = jObj.getBoolean("error");
                                    String error_msg = jObj.getString("msg");


                                    if (!error) {

                                        progressDialog.hide();



                                        Toast.makeText(getActivity(),"Order Ready",Toast.LENGTH_SHORT).show();
                                        ready.setVisibility(View.GONE);


                                    }else {

                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(),error_msg,Toast.LENGTH_SHORT).show();


                                    }

                                } catch (JSONException e) {
                                    progressDialog.hide();
                                    // JSON error
                                    e.printStackTrace();


                                    Toast.makeText (getActivity(),"Internet Error",Toast.LENGTH_SHORT ).show ();
                                }



                            }
                        } , new Response.ErrorListener () {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                //Log.e(TAG, "Login Error: " + error.getMessage());



                            }
                        }) {

                            @Override
                            protected Map<String, String> getParams() {


                                // Posting parameters to login url


                                Map<String, String> params = new HashMap<>();

                                params.put("order_id", ordrid);
                                params.put("warehouse_id", user_id);
                                params.put("vendor_id", vid);
                                String ttl = Total.getText().toString();
                                params.put("totalrupees", ttl);
                                Log.d("ttl", "getParams: "+ttl);


                                return params;


                            }

                        };
                        Singleton.getInstance( getActivity() ).addToRequestQueue(stringRequest);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();



            }
        });

        return view;
    }
}