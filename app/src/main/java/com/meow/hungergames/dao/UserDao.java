package com.meow.hungergames.dao;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.meow.hungergames.models.User;
import com.meow.hungergames.utilities.Constants;
import com.meow.hungergames.utilities.Utilities;
import com.meow.hungergames.volley.RequestCallBack;
import com.meow.hungergames.volley.TempRequestCallBack;
import com.meow.hungergames.volley.VolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserDao extends VolleyRequest {

    private Context context;
    private RequestCallBack requestCallBack;
    private TempRequestCallBack tempRequestCallBack;

    public UserDao(Context context, RequestCallBack requestCallBack) {
        super(context);
        this.context = context;
        this.requestCallBack = requestCallBack;
    }

    public UserDao(Context context, RequestCallBack requestCallBack, TempRequestCallBack tempRequestCallBack) {
        super(context);
        this.context = context;
        this.requestCallBack = requestCallBack;
        this.tempRequestCallBack = tempRequestCallBack;
    }

    public void saveUser(JSONObject jsonObject) {
        String url = Utilities.getApiUrlSaveUser();
        callApiForObject(
                url,
                new DaoCallBack() {
                    @Override
                    public void response(Object response) {
                        Toast.makeText(context, "Account Created", Toast.LENGTH_SHORT).show();
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

    public void getUser(String userId) {
        String url = Utilities.getApiUrlGetUser(userId);
        callApiForObject(
                url,
                new DaoCallBack() {
                    @Override
                    public void response(Object response) throws JSONException {
                        User user = new User((JSONObject) response);
                        requestCallBack.onRequestSuccessful(
                                user,
                                Constants.GET_USER,
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
                                Constants.GET_USER,
                                false
                        );
                    }
                },
                Constants.METHOD_GET,
                null
        );
    }

    public void getUserComment(String userId, int position, String message) {
        String url = Utilities.getApiUrlGetUser(userId);
        callApiForObject(
                url,
                new DaoCallBack() {
                    @Override
                    public void response(Object response) throws JSONException {
                        User user = new User((JSONObject) response);
                        tempRequestCallBack.onRequestTempSuccessful(
                                user,
                                Constants.GET_USER,
                                true,
                                position,
                                message
                        );
                    }

                    @Override
                    public void stringResponse(String response) {

                    }

                    @Override
                    public void errorResponse(VolleyError error) {
                        tempRequestCallBack.onRequestTempSuccessful(
                                null,
                                Constants.GET_USER,
                                false,
                                position,
                                message
                        );
                    }
                },
                Constants.METHOD_GET,
                null
        );
    }

    public void getAllUsers() {
        String url = Utilities.getApiUrlAllUsers();
        callApiForArray(
                url,
                new DaoCallBack() {
                    @Override
                    public void response(Object response) throws JSONException {
                        JSONArray jsonArray = (JSONArray) response;
                        ArrayList<User> users = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            User user = new User(jsonArray.getJSONObject(i));
                            users.add(user);
                        }
                        requestCallBack.onRequestSuccessful(
                                users,
                                Constants.GET_ALL_USERS,
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
                                Constants.GET_ALL_USERS,
                                false
                        );
                    }
                },
                Constants.METHOD_GET,
                null
        );
    }

    public void deleteUser(JSONObject jsonObject, String userId) {
        String url = Utilities.getApiUrlDeleteUser(userId);
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
