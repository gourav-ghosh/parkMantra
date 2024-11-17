package com.example.parkmantra.startup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.example.parkmantra.R;
import com.example.parkmantra.common.Common;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Common.intent(SplashScreenActivity.this, LoginWithPasswordActivity.class);
                finish();
            }
        }, 3000);
    }
}