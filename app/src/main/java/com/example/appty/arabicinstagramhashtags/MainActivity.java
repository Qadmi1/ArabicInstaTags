package com.example.appty.arabicinstagramhashtags;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final String INSTA_URL ="https://api.instagram.com/v1/tags/search?q=حلا&access_token=4580320737.5854d71.7ad20f499f1f4ee692c078bf186335dd";
    ArrayAdapter<String> adapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        listView.setAdapter(adapter);
        new FetchTags().execute();
    }

    class FetchTags extends AsyncTask<Void, Void, List<String>>{


        @Override
        protected List<String> doInBackground(Void... voids) {

            List<String> tags = QueryUtills.fetchHashTags(INSTA_URL);
            return tags;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);

            adapter.clear();
            if (adapter != null) {
                adapter.addAll(strings);
            }
            }
    }
}
