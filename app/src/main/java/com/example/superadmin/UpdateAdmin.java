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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateAdmin extends AppCompatActivity {
    private EditText searchAdmin,adminName,adminPass;
    private Button searchAdminBtn,updateAdminBtn;
    private String SearchPn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_admin);

        searchAdmin=findViewById(R.id.searchSaleman);
        adminName=findViewById(R.id.SName);
        adminPass=findViewById(R.id.adminPass);
        searchAdminBtn=findViewById(R.id.searchSalesmanBtn);
        updateAdminBtn=findViewById(R.id.updateAdminBtn);

        searchAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        updateAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update();
            }
        });

    }

    public void search() {
        String SearchNo = searchAdmin.getText().toString();
        if (SearchNo != null) {
            SearchPn = SearchNo.replaceFirst("^0+(?!$)", "");
            String url = "http://avaniatech.co.ke/api/adminInfo/" + SearchPn;
            final ProgressDialog pd = new ProgressDialog(getApplicationContext());
            pd.setMessage("Searching please wait ...");
            pd.show();
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pd.dismiss();
                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject smanloc = array.getJSONObject(i);
                            String z = smanloc.getString("username");
                            adminName.setText(z);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("Encountered an error", " error" + e);
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Encountered an error", "UpdateAdmin volley request error" + error);
                    Toast toast = Toast.makeText(UpdateAdmin.this, "Error, Admin not found", Toast.LENGTH_SHORT);
                    toast.show();

                }
            });
        } else {
            Toast toast = Toast.makeText(UpdateAdmin.this, "Enter Phone No", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public void Update(){
            final String Xname,Xpass;
            String SearchNo=searchAdmin.getText().toString();
            if (SearchNo!=null){
                SearchPn=SearchNo.replaceFirst("^0+(?!$)", "");
            }
            Xname =adminName.getText().toString();
            Xpass=adminPass.getText().toString();
            if(Xname!=null&&Xpass!=null&&SearchPn!=null){
                String url="http://avaniatech.co.ke/updateAdmin/"+SearchPn;
                final ProgressDialog pd = new ProgressDialog(getApplicationContext());
                pd.setMessage("Saving please wait ...");
                StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url, new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                    pd.dismiss();
                    if (response.equalsIgnoreCase("success")) {
                        Toast toast = Toast.makeText(UpdateAdmin.this, " Admin Succesfully Updated", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(UpdateAdmin.this, " An Error Occured", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
              }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pd.dismiss();
                    Log.e("UpdateAdmin", "No response received from server");
                    Toast toast = Toast.makeText(UpdateAdmin.this, " Server Error", Toast.LENGTH_LONG);
                    toast.show();
                  }
                }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("username", Xname);
                    parameters.put("password",Xpass);

                    return parameters;
                }


            };

            RequestQueue requestQueue = Volley.newRequestQueue(UpdateAdmin.this);
            if (requestQueue == null) {
                Log.e("UpdateAdmin", "Master the requestQueue is null");
            } else {
                requestQueue.add(stringRequest);
            }

            }else{
                Toast toast=Toast.makeText(UpdateAdmin.this,"Fill empty fields",Toast.LENGTH_SHORT);
                toast.show();
            }
    }

}
