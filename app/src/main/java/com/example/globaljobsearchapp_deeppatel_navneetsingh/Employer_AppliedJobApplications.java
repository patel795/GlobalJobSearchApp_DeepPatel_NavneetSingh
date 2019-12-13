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

import java.util.ArrayList;

public class Employer_AppliedJobApplications extends AppCompatActivity {

    ListView job_applications;
    public static ArrayList<String> job_application = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer__applied_job_applications);

        job_applications = findViewById(R.id.listivew_job_applications);

        job_application.add("Student1");
        job_application.add("Student2");
        job_application.add("Student3");
        job_application.add("Student4");
        job_application.add("Student5");
        job_application.add("Student6");
        job_application.add("Student7");
        job_application.add("Student8");
        job_application.add("Student9");
        job_application.add("Student10");
        job_application.add("Student11");
        job_application.add("Student12");
        job_application.add("Student13");

        ArrayAdapter<String> jobApplicationAdapter =
                new ArrayAdapter<String>(Employer_AppliedJobApplications.this,android.R.layout.simple_list_item_1, job_application);
        job_applications.setAdapter(jobApplicationAdapter);

        job_applications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) job_applications.getItemAtPosition(position);
                Toast.makeText(Employer_AppliedJobApplications.this, clickedItem,Toast.LENGTH_SHORT).show();
            }
        });
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
            startActivity(new Intent(Employer_AppliedJobApplications.this, Employer_View.class));
            Toast.makeText(this, "Post Job is clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.jobs_posted) {
            startActivity(new Intent(Employer_AppliedJobApplications.this, Employer_JobsPosted.class));
            Toast.makeText(this, "Job Posted is clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.listivew_job_applications) {
            startActivity(new Intent(Employer_AppliedJobApplications.this, Employer_AppliedJobApplications.class));
            Toast.makeText(this, "Job Applications is clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.log_out) {
            //    return View(viewId);
            startActivity(new Intent(Employer_AppliedJobApplications.this, MainActivity.class));
            Toast.makeText(this, "LogOut", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
