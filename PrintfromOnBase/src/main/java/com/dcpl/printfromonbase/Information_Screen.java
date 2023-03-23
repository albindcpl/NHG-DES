package com.dcpl.printfromonbase;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;



public class Information_Screen extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);


/**
 * The activity finds an ImageView with the ID "ivBack" in the layout and sets up an OnClickListener for it.
 * When the user clicks on this ImageView, the app creates a new Intent object to launch the "OnBase_Login_Screen" activity
 * and calls the "startActivity" method to start it.
 */
        imageView = findViewById(R.id.ivBack);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OnBase_Login_Screen.class);
                startActivity(intent);
                finish();
            }

        });
    }
}
