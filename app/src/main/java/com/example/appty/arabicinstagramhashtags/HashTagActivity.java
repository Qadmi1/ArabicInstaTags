package com.example.appty.arabicinstagramhashtags;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.appty.arabicinstagramhashtags.Networking.TagsLoader;

import java.util.ArrayList;

/**
 * Created by appty on 30/06/18.
 */

public class HashTagActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<String>> {

    public static final String TAGS = "tags";
    ArrayList<String> list;
    static final String INSTA_URL ="https://api.instagram.com/v1/tags/search";
    private static final String ACCESS_TOKEN = "4580320737.5854d71.7ad20f499f1f4ee692c078bf186335dd";
    private static final int LOADER_ID = 1;
    public static final String CATEGORY_TAG = "category";
    private String hashtag;
    private static final String TAG = "HashTagActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hashtag_activity);
        hashtag = getIntent().getStringExtra(CATEGORY_TAG);



        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        // Make sure there is a connection before fetching the data
        if (activeNetwork != null && activeNetwork.isConnected()) {
            // Create a reference to the LoaderManager
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader to create a new loader or use the existing one.
            loaderManager.initLoader(LOADER_ID, null, this);
        }
        /**
         * ************** Add else here to handle the empty view if you will use it ****************
         */

    }

    @Override
    public Loader<ArrayList<String>> onCreateLoader(int id, Bundle args) {


        Uri baseUri = Uri.parse(INSTA_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", hashtag);
        uriBuilder.appendQueryParameter("access_token", ACCESS_TOKEN);

        return new TagsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<String>> loader, ArrayList<String> tags) {
        // First clear the adapter
//        adapter.clear();
//
//        // Make sure that that the passed List is not null or empty
//            if (tags != null && !tags.isEmpty())
//            adapter.addAll(tags);

        list = tags;
        StringBuilder builder = new StringBuilder();
        TextView textView = findViewById(R.id.list_of_thirty_tags);
        for (int i =0 ; i<list.size(); i++) {

                builder.append(getString(R.string.hash) + list.get(i)+"  ");
                textView.setText(builder);

        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<String>> loader) {
    }


}
