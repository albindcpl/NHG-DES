package com.dcpl.printfromonbase;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class OBConnector_Settings_Screen extends AppCompatActivity {

    TextView IpAddress;
    Button btnsave;
    public static final String ipaddress = "webserviceip";
    private String text;
    private ImageView imageView;
    private ImageView imageVieww;
    private Button btnCancel;
    RadioButton authenticationTY;
    RadioGroup radioGroup;
    RadioButton AD;
    String ADDomin;
    RadioButton SA;
    TextView tvDomain;
    EditText editTextdomain;
    ImageView ivPlus;
    View view;
    LinearLayout layone;

    public static Domain_Adapter domainAdapter;
    public static List<String> Domain = new ArrayList<>();
    public static ListView listView;
    private String name = "";
    public static final String DOMAINLIST = "DomainList";

    android.app.AlertDialog.Builder builder;
    final Context context = this;
    Button btnclear;
    private static String clickeditem;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_setting);
        IpAddress = findViewById(R.id.edIpAddress);
        btnsave = findViewById(R.id.btnsave);
        tvDomain = findViewById(R.id.tvvDomain);
        ivPlus = findViewById(R.id.ivPlus);
        listView = findViewById(R.id.lvListView);
        editTextdomain = findViewById(R.id.edittextDomain);
        layone = findViewById(R.id.llLinearLayout);

        imageView = findViewById(R.id.ivBackk);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), OnBase_Login_Screen.class);
                startActivity(intent1);
                finish();
            }
        });

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        AD = findViewById(R.id.radioAD);
        SA = findViewById(R.id.radioSA);
        AD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layone.setVisibility(View.VISIBLE);
            }
        });
        SA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layone.setVisibility(View.GONE);
                SharedPreferences pref = getSharedPreferences("MyPref", MODE_PRIVATE);
                if (pref.contains(DOMAINLIST)) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.remove(DOMAINLIST);
                    editor.commit();

                }
                Domain.clear();
                domainAdapter.notifyDataSetChanged();
            }
        });


        ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextdomain.getText().toString().equals("") || editTextdomain.getText().toString().equals(null)) {

                } else {
                    String editTextDomain = editTextdomain.getText().toString();
                    Domain.add(editTextDomain);
                    domainAdapter.notifyDataSetChanged();
                    editTextdomain.setText("");

                    builder = new android.app.AlertDialog.Builder(OBConnector_Settings_Screen.this);
                    builder.setCancelable(false);
                    builder.setMessage("Do you want to add another Domain");

                    builder.setPositiveButton("Save & Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveDomainData(Domain);
                            saveData();
                            saveRadioButtons();
                            Intent mainIntent = new Intent(OBConnector_Settings_Screen.this, OnBase_Login_Screen.class);
                            startActivity(mainIntent);
//                            saveData();
//                            saveDomainData(Domain);
                            //   finishAffinity();
                            finish();


                        }
                    });
                    builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            saveData();
//                            saveDomainData(Domain);

                        }
                    });

                    builder.show();
                }

            }
        });

        btnCancel = findViewById(R.id.btncancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), OnBase_Login_Screen.class);
                startActivity(intent1);
                finish();
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                authenticationTY = (RadioButton) findViewById(selectedId);
                if (IpAddress.getText().toString().trim().isEmpty()) {
                    IpAddress.setError("Enter your IpAddress");
                    IpAddress.requestFocus();
                }
                if (selectedId != R.id.radioAD && selectedId != R.id.radioSA) {
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Select an Authentication Type !!!", Toast.LENGTH_LONG);
                    toast1.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast1.show();


                } else if (selectedId == R.id.radioAD) {
                    if (Domain.size() > 0) {
                        saveDomainData(Domain);
                        saveData();
                        saveRadioButtons();
                        Intent intent = new Intent(getApplicationContext(), OnBase_Login_Screen.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast toast1 = Toast.makeText(getApplicationContext(), "Please enter atlease one Domain !!!", Toast.LENGTH_LONG);
                        toast1.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast1.show();
                    }


                } else if (selectedId == R.id.radioSA) {
                    saveData();
                    saveRadioButtons();
                    Intent intent = new Intent(getApplicationContext(), OnBase_Login_Screen.class);
                    startActivity(intent);
                    finish();
                }


            }


        });

        loadData();
        loadRadioButtons();
        updateViews();
        // Log.d("","Domain size: " + Domain.size());

        domainAdapter = new Domain_Adapter(getApplicationContext(), Domain);
        listView.setAdapter(domainAdapter);
        imageVieww = findViewById(R.id.ivMenuBarr);
        imageVieww.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Information_Screen.class);
                startActivity(intent);
            }
        });
        IpAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                hideKeybaord(view);
            }
        });

        IpAddress.clearFocus();
    }

    public void saveRadioButtons() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MYPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("AD", AD.isChecked());
        editor.putBoolean("SA", SA.isChecked());
        editor.apply();
    }

    public void loadRadioButtons() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MYPref", MODE_PRIVATE);
        AD.setChecked(sharedPreferences.getBoolean("AD", false));
        SA.setChecked(sharedPreferences.getBoolean("SA", false));
        if (sharedPreferences.getBoolean("AD", false)) {
            layone.setVisibility(View.VISIBLE);
            loadDomainData();


        } else if (sharedPreferences.getBoolean("SA", false)) {
            layone.setVisibility(View.GONE);
        }

    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }

    private void saveData() {
        String editText = IpAddress.getText().toString();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ipaddress, editText);
        editor.commit();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        text = sharedPreferences.getString(ipaddress, "");
    }

    public void updateViews() {
        IpAddress.setText(text);
    }


    private void saveDomainData(List<String> domainlist) {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(domainlist);

        editor.putString(DOMAINLIST, jsonFavorites);
        editor.commit();
    }

    private void loadDomainData() {
        SharedPreferences pref = getSharedPreferences("MyPref", MODE_PRIVATE);
        if (pref.contains(DOMAINLIST)) {
            String jsondomain = pref.getString(DOMAINLIST, null);
            Gson gson = new Gson();
            String[] domains = gson.fromJson(jsondomain,
                    String[].class);
            Domain = Arrays.asList(domains);
            Domain = new ArrayList<String>(Domain);


        }
    }



}


