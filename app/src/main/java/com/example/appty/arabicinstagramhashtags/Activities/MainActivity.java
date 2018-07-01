package com.example.appty.arabicinstagramhashtags.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.appty.arabicinstagramhashtags.HashTagActivity;
import com.example.appty.arabicinstagramhashtags.R;


public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener{


    ArrayAdapter<String> adapter;
    private String hashTag = "";
    private String[] categoriesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list);
        categoriesList = getResources().getStringArray(R.array.categories);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categoriesList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        hashTag = categoriesList[position];
        Intent intent = new Intent(MainActivity.this, HashTagActivity.class);
        intent.putExtra(HashTagActivity.CATEGORY_TAG, hashTag);
        startActivity(intent);

    }
}
