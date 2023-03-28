package com.meow.hungergames.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Comment {
    private String postId;
    private String commentId;
    private String userId;
    private String userName;
    private String userImg;
    private String comment;
    private String dateTime;


    public Comment() {

    }

    public Comment(String postId, String commentId, String userId, String userName, String userImg, String comment, String dateTime) {
        this.postId = postId;
        this.commentId = commentId;
        this.userId = userId;
        this.userName = userName;
        this.userImg = userImg;
        this.comment = comment;
        this.dateTime = dateTime;
    }

    public Comment(JSONObject jsonObject) throws JSONException {
        this.postId = jsonObject.getString("postId");
        this.commentId = jsonObject.getString("commentId");
        this.userId = jsonObject.getString("userId");
        this.userName = jsonObject.getString("userName");
        this.userImg = jsonObject.getString("userImg");
        this.comment = jsonObject.getString("comment");
        this.dateTime = jsonObject.getString("dateTime");
    }


    public JSONObject getJSONObject() {
        JSONObject param = new JSONObject();
        try {
            param.put("postId", postId);
            param.put("commentId", commentId);
            param.put("userId", userId);
            param.put("userName", userName);
            param.put("userImg", userImg);
            param.put("comment", comment);
            param.put("dateTime", dateTime);
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

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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
}