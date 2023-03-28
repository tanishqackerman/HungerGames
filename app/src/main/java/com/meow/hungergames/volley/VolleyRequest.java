package com.meow.hungergames.volley;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.meow.hungergames.dao.DaoCallBack;
import com.meow.hungergames.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class VolleyRequest {

    private Context context;

    public VolleyRequest(Context context) {
        this.context = context;
    }

    public void callApiForArray(String url, final DaoCallBack daoCallBack, int method, JSONObject jsonObject) {
        switch (method) {
            case Constants.METHOD_GET:
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            daoCallBack.response(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        daoCallBack.errorResponse(error);
                    }
                });
                VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
                break;
        }
    }

    public void callApiForObject(String url, final DaoCallBack daoCallBack, int method, JSONObject jsonObject) {
        switch (method) {
            case Constants.METHOD_GET:
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            daoCallBack.response(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        daoCallBack.errorResponse(error);
                    }
                });
                VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
                break;
            case Constants.METHOD_POST:
                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                        url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            daoCallBack.response(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        daoCallBack.errorResponse(error);
                    }
                });
                VolleySingleton.getInstance(context).addToRequestQueue(jsonRequest);
                break;
            case Constants.METHOD_DELETE:
                JsonObjectRequest jsonrequest = new JsonObjectRequest(Request.Method.DELETE,
                        url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            daoCallBack.response(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        daoCallBack.errorResponse(error);
                    }
                });
                VolleySingleton.getInstance(context).addToRequestQueue(jsonrequest);
                break;
            case Constants.METHOD_PUT:
                JsonObjectRequest jsonREquest = new JsonObjectRequest(Request.Method.PUT,
                        url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            daoCallBack.response(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        daoCallBack.errorResponse(error);
                    }
                });
                VolleySingleton.getInstance(context).addToRequestQueue(jsonREquest);
                break;
        }
    }
}