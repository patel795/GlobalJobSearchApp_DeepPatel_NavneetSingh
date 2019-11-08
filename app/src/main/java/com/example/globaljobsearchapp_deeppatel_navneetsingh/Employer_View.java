package com.example.globaljobsearchapp_deeppatel_navneetsingh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Employer_View extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer__view);
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
            Toast.makeText(this, "Post Job is clicked", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.jobs_posted){
            Toast.makeText(this, "Job Posted is clicked", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.job_applications){
            Toast.makeText(this, "Job Applications is clicked", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.log_out){
        //    return View(viewId);
        }
        return super.onOptionsItemSelected(item);
    }

}
