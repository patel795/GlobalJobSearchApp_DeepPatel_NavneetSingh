package com.example.globaljobsearchapp_deeppatel_navneetsingh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Employers_editJobs extends AppCompatActivity {

    EditText edt_companyName;
    Button btn_close;
    Button btn_edit;
    Spinner spinner_country;
    Spinner spinner_program;
    Spinner spinner_language;
    EditText edt_jobdescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employers_edit_jobs);

        btn_close = findViewById(R.id.btn_close);

        btn_close.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Employers_editJobs.this, Employer_JobsPosted.class));
            }
        });
    }
}
