package com.meow.hungergames.models;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ExpItem implements Parcelable {
    private String recipeImage;
    private String recipeName;

    public ExpItem(String recipeImage, String recipeName) {
        this.recipeImage = recipeImage;
        this.recipeName = recipeName;
    }

    protected ExpItem(Parcel in) {
        recipeImage = in.readString();
        recipeName = in.readString();
    }

    public static final Creator<ExpItem> CREATOR = new Creator<ExpItem>() {
        @Override
        public ExpItem createFromParcel(Parcel in) {
            return new ExpItem(in);
        }

        @Override
        public ExpItem[] newArray(int size) {
            return new ExpItem[size];
        }
    };

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(recipeImage);
        dest.writeString(recipeName);
    }
}
