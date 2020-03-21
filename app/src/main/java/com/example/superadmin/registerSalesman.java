package com.example.superadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class registerSalesman extends AppCompatActivity {
    private EditText registerSName,registerSPass,registerSphone;
    private Spinner spinner;
    private Button registerSBtn;
    private String select,Sname,Spass,Sphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_salesman);

        registerSName=findViewById(R.id.registerSName);
        registerSPass=findViewById(R.id.adminPass);
        registerSphone=findViewById(R.id.registerSPhone);

        spinner=findViewById(R.id.spinner3);
        registerSBtn=findViewById(R.id.UpdateSBtn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Branches, R.layout.sxspinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                select = parent.getItemAtPosition(position).toString();
            }
        });
        registerSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storedata();
            }
        });


    }

    private void storedata() {
        Sname = registerSName.getText().toString();
        Spass = registerSPass.getText().toString();
        String pn = registerSphone.getText().toString();
        String url = "http://avaniatech.co.ke/api/register";
        if (Sname != null && Spass != null && Sphone != null) {
            Sphone = pn.replaceFirst("^0+(?!$)", "");
            final ProgressDialog pd = new ProgressDialog(getApplicationContext());
            pd.setMessage("Saving please wait ...");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pd.dismiss();
                    if (response.equalsIgnoreCase("inserted")) {
                        Toast toast = Toast.makeText(registerSalesman.this, " Admin Succesfully Saved", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(registerSalesman.this, " Cannot Register", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pd.dismiss();
                    Log.e("registerAdmin", "No response received from server");
                    Toast toast = Toast.makeText(registerSalesman.this, " Server Error", Toast.LENGTH_LONG);
                    toast.show();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("username", Sname);
                    parameters.put("phoneNo", Sphone);
                    parameters.put("password", Spass);
                    parameters.put("branch", select);

                    return parameters;
                }

                ;
            };

            RequestQueue requestQueue = Volley.newRequestQueue(registerSalesman.this);
            if (requestQueue == null) {
                Log.e("registerSalesMan", "Master the requestQueue is null");
            } else {
                requestQueue.add(stringRequest);
            }
        }
    }

}


