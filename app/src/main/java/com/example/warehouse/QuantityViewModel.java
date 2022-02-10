package com.example.warehouse;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class QuantityViewModel extends ViewModel {

    UserInfo userInfo;
    JSONObject server_responce;
    private MutableLiveData<Integer> qty;
    private MutableLiveData<String> total;
    String user_id,totals;
    int cartcount;
    ProgressDialog progressDialog;

    public MutableLiveData<Integer> getCounter(Context c, String oid){
        if (qty == null){
            qty = new MutableLiveData<>();
            loadData(c,oid);
        }
        return qty;


    }
    public MutableLiveData<String> getTotal(Context c,String oid) {
        progressDialog = new ProgressDialog(c);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        if (total == null) {
            total = new MutableLiveData<>();

            loadData(c,oid);

        }
        return total;
    }


    public void loadData(Context c , String oid) {
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




                                } else {
                                    totals = server_responce.getString("total");
                                    total.setValue(totals);
                                    int x = jsonArray.length()-1;

                                    if (i==x){
                                        cartcount = server_responce.getInt("qty");
                                        Log.d("ccc", "onResponse: "+cartcount);


                                    }
                                    qty.setValue(cartcount);
                                }

                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                            Log.d("Error.Response",e.getMessage());


                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response1", error.getMessage());
                        Toast.makeText(c, "Internet Issue", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {


                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                userInfo = new UserInfo(c);
                user_id = userInfo.getKeyId();
                params.put("order_id", oid);
                params.put("warehouse_id", user_id);
                return params;


            }

        };
        Singleton.getInstance (c).addToRequestQueue (postRequest );
    }


}
