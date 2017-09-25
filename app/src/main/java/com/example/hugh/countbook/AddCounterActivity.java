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

public class AddCounterActivity extends AppCompatActivity {

    private EditText counterName;
    private EditText initialValue;
    private EditText comment;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counter);

        counterName = (EditText) findViewById(R.id.counterName);
        initialValue = (EditText) findViewById(R.id.initialValue);
        comment = (EditText) findViewById(R.id.comment);
    }

    private void createCounter(){
        createButton = (Button) findViewById(R.id.createCounter);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationSuccess()){
                    Intent mainIntent = new Intent(AddCounterActivity.this, MainActivity.class);
                    mainIntent.putExtra("counterName", counterName.getText().toString());
                    mainIntent.putExtra("initialValue", Integer.parseInt(initialValue.getText().toString()));
                    mainIntent.putExtra("comment", comment.getText().toString());
                    setResult(RESULT_OK, mainIntent);
                    finish();
                } else{
                    assert true;
                }
            }
        });
    }

    private Boolean validationSuccess(){
        return true;// Check if all fields are entered correctly
    }
}
