package com.meow.hungergames.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.meow.hungergames.models.Comment;
import com.meow.hungergames.models.Like;
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

public class LikeDao extends VolleyRequest {

    private Context context;
    private RequestCallBack requestCallBack;
    private TempRequestCallBack tempRequestCallBack;

    public LikeDao(Context context, RequestCallBack requestCallBack) {
        super(context);
        this.context = context;
        this.requestCallBack = requestCallBack;
    }

    public LikeDao(Context context, RequestCallBack requestCallBack, TempRequestCallBack tempRequestCallBack) {
        super(context);
        this.context = context;
        this.requestCallBack = requestCallBack;
        this.tempRequestCallBack = tempRequestCallBack;
    }

    public void saveLike(JSONObject jsonObject) {
        String url = Utilities.getApiUrlSaveLike();
        callApiForObject(
                url,
                new DaoCallBack() {
                    @Override
                    public void response(Object response) {
                        Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();
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

    public void getLikes(String postId) {
        String url = Utilities.getApiUrlGetLike(postId);
        callApiForArray(
                url,
                new DaoCallBack() {
                    @Override
                    public void response(Object response) throws JSONException {
                        JSONArray jsonArray = (JSONArray) response;
                        ArrayList<Like> likes = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Like like = new Like(jsonArray.getJSONObject(i));
                            likes.add(like);
                        }
                        requestCallBack.onRequestSuccessful(
                                likes,
                                Constants.GET_LIKES_OF_POST,
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
                                Constants.GET_COMMENTS_OF_POST,
                                false
                        );
                        if (error == null || error.networkResponse == null) {
                            return;
                        }

                        String body = "";
                        //get status code here
                        final String statusCode = String.valueOf(error.networkResponse.statusCode);
                        //get response body and parse with appropriate encoding
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            // exception
                        }
                        Log.d("hehe", body);
                    }
                },
                Constants.METHOD_GET,
                null
        );
    }

    public void checkIfLiked(String postId, String userId, int position, String message) {
        String url = Utilities.getApiUrlCheckLike(postId, userId);
        callApiForObject(
                url,
                new DaoCallBack() {
                    @Override
                    public void response(Object response) throws JSONException {
                        JSONObject jsonObject = (JSONObject) response;
                        Like liked = new Like(jsonObject);
                        tempRequestCallBack.onRequestTempSuccessful(
                                liked,
                                Constants.CHECK_LIKED,
                                true,
                                position,
                                message
                        );
                        requestCallBack.onRequestSuccessful(
                                liked,
                                Constants.CHECK_LIKED,
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
                                Constants.CHECK_LIKED,
                                false
                        );
                        requestCallBack.onRequestSuccessful(
                                null,
                                Constants.CHECK_LIKED,
                                false
                        );
                    }
                },
                Constants.METHOD_GET,
                null
        );
    }

    public void deleteLike(JSONObject jsonObject, String postId, String userId) {
        String url = Utilities.getApiUrlDeleteLike(postId, userId);
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
