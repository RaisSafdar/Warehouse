package com.example.warehouse;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
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
import java.util.Objects;

public class QuantityViewModel extends ViewModel {

    UserInfo userInfo;
    JSONObject server_responce;
    private MutableLiveData<Integer> qty;
    private MutableLiveData<String> total;
    private MutableLiveData<String> status;
    String user_id, totals,statuses;
    int cartcount;
    ProgressDialog progressDialog;

    public MutableLiveData<Integer> getCounter(Context c, String oid, String vid, TextView total,TextView qttys){
        if (qty == null){
            qty = new MutableLiveData<>();
            loadData(c,oid,vid,total,qttys);
        }
        return qty;


    }

    public MutableLiveData<String> getTotal(Context c, String oid, String vid, TextView total,TextView qttys) {
        progressDialog = new ProgressDialog(c);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        if (this.total == null) {
            this.total = new MutableLiveData<>();

            loadData(c,oid,vid,total,qttys);

        }
        return this.total;
    }



    public void loadData(Context c , String oid, String vid, TextView textView,TextView qttys) {
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

                                    Toast.makeText(c.getApplicationContext(), ""+vid, Toast.LENGTH_SHORT).show();


                                } else {

                                   String totals = server_responce.getString("total");



                                  //  total.setValue(totals);
//                                    VendorNames vendorNames = new VendorNames(vid,oid,status,c);
//                                    vendorNames.Total.setText(totals);
                                    textView.setText(totals);



                                    int x = jsonArray.length()-1;

                                    if (i==x){
                                     int cartcount = server_responce.getInt("qty");
                                        Log.d("ccc", "onResponse: "+cartcount);

                                        qttys.setText(String.valueOf(cartcount));

                                    }

                                    //qty.setValue(cartcount);
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
                params.put("vendor_id", vid);
                params.put("warehouse_id", user_id);
                return params;


            }

        };
        Singleton.getInstance (c).addToRequestQueue (postRequest );
    }


}
