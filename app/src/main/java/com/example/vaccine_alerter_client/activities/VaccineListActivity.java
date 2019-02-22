package com.example.vaccine_alerter_client.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.vaccine_alerter_client.R;
import com.example.vaccine_alerter_client.interfaces.LoadContentListener;
import com.example.vaccine_alerter_client.network.NetWorker;
import com.example.vaccine_alerter_client.util.Mtandao;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class VaccineListActivity extends AppCompatActivity implements LoadContentListener {

    private Toolbar toolbar;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;

    private String id = "";


    private TextView bcg1_days;
    private TextView bcg1_date;
    private TextView bcg1_status;

    private TextView dpt1_days;
    private TextView dpt1_date;
    private TextView dpt1_status;

    private TextView dpt2_days;
    private TextView dpt2_date;
    private TextView dpt2_status;

    private TextView hepb1_days;
    private TextView hepb1_date;
    private TextView hepb1_status;

    private TextView hepb2_days;
    private TextView hepb2_date;
    private TextView hepb2_status;

    private TextView hepb3_days;
    private TextView hepb3_date;
    private TextView hepb3_status;

    private TextView hib1_days;
    private TextView hib1_date;
    private TextView hib1_status;

    private TextView hib2_days;
    private TextView hib2_date;
    private TextView hib2_status;

    private TextView meas_days;
    private TextView meas_date;
    private TextView meas_status;

    private TextView opv1_days;
    private TextView opv1_date;
    private TextView opv1_status;

    private TextView opv2_days;
    private TextView opv2_date;
    private TextView opv2_status;

    private TextView opv3_days;
    private TextView opv3_date;
    private TextView opv3_status;

    private TextView pneu_days;
    private TextView pneu_date;
    private TextView pneu_status;

    private TextView rota1_days;
    private TextView rota1_date;
    private TextView rota1_status;

    private TextView rota2_days;
    private TextView rota2_date;
    private TextView rota2_status;

    private TextView vitA1_days;
    private TextView vitA1_date;
    private TextView vitA1_status;

    private TextView vitA2_days;
    private TextView vitA2_date;
    private TextView vitA2_status;

    private TextView yellow_days;
    private TextView yellow_date;
    private TextView yellow_status;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_vaccine_list);
        setUIConfig();
        getIncomingIntent(savedInstanceState);
        loadVaccines();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUIConfig() {

        bcg1_days = (TextView) findViewById(R.id.bcg1_days);
        bcg1_date = (TextView)  findViewById(R.id.bcg1_date);
        bcg1_status = (TextView) findViewById(R.id.bcg1_status);

        dpt1_days = (TextView) findViewById(R.id.dpt1_days);
        dpt1_date = (TextView)  findViewById(R.id.dpt1_date);
        dpt1_status = (TextView) findViewById(R.id.dpt1_status);

        dpt2_days = (TextView) findViewById(R.id.dpt2_days);
        dpt2_date = (TextView)  findViewById(R.id.dpt2_date);
        dpt2_status = (TextView) findViewById(R.id.dpt2_status);

        hepb1_days = (TextView) findViewById(R.id.hepb1_days);
        hepb1_date = (TextView)  findViewById(R.id.hepb1_date);
        hepb1_status = (TextView) findViewById(R.id.hepb1_status);

        hepb2_days = (TextView) findViewById(R.id.hepb2_days);
        hepb2_date = (TextView)  findViewById(R.id.hepb2_date);
        hepb2_status = (TextView) findViewById(R.id.hepb2_status);

        hepb3_days = (TextView) findViewById(R.id.hepb3_days);
        hepb3_date = (TextView)  findViewById(R.id.hepb3_date);
        hepb3_status = (TextView) findViewById(R.id.hepb3_status);

        hib1_days = (TextView) findViewById(R.id.hib1_days);
        hib1_date = (TextView)  findViewById(R.id.hib1_date);
        hib1_status = (TextView) findViewById(R.id.hib1_status);

        hib2_days = (TextView) findViewById(R.id.hib2_days);
        hib2_date = (TextView)  findViewById(R.id.hib2_date);
        hib2_status = (TextView) findViewById(R.id.hib2_status);

        hib2_days = (TextView) findViewById(R.id.hib2_days);
        hib2_date = (TextView)  findViewById(R.id.hib2_date);
        hib2_status = (TextView) findViewById(R.id.hib2_status);

        meas_days = (TextView) findViewById(R.id.meas_days);
        meas_date = (TextView)  findViewById(R.id.meas_date);
        meas_status = (TextView) findViewById(R.id.meas_status);

        opv1_days = (TextView) findViewById(R.id.opv1_days);
        opv1_date = (TextView)  findViewById(R.id.opv1_date);
        opv1_status = (TextView) findViewById(R.id.opv1_status);

        opv2_days = (TextView) findViewById(R.id.opv2_days);
        opv2_date = (TextView)  findViewById(R.id.opv2_date);
        opv2_status = (TextView) findViewById(R.id.opv2_status);

        opv3_days = (TextView) findViewById(R.id.opv3_days);
        opv3_date = (TextView)  findViewById(R.id.opv3_date);
        opv3_status = (TextView) findViewById(R.id.opv3_status);

        pneu_days = (TextView) findViewById(R.id.pneu_days);
        pneu_date = (TextView)  findViewById(R.id.pneu_date);
        pneu_status = (TextView) findViewById(R.id.pneu_status);

        rota1_days = (TextView) findViewById(R.id.rota1_days);
        rota1_date = (TextView)  findViewById(R.id.rota1_date);
        rota1_status = (TextView) findViewById(R.id.rota1_status);

        rota2_days = (TextView) findViewById(R.id.rota2_days);
        rota2_date = (TextView)  findViewById(R.id.rota2_date);
        rota2_status = (TextView) findViewById(R.id.rota2_status);

        vitA1_days = (TextView) findViewById(R.id.vit1_days);
        vitA1_date = (TextView)  findViewById(R.id.vit1_date);
        vitA1_status = (TextView) findViewById(R.id.vit1_status);

        vitA2_days = (TextView) findViewById(R.id.vit2_days);
        vitA2_date = (TextView)  findViewById(R.id.vit2_date);
        vitA2_status = (TextView) findViewById(R.id.vit2_status);

        yellow_days = (TextView) findViewById(R.id.yellow_days);
        yellow_date = (TextView)  findViewById(R.id.yellow_date);
        yellow_status = (TextView) findViewById(R.id.yellow_status);



        toolbar = (Toolbar) findViewById(R.id.toolbar_vaccine_list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.vaccine_swipe_container);
        view = getWindow().getDecorView().getRootView();
        toolbar.setTitle("Vaccines");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override

            public void onRefresh() {

                loadVaccines();

            }

        });

        swipeRefreshLayout .setColorSchemeResources(android.R.color.holo_blue_bright,

                android.R.color.holo_green_light,

                android.R.color.holo_orange_light,

                android.R.color.holo_red_light);

    }

    private void loadVaccines() {

        swipeRefreshLayout.setRefreshing(true);

        Log.d("------->","id"+id);

        if (Mtandao.checkInternet(getApplicationContext())) {

            new NetWorker().loadChildDetails(this, id);

        } else {

            showSnackBar("Check internet connection and try again!");
            swipeRefreshLayout.setRefreshing(false);
        }


    }

    @Override
    public void onLoadValidResponse(JSONObject response) {

        Log.d("---->","Load successful:"+response.toString());
        //childrenList.clear();
        extractJSONResponse(response);
        //displayChildrenList();
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onLoadErrorResponse(String response) {

        swipeRefreshLayout.setRefreshing(false);
        showSnackBar("Something went wrong.Try again later");

    }

    private void showSnackBar(String mesg) {

        Snackbar.make(view, mesg, Snackbar.LENGTH_SHORT)
                .show();

    }

    private void getIncomingIntent(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {

                moveToMainActivity();

            } else {

                id = extras.getString("siteId");

            }
        } else {

            id = (String) savedInstanceState.getSerializable("siteId");

        }

    }

    private void extractJSONResponse(JSONObject json) {

        try {


            //int id = json.getJSONObject("vaccines").getJSONObject("OPV1").getString("id");
            //String firstName = json.getJSONArray("children").getJSONObject(i).getString("first_name");
         //   String lastName = json.getJSONArray("children").getJSONObject(i).getString("last_name");
           // String gender = json.getJSONArray("children").getJSONObject(i).getString("gender");

            int opv1_due = json.getJSONObject("vaccines").getJSONObject("OPV1").getInt("due");
            int opv1_admin = json.getJSONObject("vaccines").getJSONObject("OPV1").getInt("administered");
            String opv1_date_admin = json.getJSONObject("vaccines").getJSONObject("OPV1").getString("date_administered");
            int opv1_days = json.getJSONObject("vaccines").getJSONObject("OPV1").getInt("days");


            int bcg1_due = json.getJSONObject("vaccines").getJSONObject("BCG1").getInt("due");
            int bcg1_admin = json.getJSONObject("vaccines").getJSONObject("BCG1").getInt("administered");
            String bcg1_date_admin = json.getJSONObject("vaccines").getJSONObject("BCG1").getString("date_administered");
            int bcg1_days = json.getJSONObject("vaccines").getJSONObject("BCG1").getInt("days");

            int hepB1_due = json.getJSONObject("vaccines").getJSONObject("HEPB1").getInt("due");
            int hepB1_admin = json.getJSONObject("vaccines").getJSONObject("HEPB1").getInt("administered");
            String hepB1_date_admin = json.getJSONObject("vaccines").getJSONObject("HEPB1").getString("date_administered");
            int hepB1_days = json.getJSONObject("vaccines").getJSONObject("HEPB1").getInt("days");


            int dpt1_due = json.getJSONObject("vaccines").getJSONObject("DPT1").getInt("due");
            int dpt1_admin = json.getJSONObject("vaccines").getJSONObject("DPT1").getInt("administered");
            String dpt1_date_admin = json.getJSONObject("vaccines").getJSONObject("DPT1").getString("date_administered");
            int dpt1_days = json.getJSONObject("vaccines").getJSONObject("DPT1").getInt("days");


            int hibB1_due = json.getJSONObject("vaccines").getJSONObject("HIBB1").getInt("due");
            int hibB1_admin = json.getJSONObject("vaccines").getJSONObject("HIBB1").getInt("administered");
            String hibB1_date_admin = json.getJSONObject("vaccines").getJSONObject("HIBB1").getString("date_administered");
            int hibB1_days = json.getJSONObject("vaccines").getJSONObject("HIBB1").getInt("days");


            int hepB2_due = json.getJSONObject("vaccines").getJSONObject("HEPB2").getInt("due");
            int hepB2_admin = json.getJSONObject("vaccines").getJSONObject("HEPB2").getInt("administered");
            String hepB2_date_admin = json.getJSONObject("vaccines").getJSONObject("HEPB2").getString("date_administered");
            int hepB2_days = json.getJSONObject("vaccines").getJSONObject("HEPB2").getInt("days");

            int opv2_due = json.getJSONObject("vaccines").getJSONObject("OPV2").getInt("due");
            int opv2_admin = json.getJSONObject("vaccines").getJSONObject("OPV2").getInt("administered");
            String opv2_date_admin = json.getJSONObject("vaccines").getJSONObject("OPV2").getString("date_administered");
            int opv2_days = json.getJSONObject("vaccines").getJSONObject("OPV2").getInt("days");


            int pneu_due = json.getJSONObject("vaccines").getJSONObject("PNEU").getInt("due");
            int pneu_admin = json.getJSONObject("vaccines").getJSONObject("PNEU").getInt("administered");
            String pneu_date_admin = json.getJSONObject("vaccines").getJSONObject("PNEU").getString("date_administered");
            int pneu_days = json.getJSONObject("vaccines").getJSONObject("PNEU").getInt("days");


            int rota1_due = json.getJSONObject("vaccines").getJSONObject("ROTA1").getInt("due");
            int rota1_admin = json.getJSONObject("vaccines").getJSONObject("ROTA1").getInt("administered");
            String rota1_date_admin = json.getJSONObject("vaccines").getJSONObject("ROTA1").getString("date_administered");
            int rota1_days = json.getJSONObject("vaccines").getJSONObject("ROTA1").getInt("days");


            int dpt2_due = json.getJSONObject("vaccines").getJSONObject("DPT2").getInt("due");
            int dpt2_admin = json.getJSONObject("vaccines").getJSONObject("DPT2").getInt("administered");
            String dpt2_date_admin = json.getJSONObject("vaccines").getJSONObject("DPT2").getString("date_administered");
            int dpt2_days = json.getJSONObject("vaccines").getJSONObject("DPT2").getInt("days");

            int hibB2_due = json.getJSONObject("vaccines").getJSONObject("HIBB2").getInt("due");
            int hibB2_admin = json.getJSONObject("vaccines").getJSONObject("HIBB2").getInt("administered");
            String hibB2_date_admin = json.getJSONObject("vaccines").getJSONObject("HIBB2").getString("date_administered");
            int hibB2_days = json.getJSONObject("vaccines").getJSONObject("HIBB2").getInt("days");

            int hepB3_due = json.getJSONObject("vaccines").getJSONObject("HEBB3").getInt("due");
            int hepB3_admin = json.getJSONObject("vaccines").getJSONObject("HEBB3").getInt("administered");
            String hepB3_date_admin = json.getJSONObject("vaccines").getJSONObject("HEBB3").getString("date_administered");
            int hepB3_days = json.getJSONObject("vaccines").getJSONObject("HEBB3").getInt("days");


            int opv3_due = json.getJSONObject("vaccines").getJSONObject("OPV3").getInt("due");
            int opv3_admin = json.getJSONObject("vaccines").getJSONObject("OPV3").getInt("administered");
            String opv3_date_admin = json.getJSONObject("vaccines").getJSONObject("OPV3").getString("date_administered");
            int opv3_days = json.getJSONObject("vaccines").getJSONObject("OPV3").getInt("days");

            int vitA1_due = json.getJSONObject("vaccines").getJSONObject("VITA1").getInt("due");
            int vitA1_admin = json.getJSONObject("vaccines").getJSONObject("VITA1").getInt("administered");
            String vitA1_date_admin = json.getJSONObject("vaccines").getJSONObject("VITA1").getString("date_administered");
            int vitA1_days = json.getJSONObject("vaccines").getJSONObject("VITA1").getInt("days");


            int rota2_due = json.getJSONObject("vaccines").getJSONObject("VOTA2").getInt("due");
            int rota2_admin = json.getJSONObject("vaccines").getJSONObject("VOTA2").getInt("administered");
            String rota2_date_admin = json.getJSONObject("vaccines").getJSONObject("VOTA2").getString("date_administered");
            int rota2_days = json.getJSONObject("vaccines").getJSONObject("VOTA2").getInt("days");


            int vitA2_due = json.getJSONObject("vaccines").getJSONObject("VITA2").getInt("due");
            int vitA2_admin = json.getJSONObject("vaccines").getJSONObject("VITA2").getInt("administered");
            String vitA2_date_admin = json.getJSONObject("vaccines").getJSONObject("VITA2").getString("date_administered");
            int vitA2_days = json.getJSONObject("vaccines").getJSONObject("VITA2").getInt("days");


            int measles_due = json.getJSONObject("vaccines").getJSONObject("MEAS").getInt("due");
            int measles_admin = json.getJSONObject("vaccines").getJSONObject("MEAS").getInt("administered");
            String measles_date_admin = json.getJSONObject("vaccines").getJSONObject("MEAS").getString("date_administered");
            int measles_days = json.getJSONObject("vaccines").getJSONObject("MEAS").getInt("days");


            int yellow_due = json.getJSONObject("vaccines").getJSONObject("YELLOW").getInt("due");
            int yellow_admin = json.getJSONObject("vaccines").getJSONObject("YELLOW").getInt("administered");
            String yellow_date_admin = json.getJSONObject("vaccines").getJSONObject("YELLOW").getString("date_administered");
            int yellow_days = json.getJSONObject("vaccines").getJSONObject("YELLOW").getInt("days");

            this.bcg1_days.setText(String.valueOf(bcg1_days));
            this.bcg1_date.setText(bcg1_date_admin);

            if(bcg1_due == 1){

                this.bcg1_status.setText("VACCINE is due!");

            }else if(bcg1_admin == 1){

                this.bcg1_status.setText("VACCINE Was Administered!");
            }else{

                this.bcg1_status.setText("");
            }



            this.dpt1_days.setText(String.valueOf(dpt1_days));
            this.dpt1_date.setText(dpt1_date_admin);

            if(dpt1_due == 1){

                this.dpt1_status.setText("VACCINE is due!");

            }else if(dpt1_admin == 1){

                this.dpt1_status.setText("VACCINE Was Administered!");
            }else{

                this.dpt1_status.setText("");
            }

            this.dpt2_days.setText(String.valueOf(dpt2_days));
            this.dpt2_date.setText(dpt2_date_admin);

            if(dpt2_due == 1){

                this.dpt2_status.setText("VACCINE is due!");

            }else if(dpt2_admin == 1){

                this.dpt2_status.setText("VACCINE Was Administered!");
            }else{

                this.dpt2_status.setText("");
            }

            this.hepb1_days.setText(String.valueOf(hepB1_days));
            this.hepb1_date.setText(hepB1_date_admin);

            if(hepB1_due == 1){

                this.hepb1_status.setText("VACCINE is due!");


            }else if(hepB1_admin == 1){

                this.hepb1_status.setText("VACCINE Was Administered!");
            }else{

                this.hepb1_status.setText("");
            }

            this.hepb2_days.setText(String.valueOf(hepB2_days));
            this.hepb2_date.setText(hepB2_date_admin);

            if(hepB2_due == 1){

                this.hepb2_status.setText("VACCINE is due!");

            }else if(hepB2_admin == 1){

                this.hepb2_status.setText("VACCINE Was Administered!");
            }else{

                this.hepb2_status.setText("");
            }

            this.hepb3_days.setText(String.valueOf(hepB3_days));
            this.hepb3_date.setText(hepB3_date_admin);

            if(hepB3_due == 1){

                this.hepb3_status.setText("VACCINE is due!");

            }else if(hepB3_admin == 1){

                this.hepb3_status.setText("VACCINE Was Administered!");
            }else{

                this.hepb3_status.setText("");
            }

            this.hib1_days.setText(String.valueOf(hibB1_days));
            this.hib1_date.setText(hibB1_date_admin);

            if(hibB1_due == 1){

                this.hib1_status.setText("VACCINE is due!");

            }else if(hibB1_admin == 1){

                this.hib1_status.setText("VACCINE Was Administered!");
            }else{

                this.hib1_status.setText("");
            }

            this.hib2_days.setText(String.valueOf(hibB2_days));
            this.hib2_date.setText(hibB2_date_admin);

            if(hibB2_due == 1){

                this.hib2_status.setText("VACCINE is due!");

            }else if(hibB2_admin == 1){

                this.hib2_status.setText("VACCINE Was Administered!");
            }else{

                this.hib2_status.setText("");
            }

            this.meas_days.setText(String.valueOf(bcg1_days));
            this.meas_date.setText(bcg1_date_admin);

            if(dpt1_due == 1){

                this.meas_status.setText("VACCINE is due!");

            }else if(bcg1_admin == 1){

                this.meas_status.setText("VACCINE Was Administered!");
            }else{

                this.meas_status.setText("");
            }


            this.opv1_days.setText(String.valueOf(opv1_days));
            this.opv1_date.setText(opv1_date_admin);

            if(opv1_due == 1){

                this.opv1_status.setText("VACCINE is due!");

            }else if(opv1_admin == 1){

                this.opv1_status.setText("VACCINE Was Administered!");
            }else{

                this.opv1_status.setText("");
            }

            this.opv2_days.setText(String.valueOf(opv2_days));
            this.opv2_date.setText(opv2_date_admin);

            if(opv2_due == 1){

                this.opv2_status.setText("VACCINE is due!");

            }else if(opv2_admin == 1){

                this.opv2_status.setText("VACCINE Was Administered!");
            }else{

                this.opv2_status.setText("");
            }

            this.opv3_days.setText(String.valueOf(opv3_days));
            this.opv3_date.setText(opv3_date_admin);

            if(opv3_due == 1){

                this.opv3_status.setText("VACCINE is due!");

            }else if(opv3_admin == 1){

                this.opv3_status.setText("VACCINE Was Administered!");
            }else{

                this.opv3_status.setText("");
            }

            this.pneu_days.setText(String.valueOf(pneu_days));
            this.pneu_date.setText(pneu_date_admin);

            if(pneu_due == 1){

                this.pneu_status.setText("VACCINE is due!");

            }else if(pneu_admin == 1){

                this.pneu_status.setText("VACCINE Was Administered!");
            }else{

                this.pneu_status.setText("");
            }

            this.rota1_days.setText(String.valueOf(rota1_days));
            this.rota1_date.setText(rota1_date_admin);

            if(rota1_due == 1){

                this.rota1_status.setText("VACCINE is due!");

            }else if(rota1_admin == 1){

                this.rota1_status.setText("VACCINE Was Administered!");
            }else{

                this.rota1_status.setText("");
            }

            this.rota2_days.setText(String.valueOf(rota2_days));
            this.rota2_date.setText(rota2_date_admin);

            if(rota2_due == 1){

                this.rota2_status.setText("VACCINE is due!");

            }else if(rota2_admin == 1){

                this.rota2_status.setText("VACCINE Was Administered!");
            }else{

                this.rota2_status.setText("");
            }

            this.vitA1_days.setText(String.valueOf(vitA1_days));
            this.vitA1_date.setText(vitA1_date_admin);

            if(vitA1_due == 1){

                this.rota2_status.setText("VACCINE is due!");

            }else if(vitA1_admin == 1){

                this.rota2_status.setText("VACCINE Was Administered!");
            }else{

                this.rota2_status.setText("");
            }


            this.vitA1_days.setText(String.valueOf(vitA1_days));
            this.vitA1_date.setText(vitA1_date_admin);

            if(vitA1_due == 1){

                this.vitA1_status.setText("VACCINE is due!");

            }else if(vitA1_admin == 1){

                this.vitA1_status.setText("VACCINE Was Administered!");
            }else{

                this.vitA1_status.setText("");
            }

            this.vitA2_days.setText(String.valueOf(vitA2_days));
            this.vitA2_date.setText(vitA2_date_admin);

            if(vitA2_due == 1){

                this.vitA2_status.setText("VACCINE is due!");

            }else if(vitA2_admin == 1){

                this.vitA2_status.setText("VACCINE Was Administered!");
            }else{

                this.vitA2_status.setText("");
            }

            this.meas_days.setText(String.valueOf(measles_days));
            this.meas_date.setText(measles_date_admin);

            if(measles_due == 1){

                this.meas_status.setText("VACCINE is due!");

            }else if(measles_admin == 1){

                this.meas_status.setText("VACCINE Was Administered!");
            }else{

                this.meas_status.setText("");
            }

            this.yellow_days.setText(String.valueOf(yellow_days));
            this.yellow_date.setText(yellow_date_admin);

            if(yellow_due == 1){

                this.yellow_status.setText("VACCINE is due!");

            }else if(yellow_admin == 1){

                this.yellow_status.setText("VACCINE Was Administered!");
            }else{

                this.yellow_status.setText("");
            }

                    /*
                    if (opv1_due == 1 || bcg1_due == 1 || hepB1_due == 1 ||
                            dpt1_due == 1 || hibB1_due == 1 || hepB2_due == 1 ||
                            opv2_due == 1 || pneu_due == 1 || rota1_due == 1 ||
                            dpt2_due == 1 || hibB2_due == 1 || hepB3_due == 1 ||
                            opv3_due == 1 || vitA1_due == 1 || rota2_due == 1 ||
                            vitA2_due == 1 || measles_due == 1 || yellow_due == 1
                    ) {



                    }
*/
                    /*
                    childrenList.add(new ChildModel(id, firstName, lastName, gender,
                            opv1_due, bcg1_due, hepB1_due, dpt1_due, hibB1_due, hepB2_due,
                            opv2_due, pneu_due, rota1_due, dpt2_due, hibB2_due, hepB3_due,
                            opv3_due, vitA1_due, rota2_due, vitA2_due, measles_due, yellow_due, vaccineDue));
                            */



            //  Log.d("Children------->", childrenList.toString());
        } catch (JSONException jsonE) {


            Log.d("Err---->", jsonE.toString());
        }finally {



        }


    }

    private void moveToMainActivity(){

        startActivity(new Intent(this, MainActivity.class));
    }
}