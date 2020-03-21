package com.example.superadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    private EditText username,password;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        loginBtn=findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    public void login() {
        String Lusername = username.getText().toString();
        String Lpassword = username.getText().toString();
        if (Lusername != null && Lpassword != null) {
            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("logging in... Please wait");
            progressDialog.show();
            String url = "http://avaniatech.co.ke/api/loginAdmin";
            try {
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if (response.equalsIgnoreCase("matches")) {
                            Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
                            startActivity(intent);
                        } else {
                            Toast toast = Toast.makeText(MainActivity.this, "login failed,incorrect details", Toast.LENGTH_LONG);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_LONG);
                        toast.show();

                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                if (requestQueue == null) {
                    Log.e("Request status", "null Request Error buddy");
                } else {
                    Log.i("status", "am doing good buddy");
                    requestQueue.add(request);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }else{
            Log.e("LoginActivity"," fields are empty");
        }

    }
}
