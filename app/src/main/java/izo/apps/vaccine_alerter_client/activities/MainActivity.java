package izo.apps.vaccine_alerter_client.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import com.crashlytics.android.Crashlytics;
import izo.apps.vaccine_alerter_client.R;
import izo.apps.vaccine_alerter_client.adapters.ChildrenAdapter;
import izo.apps.vaccine_alerter_client.data.PreferenceManager;
import izo.apps.vaccine_alerter_client.etc.DividerItemDecoration;
import izo.apps.vaccine_alerter_client.interfaces.LoadContentListener;
import izo.apps.vaccine_alerter_client.models.ChildModel;
import izo.apps.vaccine_alerter_client.network.NetWorker;
import izo.apps.vaccine_alerter_client.receivers.OnBootReceiver;
import izo.apps.vaccine_alerter_client.schedulers.VaccineCheckSchedule;
import izo.apps.vaccine_alerter_client.util.Mtandao;
import com.google.android.material.navigation.NavigationView;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends BaseActivity implements LoadContentListener,
        NavigationView.OnNavigationItemSelectedListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private int backPressCount = 0;
    private Toolbar toolbar;
    private View view;
    private RecyclerView recyclerView;
    private ChildrenAdapter childrenAdapter;
    private ArrayList<ChildModel> childrenList;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUIComponents();
        loadChildren();
        enableOnBootReceiver();
        scheduleJob();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    protected void setUIComponents() {

        drawerLayout = findViewById(R.id.drawer_layout);
        view = getWindow().getDecorView().getRootView();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_children);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.child_swipe_container);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView nav_names = (TextView) header.findViewById(R.id.nav_names);
        TextView nav_number = (TextView) header.findViewById(R.id.nav_number);
        TextView nav_gender = (TextView) header.findViewById(R.id.nav_gender);


        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Children");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_hamburger);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
        nav_names.setText(new PreferenceManager(this).getGuardianName());
        nav_number.setText(new PreferenceManager(this).getGuardianNumber());
        nav_gender.setText(new PreferenceManager(this).getGuardianGender());

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override

            public void onRefresh() {

                loadChildren();

            }

        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,

                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        childrenList = new ArrayList<>();
        childrenAdapter = new ChildrenAdapter(childrenList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(childrenAdapter);

    }

    private void loadChildren() {

        swipeRefreshLayout.setRefreshing(true);

        if (Mtandao.checkInternet(getApplicationContext())) {

            new NetWorker().loadChildren(this, String.valueOf(new PreferenceManager(this).getGuardianId()));

        } else {

            showAlertSnackBar(view,"Check internet connection and try again!");
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onBackPressed() {
        backPressCount++;

        if(backPressCount>1){
            exitApp();
        }

    }

    @Override
    public void onLoadValidResponse(JSONObject response) {

        childrenList.clear();
        extractJSONResponse(response);
        displayChildrenList();
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onLoadErrorResponse(Pair response) {

        swipeRefreshLayout.setRefreshing(false);
        showAlertSnackBar(view,response.second.toString());

    }

    private void extractJSONResponse(JSONObject json) {

        try {

            for (int i = 0; i < json.getJSONArray("children").length(); i++) {

                Boolean vaccineDue = false;

                int id = json.getJSONArray("children").getJSONObject(i).getInt("id");
                String firstName = json.getJSONArray("children").getJSONObject(i).getString("first_name");
                String lastName = json.getJSONArray("children").getJSONObject(i).getString("last_name");
                String gender = json.getJSONArray("children").getJSONObject(i).getString("gender");

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
                        opv2_due == 1 || pneu_due == 1 || rota1_due == 1 ||
                        dpt2_due == 1 || hibB2_due == 1 || hepB3_due == 1 ||
                        opv3_due == 1 || vitA1_due == 1 || rota2_due == 1 ||
                        vitA2_due == 1 || measles_due == 1 || yellow_due == 1
                ) {

                    vaccineDue = true;

                }

                childrenList.add(new ChildModel(id, firstName, lastName, gender,
                        opv1_due, bcg1_due, hepB1_due, dpt1_due, hibB1_due, hepB2_due,
                        opv2_due, pneu_due, rota1_due, dpt2_due, hibB2_due, hepB3_due,
                        opv3_due, vitA1_due, rota2_due, vitA2_due, measles_due, yellow_due, vaccineDue));

            }

        } catch (JSONException jsonE) {
            Crashlytics.logException(jsonE);
            exitApp();
        }

    }

    public void displayChildrenList() {

        int resId = R.anim.layout_animation_down_to_up;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(), resId);
        recyclerView.setLayoutAnimation(animation);
        childrenAdapter.notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();

    }

    private void stopScheduledJob() {

        VaccineCheckSchedule.stopVaccineChecker(this);
    }

    private void scheduleJob() {

        if(!(new PreferenceManager(getApplicationContext()).getCheckScheduler())){

            new PreferenceManager(getApplicationContext()).setDate();
            VaccineCheckSchedule.startVaccineChecker(this);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_log_out) {

            Intent intent = new Intent(this, LoginActivity.class);
            clearGuardianDetails();
            stopScheduledJob();
            startActivity(intent);
        }
        return true;
    }

    private void clearGuardianDetails() {

        PreferenceManager preferenceManager = new PreferenceManager(this);
        preferenceManager.setGuardianNumber(null);
        preferenceManager.setGuardianGender(null);
        preferenceManager.setGuardianId(-1);
        preferenceManager.setGuardianName(null);
        preferenceManager.setScheduler(false);

    }

    private void enableOnBootReceiver() {

        ComponentName receiver = new ComponentName(this, OnBootReceiver.class);
        PackageManager pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

}

