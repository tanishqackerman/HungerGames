package com.meow.hungergames.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Saved {

    private RecipePost post;
    private String savedBy;
    private String dateTime;

    public Saved() {

    }

    public Saved(RecipePost post, String savedBy, String dateTime) {
        this.post = post;
        this.savedBy = savedBy;
        this.dateTime = dateTime;
    }

    public Saved(JSONObject jsonObject) throws JSONException {
        this.post = new RecipePost(jsonObject.getJSONObject("post"));
        this.savedBy = jsonObject.getString("savedBy");
        this.dateTime = jsonObject.getString("dateTime");
    }

    public JSONObject getJSONObject() {
        JSONObject param = new JSONObject();
        try {
            param.put("post", post.getJSONObject());
            param.put("savedBy", savedBy);
            param.put("dateTime", dateTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return param;
    }

    public RecipePost getPost() {
        return post;
    }

    public void setPost(RecipePost post) {
        this.post = post;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSavedBy() {
        return savedBy;
    }

    public void setSavedBy(String savedBy) {
        this.savedBy = savedBy;
    }
}
