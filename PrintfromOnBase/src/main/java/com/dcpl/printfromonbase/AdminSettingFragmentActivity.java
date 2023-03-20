/**
 * dminSettingFragmentActivity that extends the AppCompatActivity. It is responsible for displaying the admin
 * setting fragment screen of the application.
 *
 * The activity contains an ImageView called imageView and a TextView called textView.
 * The imageView is used as a back button to navigate back to the OnBase login screen. The textView is used
 * to display the username of the logged-in user.
 *
 * The activity also contains another ImageView called imageVieww, which is used as a menu button to
 * navigate to the information screen of the application.
 *
 * In onCreate() method, the activity sets the content view to the layout resource file activity_fragment_adminsetting.xml.
 * It also sets an OnClickListener on the imageView and imageVieww to navigate to the OnBase login screen and information
 * screen respectively, when they are clicked.
 */


package com.dcpl.printfromonbase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class AdminSettingFragmentActivity extends AppCompatActivity {

    ImageView imageView;
    ImageView imageVieww;
    private TextView textView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_fragment_adminsetting);

        imageView = findViewById(R.id.ivBackk);
        textView = findViewById(R.id.tvtext);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),OnBase_Login_Screen.class);
                startActivity(intent1);
                finish();
            }
        });


//        if(LoginActivity.adauth==true){
//            textView.setText(LoginActivity.stringusername);
//            textView.setCompoundDrawablesWithIntrinsicBounds(
//                    R.drawable.hello, 0, 0, 0);
//        }
//        else if(LoginActivity.adauth==false) {
//
//            textView.setText(LoginActivity.stringusername);
//            textView.setCompoundDrawablesWithIntrinsicBounds(
//                    R.drawable.hello, 0, 0, 0);
//        }

        imageVieww = findViewById(R.id.ivMenuBarr);
        imageVieww.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Information_Screen.class);
                startActivity(intent);

            }
        });

    }
}
