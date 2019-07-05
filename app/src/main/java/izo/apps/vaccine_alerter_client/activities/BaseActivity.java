package izo.apps.vaccine_alerter_client.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import izo.apps.vaccine_alerter_client.R;
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

        Intent start = new Intent(Intent.ACTION_MAIN);
        start.addCategory(Intent.CATEGORY_HOME);
        start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(start);
    }

}
