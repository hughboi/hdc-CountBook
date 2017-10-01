package com.example.hugh.countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EditCounterActivity extends AppCompatActivity {
    private EditText editCounterName;
    private EditText lastModifiedDate;
    private EditText editInitialValue;
    private EditText editCurrentValue;
    private EditText editComment;
    private Button updateButton;
    private Button deleteButton;
    private Button resetButton;
    private Counter clickedCounter;
    private int counterPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_counter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editCounterName = (EditText) findViewById(R.id.editCounterName);
        lastModifiedDate = (EditText) findViewById(R.id.editDate);
        editInitialValue = (EditText) findViewById(R.id.editInitialValue);
        editCurrentValue = (EditText) findViewById(R.id.editCurrentValue);
        editComment = (EditText) findViewById(R.id.editComment);
        updateButton = (Button) findViewById(R.id.updateCounter);
        deleteButton = (Button) findViewById(R.id.deleteCounter);
        resetButton = (Button) findViewById(R.id.resetCounter);
        createButtonListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = getIntent();
        clickedCounter = (Counter) i.getParcelableExtra("clickedCounter");
        counterPosition = i.getIntExtra("position", -1);
        setViewText();
    }

    private void createButtonListeners(){
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validationSuccess()) {
                    Intent updateCounterIntent = new Intent(EditCounterActivity.this, MainActivity.class);
                    updateCounterIntent.putExtra("EDIT_TASK", "UPDATE");
                    clickedCounter.setCounterName(editCounterName.getText().toString());
                    clickedCounter.setInitialCounterValue(Integer.parseInt(editInitialValue.getText().toString()));
                    clickedCounter.setCurrentCounterValue(Integer.parseInt(editCurrentValue.getText().toString()));
                    clickedCounter.setComment(editComment.getText().toString());
                    updateCounterIntent.putExtra("updatedCounter", clickedCounter);
                    updateCounterIntent.putExtra("position", counterPosition);
                    setResult(RESULT_OK, updateCounterIntent);
                    finish();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deleteCounterIntent = new Intent(EditCounterActivity.this, MainActivity.class);
                deleteCounterIntent.putExtra("EDIT_TASK", "DELETE");
                deleteCounterIntent.putExtra("position", counterPosition);
                setResult(RESULT_OK, deleteCounterIntent);
                finish();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resetCounterIntent = new Intent(EditCounterActivity.this, MainActivity.class);
                resetCounterIntent.putExtra("EDIT_TASK", "RESET");
                resetCounterIntent.putExtra("position", counterPosition);
                setResult(RESULT_OK, resetCounterIntent);
                finish();
            }
        });
    }

    private void setViewText(){
        editCounterName.setText(clickedCounter.getCounterName());
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String LMD = "Last Modified Date: " + df.format(clickedCounter.getLastModifiedDate());
        lastModifiedDate.setText(LMD);
        editInitialValue.setText(Integer.toString(clickedCounter.getInitialCounterValue()));
        editCurrentValue.setText(Integer.toString(clickedCounter.getCurrentCounterValue()));
        editComment.setText(clickedCounter.getComment());
    }

    private boolean validationSuccess(){
        if(editCounterName.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "You must enter a name for the counter.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(editInitialValue.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "You must enter an initial value for the counter.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(editCurrentValue.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "You must enter a current value for the counter.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
