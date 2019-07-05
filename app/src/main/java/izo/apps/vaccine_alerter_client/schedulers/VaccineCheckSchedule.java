package izo.apps.vaccine_alerter_client.schedulers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import izo.apps.vaccine_alerter_client.data.PreferenceManager;
import izo.apps.vaccine_alerter_client.receivers.DailyCheckReceiver;
import java.util.Calendar;


public class VaccineCheckSchedule {
    private static AlarmManager alarmMgr;
    private static PendingIntent alarmIntent;

    public static void startVaccineChecker(Context context) {

        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyCheckReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        //schedules every day at 8AM
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,9);
        calendar.set(Calendar.MINUTE, 30);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC, calendar.getTimeInMillis(), alarmIntent);
        } else {

            alarmMgr.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntent);
        }
        new PreferenceManager(context).setScheduler(true);
    }

    public static void stopVaccineChecker(Context context) {

        Log.d("---->13:10 job", "Job scheduled stopped!");
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyCheckReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        if (alarmMgr != null) {
            alarmMgr.cancel(alarmIntent);

        }
    }
}
