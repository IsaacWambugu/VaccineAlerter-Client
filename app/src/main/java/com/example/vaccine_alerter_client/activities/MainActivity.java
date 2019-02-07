package com.example.vaccine_alerter_client.activities;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import com.example.vaccine_alerter_client.R;
import com.example.vaccine_alerter_client.adapters.ChildrenAdapter;
import com.example.vaccine_alerter_client.data.PreferenceManager;
import com.example.vaccine_alerter_client.interfaces.LoadContentListener;
import com.example.vaccine_alerter_client.models.ChildModel;
import com.example.vaccine_alerter_client.network.NetWorker;
import com.example.vaccine_alerter_client.others.DividerItemDecoration;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity implements LoadContentListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private View view;
    private RecyclerView recyclerView;
    private ChildrenAdapter childrenAdapter;
    private ArrayList<ChildModel> childrenList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUIComponents();
        loadChildren();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    protected void setUIComponents(){

        view = getWindow().getDecorView().getRootView();
        recyclerView    = (RecyclerView) findViewById(R.id.recycler_view_children);
       // activity_site_list = (View) findViewById(R.id.activity_rental_list_view);
        swipeRefreshLayout    = (SwipeRefreshLayout) findViewById(R.id.child_swipe_container);
        toolbar               = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Children List");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override

            public void onRefresh() {

                loadChildren();

            }

        });

        swipeRefreshLayout .setColorSchemeResources(android.R.color.holo_blue_bright,

                android.R.color.holo_green_light,

                android.R.color.holo_orange_light,

                android.R.color.holo_red_light);

        childrenList = new ArrayList<>();
        childrenAdapter = new ChildrenAdapter(childrenList);
        //recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(childrenAdapter);



/*
        sitesData       = new ArrayList<>();
        sitesAdapter    = new SitesAdapter(sitesData,SiteListActivity.this, addStatus);
        recyclerView    = (RecyclerView) findViewById(R.id.rental_list_recycler_view);

        Configuration orientation = new Configuration();

        if(this.recyclerView.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        } else if (this.recyclerView.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sitesAdapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {

                Log.d("Paginating--->","Please wait");
                progressBar.setVisibility(View.VISIBLE);
                if(!addStatus && !endPagination) {

                    loadSiteData();
                }

            }
        });
        */

    }
    private void loadChildren(){

        swipeRefreshLayout.setRefreshing(true);


        new NetWorker().loadChildren(this, this, String.valueOf(new PreferenceManager(this).getGuardianId()));

    }

    @Override
    public void onBackPressed() {

        moveTaskToBack(false);

    }
    @Override
    public void onLoadValidResponse(JSONObject response) {

        childrenList.clear();
        extractJSONResponse(response);
        displayChildrenList();
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onLoadErrorResponse(String response) {

        swipeRefreshLayout.setRefreshing(false);
        showSnackBar("Something went wrong.Try again later");

    }
    private void showSnackBar(String mesg){

        Snackbar.make(view,mesg, Snackbar.LENGTH_SHORT)
                .show();

    }

    private void extractJSONResponse(JSONObject json){


        try {

            for (int i = 0; i < json.getJSONArray("children").length(); i++) {

                Boolean vaccineDue  = false;

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
                        opv2_due == 1 || pneu_due == 1  || rota1_due ==1  ||
                        dpt2_due == 1 || hibB2_due == 1 || hepB3_due == 1 ||
                        opv3_due == 1 || vitA1_due == 1 || rota2_due == 1 ||
                        vitA2_due == 1|| measles_due== 1|| yellow_due == 1
                ){

                    vaccineDue = true;

                }

                childrenList.add(new ChildModel(id, firstName, lastName, gender,
                        opv1_due, bcg1_due, hepB1_due, dpt1_due, hibB1_due, hepB2_due,
                        opv2_due, pneu_due, rota1_due, dpt2_due, hibB2_due, hepB3_due,
                        opv3_due, vitA1_due, rota2_due, vitA2_due, measles_due, yellow_due, vaccineDue));

            }

            Log.d("Children------->", childrenList.toString());
        } catch(JSONException jsonE){



            Log.d("Err---->", jsonE.toString());
        }


    }
    public void displayChildrenList(){

        childrenAdapter.notifyDataSetChanged();


    }
}
