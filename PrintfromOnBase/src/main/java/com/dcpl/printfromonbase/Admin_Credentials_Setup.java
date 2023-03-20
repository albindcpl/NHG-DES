/**
 * This is an Android activity class that sets up the admin credentials for an app. The class extends the AppCompatActivity class,
 * which provides a base activity for using the appcompat library, a set of support libraries that help ensure that the app has a
 * consistent look and feel across different versions of Android.
 *The class defines three EditText views for the username and password, as well as a button to save the entered credentials.
 * It also defines two constants for the keys to be used for storing the username and password in the shared preferences.
 * In the onCreate() method, the class sets up the layout for the activity, initializes the views and the button, and sets
 * an onClickListener for the button. The onClickListener checks if the username and password fields are empty, and if the password
 * is at least 8 characters long and contains at least one uppercase letter, one lowercase letter, one digit, and one special character.
 * If all the checks pass, the entered credentials are saved in the shared preferences using the saveData() method.
 *
 * The loadData() method retrieves the saved credentials from the shared preferences and the updateViews() method updates the
 * EditText views with the retrieved credentials. Finally, the activity finishes and launches an intent for the Admin_Proceed_PopUp class.
 */

/**
 * This line of code is defining the package name for the Java class file. In Java, packages are used to group related classes,
 * interfaces, and sub-packages together, and to prevent naming conflicts between different classes. The package name is typically
 * based on the company or organization name that owns the project, followed by the project name, and then any sub-packages that
 * further categorize the classes.
 */
package com.dcpl.printfromonbase;


/**AppCompatActivity is a subclass of the FragmentActivity class and provides a base activity for
 * using the appcompat library. The appcompat library is a set of support libraries that help ensure
 * that your app has a consistent look and feel across different versions of Android.
 */
import androidx.appcompat.app.AppCompatActivity;

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
 */
import android.content.SharedPreferences;

/**The line of code "import android.os.Handler;" imports the Handler class from the android.os package into the current
 * Java file. This allows the Java code to use the Handler class to schedule and handle messages and runnables on a thread's
 * message queue.
 *The Handler class provides a way to post messages and runnables to the main thread's message queue, allowing background
 *  threads to communicate with the UI thread. By using a Handler, a background thread can request an update to the UI or
 *  schedule a task to run on the UI thread at a later time.
 *  it allows developers to create responsive and efficient apps that can perform time-consuming tasks without blocking
 *  the UI thread.
 */
import android.os.Bundle;

/**
 * This line of code is importing the View class from the Android framework, which is used for creating user interface elements in Android applications.
 *The View class is a fundamental building block of the Android UI, representing a rectangular area that can be drawn and interacted with on the screen.
 * Examples of View subclasses include Button, TextView, EditText, and ImageView, among others.
 *By importing the View class, the developer can use its various methods and properties to customize the appearance and behavior of UI elements in their Android application.
 */
import android.view.View;

/** This imports the WindowManager class from the Android framework. The WindowManager is responsible for managing the
 * windows that are displayed on the device screen. It provides access to a variety of window-related functions, such
 * as adjusting the size and position of windows, adding or removing views, and handling events related to windows.
 * By importing this class, we can use its functionality to manipulate the window of the activity in which our code is running.
 *
 */
import android.view.WindowManager;

/**
 * This line of code is importing the Button class from the Android framework, which is a subclass of the TextView class and
 * represents a clickable button that can perform an action when pressed. By importing the Button class, the developer can use
 * it to create a button element in the user interface of their Android application. They can then use various methods and properties
 * of the Button class to customize its appearance and behavior, such as setting the text displayed on the button, specifying an icon or
 * image, and adding an OnClickListener to define what happens when the button is pressed.
 */
import android.widget.Button;

/**
 * This line of code is importing the EditText class from the Android framework, which is a subclass of the TextView class and provides a user
 * interface element for users to enter text input. By importing the EditText class, the developer can use it to create a text input field in the user
 * interface of their Android application. They can then use various methods and properties of the EditText class to customize its appearance and behavior,
 * such as setting the default text value, defining input type, adding a hint or placeholder text, and specifying an OnEditorActionListener to handle input events.
 */
