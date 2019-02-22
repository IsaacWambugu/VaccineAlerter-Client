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
import com.example.vaccine_alerter_client.util.Mtandao;
import com.google.android.material.snackbar.Snackbar;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONException;
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
        if(new PreferenceManager(this).getGuardianId()==-1){

            setUiConfig();

        }else{

            goToMainActivity();

        }


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

                    if(Mtandao.checkInternet(getApplicationContext())) {

                        verifyGuardianId(guardianId.getText().toString());
                    }else{

                        showSnackBar("Check internet Connection and try again!");
                    }

                }

            }
        });
    }
    private void moveToMainActivity(){

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    private void saveGuardianDetails(int id, String names, String gender, String phoneNumber){

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        preferenceManager.setGuardianId(id);
        preferenceManager.setGuardianName(names);
        preferenceManager.setGuardianGender(gender);
        preferenceManager.setGuardianNumber(phoneNumber);

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

        Log.d("valid id--->", response.toString());

        try {

            int id = response.getInt("guardian_id");
            String name = response.getJSONObject("details").getString("full_name");
            String gender = response.getJSONObject("details").getString("gender");
            String phoneNo = String.valueOf(response.getJSONObject("details").getString("phone_number"));

            saveGuardianDetails(id, name, gender, phoneNo);

        }catch(JSONException jsonE){

            Log.d("Err---->", jsonE.toString());
        }

        rotateLoading.stop();
        continueBtn.setVisibility(View.VISIBLE);
        moveToMainActivity();

    }
    private void goToMainActivity(){

        startActivity(new Intent(this,MainActivity.class));
    }

}
