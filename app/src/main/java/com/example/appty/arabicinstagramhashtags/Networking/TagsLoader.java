package com.example.appty.arabicinstagramhashtags.Networking;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by appty on 28/06/18.
 */

public class TagsLoader extends AsyncTaskLoader<ArrayList<String>> {

    private String INSTAGRAM_REQUEST_URL;

    public TagsLoader(Context context, String passedUrl) {
        super(context);
        INSTAGRAM_REQUEST_URL = passedUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<String> loadInBackground() {
        return QueryUtills.fetchHashTags(INSTAGRAM_REQUEST_URL);
    }
}
