package com.example.globaljobsearchapp_deeppatel_navneetsingh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class Employer_JobsPosted extends AppCompatActivity {

    ListView list_jobsposted;
    String URL="http://192.168.0.25/GlobalJobSearch/JobDescriptionData";
    public static ArrayList<String> companyName = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer__jobs_posted);

        list_jobsposted = findViewById(R.id.listview_jobsposted);
        loadCompanyName(URL);

        list_jobsposted.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list_jobsposted.getItemAtPosition(position);
                Toast.makeText(Employer_JobsPosted.this,clickedItem,Toast.LENGTH_LONG).show();
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

                    ArrayAdapter<String> companyListAdapter = new ArrayAdapter<String>(Employer_JobsPosted.this,android.R.layout.simple_list_item_1, companyName);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employermenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        // int viewId = R.layout.activity_student_view__company_search;

        if (id == R.id.post_job) {
            startActivity(new Intent(Employer_JobsPosted.this, Employer_View.class));
            Toast.makeText(this, "Post Job is clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.jobs_posted) {
            startActivity(new Intent(Employer_JobsPosted.this, Employer_JobsPosted.class));
            Toast.makeText(this, "Job Posted is clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.job_applications) {
            startActivity(new Intent(Employer_JobsPosted.this, Employer_AppliedJobApplications.class));
            Toast.makeText(this, "Job Applications is clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.log_out) {
            //    return View(viewId);
            startActivity(new Intent(Employer_JobsPosted.this, MainActivity.class));
            Toast.makeText(this, "LogOut", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
