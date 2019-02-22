package com.example.vaccine_alerter_client.network;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.vaccine_alerter_client.data.Const;
import com.example.vaccine_alerter_client.interfaces.LoadContentListener;

import org.json.JSONObject;

public class NetWorker {

    private StringRequest stringRequest;
    private LoadContentListener loadContentListener;


    public NetWorker(){


    }

    public void loadChildren( Context context, String id){
        loadContentListener = (LoadContentListener) context;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, Const.GET_CHILDREN_URL+id, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("----->", response.toString());
                        loadContentListener.onLoadValidResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                        Log.d("----->", error.toString());
                        loadContentListener.onLoadErrorResponse(error.toString());
                    }
                });


        NetworkSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);


    }

    public void checkGuardian(Activity activity , Context context, String id){


        loadContentListener = (LoadContentListener) activity;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, Const.CHECK_GUARDIAN_URL+id, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("----->", response.toString());
                        loadContentListener.onLoadValidResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                        Log.d("----->", error.toString());
                        loadContentListener.onLoadErrorResponse(error.toString());
                    }
                });


        NetworkSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
    public void loadChildDetails(Context context,String id){

        loadContentListener = (LoadContentListener) context;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, Const.GET_CHILDREN_DETAILS_URL+id, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("----->", response.toString());
                        loadContentListener.onLoadValidResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                        Log.d("----->", error.toString());
                        loadContentListener.onLoadErrorResponse(error.toString());
                    }
                });


        NetworkSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);


    }

   }


