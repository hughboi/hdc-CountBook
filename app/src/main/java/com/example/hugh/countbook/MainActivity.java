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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int ADD_COUNTER_REQUEST = 1;
    private static final int EDIT_COUNTER_REQUEST = 2;
    private ListView counterListView;
    private TextView numCountView;
    private Button addCounterButton;
    private ArrayList<Counter> counterItems;
    private ArrayAdapter<Counter> adapter;

    static class ViewHolder{
        TextView counterName;
        TextView currentCounterValue;
        TextView lastModifiedDate;
        Button incrementButton;
        Button decrementButton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numCountView = (TextView) findViewById(R.id.numCounters);
        counterListView = (ListView) findViewById(R.id.counterListView);
        addCounterButton = (Button) findViewById(R.id.addCounter);
        initListeners();
    }

    @Override
    protected  void onStart(){
        super.onStart();
        counterItems = AppStorage.loadFromFile(this.getApplicationContext());
        numCountView.setText("Active Counters: " + counterItems.size());
        adapter = new CounterListAdapter();
        counterListView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == ADD_COUNTER_REQUEST && resultCode == RESULT_OK){
            String counterName = data.getStringExtra("counterName");
            int initialValue = data.getIntExtra("initialValue", 0);
            String comment = data.getStringExtra("comment");
            Counter newCounter = new Counter(counterName, initialValue, comment);
            counterItems.add(newCounter);
            AppStorage.saveInFile(counterItems, getApplicationContext());
            adapter.notifyDataSetChanged();
        }
        else if (requestCode == EDIT_COUNTER_REQUEST && resultCode == RESULT_OK){
            switch(data.getStringExtra("EDIT_TASK")){
                case "UPDATE":
                    Counter updatedCounter = (Counter) data.getParcelableExtra("updatedCounter");
                    Counter oldCounter = counterItems.get(data.getIntExtra("position", -1));
                    oldCounter.setCounterName(updatedCounter.getCounterName());
                    oldCounter.setInitialCounterValue(updatedCounter.getInitialCounterValue());
                    oldCounter.setCurrentCounterValue(updatedCounter.getCurrentCounterValue());
                    oldCounter.setComment(updatedCounter.getComment());
                    AppStorage.saveInFile(counterItems, getApplicationContext());
                    adapter.notifyDataSetChanged();
                    break;
                case "DELETE":
                    counterItems.remove(data.getIntExtra("position", -1));
                    AppStorage.saveInFile(counterItems, getApplicationContext());
                    adapter.notifyDataSetChanged();
                    break;
                case "RESET":
                    counterItems.get(data.getIntExtra("position", -1)).resetCounterValue();
                    adapter.notifyDataSetChanged();
                    AppStorage.saveInFile(counterItems, getApplicationContext());
                    break;
            }
        }
        else{
            assert false;
        }
    }

    private void initListeners() {
        // Register clicking on list item
        counterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Counter clickedCounter = counterItems.get(position);
                Intent editCounterIntent = new Intent(MainActivity.this, EditCounterActivity.class);
                editCounterIntent.putExtra("clickedCounter", clickedCounter);
                editCounterIntent.putExtra("position", position);
                startActivityForResult(editCounterIntent, EDIT_COUNTER_REQUEST);
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

            // Find counter to work with in the list view
            Counter currentCounter = counterItems.get(position);

            // Set the counter name in the view object within the list view
            holder.counterName  = (TextView) itemView.findViewById(R.id.counterName);
            holder.counterName.setText(currentCounter.getCounterName());

            // Set the last modified date field in the view object within the list view
            holder.lastModifiedDate = (TextView) itemView.findViewById(R.id.lastModifiedDate);
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String LMD = "Last Modified Date: " + df.format(currentCounter.getLastModifiedDate());
            holder.lastModifiedDate.setText(LMD);

            // set the current counter value in the view object within the list view
            holder.currentCounterValue = (TextView) itemView.findViewById(R.id.currentValue);
            String counterValue = "Current Value: " + currentCounter.getCurrentCounterValue();
            holder.currentCounterValue.setText(counterValue);

            // set on click listeners for the increment/decrement buttons in the view object
            holder.incrementButton = (Button) itemView.findViewById(R.id.incrementButton);
            holder.decrementButton = (Button) itemView.findViewById(R.id.decrementButton);
            initViewButtonListeners(holder, currentCounter);

            return itemView;
        }
    }

    private void initViewButtonListeners(ViewHolder viewHolder, final Counter associatedCounter){
        viewHolder.incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                associatedCounter.incrementCurrentCounterValue();
                adapter.notifyDataSetChanged();
                AppStorage.saveInFile(counterItems, getApplicationContext());
            }
        });

        viewHolder.decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(associatedCounter.getCurrentCounterValue() == 0){
                    Toast.makeText(getApplicationContext(), "Counter cannot go less than zero.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    associatedCounter.decrementCurrentCounterValue();
                    adapter.notifyDataSetChanged();
                    AppStorage.saveInFile(counterItems, getApplicationContext());
                }
            }
        });
    }
}
