package com.example.globaljobsearchapp_deeppatel_navneetsingh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Student_Filter_Company extends AppCompatActivity {

    ListView list_jobsposted;
    String URL="http://192.168.0.25/GlobalJobSearch/JobDescriptionData";
    public static ArrayList<String> companyName = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__filter__company);

        list_jobsposted = findViewById(R.id.company_filter_list);
        loadCompanyName(URL);

        list_jobsposted.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list_jobsposted.getItemAtPosition(position);
                Toast.makeText(Student_Filter_Company.this,clickedItem,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadCompanyName(String url) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try{
                    JSONArray JA = new JSONArray(response);
                    for(int i=0;i<JA.length();i++){
                        JSONObject jsonObject1=JA.getJSONObject(i);
                        companyName.add("" + jsonObject1.getString("CompanyName"));
                    }

                    ArrayAdapter<String> companyListAdapter = new ArrayAdapter<String>(Student_Filter_Company.this,android.R.layout.simple_list_item_1, companyName);
                    list_jobsposted.setAdapter(companyListAdapter);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}
