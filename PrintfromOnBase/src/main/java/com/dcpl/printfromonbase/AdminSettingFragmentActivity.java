/**
 * AdminSettingFragmentActivity that extends the AppCompatActivity. It is responsible for displaying the admin
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

/**
 * This activity is implementing a screen for the admin settings. It sets a flag to prevent screenshots and
 * screen recording, sets a layout for the activity, finds the ImageView and TextView views, and sets a click
 * listener on the ImageView to navigate to another activity when clicked. It also sets a click listener on
 * another ImageView to navigate to a different activity when clicked.
 */

public class AdminSettingFragmentActivity extends AppCompatActivity {

//    Declare three class-level variables: imageView, imageVieww, and textView.
    ImageView imageView;
    ImageView imageVieww;
    private TextView textView;

    //The @SuppressLint("WrongViewCast") annotation is used to suppress lint warnings about potentially incorrect casts.
    @SuppressLint("WrongViewCast")

    //Override the onCreate method of the parent class, indicating that this method will be called when the activity is created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //Call the onCreate method of the parent class, passing in the saved instance state
        super.onCreate(savedInstanceState);


        //Set a flag on the window to prevent screenshots and screen recording.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        //Set the layout for this activity to activity_fragment_adminsetting.xml.
        setContentView(R.layout.activity_fragment_adminsetting);


        //Find the ImageView and TextView views in the layout by their respective IDs.
        //Set a click listener on the ImageView to navigate to the OnBase_Login_Screen activity when it's clicked, using an intent.
        //Call finish() to close this activity after starting the new one.
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
        //imageVieww is assigned the ImageView that has an ID of ivMenuBarr using findViewById
        imageVieww = findViewById(R.id.ivMenuBarr);


        //new View.OnClickListener is created using an anonymous inner class that overrides the
        // onClick method. When the imageVieww is clicked, this onClick method will be called
        imageVieww.setOnClickListener(new View.OnClickListener() {



            //Inside the onClick method, an Intent object is created that specifies the Information_Screen activity to be started.
            // The getApplicationContext() method is used to get the application context, and the startActivity method is called on the
            // Intent object to start the activity. This will switch to the Information_Screen activity.
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Information_Screen.class);
                startActivity(intent);

            }
        });

    }
}
