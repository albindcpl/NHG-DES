package com.dcpl.printfromonbase;

/**This activity is popup screen that comes after setting admin credentials and when the user wishes to set configuration with that the backend application
 *This statement imports the Context class from the android.content package, which is part of the Android SDK.
 *The Context class represents the current state of an Android application and provides access to various
 *application-specific information, such as resources, services, and preferences.
 *By importing this class, we can create an instance of it and use its methods to interact with the
 *Android system and perform various tasks, such as launching activities and accessing system services.*/
import android.content.Context;

/** This statement imports the DialogInterface class from the android.content package, which is part of the Android SDK.
*The DialogInterface class provides an interface for implementing and handling dialog boxes in Android applications.
* By importing this class, we can create and show dialog boxes and handle user interactions with them.*/

import android.content.DialogInterface;

 /**This statement imports the Intent class from the android.content package, which is part of the Android SDK.
 The Intent class represents an operation to be performed in Android, such as launching an activity, starting a
 service, or delivering a broadcast message.
 By importing this class, we can create and use intents to communicate between components in an Android application
 and to perform various actions, such as opening a web page or sending an email.*/
import android.content.Intent;

/** This statement imports the Bundle class from the android.os package, which is part of the Android SDK.
 The Bundle class is used to store and transfer data between components in an Android application, such
 as between activities or between a service and an activity.
 By importing this class, we can create instances of the Bundle class and use its methods to add, retrieve,
 and manipulate data in a key-value format.*/
import android.os.Bundle;

/** This statement imports the WindowManager class from the android.view package, which is part of the Android SDK.
 The WindowManager class provides access to the window manager service in Android, which manages the top-level
 windows in an application and their properties, such as size, position, and visibility.
 By importing this class, we can access and manipulate the properties of the windows in an Android application,
 such as the full-screen mode, screen orientation, and system UI visibility.*/
import android.view.WindowManager;

/**line 40,41 includes .R which is a deprecated feature in android's latest library and hence has been commented out.*/
//import com.dcpl.printfromonbase.R;
//import androidx.appcompat.R;


/** This statement imports the AlertDialog class from the androidx.appcompat.app package, which is part of the AndroidX library.
 The AlertDialog class is a subclass of the Dialog class and provides a dialog box that can show a message, accept user input,
 and perform various actions, such as opening a web page or sending an email.
 By importing this class, we can create and show alert dialog boxes in an Android application and handle user interactions with them.*/
import androidx.appcompat.app.AlertDialog;

/** This statement imports the AppCompatActivity class from the androidx.appcompat.app package, which is part of the AndroidX library.
 The AppCompatActivity class is a subclass of the FragmentActivity class and provides a base class for implementing activities in an
 Android application that support the Material Design theme and functionality.
 By importing this class, we can create activity classes that support modern features such as the app bar, navigation drawer,
 and bottom navigation view, as well as backward compatibility with older Android versions.*/
import androidx.appcompat.app.AppCompatActivity;



/** This class extends the AppCompatActivity class, which provides a base class for implementing activities in an Android
 application that support the Material Design theme and functionality, as well as backward compatibility with older Android versions.
 The class name i is a pop-up dialog box specifically designed for use by an administrator, and it is
 used to confirm or prompt the user to proceed with an action or decision.*/
public class Admin_Proceed_PopUp extends AppCompatActivity {

/** This statement declares a variable named 'builder' of type AlertDialog.Builder, which is a builder class used to
 create an instance of the AlertDialog class.
 By declaring this variable, we can create and configure an alert dialog box that can show a message, accept
 user input, and perform various actions.
*/
    AlertDialog.Builder builder;
    /** This statement creates a new variable named 'context' of type Context and assigns it the value of 'this'.
 The 'this' keyword refers to the current instance of the class, which in this case is likely an activity or a fragment.
 By assigning 'this' to 'context', we are creating a reference to the current context of the activity or fragment, which can be
 used to access resources and services provided by the Android system, such as the layout inflater, the shared preferences, or
 the system clipboard.*/
    final Context context = this;

    /** This method is called when the activity is first created and is responsible for initializing the activity's user
     interface and other components.
 The method overrides the corresponding method of the AppCompatActivity class and is annotated with the @Override
 annotation to indicate that it overrides a superclass method.
 The 'savedInstanceState' parameter is a Bundle object that contains the activity's previously saved state, if any.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the superclass implementation of the method to perform any necessary initialization
        super.onCreate(savedInstanceState);

        /** Set the activity's user interface to the layout defined in the 'activity_admin_login.xml' file using the
         'setContentView' method.
         The 'R.layout.activity_admin_login' argument is a reference to the layout file that defines the
         activity's user interface, and 'R' is a class that contains references to all the resources in the
         application, such as layouts, strings, and images.*/
        setContentView(R.layout.activity_admin_login);


/** This code block uses the getWindow() method to get a reference to the current window and sets the 'FLAG_SECURE'
 flag using the 'setFlags' method.
 The 'FLAG_SECURE' flag is used to prevent the contents of the window from being captured or recorded by
 screenshots, screen recordings, or other similar means.*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);


// This line of code creates a new instance of the AlertDialog.Builder class with the current activity as its context.
        builder = new AlertDialog.Builder(this);

        // This line sets the cancelable property of the AlertDialog to false, which means that the dialog box cannot be
        // dismissed by pressing the back button or tapping outside the dialog area.
        builder.setCancelable(false);

        // This line sets the message of the AlertDialog to the string resource 'R.string.Administrator',
        // which is defined in the application's resources.
        builder.setMessage(R.string.Administrator);

        // This line sets a positive button on the AlertDialog with the label "Proceed", which, when clicked, triggers
        // an event handled by a DialogInterface.OnClickListener object that we create anonymously using an inline implementation.
        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {

          /**   This is an inline implementation of the DialogInterface.OnClickListener interface, which is used to handle
             the click event of the positive button added to the AlertDialog.
          When the positive button is clicked, this method is called and it creates a new Intent object to launch
          the Admin_Login_Screen activity, which is set as the target of the Intent.
            The startActivity() method is then called to start the Admin_Login_Screen activity, and the current
           activity is finished using the finish() method.*/

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent mainIntent = new Intent(Admin_Proceed_PopUp.this, Admin_Login_Screen.class);
                startActivity(mainIntent);
                finish();


            }
        });

        // This line sets a negative button on the AlertDialog with the label "Cancel", which, when clicked, triggers an
        // event handled by a DialogInterface.OnClickListener object that we create anonymously using an inline implementation.
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {


        /**     This is an inline implementation of the DialogInterface.OnClickListener interface, which is used to handle
             the click event of the negative button added to the AlertDialog.
 When the negative button is clicked, this method is called and it creates a new Intent object to launch the
 OnBase_Login_Screen activity, which is set as the target of the Intent.
 The startActivity() method is then called to start the OnBase_Login_Screen activity, and the current activity is
 finished using the finish() method.
 Finally, the show() method is called on the builder object to display the AlertDialog on the screen.*/

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent mainIntent = new Intent(Admin_Proceed_PopUp.this, OnBase_Login_Screen.class);
                startActivity(mainIntent);
                finish();
            }
        });
        builder.show();
    }
}


