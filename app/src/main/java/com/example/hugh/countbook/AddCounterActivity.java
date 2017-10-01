/*
 * AddCounterActivity.java
 *
 * Version: 1.0
 *
 * Date: 2017-10-01
 *
 * Author: Hugh Craig
 *
 * Copyright (c) 2017. CMPUT 301. University of Alberta - All Rights Reserved. You may use, distribute, or modify
 *  this code under terms and conditions of the Code of Student Behavior at the University of Alberta. You may find a
 *  copy of the license in th project. Otherwise please contact hdc@ualberta.ca
 */
package com.example.hugh.countbook;

import android.content.Intent;
import android.support.annotation.StringDef;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddCounterActivity extends AppCompatActivity {

    private EditText counterName;
    private EditText initialValue;
    private EditText comment;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        counterName = (EditText) findViewById(R.id.counterName);
        initialValue = (EditText) findViewById(R.id.initialValue);
        comment = (EditText) findViewById(R.id.comment);
        createCounterListener();
    }

    private void createCounterListener(){
        createButton = (Button) findViewById(R.id.createCounter);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationSuccess()){
                    Intent resultIntent = createResultIntent();
                    finish();
                }
            }
        });
    }

    private boolean validationSuccess(){
        if(counterName.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "You must enter a name for the counter.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(initialValue.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "You must enter an initial value for the counter.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Intent createResultIntent(){
        Intent mainIntent = new Intent(AddCounterActivity.this, MainActivity.class);
        mainIntent.putExtra("counterName", counterName.getText().toString());
        mainIntent.putExtra("initialValue", Integer.parseInt(initialValue.getText().toString()));
        mainIntent.putExtra("comment", comment.getText().toString());
        setResult(RESULT_OK, mainIntent);
        return mainIntent;
    }
}
