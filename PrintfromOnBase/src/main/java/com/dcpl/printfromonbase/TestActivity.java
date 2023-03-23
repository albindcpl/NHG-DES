package com.dcpl.printfromonbase;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;


/**
 * This Java class extends the AppCompatActivity class. It overrides the onCreate() method, which is called when the activity is created.
 *
 * In the onCreate() method, the super.onCreate(savedInstanceState) is called to ensure that the parent class is also initialized properly.
 *
 * The next line uses the getWindow() method to get the current window and then sets a flag FLAG_SECURE on it.
 * This flag prevents screenshots or screen recording of the activity.
 *
 * Finally, the setContentView(R.layout.activity_test) method is called to set the activity layout from the XML file activity_test.xml.
 *
 * This class may be used as an entry point for the test activity
 */
public class TestActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_test);

    }
}
