package com.example.appty.arabicinstagramhashtags.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.appty.arabicinstagramhashtags.Activities.HashTagActivity;
import com.example.appty.arabicinstagramhashtags.Activities.SecondaryPage;
import com.example.appty.arabicinstagramhashtags.R;
import com.example.appty.arabicinstagramhashtags.vo.CategoryItem;

import java.util.ArrayList;

/**
 * Created by appty on 01/07/18.
 */

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.MyViewHolder> {

    private ArrayList<CategoryItem> data;
    private Context context;

    public MainCategoryAdapter(ArrayList<CategoryItem> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final TextView categoryName = holder.categoryName;
        ImageView categoryImage = holder.categoryImage;
//        if (position == 3 ) {
//            categoryName.setTextSize(14);
//        }
//        else if ( position == 8)
//        {
//            categoryName.setTextSize(12);
//
//        }
        categoryName.setText(data.get(position).getCategoryName());
        categoryImage.setImageResource(data.get(position).getCategoryImage());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, SecondaryPage.class);
                intent.putExtra(SecondaryPage.CATEGORY_INDEX, position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName;
        ImageView categoryImage;
        RelativeLayout parentLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_text);
            categoryImage = itemView.findViewById(R.id.category_image);
            parentLayout = itemView.findViewById(R.id.parent);
        }
    }


}
