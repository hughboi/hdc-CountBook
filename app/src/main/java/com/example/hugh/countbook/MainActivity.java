package com.example.hugh.countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String STORAGE_FILE = "file.sav";
    private ListView counterList;
    private TextView numCountView;
    private Button addCounterButton;
    private ArrayList<Counter> counterItems;
    private ArrayAdapter<Counter> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numCountView = (TextView) findViewById(R.id.numCounters);
        counterList = (ListView) findViewById(R.id.counterListView);
        addCounterButton = (Button) findViewById(R.id.addCounter);
        initListeners();
    }

    @Override
    protected  void onStart(){
        super.onStart();
        populateListView();

    }

    private void populateListView(){
        counterItems = new ArrayList<Counter>();
        Counter counter1 = new Counter("Counter1", 0);
        counterItems.add(counter1);

        adapter = new ArrayAdapter<Counter>(this, R.layout.counter_item, counterItems);
        counterList.setAdapter(adapter);
    }

    private void initListeners() {
        // Register clicking on list item
        counterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                String message = "You clicked # " + position +
                        ", which is string: " + textView.getText().toString();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

        // Register add counter button
        addCounterButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, AddCounterActivity.class);
                startActivity(intent);
            }
        });
    }
}
