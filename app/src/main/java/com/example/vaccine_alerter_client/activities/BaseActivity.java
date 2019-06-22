package com.example.vaccine_alerter_client.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vaccine_alerter_client.R;
import com.google.android.material.snackbar.Snackbar;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    protected void showAlertSnackBar(View rootView, String msg ){

        Snackbar.make(rootView, msg, Snackbar.LENGTH_LONG).setActionTextColor(Color.RED)
                .setActionTextColor(Color.YELLOW).setAction(R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }
    protected void exitApp(){

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

}
