package com.example.vaccine_alerter_client.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public void setDate(){

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        editor  = sharedPref.edit();
        editor.putString("date", date);
        editor.apply();
    }
    public String getDate(){

        return sharedPref.getString("date", null);
    }
    public void setDomain(String domain){

        editor  = sharedPref.edit();
        editor.putString("domain",domain);
        editor.apply();
    }
    public String getDomain(){

        return sharedPref.getString("domain", null);
    }

    public void setGuardianName(String name){

        editor  = sharedPref.edit();
        editor.putString("guardian_name", name);
        editor.apply();

    }
    public String getGuardianName(){

        return sharedPref.getString("guardian_name", null);
    }

    public void setGuardianNumber(String number){

        editor  = sharedPref.edit();
        editor.putString("guardian_number", number);
        editor.apply();

    }
    public String getGuardianNumber(){

        return sharedPref.getString("guardian_number", null);
    }

    public void setGuardianGender(String gender){

        editor  = sharedPref.edit();
        editor.putString("guardian_gender", gender);
        editor.apply();

    }
    public String getGuardianGender(){

        return sharedPref.getString("guardian_gender", null);
    }
}
