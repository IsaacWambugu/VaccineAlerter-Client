package izo.apps.vaccine_alerter_client.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crashlytics.android.Crashlytics;
import izo.apps.vaccine_alerter_client.R;
import izo.apps.vaccine_alerter_client.interfaces.LoadContentListener;
import izo.apps.vaccine_alerter_client.network.NetWorker;
import izo.apps.vaccine_alerter_client.util.Mtandao;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ChildDetailsActivity extends BaseActivity implements LoadContentListener {

    private Toolbar toolbar;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout activity_layout;
    private String id = "";
    private TextView dpt1_days,
            dpt1_date,
            dpt2_days,
            dpt2_date,
            hepb1_days,
            hepb1_date,
            hepb2_days,
            hepb2_date,
            hepb3_days,
            hepb3_date,
            hib1_days,
            hib1_date,
            hib2_days,
            hib2_date,
            meas_days,
            meas_date,
            opv1_days,
            opv1_date,
            opv2_days,
            opv2_date,
            opv3_days,
            opv3_date,
            pneu_date,
            pneu_days,
            rota1_days,
            rota1_date,
            rota2_days,
            rota2_date,
            vitA1_days,
            vitA1_date,
            vitA2_days,
            vitA2_date,
            yellow_days,
            yellow_date;

    private ImageView atBirthAlert,
            sixWAlert,
            fourteenWAlert,
            sixMAlert,
            nineMAlert,
            bcg1_status;

    private TextView child_name,
            child_gender,
            child_dob,
            bcg1_days,
            bcg1_date;

    private ImageView dpt2_status,
            hepb1_status,
            hepb2_status,
            hepb3_status,
            dpt1_status,
            hib1_status,
            hib2_status,
            meas_status,
            opv1_status,
            opv2_status,
            opv3_status,
            pneu_status,
            rota1_status,
            rota2_status,
            vitA1_status,
            vitA2_status,
            yellow_status,
            child_image;
    private String fname, lname, gender, dob = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_details);
        getIncomingIntent(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setUIConfig();
        loadVaccines();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            moveToMainActivity();

        }
        return super.onOptionsItemSelected(item);
    }


    private void setUIConfig() {

        atBirthAlert = (ImageView) findViewById(R.id.vaccine_birth_status);
        sixWAlert = (ImageView) findViewById(R.id.vaccine_6w_status);
        fourteenWAlert = (ImageView) findViewById(R.id.vaccine_14w_status);
        sixMAlert = (ImageView) findViewById(R.id.vaccine_6m_status);
        nineMAlert = (ImageView) findViewById(R.id.vaccine_9m_status);
        child_image = (ImageView) findViewById(R.id.child_profile_image);
        activity_layout = (LinearLayout) findViewById(R.id.layout_child_details_view);
        child_name = (TextView) findViewById(R.id.child_profile_name);
        child_dob = (TextView) findViewById(R.id.child_profile_dob);
        child_gender = (TextView) findViewById(R.id.child_profile_gender);

        bcg1_days = (TextView) findViewById(R.id.bcg1_days);
        bcg1_date = (TextView) findViewById(R.id.bcg1_date);
        bcg1_status = (ImageView) findViewById(R.id.bcg1_status);

        dpt1_days = (TextView) findViewById(R.id.dpt1_days);
        dpt1_date = (TextView) findViewById(R.id.dpt1_date);
        dpt1_status = (ImageView) findViewById(R.id.dpt1_status);

        dpt2_days = (TextView) findViewById(R.id.dpt2_days);
        dpt2_date = (TextView) findViewById(R.id.dpt2_date);
        dpt2_status = (ImageView) findViewById(R.id.dpt2_status);

        hepb1_days = (TextView) findViewById(R.id.hepb1_days);
        hepb1_date = (TextView) findViewById(R.id.hepb1_date);
        hepb1_status = (ImageView) findViewById(R.id.hepb1_status);

        hepb2_days = (TextView) findViewById(R.id.hepb2_days);
        hepb2_date = (TextView) findViewById(R.id.hepb2_date);
        hepb2_status = (ImageView) findViewById(R.id.hepb2_status);

        hepb3_days = (TextView) findViewById(R.id.hepb3_days);
        hepb3_date = (TextView) findViewById(R.id.hepb3_date);
        hepb3_status = (ImageView) findViewById(R.id.hepb3_status);

        hib1_days = (TextView) findViewById(R.id.hib1_days);
        hib1_date = (TextView) findViewById(R.id.hib1_date);
        hib1_status = (ImageView) findViewById(R.id.hib1_status);

        hib2_days = (TextView) findViewById(R.id.hib2_days);
        hib2_date = (TextView) findViewById(R.id.hib2_date);
        hib2_status = (ImageView) findViewById(R.id.hib2_status);

        hib2_days = (TextView) findViewById(R.id.hib2_days);
        hib2_date = (TextView) findViewById(R.id.hib2_date);
        hib2_status = (ImageView) findViewById(R.id.hib2_status);

        meas_days = (TextView) findViewById(R.id.meas_days);
        meas_date = (TextView) findViewById(R.id.meas_date);
        meas_status = (ImageView) findViewById(R.id.meas_status);

        opv1_days = (TextView) findViewById(R.id.opv1_days);
        opv1_date = (TextView) findViewById(R.id.opv1_date);
        opv1_status = (ImageView) findViewById(R.id.opv1_status);

        opv2_days = (TextView) findViewById(R.id.opv2_days);
        opv2_date = (TextView) findViewById(R.id.opv2_date);
        opv2_status = (ImageView) findViewById(R.id.opv2_status);

        opv3_days = (TextView) findViewById(R.id.opv3_days);
        opv3_date = (TextView) findViewById(R.id.opv3_date);
        opv3_status = (ImageView) findViewById(R.id.opv3_status);

        pneu_days = (TextView) findViewById(R.id.pneu_days);
        pneu_date = (TextView) findViewById(R.id.pneu_date);
        pneu_status = (ImageView) findViewById(R.id.pneu_status);

        rota1_days = (TextView) findViewById(R.id.rota1_days);
        rota1_date = (TextView) findViewById(R.id.rota1_date);
        rota1_status = (ImageView) findViewById(R.id.rota1_status);

        rota2_days = (TextView) findViewById(R.id.rota2_days);
        rota2_date = (TextView) findViewById(R.id.rota2_date);
        rota2_status = (ImageView) findViewById(R.id.rota2_status);

        vitA1_days = (TextView) findViewById(R.id.vit1_days);
        vitA1_date = (TextView) findViewById(R.id.vit1_date);
        vitA1_status = (ImageView) findViewById(R.id.vit1_status);

        vitA2_days = (TextView) findViewById(R.id.vit2_days);
        vitA2_date = (TextView) findViewById(R.id.vit2_date);
        vitA2_status = (ImageView) findViewById(R.id.vit2_status);

        yellow_days = (TextView) findViewById(R.id.yellow_days);
        yellow_date = (TextView) findViewById(R.id.yellow_date);
        yellow_status = (ImageView) findViewById(R.id.yellow_status);

        toolbar = (Toolbar) findViewById(R.id.toolbar_vaccine_list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.vaccine_swipe_container);
        view = getWindow().getDecorView().getRootView();
        toolbar.setTitle("Vaccines");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        try {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_back);

        } catch (NullPointerException npE) {

            finish();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override

            public void onRefresh() {

                loadVaccines();

            }

        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,

                android.R.color.holo_green_light,

                android.R.color.holo_orange_light,

                android.R.color.holo_red_light);
    }

    private void loadVaccines() {

        swipeRefreshLayout.setRefreshing(true);

        if (Mtandao.checkInternet(getApplicationContext())) {

            new NetWorker().loadChildDetails(this, id);

        } else {

            showAlertSnackBar(view, "Check internet connection and try again!");
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void onLoadValidResponse(JSONObject response) {

        extractJSONResponse(response);
        activity_layout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);


    }

    @Override
    public void onLoadErrorResponse(Pair response) {

        swipeRefreshLayout.setRefreshing(false);
        showAlertSnackBar(view, response.second.toString());

    }

    private void getIncomingIntent(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

                moveToMainActivity();

            } else {

                id = extras.getString("childId");

            }
        } else {

            id = (String) savedInstanceState.getSerializable("childId");

        }

    }

    private void extractJSONResponse(JSONObject json) {

        try {

            //extract child details
            fname = json.getJSONObject("details").getString("fname");
            lname = json.getJSONObject("details").getString("lname");
            dob = json.getJSONObject("details").getString("dob");
            gender = json.getJSONObject("details").getString("gender");


            if (gender.equals("Male")) {
                this.child_image.setBackground(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_male,
                        null));

            } else if (gender.equals("Female")) {

                this.child_image.setBackground(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_female,
                        null));

            }

            //extract vaccines
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

            int hepB3_due = json.getJSONObject("vaccines").getJSONObject("HEPB3").getInt("due");
            int hepB3_admin = json.getJSONObject("vaccines").getJSONObject("HEPB3").getInt("administered");
            String hepB3_date_admin = json.getJSONObject("vaccines").getJSONObject("HEPB3").getString("date_administered");
            int hepB3_days = json.getJSONObject("vaccines").getJSONObject("HEPB3").getInt("days");

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
            if (bcg1_due == 1)
                setVaccineAlertIcon(false, this.bcg1_status);
            else if (bcg1_admin == 1)
                setVaccineAlertIcon(true, this.bcg1_status);

            this.dpt1_days.setText(String.valueOf(dpt1_days));
            this.dpt1_date.setText(dpt1_date_admin);
            if (dpt1_due == 1)
                setVaccineAlertIcon(false, this.dpt1_status);
            else if (dpt1_admin == 1)
                setVaccineAlertIcon(true, this.dpt1_status);


            this.dpt2_days.setText(String.valueOf(dpt2_days));
            this.dpt2_date.setText(dpt2_date_admin);
            if (dpt2_due == 1)
                setVaccineAlertIcon(false, this.dpt2_status);
            else if (dpt2_admin == 1)
                setVaccineAlertIcon(true, this.dpt2_status);


            this.hepb1_days.setText(String.valueOf(hepB1_days));
            this.hepb1_date.setText(hepB1_date_admin);
            if (hepB1_due == 1)
                setVaccineAlertIcon(false, this.hepb1_status);
            else if (hepB1_admin == 1)
                setVaccineAlertIcon(true, this.hepb1_status);


            this.hepb2_days.setText(String.valueOf(hepB2_days));
            this.hepb2_date.setText(hepB2_date_admin);
            if (hepB2_due == 1)
                setVaccineAlertIcon(false, this.hepb2_status);
            else if (hepB2_admin == 1)
                setVaccineAlertIcon(true, this.hepb2_status);


            this.hepb3_days.setText(String.valueOf(hepB3_days));
            this.hepb3_date.setText(hepB3_date_admin);
            if (hepB3_due == 1)
                setVaccineAlertIcon(false, this.hepb3_status);
            else if (hepB3_admin == 1)
                setVaccineAlertIcon(true, this.hepb3_status);


            this.hib1_days.setText(String.valueOf(hibB1_days));
            this.hib1_date.setText(hibB1_date_admin);
            if (hibB1_due == 1)
                setVaccineAlertIcon(false, this.hib1_status);
            else if (hibB1_admin == 1)
                setVaccineAlertIcon(true, this.hib1_status);



            this.hib2_days.setText(String.valueOf(hibB2_days));
            this.hib2_date.setText(hibB2_date_admin);
            if (hibB2_due == 1)
                setVaccineAlertIcon(false, this.hib2_status);
            else if (hibB2_admin == 1)
                setVaccineAlertIcon(true, this.hib2_status);

            this.meas_days.setText(String.valueOf(measles_days));
            this.meas_date.setText(measles_date_admin);
            if (measles_due == 1)
                setVaccineAlertIcon(false, this.meas_status);
              else if (measles_admin == 1)
                setVaccineAlertIcon(true, this.meas_status);

            this.opv1_days.setText(String.valueOf(opv1_days));
            this.opv1_date.setText(opv1_date_admin);
            if (opv1_due == 1)
                setVaccineAlertIcon(false, this.opv1_status);
                else if (opv1_admin == 1)
                setVaccineAlertIcon(true, this.opv1_status);


            this.opv2_days.setText(String.valueOf(opv2_days));
            this.opv2_date.setText(opv2_date_admin);

            if (opv2_due == 1)
                setVaccineAlertIcon(false, this.opv2_status);
            else if (opv2_admin == 1)
                setVaccineAlertIcon(true, this.opv2_status);


            this.opv3_days.setText(String.valueOf(opv3_days));
            this.opv3_date.setText(opv3_date_admin);
            if (opv3_due == 1)
                setVaccineAlertIcon(false, this.opv3_status);
            else if (opv3_admin == 1)
                setVaccineAlertIcon(true, this.opv3_status);


            this.pneu_days.setText(String.valueOf(pneu_days));
            this.pneu_date.setText(pneu_date_admin);
            if (pneu_due == 1)
                setVaccineAlertIcon(false, this.pneu_status);
            else if (pneu_admin == 1)
                setVaccineAlertIcon(true, this.pneu_status);


            this.rota1_days.setText(String.valueOf(rota1_days));
            this.rota1_date.setText(rota1_date_admin);
            if (rota1_due == 1)
                setVaccineAlertIcon(false, this.rota1_status);
            else if (rota1_admin == 1)
                setVaccineAlertIcon(true, this.rota1_status);


            this.rota2_days.setText(String.valueOf(rota2_days));
            this.rota2_date.setText(rota2_date_admin);
            if (rota2_due == 1)
                setVaccineAlertIcon(false, this.rota2_status);
            else if (rota2_admin == 1)
                setVaccineAlertIcon(true, this.rota2_status);


            this.vitA1_days.setText(String.valueOf(vitA1_days));
            this.vitA1_date.setText(vitA1_date_admin);
            if (vitA1_due == 1)
                setVaccineAlertIcon(false, this.vitA1_status);
            else if (vitA1_admin == 1)
                setVaccineAlertIcon(true, this.vitA1_status);


            this.vitA2_days.setText(String.valueOf(vitA2_days));
            this.vitA2_date.setText(vitA2_date_admin);
            if (vitA2_due == 1)
                setVaccineAlertIcon(false, this.vitA2_status);
            else if (vitA2_admin == 1)
                setVaccineAlertIcon(true, this.vitA2_status);


            this.yellow_days.setText(String.valueOf(yellow_days));
            this.yellow_date.setText(yellow_date_admin);
            if (yellow_due == 1)
                setVaccineAlertIcon(false, this.yellow_status);
            else if (yellow_admin == 1)
                setVaccineAlertIcon(true, this.yellow_status);


            //set expansion layout icon
            if (bcg1_due == 1 || opv1_due == 1 || hepB1_due == 1) {

                showWarningAlertIcon(atBirthAlert);

            }
            if (dpt1_due == 1 || opv2_due == 1 || hepB2_due == 1 || hibB1_due == 1 || pneu_due == 1 || rota1_due == 1) {

                showWarningAlertIcon(sixWAlert);

            }
            if (opv3_due == 1 || hepB3_due == 1 || dpt2_due == 1 || hibB2_due == 1 || vitA1_due == 1 || rota2_due == 1) {

                showWarningAlertIcon(fourteenWAlert);

            }
            if (vitA2_due == 1) {
                showWarningAlertIcon(sixMAlert);

            }
            if (measles_due == 1 || yellow_due == 1) {
                showWarningAlertIcon(nineMAlert);

            }
            //set child profile

            String name = fname + " " + lname;
            child_name.setText(name);
            child_gender.setText(gender);
            child_dob.setText(dob);

        } catch (JSONException jsonE) {

            Crashlytics.logException(jsonE);
            showAlertSnackBar(view, "Something went wrong! Try again later");
            afterSnackBarAction();
        }

    }

    private void moveToMainActivity() {

        startActivity(new Intent(this, MainActivity.class));
    }

    private void showWarningAlertIcon(ImageView imageView) {

        imageView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_priority_high, null));
        imageView.setVisibility(View.VISIBLE);
    }

    private void afterSnackBarAction() {

        new CountDownTimer(1000, 2000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                finish();
            }

        }.start();
    }

    private void setVaccineAlertIcon(boolean check, ImageView imageView) {

        if (check)
            imageView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_check, null));
        else
            imageView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_priority_high, null));

    }

    @Override
    public void onBackPressed() {
        moveToMainActivity();
    }

}