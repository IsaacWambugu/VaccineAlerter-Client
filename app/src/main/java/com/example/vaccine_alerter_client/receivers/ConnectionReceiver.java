package com.example.vaccine_alerter_client.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.example.vaccine_alerter_client.data.PreferenceManager;
import com.example.vaccine_alerter_client.services.VaccineCheckerService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.fabric.sdk.android.services.common.Crash;

public class ConnectionReceiver extends BroadcastReceiver {


    Context context;
    public String TAG = "ConnectionReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        //read internet connectivity state

        try {

            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo currentNetworkInfo = connectivityManager.getActiveNetworkInfo();

                //connected to Internet
                if (currentNetworkInfo != null && currentNetworkInfo.isConnectedOrConnecting()) {
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    if (!(new PreferenceManager(context).getDate() == date) && !(new PreferenceManager(context).getGuardianId() == -1)) {

                        Intent serviceIntent = new Intent(context, VaccineCheckerService.class);
                        context.startService(serviceIntent);
                    }

                } else if (currentNetworkInfo != null && (currentNetworkInfo.getState() ==
                        NetworkInfo.State.DISCONNECTED || currentNetworkInfo.getState() == NetworkInfo.State.DISCONNECTING
                        || currentNetworkInfo.getState() == NetworkInfo.State.SUSPENDED ||
                        currentNetworkInfo.getState() == NetworkInfo.State.UNKNOWN)) {

                    // when Internet is disconnected
                }

            }
        } catch (NullPointerException npE) {

            Crashlytics.logException(npE);

        }


    }
}
