package com.meow.hungergames.dao;

import com.android.volley.VolleyError;

import org.json.JSONException;

public interface DaoCallBack {
    public void response(Object response) throws JSONException;
    public void stringResponse(String response);
    public void errorResponse(VolleyError error);
}
