package com.example.warehouse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
TextView loginbtn,forgot;
EditText phonenumber, passwrod;
String phonenumbers, passwords;
UserSession userSession;
    String user_id,user_pass;
    UserInfo userInfo;
ProgressDialog progressDialog;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Signing In");
        progressDialog.setMessage("Please Wait");
        loginbtn = findViewById(R.id.loginbtn);
        phonenumber = findViewById(R.id.pnomber);
        passwrod = findViewById(R.id.passwordedt);
        imageView = findViewById(R.id.passtog);
        forgot = findViewById(R.id.forgotpass);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
            }
        });
        userSession = new UserSession(this);

        userInfo = new UserInfo(Login.this);
        user_id = userInfo.getKeyId();
        user_pass = userInfo.getKeyPass();

        if (userSession.isUserLoggedin()) {
            checkstatus(user_id,user_pass);
        }
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phonenumbers = phonenumber.getText().toString();
                passwords = passwrod.getText().toString();

                if (TextUtils.isEmpty(phonenumbers)) {
                    phonenumber.setError("Phone Required");
                    return;
                }

                if (TextUtils.isEmpty(passwords)) {
                    passwrod.setError("Password Required");
                    return;
                }
                progressDialog.show();


                StringRequest stringRequest = new StringRequest(Request.Method.POST,


                        Utils.Login2, new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        Log.d("testerror", response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");


                            if (!error) {

                                progressDialog.dismiss();
                                String error_msg = jObj.getString("msg");
                                String id = jObj.getString("id");
                                String pass = jObj.getString("pass");

                                UserInfo info = new UserInfo(getApplicationContext());

                                userSession.setLoggedin(true);
                                info.setId(id);

                                info.setPass(pass);



                                Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();


                            } else {
                                // Error in login. Get the error message
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Your Account Has Not Been Approved" , Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Incorrect Phone Or Password Try Again", Toast.LENGTH_SHORT).show();

                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //Log.e(TAG, "Login Error: " + error.getMessage());

                        Toast.makeText(getApplicationContext(), "Internet Error", Toast.LENGTH_SHORT).show();


                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {


                        // Posting parameters to login url


                        Map<String, String> params = new HashMap<>();


                        params.put("phone", phonenumbers);
                        params.put("password", passwords);


                        return params;


                    }

                };

                // Adding request to request queue
                Singleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                // Adding request to request queue
                Singleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(imageView.getId()==R.id.passtog){

                    if(passwrod.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){


                        //Show Password
                        passwrod.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    else{

                        //Hide Password
                        passwrod.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    }
                }


            }
        });
    }
    public void checkstatus(String ids, String passes){
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST,


                Utils.Checkstatus, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d("testerror1111", response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    //   String error_msg = jObj.getString("msg");

                    if (!error) {
                        String id = jObj.getString("status");
                        String pass2 = jObj.getString("pass");

                        if (id.equals("Active")) {
                            if (passes.equals(pass2)) {

                                progressDialog.dismiss();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(Login.this, "Password Changed. Login Again", Toast.LENGTH_SHORT).show();

                            }

                        }else {
                            Toast.makeText(Login.this, "Account Not Active", Toast.LENGTH_SHORT).show();

                        }









                    } else {
                        // Error in login. Get the error message
                        progressDialog.dismiss();

                        //  Toast.makeText(getApplicationContext(), "Your Account Has Not Been Approved", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    // JSON error

                    //Toast.makeText(getApplicationContext(), "Incorrect Phone Or Password Try Again", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                //Log.e(TAG, "Login Error: " + error.getMessage());

                Toast.makeText(getApplicationContext(),"Internet error", Toast.LENGTH_SHORT).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url

                Map<String, String> params = new HashMap<>();
                params.put("phone", ids);
                // params.put("password", passwords);
                return params;

            }
        };

        // Adding request to request queue
        Singleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}