package izo.apps.vaccine_alerter_client.data;

import android.util.Log;

public class Const {

    //API calls
    public static String domain;
    public static final String CHECK_GUARDIAN_PATH = "/api/guardians/";
    public static final String GET_CHILDREN_PATH =  "/api/children/";
    public static final String GET_CHILDREN_DETAILS_PATH ="/api/children/details/";

    public static void setDomain(String domain){

        Const.domain = domain;
        Log.d("---Setting domain:", domain);
    }

}
