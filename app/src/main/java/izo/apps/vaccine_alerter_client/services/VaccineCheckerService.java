package izo.apps.vaccine_alerter_client.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.util.Pair;
import com.crashlytics.android.Crashlytics;
import izo.apps.vaccine_alerter_client.R;
import izo.apps.vaccine_alerter_client.activities.ChildDetailsActivity;
import izo.apps.vaccine_alerter_client.data.PreferenceManager;
import izo.apps.vaccine_alerter_client.interfaces.LoadContentListener;
import izo.apps.vaccine_alerter_client.network.NetWorker;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class VaccineCheckerService extends IntentService implements LoadContentListener {

    private int checkerNotificationId = 101;
    private NotificationManagerCompat notificationManager;
    private  String channelId = "VaccineAlert";

    public VaccineCheckerService() {
    super("VaccineCheckerService");

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        int id  = new PreferenceManager(this).getGuardianId();
        showNotification(checkerNotificationId);

        if( id == -1 ){
            removeNotification();
            stopSelf();

        }else{

            Log.d("---->", "Network call");
            new NetWorker().loadChildren(this,String.valueOf(id));

        }
    }

    public void onLoadValidResponse(JSONObject response){

        extractJSONResponse(response);
        Log.d("---->","Valid response");
        //removeNotification(checkerNotificationId);

    }
    public void onLoadErrorResponse(Pair response){

        Log.d("---->","Error response");
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

                    showClickNotification(id,firstName+" "+lastName);

                }

            }

             //update date
            new PreferenceManager(this).setDate();

        } catch(JSONException jsonE){

            Crashlytics.logException(jsonE);
        }
        finally {
            removeNotification();
            stopSelf();

        }

    }
    private void showClickNotification(int id, String messageToShow){

        startForeground(13,new Notification());
        Intent intent = new Intent(this, ChildDetailsActivity.class);
        intent.putExtra("childId",String.valueOf(id));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId )
                .setContentTitle("Vaccine Alert!")
                .setSmallIcon(R.drawable.ic_not_logo)
                .setContentText(messageToShow)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Vaccine Alerter";
            String description = "Pending vaccine to be administered";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager = NotificationManagerCompat.from(this);
     // notificationId is a unique int for each notification that you must define
        notificationManager.notify(id, mBuilder.build());

    }
    private void showNotification(int id){


        if (Build.VERSION.SDK_INT >= 26) {

            NotificationChannel channel = new NotificationChannel(channelId,
                    "V-Alerter", NotificationManager.IMPORTANCE_DEFAULT);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            Notification notification = new NotificationCompat.Builder(this, channelId)
                    .setContentTitle("Vaccine Alert!")
                    .setContentText("Checking vaccine Schedule").build();
            startForeground(checkerNotificationId,notification);
        }

        notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define

    }

    private void removeNotification(){

        if (Build.VERSION.SDK_INT >= 26) {
            notificationManager.cancel(checkerNotificationId);
            stopForeground(Service.STOP_FOREGROUND_DETACH);
        }
    }
}

