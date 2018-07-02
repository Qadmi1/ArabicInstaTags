package com.example.appty.arabicinstagramhashtags.Activities;

import android.app.LoaderManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appty.arabicinstagramhashtags.Networking.TagsLoader;
import com.example.appty.arabicinstagramhashtags.R;

import java.util.ArrayList;

/**
 * Created by appty on 30/06/18.
 */

public class HashTagActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<String>> {
    /** ArrayList that will receive the list of tags from Instagram API */
    ArrayList<String> list;

    /** URL for hashtags data from the Instagram dataset */
    static final String INSTA_URL ="https://api.instagram.com/v1/tags/search";

    /** The access token that will be used in the API */
    private static final String ACCESS_TOKEN = "4580320737.5854d71.7ad20f499f1f4ee692c078bf186335dd";

    /** Constant value for the ArrayList of Strings Loader */
    private static final int LOADER_ID = 1;

    /** The key for the passed tag name */
    public static final String CATEGORY_TAG = "category";

    /** The tag value will be held in this variable and it will be used in the Loader */
    private String hashtag;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    private Button copyButton;

    private static final String TAG = "HashTagActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hashtag_activity);
        // Receive the passed tag value
        hashtag = getIntent().getStringExtra(CATEGORY_TAG);

        copyButton = findViewById(R.id.copy_button);
        copyButton.setVisibility(View.GONE);
        mEmptyStateTextView = findViewById(R.id.empty_view);
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
        }else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            assert loadingIndicator != null;
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_connection);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        // First clear the adapter
//        adapter.clear();
//
//        // Make sure that that the passed List is not null or empty
//            if (tags != null && !tags.isEmpty())
//            adapter.addAll(tags);

        list = tags;
        final StringBuilder builder = new StringBuilder();
        TextView textView = findViewById(R.id.list_of_thirty_tags);
        for (int i =0 ; i<list.size(); i++) {

                builder.append(getString(R.string.hash) + list.get(i)+"  ");
                textView.setText(builder);

        }

        copyButton.setVisibility(View.VISIBLE);
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(getString(R.string.copy_label), builder.toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(HashTagActivity.this, R.string.copy_success, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<String>> loader) {
    }


}
