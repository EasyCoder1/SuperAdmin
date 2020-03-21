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

public class updateSalesperson extends AppCompatActivity {
    private EditText searchSMan,SName,SPass;
    private Button searchSManBtn,updateSManBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_salesperson);
        searchSMan=findViewById(R.id.searchSaleman);
        SName=findViewById(R.id.SName);
        SPass=findViewById(R.id.SPass);
        searchSManBtn=findViewById(R.id.searchSalesmanBtn);
        updateSManBtn=findViewById(R.id.UpdateSBtn);

        searchSManBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
        updateSManBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update();
            }
        });
    }
    public void load(){
        String SearchPhone=searchSMan.getText().toString();
        String sphone = SearchPhone.replaceFirst("^0+(?!$)", "");
        String url="http://avaniatech.co.ke/api/smanInfo/ "+ sphone;
        final ProgressDialog pd = new ProgressDialog(getApplicationContext());
        pd.setMessage("Searching please wait ...");
        pd.show();
        if(sphone !=null){
            StringRequest request= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pd.dismiss();
                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONArray array=new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject smanloc = array.getJSONObject(i);
                            String z = smanloc.getString("username");
                            SName.setText(z);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("Encountered an error"," error"+e);
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Encountered an error","UpdateAdmin volley request error"+ error);
                    Toast toast=Toast.makeText(updateSalesperson.this,"Error, Admin not found",Toast.LENGTH_SHORT);
                    toast.show();

                }
            });
        }
    }


    public void Update(){
        final String Xname,Xpass;
        Xname =SName.getText().toString();
        Xpass=SPass.getText().toString();
        String SearchPhone=searchSMan.getText().toString();
        String Smn =SearchPhone.replaceFirst("^0+(?!$)", "");
        String url="http://avaniatech.co.ke/updateSman/"+Smn;
        final ProgressDialog pd = new ProgressDialog(getApplicationContext());
        pd.setMessage("Saving please wait ...");
        if(SName!=null && Xpass!=null) {
            StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pd.dismiss();
                    if (response.equalsIgnoreCase("updated")) {
                        Toast toast = Toast.makeText(updateSalesperson.this, " Salesperson Succesfully Updated", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(updateSalesperson.this, " An Error Occured", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pd.dismiss();
                    Log.e("UpdateAdmin", "No response received from server");
                    Toast toast = Toast.makeText(updateSalesperson.this, " Server Error", Toast.LENGTH_LONG);
                    toast.show();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("username", Xname);
                    parameters.put("password", Xpass);

                    return parameters;
                }

                ;
            };

            RequestQueue requestQueue = Volley.newRequestQueue(updateSalesperson.this);
            if (requestQueue == null) {
                Log.e("UpdateAdmin", "Master the requestQueue is null");
            } else {
                requestQueue.add(stringRequest);
            }
        }else{
            Toast toast=Toast.makeText(updateSalesperson.this,"Fill the missing fields",Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
