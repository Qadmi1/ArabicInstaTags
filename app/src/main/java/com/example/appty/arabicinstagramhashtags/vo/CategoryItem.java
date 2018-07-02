package com.example.appty.arabicinstagramhashtags.vo;

/**
 * Created by appty on 01/07/18.
 */

public class CategoryItem {

    private String categoryName;
    private int categoryImage;


    public CategoryItem(String categoryName, int categoryImage) {

        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryImage() {
        return categoryImage;
    }
}
