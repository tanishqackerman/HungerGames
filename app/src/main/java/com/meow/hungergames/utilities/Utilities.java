package com.meow.hungergames.utilities;

import com.meow.hungergames.volley.URLs;

public class Utilities {

    public static String getApiUrlSavePost() {
        return URLs.POST_SERVICE + "/save";
    }

    public static String getApiUrlUpdatePost() {
        return URLs.POST_SERVICE + "/update";
    }

    public static String getApiUrlGetPosts() {
        return URLs.POST_SERVICE + "/getPost";
    }

    public static String getApiUrlSinglePost(String postId) {
        return URLs.POST_SERVICE + "/getSinglePost/" + postId;
    }

    public static String getApiUrlQueryPosts(String query) {
        return URLs.POST_SERVICE + "/getPost/search/" + query;
    }

    public static String getApiUrlTypePosts(String type) {
        return URLs.POST_SERVICE + "/getPost/" + type;
    }

    public static String getApiUrlDeletePost(String postId) {
        return URLs.POST_SERVICE + "/delete/" + postId;
    }

    public static String getApiUrlSaveUser() {
        return URLs.USER_SERVICE + "/save";
    }

    public static String getApiUrlGetUser(String userId) {
        return URLs.USER_SERVICE + "/getUserDetails/" + userId;
    }

    public static String getApiUrlAllUsers() {
        return URLs.USER_SERVICE + "/getUserDetails";
    }

    public static String getApiUrlDeleteUser(String userId) {
        return URLs.USER_SERVICE + "/delete/" + userId;
    }

    public static String getApiUrlSaveComment() {
        return URLs.COMMENT_SERVICE + "/save";
    }

    public static String getApiUrlGetComment(String postId) {
        return URLs.COMMENT_SERVICE + "/getComments/" + postId;
    }

    public static String getApiUrlSaveLike() {
        return URLs.LIKE_SERVICE + "/save";
    }

    public static String getApiUrlCheckLike(String postId, String userId) {
        return URLs.LIKE_SERVICE + "/getLikes/" + postId + "/" + userId;
    }

    public static String getApiUrlDeleteLike(String postId, String userId) {
        return URLs.LIKE_SERVICE + "/delete/" + postId + "/" + userId;
    }

    public static String getApiUrlGetLike(String postId) {
        return URLs.LIKE_SERVICE + "/getLikes/" + postId;
    }

    public static String getApiUrlSaveSaved() {
        return URLs.SAVED_SERVICE + "/save";
    }

    public static String getApiUrlDeleteSave(String userId, String postId) {
        return URLs.SAVED_SERVICE + "/delete/" + userId + "/" + postId;
    }

    public static String getApiUrlGetSaved(String userId) {
        return URLs.SAVED_SERVICE + "/getSaved/" + userId;
    }

    public static String getApiUrlCheckSaved(String postId, String userId) {
        return URLs.SAVED_SERVICE + "/getSaved/" + userId + "/" + postId;
    }
}
