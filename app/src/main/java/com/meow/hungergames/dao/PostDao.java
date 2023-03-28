package com.meow.hungergames.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.firebase.auth.FirebaseAuth;
import com.meow.hungergames.models.RecipePost;
import com.meow.hungergames.utilities.Constants;
import com.meow.hungergames.utilities.Utilities;
import com.meow.hungergames.volley.RequestCallBack;
import com.meow.hungergames.volley.TempRequestCallBack;
import com.meow.hungergames.volley.VolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

public class PostDao extends VolleyRequest {

    private Context context;
    private RequestCallBack requestCallBack;
    private TempRequestCallBack tempRequestCallBack;

    public PostDao(Context context, RequestCallBack requestCallBack) {
        super(context);
        this.context = context;
        this.requestCallBack = requestCallBack;
    }

    public PostDao( Context context, TempRequestCallBack tempRequestCallBack) {
        super(context);
        this.context = context;
        this.tempRequestCallBack = tempRequestCallBack;
    }

    public PostDao(Context context, RequestCallBack requestCallBack, TempRequestCallBack tempRequestCallBack) {
        super(context);
        this.context = context;
        this.requestCallBack = requestCallBack;
        this.tempRequestCallBack = tempRequestCallBack;
    }

    public void savePost(JSONObject jsonObject) {
        String url = Utilities.getApiUrlSavePost();
        callApiForObject(
                url,
                new DaoCallBack() {
                    @Override
                    public void response(Object response) {
                        Toast.makeText(context, "Recipe Uploaded", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void stringResponse(String response) {

                    }

                    @Override
                    public void errorResponse(VolleyError error) {

                    }
                },
                Constants.METHOD_POST,
                jsonObject
        );
    }

    public void updatePost(JSONObject jsonObject) {
        String url = Utilities.getApiUrlUpdatePost();
        callApiForObject(
                url,
                new DaoCallBack() {
                    @Override
                    public void response(Object response) {
                        Toast.makeText(context, "Recipe Updated!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void stringResponse(String response) {

                    }

                    @Override
                    public void errorResponse(VolleyError error) {

                    }
                },
                Constants.METHOD_PUT,
                jsonObject
        );
    }

    public void getTypePosts(String type) {
        String url = Utilities.getApiUrlTypePosts(type);
        int check;
        switch (type) {
            case "hottest":
                check = Constants.GET_HOTTEST_POSTS;
                break;
            case "latest":
                check = Constants.GET_LATEST_POSTS;
                break;
            case "veg":
                check = Constants.GET_VEG_POSTS;
                break;
            case "nonVeg":
                check = Constants.GET_NON_VEG_POSTS;
                break;
            default:
                check = Constants.GET_USER_POST;
                break;
        }
        callApiForArray(
                url,
                new DaoCallBack() {
                    @Override
                    public void response(Object response) throws JSONException {
                        JSONArray jsonArray = (JSONArray) response;
                        ArrayList<RecipePost> posts = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            RecipePost recipePost = new RecipePost(jsonArray.getJSONObject(i));
                            if (check == Constants.GET_USER_POST) {
                                if (recipePost.getUserId().equals(FirebaseAuth.getInstance().getUid())) posts.add(recipePost);
                            } else posts.add(recipePost);
                        }
                        requestCallBack.onRequestSuccessful(
                                posts,
                                check,
                                true
                        );
                    }

                    @Override
                    public void stringResponse(String response) {

                    }

                    @Override
                    public void errorResponse(VolleyError error) {
                        requestCallBack.onRequestSuccessful(
                                null,
                                check,
                                false
                        );
                    }
                },
                Constants.METHOD_GET,
                null
        );
    }

    public void getAllPosts() {
        String url = Utilities.getApiUrlGetPosts();
        callApiForArray(
                url,
                new DaoCallBack() {
                    @Override
                    public void response(Object response) throws JSONException {
                        JSONArray jsonArray = (JSONArray) response;
                        ArrayList<RecipePost> posts = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            RecipePost recipePost = new RecipePost(jsonArray.getJSONObject(i));
                            posts.add(recipePost);
                        }
                        requestCallBack.onRequestSuccessful(
                                posts,
                                Constants.GET_ALL_POSTS,
                                true
                        );
                    }

                    @Override
                    public void stringResponse(String response) {

                    }

                    @Override
                    public void errorResponse(VolleyError error) {
                        requestCallBack.onRequestSuccessful(
                                null,
                                Constants.GET_ALL_POSTS,
                                false
                        );
                    }
                },
                Constants.METHOD_GET,
                null
        );
    }

    public void getSinglePost(String postId, String message) {
        String url = Utilities.getApiUrlSinglePost(postId);
        callApiForObject(
                url,
                new DaoCallBack() {
                    @Override
                    public void response(Object response) throws JSONException {
                        JSONObject jsonObject = (JSONObject) response;
                        RecipePost post = new RecipePost(jsonObject);
                        tempRequestCallBack.onRequestTempSuccessful(
                                post,
                                Constants.GET_SINGLE_POST,
                                true,
                                0,
                                message
                        );
                    }

                    @Override
                    public void stringResponse(String response) {

                    }

                    @Override
                    public void errorResponse(VolleyError error) {
                        requestCallBack.onRequestSuccessful(
                                null,
                                Constants.GET_SINGLE_POST,
                                false
                        );
                    }
                },
                Constants.METHOD_GET,
                null
        );
    }

    public void getPostQuery(String query) {
        String url = Utilities.getApiUrlQueryPosts(query);
        callApiForArray(
                url,
                new DaoCallBack() {
                    @Override
                    public void response(Object response) throws JSONException {
                        JSONArray jsonArray = (JSONArray) response;
                        ArrayList<RecipePost> posts = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            RecipePost recipePost = new RecipePost(jsonArray.getJSONObject(i));
                            posts.add(recipePost);
                        }
                        requestCallBack.onRequestSuccessful(
                                posts,
                                Constants.GET_QUERY_POST,
                                true
                        );
                    }

                    @Override
                    public void stringResponse(String response) {

                    }

                    @Override
                    public void errorResponse(VolleyError error) {
                        requestCallBack.onRequestSuccessful(
                                null,
                                Constants.GET_QUERY_POST,
                                false
                        );
                    }
                },
                Constants.METHOD_GET,
                null
        );
    }

    public void deletePost(JSONObject jsonObject, String postId) {
        String url = Utilities.getApiUrlDeletePost(postId);
        callApiForObject(
                url,
                new DaoCallBack() {
                    @Override
                    public void response(Object response) throws JSONException {

                    }

                    @Override
                    public void stringResponse(String response) {

                    }

                    @Override
                    public void errorResponse(VolleyError error) {

                    }
                },
                Constants.METHOD_DELETE,
                jsonObject
        );
    }
}
