package com.example.globaljobsearchapp_deeppatel_navneetsingh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

public class FilteredCompanyList extends AppCompatActivity {

    ListView comListView;
    String companyListURL="http://192.168.0.30/GlobalJobSearch/api/CompanyList";
    public static ArrayList<String> companyNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_company_list);
        loadCompanyListData(companyListURL);
    }


    public void loadCompanyListData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray JA = new JSONArray(response);
                    for (int i = 0; i < JA.length(); i++) {
                        JSONObject jsonObject = JA.getJSONObject(i);
                        if (jsonObject.getString("CompanyName") != "null") {
                            companyNameList.add("" + jsonObject.getString("CompanyName"));
                        }

                    }

                    ArrayAdapter<String> country_adapter = new ArrayAdapter<String>(FilteredCompanyList.this, R.layout.activity_filtered_company_list, companyNameList);
                    //country_adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                    comListView.setAdapter(country_adapter);



                } catch (JSONException e) {
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
