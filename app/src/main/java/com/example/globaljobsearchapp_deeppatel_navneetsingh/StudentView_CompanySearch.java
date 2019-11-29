package com.example.globaljobsearchapp_deeppatel_navneetsingh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.globaljobsearchapp_deeppatel_navneetsingh.R.array.country_list_array;
import static com.example.globaljobsearchapp_deeppatel_navneetsingh.R.array.program_list_array;

public class StudentView_CompanySearch extends AppCompatActivity {

    Spinner cntrySpinner;
    Spinner prgSpinner;
    Button searchBtn;

    public static String cntry;
    public static String prg;
    String URL = "http://10.16.7.100/GlobalJobSearch/api/DropDownFilter";
    public static ArrayList<String> cntryNameList  = new ArrayList<String>();
    public static ArrayList<String> prgNameList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view__company_search);

        cntrySpinner = findViewById(R.id.cntryList);
        prgSpinner = findViewById(R.id.prgList);
        searchBtn = findViewById(R.id.searchCompanyBtn);

        FetchDropDownData process = new FetchDropDownData();
        process.execute();
        loadSpinnerData(URL);
        cntrySpinnerOnClick();
        prgSpinnerOnClick();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentView_CompanySearch.this, Student_Filter_Company.class));
            }
        });

    }



    public void loadSpinnerData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray JA = new JSONArray(response);
                    for (int i = 0; i < JA.length(); i++) {
                        JSONObject jsonObject = JA.getJSONObject(i);
                        if (jsonObject.getString("Country") != "null") {
                            cntryNameList.add("" + jsonObject.getString("Country"));
                        }
                        if (jsonObject.getString("Program") != "null") {
                            prgNameList.add("" + jsonObject.getString("Program"));
                        }
                    }

                    ArrayAdapter<String> country_adapter = new ArrayAdapter<String>(StudentView_CompanySearch.this, R.layout.color_spinner_layout, cntryNameList);
                    country_adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                    cntrySpinner.setAdapter(country_adapter);

                    ArrayAdapter<String> program_adapter = new ArrayAdapter<String>(StudentView_CompanySearch.this, R.layout.color_spinner_layout, prgNameList);
                    program_adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                    prgSpinner.setAdapter(program_adapter);

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

    public void cntrySpinnerOnClick(){
        cntrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cntry = cntrySpinner.getItemAtPosition(cntrySpinner.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),cntry,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    public void prgSpinnerOnClick(){
        prgSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                prg = prgSpinner.getItemAtPosition(prgSpinner.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),prg,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }


}


