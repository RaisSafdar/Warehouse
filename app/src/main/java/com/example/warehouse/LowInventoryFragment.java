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
import com.example.warehouse.adapters.Fulfil_Adapter;
import com.example.warehouse.model.FulfilProductModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LowInventoryFragment extends Fragment {

    RecyclerView recyclerView;
    JSONObject server_responce;
    Fulfil_Adapter adapter;
    List<FulfilProductModel> list;
    UserInfo userInfo;
    String user_id,vendor_id;
    int id;
    ProgressDialog progressDialog;
    TextView nolow;

    public LowInventoryFragment(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_low_inventory, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        userInfo = new UserInfo(getActivity());
        recyclerView = view.findViewById(R.id.rviewlow);
        nolow = view.findViewById(R.id.nolow);
       // Toast.makeText(getActivity(), ""+vendor_id, Toast.LENGTH_SHORT).show();
        list = new ArrayList<>();

        userInfo = new UserInfo(getActivity());
        user_id = userInfo.getKeyId();

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressDialog.show();
        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.Fulfill_products,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Responsethid", response);
                        try {
                            JSONObject obj = new JSONObject(response);

                            //we have the array named pkwholesales inside the object
                            //so here we are getting that json array
                            JSONArray jsonArray = obj.getJSONArray("pkwholesales");

                            Log.d("1212", "onResponse: "+jsonArray.length());


                            for (int i = 0; i < jsonArray.length(); i++) {


                                server_responce = jsonArray.getJSONObject(i);


                                Boolean error = server_responce.getBoolean("error");
//
                                if (error) {
                                    progressDialog.dismiss();

                                } else {

                                    progressDialog.dismiss();

                                    String image = server_responce.getString("product_image");
                                    String product_images = Utils.PimageUrl + image;
                                    String pname = server_responce.getString("product_name");
                                    String quantity = server_responce.getString("quantity");



                                    FulfilProductModel listData = new FulfilProductModel(product_images,pname,quantity,"0");
                                    list.add(listData);
                                }

                            }
                            adapter=new Fulfil_Adapter(list,getActivity());

                            recyclerView.setAdapter(adapter);
                            if (recyclerView.getAdapter().getItemCount()==0){
                                nolow.setVisibility(View.VISIBLE);
                            }
                            else {
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
                params.put("vender_id", vendor_id);
                return params;


            }

        };
        Singleton.getInstance (getActivity()).addToRequestQueue (postRequest );
        return view;
    }
}