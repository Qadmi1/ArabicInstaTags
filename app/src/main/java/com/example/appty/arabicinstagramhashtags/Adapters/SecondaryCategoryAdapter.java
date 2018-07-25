package com.example.appty.arabicinstagramhashtags.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.appty.arabicinstagramhashtags.Activities.HashTagActivity;
import com.example.appty.arabicinstagramhashtags.R;
import com.example.appty.arabicinstagramhashtags.vo.CategoryItem;

import java.util.ArrayList;

/**
 * Created by appty on 04/07/18.
 */

public class SecondaryCategoryAdapter extends RecyclerView.Adapter<SecondaryCategoryAdapter.MyViewHolder> {

    private ArrayList<CategoryItem> data;
    private Context context;

    public SecondaryCategoryAdapter(ArrayList<CategoryItem> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new SecondaryCategoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        TextView categoryName = holder.categoryName;
        ImageView categoryImage = holder.categoryImage;

        categoryName.setText(data.get(position).getCategoryName());
        categoryImage.setImageResource(data.get(position).getCategoryImage());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hashTag;
                hashTag = data.get(position).getCategoryName();
                Intent intent = new Intent(context, HashTagActivity.class);
                intent.putExtra(HashTagActivity.CATEGORY_TAG, hashTag);
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
