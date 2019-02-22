package com.example.vaccine_alerter_client.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.vaccine_alerter_client.R;
import com.example.vaccine_alerter_client.activities.VaccineListActivity;
import com.example.vaccine_alerter_client.data.Const;
import com.example.vaccine_alerter_client.data.PreferenceManager;
import com.example.vaccine_alerter_client.interfaces.LoadContentListener;
import com.example.vaccine_alerter_client.models.ChildModel;
import com.example.vaccine_alerter_client.network.NetWorker;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class VaccineCheckerService extends IntentService implements LoadContentListener {

    public VaccineCheckerService() {
    super("VaccineCheckerService");

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        Log.d("----->","Service start");
        int id  = new PreferenceManager(this).getGuardianId();

        if( id == -1 ){

            stopSelf();

        }else{

                new NetWorker().loadChildren(this,String.valueOf(id));
        }
    }

    public void onLoadValidResponse(JSONObject response){

        extractJSONResponse(response);

    }
    public void onLoadErrorResponse(String response){


        stopSelf();

    }

    private void extractJSONResponse(JSONObject json){


        try {

            for (int i = 0; i < json.getJSONArray("children").length(); i++) {



                int id = json.getJSONArray("children").getJSONObject(i).getInt("id");
                String firstName = json.getJSONArray("children").getJSONObject(i).getString("first_name");
                String lastName = json.getJSONArray("children").getJSONObject(i).getString("last_name");

                int opv1_due = json.getJSONArray("children").getJSONObject(i).getInt("opv1_due");
                int bcg1_due = json.getJSONArray("children").getJSONObject(i).getInt("bcg1_due");
                int hepB1_due = json.getJSONArray("children").getJSONObject(i).getInt("hepB1_due");

                int dpt1_due = json.getJSONArray("children").getJSONObject(i).getInt("dpt1_due");
                int hibB1_due = json.getJSONArray("children").getJSONObject(i).getInt("hibB1_due");
                int hepB2_due = json.getJSONArray("children").getJSONObject(i).getInt("hepB2_due");

                int opv2_due = json.getJSONArray("children").getJSONObject(i).getInt("opv2_due");
                int pneu_due = json.getJSONArray("children").getJSONObject(i).getInt("pneu_due");
                int rota1_due = json.getJSONArray("children").getJSONObject(i).getInt("rota1_due");

                int dpt2_due = json.getJSONArray("children").getJSONObject(i).getInt("dpt2_due");
                int hibB2_due = json.getJSONArray("children").getJSONObject(i).getInt("hibB2_due");
                int hepB3_due = json.getJSONArray("children").getJSONObject(i).getInt("hepB3_due");

                int opv3_due = json.getJSONArray("children").getJSONObject(i).getInt("opv3_due");
                int vitA1_due = json.getJSONArray("children").getJSONObject(i).getInt("vitA1_due");
                int rota2_due = json.getJSONArray("children").getJSONObject(i).getInt("rota2_due");

                int vitA2_due = json.getJSONArray("children").getJSONObject(i).getInt("vitA2_due");
                int measles_due = json.getJSONArray("children").getJSONObject(i).getInt("measles_due");
                int yellow_due = json.getJSONArray("children").getJSONObject(i).getInt("yellow_due");


                if (opv1_due == 1 || bcg1_due == 1 || hepB1_due == 1 ||
                        dpt1_due == 1 || hibB1_due == 1 || hepB2_due == 1 ||
                        opv2_due == 1 || pneu_due == 1  || rota1_due ==1  ||
                        dpt2_due == 1 || hibB2_due == 1 || hepB3_due == 1 ||
                        opv3_due == 1 || vitA1_due == 1 || rota2_due == 1 ||
                        vitA2_due == 1|| measles_due== 1|| yellow_due == 1
                ){

                    showNotification(id, firstName, lastName);

                }

            }

             //update date
            new PreferenceManager(this).setDate();

        } catch(JSONException jsonE){

            Log.d("Err---->", jsonE.toString());
        }
        finally {
            stopSelf();
        }

    }

    private void showNotification(int id, String firstName, String lastName){

        startForeground(1,new Notification());
        Intent intent = new Intent(this, VaccineListActivity.class);
        intent.putExtra("siteId",String.valueOf(id));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        Log.d("--->","About to show notification");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, Const.CHANNEL_ID )
                .setContentTitle("Vaccine Alert!")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText(firstName + " " + lastName+" has a pending vaccine(s).Click to check")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Vaccine Alerter";
            String description = "Pending vaccine to be administered";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(Const.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
     // notificationId is a unique int for each notification that you must define
        notificationManager.notify(id, mBuilder.build());
    }
}

