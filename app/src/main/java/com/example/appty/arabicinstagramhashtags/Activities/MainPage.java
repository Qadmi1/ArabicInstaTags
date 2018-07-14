package com.example.appty.arabicinstagramhashtags.Activities;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import com.example.appty.arabicinstagramhashtags.Adapters.MainCategoryAdapter;
import com.example.appty.arabicinstagramhashtags.R;
import com.example.appty.arabicinstagramhashtags.vo.CategoryItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;


public class MainPage extends AppCompatActivity {

    /** List of category names */
    private String[] categoryListName;

    /** List of category images */
    private TypedArray categoryListImage;

    /** Category Item list that will hold the name and the image of the category */
    ArrayList<CategoryItem> listOfCategories = new ArrayList<>();

    /** Banned Ad view */
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the recycler view
        RecyclerView recyclerView = findViewById(R.id.recycler_grid);
        // Receive the resources
        categoryListName = getResources().getStringArray(R.array.categories);
        categoryListImage = getResources().obtainTypedArray(R.array.category_images);


        // Fill the Category Item Array list
        for (int i=0; i<categoryListName.length; i++)
        {
            listOfCategories.add(new CategoryItem(categoryListName[i], categoryListImage.getResourceId(i, 0)));
        }

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, span());

        recyclerView.setLayoutManager(layoutManager);

        final RecyclerView.Adapter adapter = new MainCategoryAdapter(listOfCategories, MainPage.this);

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
                        Collections.swap(listOfCategories, i, i- 1);
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
        BannerAdd();

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
