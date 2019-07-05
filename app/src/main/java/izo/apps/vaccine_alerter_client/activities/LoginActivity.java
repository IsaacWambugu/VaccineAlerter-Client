package izo.apps.vaccine_alerter_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;
import com.crashlytics.android.Crashlytics;
import izo.apps.vaccine_alerter_client.R;
import izo.apps.vaccine_alerter_client.data.PreferenceManager;
import izo.apps.vaccine_alerter_client.interfaces.LoadContentListener;
import izo.apps.vaccine_alerter_client.network.NetWorker;
import izo.apps.vaccine_alerter_client.util.Mtandao;
import com.victor.loading.rotate.RotateLoading;
import io.fabric.sdk.android.Fabric;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends BaseActivity implements LoadContentListener {

    private Button continueBtn;
    private EditText guardianId;
    private View view;
    private RotateLoading rotateLoading;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);
        if(new PreferenceManager(this).getGuardianId()==-1){

            setUiConfig();

        }else{

            goToMainActivity();

        }

    }

    private void setUiConfig(){

        view = getWindow().getDecorView().getRootView();
        toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        toolbar.setTitle("Login");
        continueBtn = (Button) findViewById(R.id.btn_continue);
        guardianId = (EditText) findViewById(R.id.input_id);
        rotateLoading = (RotateLoading) findViewById(R.id.rotateloading);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(guardianId.getText().toString().trim().length() == 0){

                    LoginActivity.super.showAlertSnackBar(view,"Provide Id number before you proceed");

                }else if(guardianId.getText().toString().trim().length() < 5){

                    LoginActivity.super.showAlertSnackBar(view,"Provide a valid Id number and try again");


                }
                else{

                    if(Mtandao.checkInternet(getApplicationContext())) {

                        verifyGuardianId(guardianId.getText().toString());
                    }else{

                        LoginActivity.super.showAlertSnackBar(view,"Check internet Connection and try again!");

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

    private void verifyGuardianId(String id){

        continueBtn.setVisibility(View.GONE);
        rotateLoading.start();
        new NetWorker().checkGuardian(this,this,id);

    }

    @Override
    public void onLoadErrorResponse(Pair response) {
       // rotateLoading.setVisibility(View.GONE);
        rotateLoading.stop();
        continueBtn.setText("Retry");
        continueBtn.setVisibility(View.VISIBLE);
        showAlertSnackBar(view,response.second.toString());

    }

    @Override
    public void onLoadValidResponse(JSONObject response) {

        try {

            int id = response.getInt("guardian_id");
            String fname = response.getJSONObject("details").getString("fname");
            String lname = response.getJSONObject("details").getString("lname");
            String gender = response.getJSONObject("details").getString("gender");
            String phoneNo = String.valueOf(response.getJSONObject("details").getString("phone_number"));

            String fullName = fname + " "+ lname;
            saveGuardianDetails(id, fullName, gender, phoneNo);


        }catch(JSONException jsonE){

            Crashlytics.logException(jsonE);
            Log.d("---->",jsonE.toString());
            exitApp();
        }

        rotateLoading.stop();
        continueBtn.setVisibility(View.VISIBLE);
        moveToMainActivity();

    }
    private void goToMainActivity(){

        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

        exitApp();
    }

}
