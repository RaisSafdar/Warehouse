package com.example.warehouse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {
    ImageView accinfo,settings,share,call;
    TextView username;
    UserInfo userInfo;
    JSONObject server_responce;
    String user_id;
    String name;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);


        accinfo = view.findViewById(R.id.arrow1);
        settings = view.findViewById(R.id.arrow3);
        share = view.findViewById(R.id.arrow4);

        call = view.findViewById(R.id.arrow7);

        username = view.findViewById(R.id.username);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...Please wait");
        userInfo = new UserInfo(getActivity());
        user_id = userInfo.getKeyId();

        progressDialog.show();
        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.AccInfo,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

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


                                    Toast.makeText(getActivity(), "Internet Error", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();


                                } else {
                                    progressDialog.dismiss();



                                    name = server_responce.getString("name");
                                    username.setText(name.toUpperCase());
                                    Log.d("hgjhg", "onResponse: "+name);


                                    //Toast.makeText(getActivity(),image,Toast.LENGTH_LONG).show();



                                }
                            }



                        } catch (JSONException e) {

                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(),"No Internet",Toast.LENGTH_LONG).show();


                        }
                    }
                },
                new Response.ErrorListener()
                {
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

                params.put("id", user_id);




                return params;


            }


        };
        Singleton.getInstance (getActivity()).addToRequestQueue (postRequest );

        accinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AccountInformation.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
////                Intent intent = new Intent(getActivity(),Settings.class);
////                getActivity().getSupportFragmentManager().popBackStack();
////
////                startActivity(intent);

                UserSession sessionManager = new UserSession(getActivity());
                sessionManager.setLoggedin(false);
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().getFragmentManager().popBackStack();
                getActivity().finish();

            }


        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "share");
                startActivity(Intent.createChooser(shareIntent, "Share Via"));
            }
        });

//        complaints.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(),Complaint.class);
//                startActivity(intent);
//            }
//        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ContactUs.class);
                startActivity(intent);
            }
        });

//        chats.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(getActivity(),CustomerChat.class);
////                startActivity(intent);
//            }
//        });


        return view;
    }
}