import android.widget.EditText;

/**
 * This line of code is importing the Toast class from the Android framework, which is a mechanism for displaying short messages or notifications to the user on the screen.
 *By importing the Toast class, the developer can use its static methods to create and display a simple message to the user, typically in the form of a small pop-up box
 * that disappears after a few seconds. The Toast class provides methods to set the duration, position, and content of the message, such as a string of text or a custom layout.
 */
import android.widget.Toast;


/**
 * This code snippet is defining a new Java class named Admin_Credentials_Setup that extends the AppCompatActivity class from the Android framework.
 *The AppCompatActivity class is a base class for activities in Android that provides compatibility support for newer Android features on older versions
 * of the platform. By extending this class, the developer can take advantage of various built-in methods and properties that simplify the creation and
 * management of user interface elements and activity lifecycle events.
 */
public class Admin_Credentials_Setup extends AppCompatActivity {


    /**
     * This code snippet is declaring a new variable named username of type EditText. The EditText class is a subclass of TextView in the Android framework,
     * which provides a user interface element for users to enter and edit text input. By declaring a variable of this type, the developer is creating a reference
     * to an instance of the EditText class, which can be used to manipulate or access the properties and methods of that object.
     */
    EditText username;


    /**
     * This code snippet is declaring a new variable named password of type EditText.
     * The EditText class is a subclass of TextView in the Android framework, which provides a user interface element for users to enter and edit text input.
     * By declaring a variable of this type, the developer is creating a reference to an instance of the EditText class, which can be used to manipulate or
     * access the properties and methods of that object.
     */
    EditText password;

    /**
     * This code snippet is declaring a new variable named btnsaved of type Button.
     * The Button class is a subclass of TextView in the Android framework, which provides a user interface element that represents a
     * clickable button in the user interface. By declaring a variable of this type, the developer is creating a reference to an instance
     * of the Button class, which can be used to manipulate or access the properties and methods of that object.
     */
    Button btnsaved;


    /**
     * This code snippet is defining a new constant value named Username of type String, which has been marked as public and static,
     * indicating that it can be accessed and used from other parts of the code. The constant value is initialized with the string "username",
     * which suggests that it will be used to store or reference the user's chosen username or login name. By defining this constant value, the developer
     * can use it to avoid using hard-coded strings in other parts of the code, which can make the code more readable, maintainable, and less error-prone.
     */
    public static final String Username = "username";

    /**
     * This code snippet is defining a new constant value named Password of type String, which has been marked as public and static, indicating
     * that it can be accessed and used from other parts of the code. The constant value is initialized with the string "password", which suggests
     * that it will be used to store or reference the user's chosen password or login password. By defining this constant value, the developer can
     * use it to avoid using hard-coded strings in other parts of the code, which can make the code more readable, maintainable, and less error-prone.
     */
    public static final String Password = "password";

    /**
     * This code snippet is declaring a new private variable named text of type String. By declaring a variable as private, the developer is restricting
     * access to that variable to only within the class where it is declared. This means that other classes or objects outside of this class will not be
     * able to directly access or modify the value of this variable. The String data type in Java represents a sequence of characters or text. By declaring
     * text as a String, the developer is creating a reference to an instance of the String class, which can be used to manipulate or access the properties
     * and methods of that object.
     */
    private String text;
    private String text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/**Set secure flag to prevent screenshots
 This is done using the getWindow() method to get the window
 associated with the activity, and then calling the setFlags() method on that window
 to set the secure flag.*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,

                /**the FLAG_SECURE flag is being set to ensure that any sensitive information displayed in the activity's window
                 *  is not captured or recorded, for example, when the user is entering their login credentials or viewing private data.
                 */
                WindowManager.LayoutParams.FLAG_SECURE);

        // Set the content view of the activity to the "activity__admin" layout file
        setContentView(R.layout.activity__admin);

// Find the EditText view with ID "edUserNames" in the layout, and assign it to the "username" variable
        username = findViewById(R.id.edUserNames);

// Find the EditText view with ID "edPasswords" in the layout, and assign it to the "password" variable
        password = findViewById(R.id.edPasswords);

