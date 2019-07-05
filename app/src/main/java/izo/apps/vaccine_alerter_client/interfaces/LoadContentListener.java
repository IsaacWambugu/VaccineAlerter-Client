package izo.apps.vaccine_alerter_client.interfaces;

import android.util.Pair;
import org.json.JSONObject;

public interface LoadContentListener {

    void onLoadValidResponse(JSONObject response);
    void onLoadErrorResponse(Pair response);
}
