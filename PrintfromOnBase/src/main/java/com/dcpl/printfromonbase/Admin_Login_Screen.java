/**This java activity enables the user to log into the admin side of the application
 * "Admin_Login_Screen" that represents a login screen for an administrative user. The activity contains
 * UI elements such as EditTexts for the username and password, a button to initiate the login process,
 * and an ImageView that can be clicked to autofill the username and password fields with previously saved values.
 *When the user clicks the login button, the activity retrieves the values entered in the EditText fields and
 * compares them with the stored username and password. If the values match, the activity launches a new activity
 * called "AdminSettingFragmentActivity" and finishes itself. If the values do not match, a message is displayed
 * indicating that the login information is incorrect. This activity provides a simple user interface for an
 * administrative user to log in and access specific functionality within the application.
 */


package com.dcpl.printfromonbase;

//Refer to Splash activity and Admin_Credentials_Setup for explanation about each imports
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Admin_Login_Screen extends AppCompatActivity {

	/**
	 * These lines declare private instance variables of different types that will be used in the class.
	 * A Button variable named "button", two EditText variables named "editTextadminpassword" and "editTextUsername",
	 * three String variables named "etPassword", "etUsername", and "TAG", and an ImageView variable named "ivKey" are declared.
	 */

	private Button button;
	private EditText editTextadminpassword;
	private EditText editTextUsername;
	private String etPassword = "";
	private String etUsername = "";
	private String TAG = "";
	private ImageView ivKey;


//This line is an annotation that indicates that the method that follows is being overridden.
// The method "onCreate" is called when the activity is starting. The call to "super.onCreate(savedInstanceState)"
// is made to perform any necessary initialization of the superclass before executing the code in the overridden method.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
				WindowManager.LayoutParams.FLAG_SECURE);

// Set the layout for the activity
		setContentView(R.layout.activity_admin);

// Get references to the UI elements
		editTextUsername = findViewById(R.id.editTextUsername);
		editTextadminpassword = findViewById(R.id.editTextPassword);
		button = findViewById(R.id.btnLogin);

// Get the saved username and password from the shared preferences
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
		String username = pref.getString("username", "");
		String password = pref.getString("password", "");

// Set a click listener for the login button
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Get the username and password entered by the user
				etUsername = editTextUsername.getText().toString();
				etPassword = editTextadminpassword.getText().toString();

				// Check if username field is empty
				if (etUsername.trim().isEmpty()) {
					etUsername = username;
					Toast.makeText(getApplicationContext(), "Enter your UserName", Toast.LENGTH_SHORT).show();
				}
				// Check if password field is empty
				else if (etPassword.isEmpty()) {
					etPassword = password;
					Toast.makeText(getApplicationContext(), "Enter your Password", Toast.LENGTH_SHORT).show();
				}
				// Check if the entered username and password match the saved username and password
				else if (etUsername.equals(username) && etPassword.equals(password)) {
					Intent intent = new Intent(getApplicationContext(), AdminSettingFragmentActivity.class);
					startActivity(intent);
					finish();
				}
				// The entered username and password do not match the saved username and password
				else {
					Toast.makeText(getApplicationContext(), "Incorrect UserName and Password", Toast.LENGTH_SHORT).show();
				}
			}
		});

// Set a click listener for the key icon
		ivKey = findViewById(R.id.ivKeyy);
		ivKey.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Set the username and password fields to the saved username and password
				editTextUsername.setText(username);
				editTextadminpassword.setText(password);
			}
		});
	}}