package com.example.vaccine_alerter_client.interfaces;

import org.json.JSONObject;

public interface LoadContentListener {

    void onLoadValidResponse(JSONObject response);
    void onLoadErrorResponse(String response);
}
