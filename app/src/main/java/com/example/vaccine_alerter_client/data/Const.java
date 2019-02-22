package com.example.vaccine_alerter_client.data;

public class Const {

    //API calls
    public static final String DOMAIN = "http://192.168.1.8:5657";
    public static final String CHECK_GUARDIAN_URL = DOMAIN + "/api/guardians/";
    public static final String GET_CHILDREN_URL = DOMAIN + "/api/children/";
    public static final String GET_CHILDREN_DETAILS_URL = DOMAIN + "/api/children/details/";


    //Notification
    public static final String CHANNEL_ID = "VaccineAlert";


}
