package com.example.hwhong.asynctaskexplore;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    private String[] companies = {"Beme", "Kickstarter", "Twitter", "Tumblr", "Google", "Uber", "Lyft",
        "Facebook", "Amazon", "Instagram", "Linkedin", "Visa", "Bloomberg", "New York Times", "Airbnb"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                new ArrayList<String>()));

        new MyTask().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),
                        "You clicked on "+ companies[i] , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class MyTask extends AsyncTask<Void, String, String> {

        ArrayAdapter<String> adapter;
        private ProgressBar progressBar;
        int counter;

        @Override
        protected void onPreExecute() {
            adapter = (ArrayAdapter<String>) listView.getAdapter();
            progressBar = (ProgressBar)findViewById(R.id.progressBar);
            progressBar.setMax(15);
            progressBar.setProgress(0);
            progressBar.setVisibility(View.VISIBLE);
            counter = 0;
        }

        @Override
        protected String doInBackground(Void... voids) {
            for (String name: companies) {
                //invokes OnProgressUpdate method
                publishProgress(name);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "All names added";

        }

        @Override
        protected void onProgressUpdate(String... values) {
            adapter.add(values[0]);
            counter++;
            progressBar.setProgress(counter);
        }

        @Override
        protected void onPostExecute(String result) {
            //invoked on UI after computation finishes
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            progressBar.setProgress(View.GONE);
        }
    }
}
