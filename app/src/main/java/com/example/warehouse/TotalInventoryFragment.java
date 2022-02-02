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
import com.example.warehouse.adapters.FulfilAdapterTotal;
import com.example.warehouse.adapters.Fulfil_Adapter;
import com.example.warehouse.model.FulfilProductModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalInventoryFragment extends Fragment {

    RecyclerView recyclerView;
    JSONObject server_responce;
    FulfilAdapterTotal adapter;
    List<FulfilProductModel> list;
    UserInfo userInfo;
    String user_id,vender_id;
    ProgressDialog progressDialog;
    TextView nolow;

    public TotalInventoryFragment(String vender_id) {
        this.vender_id = vender_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_total_inventory, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        userInfo = new UserInfo(getActivity());
        recyclerView = view.findViewById(R.id.rviewtotal);
        nolow = view.findViewById(R.id.nolow);
        list = new ArrayList<FulfilProductModel>();

        userInfo = new UserInfo(getActivity());
        user_id = userInfo.getKeyId();

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressDialog.show();
        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.Fulfill_productsTotal,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Responserate", response);
                        try {
                            JSONObject obj = new JSONObject(response);

                            //we have the array named pkwholesales inside the object
                            //so here we are getting that json array
                            JSONArray jsonArray = obj.getJSONArray("pkwholesales");

                            Log.d("1212rate", "onResponse: "+jsonArray.length());


                            for (int i = 0; i < jsonArray.length(); i++) {


                                server_responce = jsonArray.getJSONObject(i);


                                Boolean error = server_responce.getBoolean("error");
//
                                if (error) {
                                    progressDialog.dismiss();
                                        nolow.setVisibility(View.VISIBLE);



                                } else {


                                    nolow.setVisibility(View.GONE);
                                    String image = server_responce.getString("product_image");
                                    String product_images = Utils.PimageUrl + image;
                                    String pname = server_responce.getString("product_name");
                                    String quantity = server_responce.getString("quantity");
                                    String rate = server_responce.getString("rate");



                                    FulfilProductModel listData = new FulfilProductModel(product_images,pname,quantity,rate);
                                    list.add(listData);
                                }

                            }
                            adapter=new FulfilAdapterTotal(list,getActivity());

                            recyclerView.setAdapter(adapter);
                            if (recyclerView.getAdapter().getItemCount()==0){
                                progressDialog.dismiss();
                                nolow.setVisibility(View.VISIBLE);
                            }
                            else {
                                progressDialog.dismiss();
                                nolow.setVisibility(View.GONE);
                            }

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
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Internet error", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {


                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("vender_id", vender_id);
                return params;


            }

        };
        Singleton.getInstance (getActivity()).addToRequestQueue (postRequest );
        return view;
    }
}