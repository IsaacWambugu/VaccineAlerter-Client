package izo.apps.vaccine_alerter_client.network;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.Pair;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import izo.apps.vaccine_alerter_client.data.Const;
import izo.apps.vaccine_alerter_client.interfaces.LoadContentListener;
import org.json.JSONException;
import org.json.JSONObject;

public class NetWorker {

    private String domain = "";
    private LoadContentListener loadContentListener;

    public NetWorker(){

        domain = Const.domain;

    }

    public void loadChildren( Context context, String id){
        loadContentListener = (LoadContentListener) context;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, domain + Const.GET_CHILDREN_PATH+id, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        loadContentListener.onLoadValidResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loadContentListener.onLoadErrorResponse(checkErrorResponse(error));
                    }
                });

        NetworkSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }

    public void checkGuardian(Activity activity , Context context, String id){

        loadContentListener = (LoadContentListener) activity;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, domain + Const.CHECK_GUARDIAN_PATH +id, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                       loadContentListener.onLoadValidResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loadContentListener.onLoadErrorResponse(checkErrorResponse(error));
                    }
                });


        NetworkSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
    public void loadChildDetails(Context context,String id){

        loadContentListener = (LoadContentListener) context;

        String url  = domain + Const.GET_CHILDREN_DETAILS_PATH +id;

        Log.d("---->url",url);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, domain + Const.GET_CHILDREN_DETAILS_PATH +id, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        loadContentListener.onLoadValidResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("--->",error.toString());
                        loadContentListener.onLoadErrorResponse(checkErrorResponse(error));
                    }
                });


        NetworkSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);


    }
    private Pair<Integer, String> checkErrorResponse(VolleyError error) {

        String msg = "";
        NetworkResponse networkResponse = error.networkResponse;
        int code = 0;
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            code = 1;
            msg = "Check Internet connection and try again";
        } else if (error instanceof AuthFailureError) {
            code = 2;
            msg = "Check your login credentials and try again";
        } else if (error instanceof ServerError) {

            int statusCode = networkResponse.statusCode;

            try {

                msg = new JSONObject(new String(error.networkResponse.data, "UTF-8")).getString("message");

                if (statusCode == 404) {
                    code = 4;


                }else if(statusCode == 409){
                    code = 1;

                }else {
                    code = 3;
                     msg = "Server error try again later";
                }
            } catch (JSONException jE) {
                code = 3;
                msg = "Check details and try again";

            } catch (java.io.UnsupportedEncodingException ioE) {
                code = 3;
                msg = "Check details and try again";
            }
        } else if (error instanceof NetworkError) {
            code = 1;
            msg = "Check Internet connection and try again";
        } else if (error instanceof ParseError) {
            code = 2;
            msg = "Check you login credential and try again";

        }

        return Pair.create(code, msg);
    }

   }


