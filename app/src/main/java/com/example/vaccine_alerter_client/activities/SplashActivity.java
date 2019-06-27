package com.example.vaccine_alerter_client.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.crashlytics.android.Crashlytics;
import com.example.vaccine_alerter_client.R;
import com.example.vaccine_alerter_client.data.Const;
import com.example.vaccine_alerter_client.data.PreferenceManager;
import com.example.vaccine_alerter_client.util.Mtandao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends BaseActivity {

    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private FirebaseAnalytics mFirebaseAnalytics;
    private View rootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        rootView = (View) findViewById(R.id.activity_splash_view);
        getDomain();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDomain();

    }

    private void getDomain() {
        Log.d("---->","Get Domain");

        if (Mtandao.checkInternet(getApplicationContext())) {

            mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
            mFirebaseRemoteConfig.fetch(0)
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                mFirebaseRemoteConfig.activateFetched();
                                String domain = mFirebaseRemoteConfig.getString("domain");
                                Log.d("----->", "domain from firebase");
                                Log.d("----->", "domain:" + domain);
                                Const.setDomain(domain);
                                new PreferenceManager(getApplicationContext()).setDomain(domain);
                                // mFirebaseRemoteConfig.activateFetched();
                                showSplashScreen();

                            } else {

                                showSnackBar();
                            }

                        }
                    });
        }else
            showSnackBar();


/*

        mFirebaseRemoteConfig.reset();
        mFirebaseRemoteConfig.ensureInitialized();
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(SplashActivity.this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            String currentDomain = new PreferenceManager(getApplicationContext()).getDomain();
                            if(updated){

                                Log.d("--->","fetch and updated");


                                Log.d("--->",mFirebaseRemoteConfig.getString("domain"));
                            }else{
                                Log.d("--->","fetch but not updated");
                                Const.setDomain(currentDomain);

                            }


                        } else {
                            Log.d("--->","fetch failed");
                            }

                    }
                });
                */
    }

    private void showSplashScreen() {

        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(2 * 1000);
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {

                    Crashlytics.logException(e);
                    exitApp();
                }
            }
        };

        background.start();

    }

    public void showSnackBar() {
        View rootView = (View) findViewById(R.id.activity_splash_view);
        Snackbar.make(rootView, "App could not initialize, check internet connection", Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.YELLOW)
                .setAction(R.string.Retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        getDomain();
                    }
                }).show();

    }
}
