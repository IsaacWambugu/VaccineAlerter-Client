package com.example.vaccine_alerter_client.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    final private int mode = Activity.MODE_PRIVATE;
    final private String myPrefs = "MyPreferences_001";
    private SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    public PreferenceManager(Context context){

        sharedPref = context.getSharedPreferences(myPrefs, mode);

    }

    public void setGuardianId(int id){

        editor  = sharedPref.edit();
        editor.putInt("guardian_id", id);
        editor.apply();

    }

    public int getGuardianId(){

        return sharedPref.getInt("guardian_id", -1);
    }


}