// Find the Button view with ID "btnSaved" in the layout, and assign it to the "btnsaved" variable
        btnsaved = findViewById(R.id.btnSaved);

//     btnsaved.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (username.getText().toString().trim().isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "Enter your UserName", Toast.LENGTH_SHORT).show();
//                    username.requestFocus();
//                }
//                else if ((password.getText().toString().isEmpty())) {
//                    Toast.makeText(getApplicationContext(), "Enter your Password", Toast.LENGTH_SHORT).show();
//                    password.requestFocus();
//                }
//                else if ((password.getText().toString().trim().length() < 8)){
//                    Toast.makeText(getApplicationContext(), "Password must contain 8 letters", Toast.LENGTH_SHORT).show();
//                    password.requestFocus();
//                }
//                else {
//
//                    saveData();
//                    Intent intent = new Intent(getApplicationContext(), Admin_Proceed_PopUp.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        });
//       Updated code on 28-02-2023(to add checks to admin password for added security) (line 62-112)

        btnsaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordStr = password.getText().toString();

                // Check if the username field is empty
                if (username.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter your UserName", Toast.LENGTH_SHORT).show();
                    username.requestFocus();
                }
                // Check if the password field is empty
                else if (passwordStr.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter your Password", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }
                // Check if the password has less than 8 characters
                else if (passwordStr.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Password must contain at least 8 characters", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }
                else {
                    // Check if the password contains at least one lowercase, uppercase letter, one digit, and one special character
                    boolean hasLowerCase = false;
                    boolean hasUpperCase = false;
                    boolean hasDigit = false;
                    boolean hasSpecialChar = false;
                    String specialChars = "!@#$%^&*()_-+={}[]\\|:;\"',.<>?/~`";

                    for (char c : passwordStr.toCharArray()) {
                        if (Character.isLowerCase(c)) {
                            hasLowerCase = true;
                        } else if (Character.isUpperCase(c)) {
                            hasUpperCase = true;
                        } else if (Character.isDigit(c)) {
                            hasDigit = true;
                        } else if (specialChars.indexOf(c) != -1) {
                            hasSpecialChar = true;
                        }
                    }

                    // Check if the password contains both uppercase and lowercase letters
                    // Use logical OR operator to combine two boolean expressions
                    // Use logical NOT operator to negate the boolean value of the expression
                    if (!hasLowerCase || !hasUpperCase) {
                        Toast.makeText(getApplicationContext(), "Password must contain both upper and lower case letters", Toast.LENGTH_SHORT).show();
                        password.requestFocus();
                    }
                    // Check if the password contains at least one digit
                    else if (!hasDigit) {
                        Toast.makeText(getApplicationContext(), "Password must contain at least one digit", Toast.LENGTH_SHORT).show();
                        password.requestFocus();
                    }
                    // Check if the password contains at least one special character
                    else if (!hasSpecialChar) {
                        Toast.makeText(getApplicationContext(), "Password must contain at least one special character", Toast.LENGTH_SHORT).show();
                        password.requestFocus();
                    }
                    else {
                        // Save the input data to SharedPreferences
                        saveData();
                        // Start a new activity
                        Intent intent = new Intent(getApplicationContext(), Admin_Proceed_PopUp.class);
                        startActivity(intent);
                        // Finish the current activity
                        finish();
                    }
                }
            }
        });

// Load data from SharedPreferences
        loadData();
// Update the UI views
        updateViews();


    }

    private void saveData() {
        // Get the input text from username and password fields
        String editText = username.getText().toString();
        String editText1 = password.getText().toString();

        // Get the SharedPreferences instance for "MyPref" with private mode
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        // Get the SharedPreferences editor to modify the preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Add the input text values to the editor
        editor.putString(Username, editText);
        editor.putString(Password, editText1);

        // Save the changes to SharedPreferences
        editor.commit();
    }

    private void loadData() {
        // Get the SharedPreferences instance for "MyPref" with private mode
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        // Retrieve the previously saved text values from SharedPreferences
        text = sharedPreferences.getString(Username, "");
        text1 = sharedPreferences.getString(Password, "");
    }

    public void updateViews() {
        // Set the retrieved text values to the username and password fields
        username.setText(text);
        password.setText(text1);
    }
}
