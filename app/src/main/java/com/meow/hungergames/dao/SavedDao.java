package com.meow.hungergames.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.meow.hungergames.models.Like;
import com.meow.hungergames.models.RecipePost;
import com.meow.hungergames.models.Saved;
import com.meow.hungergames.utilities.Constants;
import com.meow.hungergames.utilities.Utilities;
import com.meow.hungergames.volley.RequestCallBack;
import com.meow.hungergames.volley.TempRequestCallBack;
import com.meow.hungergames.volley.VolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SavedDao extends VolleyRequest {

    private Context context;
    private RequestCallBack requestCallBack;
    private TempRequestCallBack tempRequestCallBack;

    public SavedDao(Context context, RequestCallBack requestCallBack, TempRequestCallBack tempRequestCallBack) {
        super(context);
        this.context = context;
        this.requestCallBack = requestCallBack;
        this.tempRequestCallBack = tempRequestCallBack;
    }

    public void saveSaved(JSONObject jsonObject) {
        String url = Utilities.getApiUrlSaveSaved();
        callApiForObject(
                url,
                new DaoCallBack() {
                    @Override
                    public void response(Object response) {
                        Toast.makeText(context, "Recipe Saved", Toast.LENGTH_SHORT).show();
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

    public void getSaved(String userId) {
        String url = Utilities.getApiUrlGetSaved(userId);
        callApiForArray(
                url,
                new DaoCallBack() {
                    @Override
                    public void response(Object response) throws JSONException {
                        JSONArray jsonArray = (JSONArray) response;
                        ArrayList<Saved> saveds = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Saved saved = new Saved(jsonArray.getJSONObject(i));
                            saveds.add(saved);
                        }
                        requestCallBack.onRequestSuccessful(
                                saveds,
                                Constants.GET_SAVED,
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
                                Constants.GET_SAVED,
                                false
                        );
                    }
                },
                Constants.METHOD_GET,
                null
        );
    }

    public void checkIfSaved(String postId, String userId, int position, String message) {
        String url = Utilities.getApiUrlCheckSaved(postId, userId);
        callApiForObject(
                url,
                new DaoCallBack() {
                    @Override
                    public void response(Object response) throws JSONException {
                        JSONObject jsonObject = (JSONObject) response;
                        Saved saved = new Saved(jsonObject);
                        tempRequestCallBack.onRequestTempSuccessful(
                                saved,
                                Constants.CHECK_SAVED,
                                true,
                                position,
                                null
                        );
                    }

                    @Override
                    public void stringResponse(String response) {

                    }

                    @Override
                    public void errorResponse(VolleyError error) {
                        tempRequestCallBack.onRequestTempSuccessful(
                                null,
                                Constants.CHECK_SAVED,
                                false,
                                position,
                                message
                        );
                        Log.d("hehe", error.toString());
                    }
                },
                Constants.METHOD_GET,
                null
        );
    }

    public void deleteSave(JSONObject jsonObject, String userId, String postId) {
        String url = Utilities.getApiUrlDeleteSave(userId, postId);
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