package com.example.hugh.countbook;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String STORAGE_FILE = "file.sav";
    private static final int ADD_COUNTER_REQUEST = 1;
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

    @Override
    protected void onResume(){
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == ADD_COUNTER_REQUEST && resultCode == RESULT_OK){
            // Retrieve information from add counter activity, and pass

        } else{
            assert false; // !!!! CHANGE LATER !!!!
        }
    }

    private void populateListView(){
        counterItems = new ArrayList<Counter>();
        Counter counter1 = new Counter("Counter1", 5);
        Counter counter2 = new Counter("Counter2", 3);
        counterItems.add(counter1);
        counterItems.add(counter2);

        adapter = new CounterListAdapter();
        counterList.setAdapter(adapter);
    }

    private void initListeners() {
        // Register clicking on list item
        counterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Counter clickedCounter = counterItems.get(position);
                String message = "You clicked position " + position +
                        ", which is counter: " + clickedCounter.getCounterName();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

        // Register add counter button
        addCounterButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent addCounterIntent = new Intent(MainActivity.this, AddCounterActivity.class);
                startActivityForResult(addCounterIntent, ADD_COUNTER_REQUEST);
            }
        });
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(STORAGE_FILE,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter (new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(counterItems, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    static class ViewHolder{
        TextView counterName;
        TextView currentCounterValue;
        Button incrementButton;
        Button decrementButton;
    }
    // Idea taken from https://www.youtube.com/watch?v=WRANgDgM2Zg
    // 2017-09-24
    private class CounterListAdapter extends ArrayAdapter<Counter> {
        public CounterListAdapter(){
            super(MainActivity.this, R.layout.counter_item, counterItems);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = convertView;

            if (itemView == null){
                itemView = inflator.inflate(R.layout.counter_item, parent, false);
            }

            // Find car to work with
            Counter currentCounter = counterItems.get(position);

            // Fill the view
            holder.counterName  = (TextView) itemView.findViewById(R.id.counterName);
            holder.counterName.setText(currentCounter.getCounterName());
            holder.currentCounterValue = (TextView) itemView.findViewById(R.id.currentValue);
            String counterValue = "Current Value: " + currentCounter.getCurrentCounterValue();
            holder.currentCounterValue.setText(counterValue);
            Button incrementButton = (Button) itemView.findViewById(R.id.incrementButton);
            Button decrementButton = (Button) itemView.findViewById(R.id.decrementButton);

            return itemView;
        }
    }
}
