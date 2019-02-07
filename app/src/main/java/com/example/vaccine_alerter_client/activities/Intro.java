package com.example.vaccine_alerter_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vaccine_alerter_client.R;
import com.example.vaccine_alerter_client.data.PreferenceManager;
import com.example.vaccine_alerter_client.interfaces.LoadContentListener;
import com.example.vaccine_alerter_client.network.NetWorker;
import com.google.android.material.snackbar.Snackbar;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;


public class Intro extends AppCompatActivity implements LoadContentListener {

    private Button continueBtn;
    private EditText guardianId;
    private View view;
    private RotateLoading rotateLoading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        setUiConfig();

    }

    private void setUiConfig(){

        view = getWindow().getDecorView().getRootView();

        continueBtn = (Button) findViewById(R.id.btn_continue);
        guardianId = (EditText) findViewById(R.id.input_id);
        rotateLoading = (RotateLoading) findViewById(R.id.rotateloading);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(guardianId.getText().toString().trim().length() == 0){

                    showSnackBar("Provide Id number before you proceed");

                }else if(guardianId.getText().toString().trim().length() < 5){

                    showSnackBar("Provide a valid Id number and try again");

                }
                else{

                    verifyGuardianId(guardianId.getText().toString());

                }

            }
        });
    }
    private void moveToMainActivity(){

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    private void saveGuardianId(){
        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        preferenceManager.setGuardianId(Integer.parseInt(guardianId.getText().toString()));

    }
    private void showSnackBar(String mesg){

        Snackbar.make(view,mesg, Snackbar.LENGTH_SHORT)
                .show();

    }
    private void verifyGuardianId(String id){

        continueBtn.setVisibility(View.INVISIBLE);
        rotateLoading.start();
        new NetWorker().checkGuardian(this,this,id);

    }

    @Override
    public void onLoadErrorResponse(String response) {
       // rotateLoading.setVisibility(View.GONE);
        rotateLoading.stop();
        continueBtn.setText("Retry");
        continueBtn.setVisibility(View.VISIBLE);

        showSnackBar("Check Id number and try again!");
    }

    @Override
    public void onLoadValidResponse(JSONObject response) {

        Log.d("Response--->", response.toString());
        saveGuardianId();
        rotateLoading.stop();
        continueBtn.setVisibility(View.VISIBLE);
        moveToMainActivity();

    }

}
