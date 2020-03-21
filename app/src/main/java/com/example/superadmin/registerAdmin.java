package com.example.superadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import java.util.HashMap;
import java.util.Map;

public class registerAdmin extends AppCompatActivity {

    private EditText registerAdName, registerAdPass,registerAdPhone;
    private Button registerAdBtn;
    private String AdName, AdPass,AdPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);

        registerAdBtn = findViewById(R.id.UpdateSBtn);
        registerAdName = findViewById(R.id.registerSName);
        registerAdPass = findViewById(R.id.adminPass);
        registerAdPhone=findViewById(R.id.adminPhone);

        AdName = registerAdName.getText().toString();
        AdPass = registerAdPass.getText().toString();
        AdPhone = registerAdPhone.getText().toString();

        registerAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AdName != null && AdPass != null && AdPhone != null) {

                    final ProgressDialog pd = new ProgressDialog(getApplicationContext());
                    pd.setMessage("Saving please wait ...");
                    pd.show();
                    String url = "http://avaniatech.co.ke/api/adminreg";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            if (response.equalsIgnoreCase("success")) {
                                Toast toast = Toast.makeText(registerAdmin.this, " Admin Succesfully Saved", Toast.LENGTH_LONG);
                                toast.show();
                            } else {
                                Toast toast = Toast.makeText(registerAdmin.this, " An Error Occured", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.dismiss();
                            Log.e("registerAdmin", "No response received from server");
                            Toast toast = Toast.makeText(registerAdmin.this, " Server Error", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("username", AdName);
                            parameters.put("password", AdPass);
                            parameters.put("phoneNo", AdPhone);
                            //parameters.put("age",et_age.getText().toString());

                            return parameters;

                        }

                        ;
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(registerAdmin.this);
                    if (requestQueue == null) {
                        Log.e("registerAdmin", "Master the requestQueue is null");
                    } else {
                        requestQueue.add(stringRequest);
                    }
                }else {
                    Log.e("register admin","parameters are empty");
                    Toast toast = Toast.makeText(registerAdmin.this, " fill in fields", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}
