package com.dcpl.printfromonbase;

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
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Admin_Login_Screen extends AppCompatActivity {

	private Button button;
	private EditText editTextadminpassword;
	private EditText editTextUsername;
	private String etPassword = "";
	private String etUsername = "";
	private String TAG = "";
	private ImageView ivKey;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
				WindowManager.LayoutParams.FLAG_SECURE);

		setContentView(R.layout.activity_admin);

		editTextUsername = findViewById(R.id.editTextUsername);
		editTextadminpassword = findViewById(R.id.editTextPassword);
		button = findViewById(R.id.btnLogin);

		// Get the saved username and password from the encrypted shared preferences
		MasterKey masterKey = null;
		SharedPreferences pref = null;
		try {
			masterKey = new MasterKey.Builder(this)
					.setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
					.build();
			pref = EncryptedSharedPreferences.create(
					this,
					"MyPref",
					masterKey,
					EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
					EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
		}

		String username = pref.getString("username", "");
		String password = pref.getString("password", "");

		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				etUsername = editTextUsername.getText().toString();
				etPassword = editTextadminpassword.getText().toString();

				if (etUsername.trim().isEmpty()) {
					etUsername = username;
					Toast.makeText(getApplicationContext(), "Enter your UserName", Toast.LENGTH_SHORT).show();
				} else if (etPassword.isEmpty()) {
					etPassword = password;
					Toast.makeText(getApplicationContext(), "Enter your Password", Toast.LENGTH_SHORT).show();
				} else if (etUsername.equals(username) && etPassword.equals(password)) {
					Intent intent = new Intent(getApplicationContext(), AdminSettingFragmentActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "Incorrect UserName and Password", Toast.LENGTH_SHORT).show();
				}
			}
		});

		ivKey = findViewById(R.id.ivKeyy);
		ivKey.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				editTextUsername.setText(username);
				editTextadminpassword.setText(password);
			}
		});
	}
}
