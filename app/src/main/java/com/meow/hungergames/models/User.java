package com.meow.hungergames.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private String userId;
    private String userName;
    private String userImage;
    private String userBio;
    private String joiningDate;

    public User() {
    }

    public User(String userId, String userName, String userImage, String userBio, String joiningDate) {
        this.userId = userId;
        this.userName = userName;
        this.userImage = userImage;
        this.userBio = userBio;
        this.joiningDate = joiningDate;
    }

    public User(JSONObject jsonObject) throws JSONException {
        this.userId = jsonObject.getString("userId");
        this.userName = jsonObject.getString("userName");
        this.userImage = jsonObject.getString("userImage");
        this.userBio = jsonObject.getString("userBio");
        this.joiningDate = jsonObject.getString("joiningDate");
    }

    public JSONObject getJSONObject() {
        JSONObject param = new JSONObject();
        try {
            param.put("userId", userId);
            param.put("userName", userName);
            param.put("userImage", userImage);
            param.put("userBio", userBio);
            param.put("joiningDate", joiningDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return param;
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

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }
}
