package com.example.appty.arabicinstagramhashtags.Activities;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import com.example.appty.arabicinstagramhashtags.Adapters.SecondaryCategoryAdapter;
import com.example.appty.arabicinstagramhashtags.R;
import com.example.appty.arabicinstagramhashtags.vo.CategoryItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by appty on 04/07/18.
 */

public class SecondaryPage extends AppCompatActivity {
    /**
     * List of category names
     */
    private String[] categoryListName;

    /**
     * List of category images
     */
    private TypedArray categoryListImage;

    /**
     * Category Item list that will hold the name and the image of the category
     */
    ArrayList<CategoryItem> listOfCategories = new ArrayList<>();

    /**
     * Banned Ad view
     */
    private AdView mAdView;

    /**
     * The key for the passed tag name
     */
    public static final String CATEGORY_INDEX = "index";

    /**
     * The key for the passed title name
     */
    public static final String CATEGORY_TITLE = "title";

    /**
     * The index hold the passed index of the main category
     */
    private int index;

    /**
     * The title hold the passed title of the main category
     */
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-5555881324034144~6250596660");

        index = getIntent().getIntExtra(CATEGORY_INDEX, 0);

        title = getIntent().getStringExtra(CATEGORY_TITLE);

        getSupportActionBar().setTitle(title);

        // Find the recycler view
        RecyclerView recyclerView = findViewById(R.id.recycler_grid);
        // Receive the resources

        fillNameAndImage();

        // Fill the Category Item Array list
        for (int i = 0; i < categoryListName.length; i++) {
            listOfCategories.add(new CategoryItem(categoryListName[i], categoryListImage.getResourceId(i, 0)));
        }

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, span());

        recyclerView.setLayoutManager(layoutManager);

        final RecyclerView.Adapter adapter = new SecondaryCategoryAdapter(listOfCategories, SecondaryPage.this);


        ItemTouchHelper.Callback callbacks = new ItemTouchHelper.Callback() {
            // Try reducing the movement
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG, ItemTouchHelper.DOWN | ItemTouchHelper.UP
                        | ItemTouchHelper.START | ItemTouchHelper.END);
            }

            // Try applying different logic
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                if (from < to) {
                    for (int i = from; i < to; i++) {
                        Collections.swap(listOfCategories, i, i + 1);
                    }

                } else {

                    for (int i = from; i > to; i--) {
                        Collections.swap(listOfCategories, i, i - 1);
                    }
                }

                adapter.notifyItemMoved(from, to);

                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }


        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callbacks);

        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Add the banner Add
        BannerAdd();
    }

    private void fillNameAndImage() {

        switch (index) {
            case 1:
                categoryListName = getResources().getStringArray(R.array.like);
                categoryListImage = getResources().obtainTypedArray(R.array.like_images);
                break;
            case 2:
                categoryListName = getResources().getStringArray(R.array.desert);
                categoryListImage = getResources().obtainTypedArray(R.array.desert_images);
                break;
            case 3:
                categoryListName = getResources().getStringArray(R.array.country);
                categoryListImage = getResources().obtainTypedArray(R.array.countries_images);
                break;
            case 4:
                categoryListName = getResources().getStringArray(R.array.dress);
                categoryListImage = getResources().obtainTypedArray(R.array.dress_images);
                break;
            case 5:
                categoryListName = getResources().getStringArray(R.array.beauty);
                categoryListImage = getResources().obtainTypedArray(R.array.beauty_images);
                break;

            case 6:
                categoryListName = getResources().getStringArray(R.array.sport);
                categoryListImage = getResources().obtainTypedArray(R.array.sport_images);
                break;

            case 7:
                categoryListName = getResources().getStringArray(R.array.doctor);
                categoryListImage = getResources().obtainTypedArray(R.array.doctor_images);
                break;

            case 8:
                categoryListName = getResources().getStringArray(R.array.cook);
                categoryListImage = getResources().obtainTypedArray(R.array.cook_images);
                break;

            case 9:
                categoryListName = getResources().getStringArray(R.array.accessory);
                categoryListImage = getResources().obtainTypedArray(R.array.accessory_images);
                break;

            case 10:
                categoryListName = getResources().getStringArray(R.array.food);
                categoryListImage = getResources().obtainTypedArray(R.array.food_images);
                break;

            case 11:
                categoryListName = getResources().getStringArray(R.array.car);
                categoryListImage = getResources().obtainTypedArray(R.array.car_images);
                break;

            case 12:
                categoryListName = getResources().getStringArray(R.array.beverage);
                categoryListImage = getResources().obtainTypedArray(R.array.beverage_images);
                break;

            case 13:
                categoryListName = getResources().getStringArray(R.array.photo);
                categoryListImage = getResources().obtainTypedArray(R.array.photo_images);
                break;
            case 14:
                categoryListName = getResources().getStringArray(R.array.art);
                categoryListImage = getResources().obtainTypedArray(R.array.art_images);
                break;

            case 15:
                categoryListName = getResources().getStringArray(R.array.delivery);
                categoryListImage = getResources().obtainTypedArray(R.array.delivery_images);
                break;

            case 16:
                categoryListName = getResources().getStringArray(R.array.entertainment);
                categoryListImage = getResources().obtainTypedArray(R.array.entertainment_images);
                break;

            case 17:
                categoryListName = getResources().getStringArray(R.array.shoe);
                categoryListImage = getResources().obtainTypedArray(R.array.shoe_images);
                break;

            case 18:
                categoryListName = getResources().getStringArray(R.array.travel);
                categoryListImage = getResources().obtainTypedArray(R.array.travel_images);
                break;

            case 19:
                categoryListName = getResources().getStringArray(R.array.tech);
                categoryListImage = getResources().obtainTypedArray(R.array.tech_images);
                break;

            case 20:
                categoryListName = getResources().getStringArray(R.array.health);
                categoryListImage = getResources().obtainTypedArray(R.array.health_images);
                break;

            case 21:
                categoryListName = getResources().getStringArray(R.array.animals);
                categoryListImage = getResources().obtainTypedArray(R.array.animals_images);
                break;
        }
    }

    private void BannerAdd() {
        // Find the Ad view then build it then load it(Banner)
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    private int span() {

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        float cardWidth = getResources().getDimension(R.dimen.card_view);

        return (int) Math.floor(screenWidth / cardWidth);
    }

}
