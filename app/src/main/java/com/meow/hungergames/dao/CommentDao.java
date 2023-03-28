package com.meow.hungergames.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.meow.hungergames.models.Comment;
import com.meow.hungergames.models.RecipePost;
import com.meow.hungergames.utilities.Constants;
import com.meow.hungergames.utilities.Utilities;
import com.meow.hungergames.volley.RequestCallBack;
import com.meow.hungergames.volley.VolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CommentDao extends VolleyRequest {

    private Context context;
    private RequestCallBack requestCallBack;

    public CommentDao(Context context, RequestCallBack requestCallBack) {
        super(context);
        this.context = context;
        this.requestCallBack = requestCallBack;
    }

    public void saveComment(JSONObject jsonObject) {
        String url = Utilities.getApiUrlSaveComment();
        callApiForObject(
                url,
                new DaoCallBack() {
                    @Override
                    public void response(Object response) {
                        Toast.makeText(context, "Comment Uploaded", Toast.LENGTH_SHORT).show();
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

    public void getComments(String postId) {
        String url = Utilities.getApiUrlGetComment(postId);
        callApiForArray(
                url,
                new DaoCallBack() {
                    @Override
                    public void response(Object response) throws JSONException {
                        JSONArray jsonArray = (JSONArray) response;
                        ArrayList<Comment> comments = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Comment comment = new Comment(jsonArray.getJSONObject(i));
                            comments.add(comment);
                        }
                        requestCallBack.onRequestSuccessful(
                                comments,
                                Constants.GET_COMMENTS_OF_POST,
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
                    }
                },
                Constants.METHOD_GET,
                null
        );
    }
}
