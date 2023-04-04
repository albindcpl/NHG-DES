package com.dcpl.printfromonbase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Get the saved admin username, password, and EULA status from encrypted shared preferences
                MasterKey masterKey = null;
                SharedPreferences sharedPreferences = null;
                try {
                    masterKey = new MasterKey.Builder(getApplicationContext())
                            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                            .build();
                    sharedPreferences = EncryptedSharedPreferences.create(
                            getApplicationContext(),
                            "MyPref",
                            masterKey,
                            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
                } catch (GeneralSecurityException | IOException e) {
                    e.printStackTrace();
                }

                String username = sharedPreferences.getString("username", "");
                String password = sharedPreferences.getString("password", "");
                String EULAcheck = sharedPreferences.getString("EULAApproved", "");

                if (EULAcheck.equals("True")) {
                    if (username.equals("") || password.equals("")) {
                        Intent adminIntent = new Intent(SplashActivity.this, Admin_Credentials_Setup.class);
                        startActivity(adminIntent);
                        finish();
                    } else {
                        Intent onBaseIntent = new Intent(SplashActivity.this, OnBase_Login_Screen.class);
                        startActivity(onBaseIntent);
                        finish();
                    }
                } else {
                    Intent termsIntent = new Intent(SplashActivity.this, TermsAndConditions.class);
                    startActivity(termsIntent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
