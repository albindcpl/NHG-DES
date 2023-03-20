/** this code sets up an activity that displays the terms and conditions of using the app,
 and prompts the user to either accept or decline them.
 Declare UI elements and dialog builder
 Set the UI layout for the activity
 Prevent screenshots
 Build the alert dialog
 Define actions for the "Accept" button
 Define actions for the "Decline" button
 Display the alert dialog
 */

package com.dcpl.printfromonbase;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TermsAndConditions extends AppCompatActivity {


//Declare the necessary UI elements and dialog builder
    Button acceptButton;
    Button declineButton;
    AlertDialog.Builder builder;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set UI layout for the activity
        setContentView(R.layout.activity_terms_and_conditions);

        //prevent screen from taking screenshots
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        //Build alert dialog to display the terms and conditions
        builder = new AlertDialog.Builder(this);
        builder.setTitle(Html.fromHtml("<font color='#0000FF'>Terms and Conditions</font>"));
        builder.setCancelable(false);
        builder.setMessage(R.string.Terms_and_Conditions);

        //Define the action to be taken when the user clicks the "Accept" button
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("EULAApproved", "True");
                editor.commit();
//                editor.apply();
                Intent mainIntent = new Intent(TermsAndConditions.this, Admin_Credentials_Setup.class);
                startActivity(mainIntent);
                finish();

            }
        });

        //Define the action to be taken when the users clicks the "Decline" button
        builder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You must accept the license agreement to use this app.", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
        builder.show();
    }}
