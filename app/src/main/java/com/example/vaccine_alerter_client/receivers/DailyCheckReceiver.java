package com.example.vaccine_alerter_client.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.vaccine_alerter_client.services.VaccineCheckerService;

public class DailyCheckReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent startServiceIntent = new Intent(context, VaccineCheckerService.class);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            context.stopService(startServiceIntent);
            context.startForegroundService(startServiceIntent);

        } else {

            context.stopService(startServiceIntent);
            context.startService(startServiceIntent);
        }
        */
        context.startService(startServiceIntent);
    }
}