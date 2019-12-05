package com.example.globaljobsearchapp_deeppatel_navneetsingh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Employers_editJobs extends AppCompatActivity {

    public static String country;
    public static String program;
    public static String language;
    EditText edt_companyName;
    Button btn_close;
    Button btn_edit;
    Spinner spinner_country;
    Spinner spinner_program;
    Spinner spinner_language;
    EditText edt_jobdescription;

    String URL="http://192.168.0.25/GlobalJobSearch/api/DropDownFilter";
    String postedJobURL = "http://192.168.0.25/GlobalJobSearch/JobDescriptionData/Get?id=";
    String EditDescriptionURL="http://192.168.0.25/GlobalJobSearch/EditJobDescription";
    public static ArrayList<String> CountryName = new ArrayList<String>();
    public static ArrayList<String> ProgramName = new ArrayList<String>();
    public static ArrayList<String> LanguageName = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employers_edit_jobs);

        btn_close = findViewById(R.id.btn_close);
        btn_edit = findViewById(R.id.btn_edit);
        spinner_country = findViewById(R.id.spinner_country);
        spinner_program = findViewById(R.id.spinner_program);
        spinner_language = findViewById(R.id.spinner_language);
        edt_jobdescription = findViewById(R.id.edttext_jobdescription);
        edt_companyName = findViewById(R.id.edttext_companyname);

        CountryName.clear();
        ProgramName.clear();
        LanguageName.clear();

        final String dataID = getIntent().getStringExtra("ID");
        loadPostedJobData(postedJobURL + dataID);
        countrySpinnerOnClick();
        programSpinnerOnClick();
        languageSpinnerOnClick();
        btn_close.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Employers_editJobs.this, Employer_JobsPosted.class));
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest=new StringRequest(Request.Method.POST, EditDescriptionURL, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),country,Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String,String> map = new HashMap<>();
                        map.put("Country",country);
                        map.put("Program", program);
                        map.put("jobDescription", edt_jobdescription.getText().toString());
                        map.put("language",language);
                        map.put("CompanyName", edt_companyName.getText().toString());
                        map.put("ID", dataID);
                        return map;
                    }
                };
                int socketTimeout = 30000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                requestQueue.add(stringRequest);
            }
        });
    }

    public void spinnerArrayAdapter(){
        ArrayAdapter<String> country_adapter = new ArrayAdapter<String>(Employers_editJobs.this, R.layout.color_spinner_layout, CountryName);
        country_adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner_country.setAdapter(country_adapter);

        ArrayAdapter<String> program_adapter = new ArrayAdapter<String>(Employers_editJobs.this, R.layout.color_spinner_layout, ProgramName);
        program_adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner_program.setAdapter(program_adapter);

        ArrayAdapter<String> language_adapter = new ArrayAdapter<String>(Employers_editJobs.this, R.layout.color_spinner_layout, LanguageName);
        language_adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner_language.setAdapter(language_adapter);
    }

    public void loadSpinnerData(String response){
        try{
            JSONArray JA = new JSONArray(response);
            for(int i=0;i<JA.length();i++){
                JSONObject jsonObject1=JA.getJSONObject(i);
                if(jsonObject1.getString("Country") != "null" && !CountryName.contains(jsonObject1.getString("Country"))){
                    CountryName.add("" + jsonObject1.getString("Country"));
                }
                if(jsonObject1.getString("Program") != "null" && !ProgramName.contains(jsonObject1.getString("Program"))){
                    ProgramName.add("" + jsonObject1.getString("Program"));
                }
                if(jsonObject1.getString("Language") != "null" && !LanguageName.contains(jsonObject1.getString("Language"))){
                    LanguageName.add("" + jsonObject1.getString("Language"));
                }
            }

            spinnerArrayAdapter();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void loadJobData(String response){
        try{
            JSONObject JA = new JSONObject(response);
            /*if(JA.getJSONObject("Country") && !CountryName.contains(jsonObject1.getString("Country"))){
                CountryName.add("" + jsonObject1.getString("Country"));
            }
            if(jsonObject1.getString("Program") != "null" && !ProgramName.contains(jsonObject1.getString("Program"))){
                ProgramName.add("" + jsonObject1.getString("Program"));
            }
            if(jsonObject1.getString("Language") != "null" && !LanguageName.contains(jsonObject1.getString("Language"))){
                LanguageName.add("" + jsonObject1.getString("Language"));
            }*/

            //String country = JA.getString("Country");

            edt_companyName.setText(JA.getString("CompanyName"));
            CountryName.add("" + JA.getString("Country"));
            ProgramName.add("" + JA.getString("Program"));
            LanguageName.add("" + JA.getString("Language"));
            edt_jobdescription.setText(JA.getString("Job_Description"));

        }catch (JSONException e){
            e.printStackTrace();
        }
        loadData(URL);
    }

    public void loadData(String url) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                loadSpinnerData(response);
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

    public void loadPostedJobData(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                loadJobData(response);
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

    public void countrySpinnerOnClick(){
        spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country = spinner_country.getItemAtPosition(spinner_country.getSelectedItemPosition()).toString();
                //Toast.makeText(getApplicationContext(),country,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    public void programSpinnerOnClick(){
        spinner_program.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                program = spinner_program.getItemAtPosition(spinner_program.getSelectedItemPosition()).toString();
                //Toast.makeText(getApplicationContext(),program,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    public void languageSpinnerOnClick(){
        spinner_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                language = spinner_language.getItemAtPosition(spinner_language.getSelectedItemPosition()).toString();
                //Toast.makeText(getApplicationContext(),program,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }
}
