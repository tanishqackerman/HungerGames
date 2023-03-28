package com.meow.hungergames.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipePost implements Parcelable {
    private String postId;
    private String userId;
    private String userName;
    private String userImg;
    private String imgUrl;
    private String recipeName;
    private int likes;
    private int comments;
    private List<String> ingredients;
    private List<String> steps;
    private String date;
    private boolean isVeg;

    public RecipePost() {
    }

    public RecipePost(String postId, String userId, String userName, String userImg, String imgUrl, String recipeName, int likes, int comments, List<String> ingredients, List<String> steps, String date, boolean isVeg) {
        this.postId = postId;
        this.userId = userId;
        this.userName = userName;
        this.userImg = userImg;
        this.imgUrl = imgUrl;
        this.recipeName = recipeName;
        this.likes = likes;
        this.comments = comments;
        this.ingredients = ingredients;
        this.steps = steps;
        this.date = date;
        this.isVeg = isVeg;
    }

    public RecipePost(JSONObject jsonObject) throws JSONException {
        this.postId = jsonObject.getString("postId");
        this.userId = jsonObject.getString("userId");
        this.userName = jsonObject.getString("userName");
        this.userImg = jsonObject.getString("userImg");
        this.imgUrl = jsonObject.getString("imgUrl");
        this.recipeName = jsonObject.getString("recipeName");
        this.likes = jsonObject.getInt("likes");
        this.comments = jsonObject.getInt("comments");
        List<String> hehe = new ArrayList<>();
        JSONArray jArray = jsonObject.getJSONArray("ingredients");
        for (int i = 0; i < jArray.length(); i++) {
            hehe.add(jArray.getString(i));
        }
        this.ingredients = hehe;
        List<String> meow = new ArrayList<String>();
        JSONArray jarray = jsonObject.getJSONArray("steps");
        for (int i = 0; i < jarray.length(); i++){
            meow.add(jarray.getString(i));
        }
        this.steps = meow;
        this.date = jsonObject.getString("date");
        this.isVeg = jsonObject.getBoolean("isVeg");
    }

    protected RecipePost(Parcel in) {
        postId = in.readString();
        userId = in.readString();
        userName = in.readString();
        userImg = in.readString();
        imgUrl = in.readString();
        recipeName = in.readString();
        likes = in.readInt();
        comments = in.readInt();
        ingredients = in.createStringArrayList();
        steps = in.createStringArrayList();
        date = in.readString();
        isVeg = in.readByte() != 0;
    }

    public static final Creator<RecipePost> CREATOR = new Creator<RecipePost>() {
        @Override
        public RecipePost createFromParcel(Parcel in) {
            return new RecipePost(in);
        }

        @Override
        public RecipePost[] newArray(int size) {
            return new RecipePost[size];
        }
    };

    public JSONObject getJSONObject() {
        JSONObject param = new JSONObject();
        JSONArray jsonArrayIng = new JSONArray(ingredients);
        JSONArray jsonArraySt = new JSONArray(steps);
        try {
            param.put("postId", postId);
            param.put("userId", userId);
            param.put("userName", userName);
            param.put("userImg", userImg);
            param.put("imgUrl", imgUrl);
            param.put("recipeName", recipeName);
            param.put("likes", likes);
            param.put("comments", comments);
            param.put("ingredients", jsonArrayIng);
            param.put("steps", jsonArraySt);
            param.put("date", date);
            param.put("isVeg", isVeg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return param;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getIsVeg() {
        return isVeg;
    }

    public void setIsVeg(boolean veg) {
        isVeg = veg;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(postId);
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(userImg);
        dest.writeString(imgUrl);
        dest.writeString(recipeName);
        dest.writeInt(likes);
        dest.writeInt(comments);
        dest.writeStringList(ingredients);
        dest.writeStringList(steps);
        dest.writeString(date);
        dest.writeByte((byte) (isVeg ? 1 : 0));
    }
}
