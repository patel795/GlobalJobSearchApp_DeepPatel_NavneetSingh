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
            Toast.makeText(this, "Post Job is clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        }
        else if(id == R.id.jobs_posted){
            Toast.makeText(this, "Job Posted is clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Employer_View.class));
        }
        else if(id == R.id.job_applications){
            Toast.makeText(this, "Job Applications is clicked", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.log_out){
            startActivity(new Intent(this, StudentView_CompanySearch.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
