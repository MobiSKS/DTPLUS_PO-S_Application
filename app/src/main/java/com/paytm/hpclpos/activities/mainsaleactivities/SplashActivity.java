package com.paytm.hpclpos.activities.mainsaleactivities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.stetho.Stetho;
import com.paytm.hpclpos.R;
import com.paytm.hpclpos.constants.Constants;
import com.paytm.hpclpos.constants.GlobalMethods;
import com.paytm.hpclpos.constants.SharedPreferencesData;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Stetho.initializeWithDefaults(this);
        SharedPreferencesData sharedPref = new SharedPreferencesData(this);
        // Checks for terminal id is null navigating to Terminal Id Screen
        if (!sharedPref.getSharedPreferenceData(Constants.TOKENIDPREF, Constants.TERMINAL_ID).equals("")) {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(SplashActivity.this, TerminalIdActivity.class);
            startActivity(i);
            finish();
        }
    }
}