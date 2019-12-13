package com.example.globaljobsearchapp_deeppatel_navneetsingh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employermenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.post_job){
            startActivity(new Intent(BaseActivity.this, Employer_View.class));
            Toast.makeText(this, "Post Job is clicked", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.jobs_posted){
            startActivity(new Intent(BaseActivity.this, Employer_JobsPosted.class));
            Toast.makeText(this, "Job Posted is clicked", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.listivew_job_applications){
            startActivity(new Intent(BaseActivity.this, Employer_AppliedJobApplications.class));
            Toast.makeText(this, "Job Applications is clicked", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.log_out){
            //    return View(viewId);
            startActivity(new Intent(BaseActivity.this, MainActivity.class));
            Toast.makeText(this, "LogOut", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
