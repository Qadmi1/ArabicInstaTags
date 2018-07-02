package com.example.appty.arabicinstagramhashtags.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appty.arabicinstagramhashtags.CategoryAdapter;
import com.example.appty.arabicinstagramhashtags.HashTagActivity;
import com.example.appty.arabicinstagramhashtags.R;
import com.example.appty.arabicinstagramhashtags.vo.CategoryItem;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener{


    private String hashTag = "";
    private String[] categoryListName;
    private TypedArray categoryListImage;
    ArrayList<CategoryItem> listOfCategories = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_grid);
        categoryListName = getResources().getStringArray(R.array.categories);
        categoryListImage = getResources().obtainTypedArray(R.array.category_images);

        for (int i=0; i<categoryListName.length; i++)
        {
            listOfCategories.add(new CategoryItem(categoryListName[i], categoryListImage.getResourceId(i, 0)));
        }

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, span());

        recyclerView.setLayoutManager(layoutManager);

        final RecyclerView.Adapter adapter = new CategoryAdapter(listOfCategories, MainActivity.this);


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



    private int span() {

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        float cardWidth = getResources().getDimension(R.dimen.card_view);

        return (int) Math.floor(screenWidth / cardWidth);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        hashTag = categoryListName[position];
        Intent intent = new Intent(MainActivity.this, HashTagActivity.class);
        intent.putExtra(HashTagActivity.CATEGORY_TAG, hashTag);
        startActivity(intent);

    }
}
