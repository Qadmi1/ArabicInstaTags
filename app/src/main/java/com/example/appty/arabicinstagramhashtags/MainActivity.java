package com.example.appty.arabicinstagramhashtags;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appty.arabicinstagramhashtags.vo.HashTag;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<String>>{
    static final String INSTA_URL ="https://api.instagram.com/v1/tags/search?q=حلا&access_token=" +
            "4580320737.5854d71.7ad20f499f1f4ee692c078bf186335dd";
    ArrayAdapter<String> adapter;
    private ListView listView;
    private static final int LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        listView.setAdapter(adapter);

        LoaderManager loaderManager = getLoaderManager();
        // Initialize the loader to create a new loader or use the existing one.
        loaderManager.initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<List<String>> onCreateLoader(int id, Bundle args) {




        return new TagsLoader(this, INSTA_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<String>> loader, List<String> tags) {
        // First clear the adapter
        adapter.clear();

        // Make sure that that the passed List is not null or empty
            if (tags != null && !tags.isEmpty())
            adapter.addAll(tags);
    }

    @Override
    public void onLoaderReset(Loader<List<String>> loader) {

    }


}
