package com.example.appty.arabicinstagramhashtags.Activities;

import android.app.LoaderManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appty.arabicinstagramhashtags.Networking.TagsLoader;
import com.example.appty.arabicinstagramhashtags.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

/**
 * Created by appty on 10/07/18.
 */

public class SearchTags extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<String>>,
        View.OnClickListener {
    /**
     * ArrayList that will receive the list of tags from Instagram API
     */
    ArrayList<String> list;

    /**
     * URL for hashtags data from the Instagram dataset
     */
    static final String INSTA_URL = "https://api.instagram.com/v1/tags/search";

    /**
     * The access token that will be used in the API
     */
    private static final String ACCESS_TOKEN = "4580320737.5854d71.7ad20f499f1f4ee692c078bf186335dd";

    /**
     * Constant value for the ArrayList of Strings Loader
     */
    private static final int LOADER_ID = 1;

    /**
     * The key for the passed tag name
     */
    public static final String CATEGORY_TAG = "category";

    /**
     * The tag value will be held in this variable and it will be used in the Loader
     */
    private String hashtag;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    /**
     * EditText that will receive the search staring
     */
    private EditText searchEditText;

    /**
     * ImageButton that will search for the hashTag
     */
    private ImageButton searchButton;

    private View loadingIndicator;

    private ImageButton copyButton;

    private ImageButton instaCopyButton;

    private ImageButton faceCopyButton;

    private ImageButton twitterCopyButton;

    private InterstitialAd mInterstitialAd;

    //declare boolean
    private boolean copyClicked = false;
    private int instaCounter = 0;
    private boolean instaCicked = false;

    private int faceCounter = 0;
    private boolean faceClicked = false;

    private int twitterCounter = 0;
    private boolean twitterClicked = false;
    /**
     * Banned Ad view
     */
    private AdView mAdView;

    private static final String TAG = "HashTagActivity";

    final StringBuilder builder = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tags);


        MobileAds.initialize(this, "ca-app-pub-5555881324034144~6250596660");

        // Add the interstitial Add
        addInterstitialAd();

        findViews();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hashtag = searchEditText.getText().toString().trim();

                ConnectivityManager cm =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                // Check the connection
                assert cm != null;
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                // Make sure there is a connection before fetching the data
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    // Create a reference to the LoaderManager
                    LoaderManager loaderManager = getLoaderManager();
                    loadingIndicator.setVisibility(View.VISIBLE);
                    // Initialize the loader to create a new loader or use the existing one.
                    loaderManager.initLoader(LOADER_ID, null, SearchTags.this);
                } else {
                    // Otherwise, display error
                    // First, hide loading indicator so error message will be visible
                    loadingIndicator = findViewById(R.id.loading_indicator_search);
                    assert loadingIndicator != null;
                    loadingIndicator.setVisibility(View.GONE);

                    // Update empty state with no connection error message
                    mEmptyStateTextView.setText(R.string.no_connection);

                    // Add the up button
                }
            }
        });

        getSupportActionBar().setTitle(R.string.search_for_tags);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Add the banner Add
        addBannerAdd();
    }

    private void findViews() {
        mEmptyStateTextView = findViewById(R.id.empty_view_search);

        searchEditText = findViewById(R.id.search_edit_text);

        loadingIndicator = findViewById(R.id.loading_indicator_search);

        searchButton = findViewById(R.id.search_image_button);

        copyButton = findViewById(R.id.copy_image_button_search);
//
//        // Make the button invisible until the tags finish loading
//        copyButton.setVisibility(View.GONE);


        instaCopyButton = findViewById(R.id.copy_insta_button_search);

//        // Make the button invisible until the tags finish loading
//        instaCopyButton.setVisibility(View.GONE);

        faceCopyButton = findViewById(R.id.copy_face_button_search);

//        // Make the button invisible until the tags finish loading
//        faceCopyButton.setVisibility(View.GONE);

        twitterCopyButton = findViewById(R.id.copy_twitter_button_search);

//        // Make the button invisible until the tags finish loading
//        twitterCopyButton.setVisibility(View.GONE);
    }


    private void addInterstitialAd() {
        final AdRequest adRequest = new AdRequest.Builder().build();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5555881324034144/8493616623");
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                AdRequest adRequest1 = new AdRequest.Builder().build();
                mInterstitialAd.loadAd(adRequest1);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Toast.makeText(SearchTags.this, Integer.toString(errorCode), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Toast.makeText(SearchTags.this, "onAdOpened", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Toast.makeText(SearchTags.this, "onAdLeftApplication", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdClosed() {
                if (instaCicked) {
                    openInstaApp();
                }
                if (faceClicked) {
                    openFaceaApp();
                }
                if (twitterClicked) {
                    openTwitterApp();
                }
            }
        });
    }

    private void addBannerAdd() {
        // Find the Ad view then build it then load it(Banner)
        mAdView = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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
        loadingIndicator.setVisibility(View.GONE);

        list = tags;
        TextView textView = findViewById(R.id.list_of_thirty_tags_search);
        textView.setTextIsSelectable(true);
        for (int i = 0; i < list.size(); i++) {

            builder.append(getString(R.string.hash) + list.get(i) + "  ");
            textView.setText(builder);
        }

        copyButton.setVisibility(View.VISIBLE);
        copyButton.setOnClickListener(this);

        instaCopyButton.setVisibility(View.VISIBLE);
        instaCopyButton.setOnClickListener(this);

        faceCopyButton.setVisibility(View.VISIBLE);
        faceCopyButton.setOnClickListener(this);

        twitterCopyButton.setVisibility(View.VISIBLE);
        twitterCopyButton.setOnClickListener(this);

        copyClicked = false;
        instaCicked = false;
        faceClicked = false;
        twitterClicked = false;

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<String>> loader) {
        list.clear();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (copyClicked) {
            mInterstitialAd.show();

        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (copyClicked) {
            mInterstitialAd.show();
        }
        if (instaCicked) {
            mInterstitialAd.show();
        }
        if (faceClicked) {
            mInterstitialAd.show();

        }
        if (twitterClicked) {
            mInterstitialAd.show();
        }
    }

    private void openInstaApp() {
        PackageManager manager = this.getPackageManager();
        try {
            Intent intent = manager.getLaunchIntentForPackage("com.instagram.android");
            if (intent == null) {
//                Log.d(TAG, "intent==null");
                throw new PackageManager.NameNotFoundException();
            }
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            this.startActivity(intent);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void openFaceaApp() {
        Intent facebookIntent = openFacebook(this);
        startActivity(facebookIntent);
    }

    public static Intent openFacebook(Context context) {
        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://page/376227335860239"));
        } catch (Exception e) {

            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/karthikofficialpage"));
        }
    }

    private void openTwitterApp() {
        PackageManager manager = this.getPackageManager();
        try {
            Intent intent = manager.getLaunchIntentForPackage("com.twitter.android");
            if (intent == null) {
//                Log.d(TAG, "intent==null");
                throw new PackageManager.NameNotFoundException();
            }
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            this.startActivity(intent);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        ClipboardManager clipboard;
        ClipData clip;
        switch (view.getId()) {

            case R.id.copy_image_button_search:
                copyClicked = true;
                clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clip = ClipData.newPlainText(getString(R.string.copy_label), builder.toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(SearchTags.this,R.string.copy_insta_success, Toast.LENGTH_SHORT).show();
                break;

            case R.id.copy_insta_button_search:
                instaCicked = true;
                instaCounter++;
                clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clip = ClipData.newPlainText(getString(R.string.copy_label), builder.toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(SearchTags.this,R.string.copy_insta_success, Toast.LENGTH_SHORT).show();
                if (instaCounter <= 1) {
                    mInterstitialAd.show();
                } else {
                    openInstaApp();
                }
                break;

            case R.id.copy_face_button_search:
                faceClicked = true;
                faceCounter++;
                clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clip = ClipData.newPlainText(getString(R.string.copy_label), builder.toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(SearchTags.this, R.string.copy_success, Toast.LENGTH_SHORT).show();

                if (faceCounter <= 1) {
                    mInterstitialAd.show();

                } else {
                    openFaceaApp();
                }
                break;

            case R.id.copy_twitter_button_search:
                twitterClicked = true;
                twitterCounter++;
                clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clip = ClipData.newPlainText(getString(R.string.copy_label), builder.toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(SearchTags.this, R.string.copy_success, Toast.LENGTH_SHORT).show();
                if (twitterCounter <= 1) {
                    mInterstitialAd.show();
                } else {
                    openTwitterApp();
                }
                break;
        }
    }
}


