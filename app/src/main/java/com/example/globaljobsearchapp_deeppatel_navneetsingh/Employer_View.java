package com.example.globaljobsearchapp_deeppatel_navneetsingh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Employer_View extends AppCompatActivity {

    public static EditText descp;
    public static String country;
    public static String program;
    Spinner mySpinnerCountry;
    Spinner mySpinnerProgram;
    Button btn_post;

    String URL="http://192.168.0.30/GlobalJobSearch/api/DropDownFilter";
    String JobDescriptionURL="http://192.68.0.30/GlobalJobSearch/SaveJobDescription";
    public static ArrayList<String> CountryName = new ArrayList<String>();
    public static ArrayList<String> ProgramName = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer__view);

        descp = findViewById(R.id.edtText_description);

        mySpinnerCountry = findViewById(R.id.spinner_country);
        mySpinnerProgram = findViewById(R.id.spinner_program);
        btn_post = findViewById(R.id.btn_post);

        FetchDropDownData process = new FetchDropDownData();
        process.execute();

        loadSpinnerData(URL);
        countrySpinnerOnClick();
        programSpinnerOnClick();

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),country,Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),program,Toast.LENGTH_SHORT).show();

                RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest=new StringRequest(Request.Method.POST, JobDescriptionURL, new Response.Listener<String>() {

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
                        map.put("jobDescription", descp.getText().toString());
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


    public void countrySpinnerOnClick(){
        mySpinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country = mySpinnerCountry.getItemAtPosition(mySpinnerCountry.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),country,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    public void programSpinnerOnClick(){
        mySpinnerProgram.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                program = mySpinnerProgram.getItemAtPosition(mySpinnerProgram.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),program,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }

    public void loadSpinnerData(String url) {
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try{
                    JSONArray JA = new JSONArray(response);
                    for(int i=0;i<JA.length();i++){
                        JSONObject jsonObject1=JA.getJSONObject(i);
                        if(jsonObject1.getString("Country") != "null"){
                            CountryName.add("" + jsonObject1.getString("Country"));
                        }
                        if(jsonObject1.getString("Program") != "null"){
                            ProgramName.add("" + jsonObject1.getString("Program"));
                        }
                    }

                    descp.setText("" + CountryName);
                    ArrayAdapter<String> country_adapter = new ArrayAdapter<String>(Employer_View.this, R.layout.color_spinner_layout, CountryName);
                    country_adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                    mySpinnerCountry.setAdapter(country_adapter);

                    ArrayAdapter<String> program_adapter = new ArrayAdapter<String>(Employer_View.this, R.layout.color_spinner_layout, ProgramName);
                    program_adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                    mySpinnerProgram.setAdapter(program_adapter);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employermenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
       // int viewId = R.layout.activity_student_view__company_search;

        if(id == R.id.post_job){
            startActivity(new Intent(Employer_View.this, Employer_View.class));
            Toast.makeText(this, "Post Job is clicked", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.jobs_posted){
            startActivity(new Intent(Employer_View.this, Employer_JobsPosted.class));
            Toast.makeText(this, "Job Posted is clicked", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.job_applications){
            startActivity(new Intent(Employer_View.this, Employer_AppliedJobApplications.class));
            Toast.makeText(this, "Job Applications is clicked", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.log_out){
        //    return View(viewId);
            startActivity(new Intent(Employer_View.this, MainActivity.class));
            Toast.makeText(this, "LogOut", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
