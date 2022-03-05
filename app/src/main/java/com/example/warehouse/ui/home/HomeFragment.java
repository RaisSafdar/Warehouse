package com.example.warehouse.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.warehouse.ProfileFragment;
import com.example.warehouse.R;
import com.example.warehouse.Singleton;
import com.example.warehouse.UserInfo;
import com.example.warehouse.Utils;
import com.example.warehouse.adapters.MyordersAdapter;
import com.example.warehouse.model.MyOrdersModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    JSONObject server_responce;
    MyordersAdapter adapter;
    List<MyOrdersModel> list;
    UserInfo userInfo;
    String user_id;
    ProgressDialog progressDialog;
    TextView textView,noorder;
    RelativeLayout relmen;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View b = inflater.inflate(R.layout.fragment_home, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        recyclerView = b.findViewById(R.id.rviewmyorders);
        noorder = b.findViewById(R.id.noorder);
        relmen = b.findViewById(R.id.relmen);

        textView = b.findViewById(R.id.torder);
        list = new ArrayList<>();

        userInfo = new UserInfo(getActivity());
        user_id = userInfo.getKeyId();

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        relmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ProfileFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.hfrag, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        progressDialog.show();
        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.MyOrders,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        try {
                            JSONObject obj = new JSONObject(response);

                            //we have the array named pkwholesales inside the object
                            //so here we are getting that json array
                            JSONArray jsonArray = obj.getJSONArray("pkwholesales");

                            Log.d("1212", "onResponse: " + jsonArray.length());


                            for (int i = 0; i < jsonArray.length(); i++) {


                                server_responce = jsonArray.getJSONObject(i);


                                Boolean error = server_responce.getBoolean("error");


                                //Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();

                                if (error) {
                                    Toast.makeText(getActivity(), "You Have No Orders Yet", Toast.LENGTH_SHORT).show();
                                    textView.setText("0");
                                    noorder.setVisibility(View.VISIBLE);
                                    progressDialog.dismiss();


//                                    Toast.makeText(getActivity(), "You Have No Orders Yet", Toast.LENGTH_LONG).show();


                                } else {
                                    progressDialog.dismiss();


                                    String id = server_responce.getString("order_id");
                                    String date = server_responce.getString("date");
                                    String delivery_status = server_responce.getString("delivery_status");
                                    String pending = server_responce.getString("pending");
                                    String city = server_responce.getString("city");
                                    textView.setText(pending);
                                    String delpercent = server_responce.getString("delpercentage");




                                    //Toast.makeText(getActivity(),image,Toast.LENGTH_LONG).show();


                                    MyOrdersModel listData = new MyOrdersModel(id,
                                            date, delivery_status, city,delpercent);
                                    list.add(listData);
                                }

                            }
                            Collections.reverse(list);
                            adapter = new MyordersAdapter(list, getActivity());

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
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Internet Issue", Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {


                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("customer_id", user_id);
                return params;


            }

        };
        Singleton.getInstance(getActivity()).addToRequestQueue(postRequest);

        return b;
    }
}