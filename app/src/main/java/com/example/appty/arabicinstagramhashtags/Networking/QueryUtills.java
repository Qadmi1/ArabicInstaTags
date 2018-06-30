package com.example.appty.arabicinstagramhashtags.Networking;

import android.text.TextUtils;
import android.util.Log;

import com.example.appty.arabicinstagramhashtags.vo.HashTag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by appty on 17/06/18.
 */

public class QueryUtills {
    private static final String LOG_TAG = QueryUtills.class.getSimpleName();

    public static ArrayList<String> fetchHashTags(String requestUrl) {
        // Create URL object
        URL url = null;

        try {
            url = new URL(requestUrl);
        }catch (MalformedURLException e)

        {
            e.printStackTrace();
        }

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        ArrayList<String> hashTags = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return hashTags;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    private static ArrayList<String> extractFeatureFromJson(String instaTags) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(instaTags)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding allHashTags to
        ArrayList<HashTag> allHashTags = new ArrayList<>();
        List<HashTag> top30Tags = new ArrayList<>();

        List<String> stringTags = new ArrayList<>();
        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(instaTags);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or allHashTags).
            JSONArray instaTagsArray = baseJsonResponse.getJSONArray("data");

            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            for (int i = 0; i < instaTagsArray.length(); i++) {

                // Get a single earthquake at position i within the list of allHashTags
                JSONObject currentTag = instaTagsArray.getJSONObject(i);

                int media_count = currentTag.getInt("media_count");
                Log.v("medis count",  String.valueOf(media_count));

                String name = currentTag.getString("name");

                HashTag hashTag = new HashTag(name, media_count);
                // Create a new {@link Earthquake} object with the magnitude, location, time,
                // and url from the JSON response.

                // Add the new {@link Earthquake} to the list of allHashTags.
                allHashTags.add(hashTag);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }



        return fetchTheTop30Tags(allHashTags);
    }

    private static ArrayList<String> fetchTheTop30Tags(List<HashTag> allHashTags) {
        ArrayList<String> hashTags = new ArrayList<>();

        int i;
        int max = 0, index;
        String maxString = "";
        for (int j = 0; j < 30; j++) {
            max = allHashTags.get(0).getMediaCount();
            maxString = allHashTags.get(0).getStringTag();
            index = 0;
            for (i = 1; i < allHashTags.size(); i++) {
                if (max < allHashTags.get(i).getMediaCount()) {
                    max = allHashTags.get(i).getMediaCount();
                    maxString =allHashTags.get(i).getStringTag();
                    index = i;
                }
            }
            hashTags.add(maxString);
            allHashTags.set(index, new HashTag("", Integer.MIN_VALUE));

        }
        return hashTags;
    }
    }


