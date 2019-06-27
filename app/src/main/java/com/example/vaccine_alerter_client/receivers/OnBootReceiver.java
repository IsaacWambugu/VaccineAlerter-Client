package com.example.vaccine_alerter_client.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import com.example.vaccine_alerter_client.services.VaccineCheckerService;

public class OnBootReceiver extends BroadcastReceiver {


    private static final String TAG_BOOT_BROADCAST_RECEIVER = "BOOT_BROADCAST_RECEIVER";
    private static final String ACTION_QUICK_BOOT = "android.intent.action.QUICKBOOT_POWERON";

    @Override
    public void onReceive(Context context, Intent intent) {

        final String action = intent.getAction();
        Log.d(TAG_BOOT_BROADCAST_RECEIVER, action);

            if (Intent.ACTION_BOOT_COMPLETED.equals(action) || ACTION_QUICK_BOOT.equals(action)) {
                Log.d("--->OnReceive","Starting" );
                startServiceDirectly(context);
              //  VaccineCheckSchedule.startVaccineChecker(context);

        }
    }

    private void startServiceDirectly(Context context)
    {
                // This intent is used to start background service. The same service will be invoked for each invoke in the loop.
                Intent startServiceIntent = new Intent(context,VaccineCheckerService.class );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Log.d("--->in receiver","Starting" );
                    context.startForegroundService(startServiceIntent);
                } else {
                    context.startService(startServiceIntent);
                    Log.d("--->in receiver","Starting" );
                }

            }
}
