//package com.dcpl.printfromonbase;
//
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.WindowManager;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//
//public class SplashActivity extends AppCompatActivity {
//
//    private final int SPLASH_DISPLAY_LENGTH = 3000;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE);
//
//        setContentView(R.layout.activity_splash);
//
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
//                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
//                String EULAcheck = sharedPreferences.getString("EULAApproved", "");
//
////                String EULAcheck = sharedPreferences.getString("EULAApproved", "True");
////
////                Intent mainIntent = new Intent(SplashActivity.this,Admin_Credentials_Setup.class);
////                SplashActivity.this.startActivity(mainIntent);
////                SplashActivity.this.finish();
//                if(EULAcheck.equals("True"))
//                {
////                    Intent mainIntent = new Intent(SplashActivity.this, TermsAndConditions.class);
////                    SplashActivity.this.startActivity(mainIntent);
////                    SplashActivity.this.finish();
//                    Intent mainIntent = new Intent(SplashActivity.this,OnBase_Login_Screen.class);
//                    SplashActivity.this.startActivity(mainIntent);
//                    SplashActivity.this.finish();
//                }
//                else
//                {
////                    Intent mainIntent = new Intent(SplashActivity.this,OnBase_Login_Screen.class);
////                    SplashActivity.this.startActivity(mainIntent);
////                    SplashActivity.this.finish();
//                    Intent mainIntent = new Intent(SplashActivity.this, TermsAndConditions.class);
//                    SplashActivity.this.startActivity(mainIntent);
//                    SplashActivity.this.finish();
//                }
//
//            }
//        }, SPLASH_DISPLAY_LENGTH);
//    }
//    }



//Updated Splash Activity 25-02-2023
/** this code sets up an activity that displays the splash activity
  Its purpose is to display a splash screen for a set amount of time
 and then navigate to one of three activities depending on
 whether the end user has approved the End User License Agreement (EULA)
 and whether the admin username and password have been saved.
 */
package com.dcpl.printfromonbase;

/**
 *The line of code "import android.content.Intent;" imports the Intent class from the android.content
 * package into the current Java file. This allows the Java code to use the Intent class and create new
 * Intents, which are objects that facilitate communication between different components of an Android app,
 * such as starting a new activity or service.
 */
import android.content.Intent;

/**The line of code "import android.content.SharedPreferences;" imports the SharedPreferences interface
 * from the android.content package into the current Java file. This allows the Java code to use the
 * SharedPreferences interface and access shared preferences, which are used to store data in key-value pairs
 * persistently across multiple sessions of an Android app. The SharedPreferences interface provides methods
 * to read, write, and delete the data stored in shared preferences.
 *
 */
import android.content.SharedPreferences;

/**The line of code "import android.os.Bundle;" imports the Bundle class from the android.os package into the current
 * Java file. This allows the Java code to use the Bundle class and create new Bundle objects, which are used to store
 * and pass data between different components of an Android app. The Bundle class provides methods to put and retrieve
 * values of various types, such as integers, strings, and Parcelable objects, in the bundle. The Bundle is commonly used
 * to pass data between activities or fragments, and is also used by the Android system to save and restore the state of
 * an activity or fragment during configuration changes, such as screen rotations or language changes.
 *
 */
import android.os.Bundle;

/**The line of code "import android.os.Handler;" imports the Handler class from the android.os package into the current
 * Java file. This allows the Java code to use the Handler class to schedule and handle messages and runnables on a thread's
 * message queue.
 *The Handler class provides a way to post messages and runnables to the main thread's message queue, allowing background
 *  threads to communicate with the UI thread. By using a Handler, a background thread can request an update to the UI or
 *  schedule a task to run on the UI thread at a later time.
 *  it allows developers to create responsive and efficient apps that can perform time-consuming tasks without blocking
 *  the UI thread.
 */
import android.os.Handler;


/** This imports the WindowManager class from the Android framework. The WindowManager is responsible for managing the
 * windows that are displayed on the device screen. It provides access to a variety of window-related functions, such
 * as adjusting the size and position of windows, adding or removing views, and handling events related to windows.
 * By importing this class, we can use its functionality to manipulate the window of the activity in which our code is running.
 *
 */
import android.view.WindowManager;

//androidx.appcompat.app.AppCompatActivity is a class in the AndroidX library
// that serves as a base class for activities that use the AppCompat library features,
// such as providing a consistent look across different versions of Android,
// supporting the latest material design guidelines, and enabling backward compatibility with older versions of Android.
//By extending AppCompatActivity in your activity class, you can use the AppCompat library's features,
// such as adding an action bar, supporting the material design theme, enabling vector drawables,
// and implementing the latest Android design guidelines. This class provides access to various useful
// methods that help in handling the activity lifecycle, such as onCreate(), onStart(),
// onResume(), onPause(), onStop(), onRestart(), and onDestroy().
import androidx.appcompat.app.AppCompatActivity;

/**The SplashActivity class extends the AppCompatActivity class, which is a base class for activities
that use the support library action bar features.*/
public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    /**This calls the onCreate method of the parent class,
    AppCompatActivity, to perform any initialization needed for this activity.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         /**Set secure flag to prevent screenshots
         This is done using the getWindow() method to get the window
         associated with the activity, and then calling the setFlags() method on that window
         to set the secure flag.*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,

                        WindowManager.LayoutParams.FLAG_SECURE);
/**The setContentView() method is then called to set the layout for the activity:
This loads the activity_splash layout file and sets it as the content view for the activity.*/
        setContentView(R.layout.activity_splash);

        /**The next block of code sets up a Handler to display the splash screen for a set amount of time
        The postDelayed() method of the Handler class is used to execute a piece of code after a specified delay.
         In this case, the code block inside the run() method will be executed
         after a delay of SPLASH_DISPLAY_LENGTH milliseconds.*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /** Create a shared preference object to check if the EULA has been approved
                This retrieves the SharedPreferences object with the name "MyPref",
                 which was created earlier to store app preferences,
                 and sets the mode to MODE_PRIVATE, which means that only this app can access the preferences.*/
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

                // Get the saved admin username and password from shared preferences
                String username = sharedPreferences.getString("username", "");
                String password = sharedPreferences.getString("password", "");

                // Check if the EULA has been approved
                String EULAcheck = sharedPreferences.getString("EULAApproved", "");

                // Check if the EULA has been approved
                if (EULAcheck.equals("True")) {
                    // If the EULA has been approved, check if the admin username and password have been saved
                    if (username.equals("") || password.equals("")) {
                        // If admin username and password have not been saved, open the Admin Credentials Setup page
                        Intent adminIntent = new Intent(SplashActivity.this, Admin_Credentials_Setup.class);
                        startActivity(adminIntent);
                        finish();
                    } else {
                        // If admin username and password have been saved, open the OnBase Login Screen
                        Intent onBaseIntent = new Intent(SplashActivity.this, OnBase_Login_Screen.class);
                        startActivity(onBaseIntent);
                        finish();
                    }
                } else {
                    // If the EULA has not been approved, open the Terms and Conditions page
                    Intent termsIntent = new Intent(SplashActivity.this, TermsAndConditions.class);
                    startActivity(termsIntent);
                    finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}



