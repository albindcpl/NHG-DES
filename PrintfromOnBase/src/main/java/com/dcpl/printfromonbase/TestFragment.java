package com.dcpl.printfromonbase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.dcpl.printfromonbase.task.WebService_GetSA_CloseSession;
import com.dcpl.printfromonbase.task.WebService_GetSA_Login;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class TestFragment extends Fragment {

    public static EditText edConnectorPath;
    public static EditText edUsername;
    public static EditText edPassword;
    public static String stringusername = "";
    public static String stringpassword = "";
    String loginsoapmethod = "SA_Login";
    public static String loginResult;
    public static Button btnTest;
    Button ButtonBack;
    View V;
    public static String getClosesession = "SA_Closesession";
    public static EditText edFullPath;
    private String login ="";
    public static EditText edProtocolCP;
    String webserviceip;
    String FullPath;
    String Protocol;
    public static String CP;
    //   ="http://192.168.252.141/S2OBServer/s2observer.asmx?wsdl";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        new LoadCapabilitiesTask(context,PrintPropertyScreen.mFragment).execute();
        V = inflater.inflate(R.layout.fragmenttest, container, false);

        edConnectorPath = V.findViewById(R.id.edCP);
        edProtocolCP = V.findViewById(R.id.edProtocolCP);
        edFullPath = V.findViewById(R.id.edFP);
        edUsername = V.findViewById(R.id.edUname);
        edPassword = V.findViewById(R.id.edP);
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        webserviceip = pref.getString("webserviceip", "");
        FullPath = pref.getString("FullPATH","");
        Protocol = pref.getString("protocol","");
        edConnectorPath.setText(webserviceip);
        edProtocolCP.setText(Protocol);
        edFullPath.setText(FullPath);
        // CP = Protocol+webserviceip+FullPath;
        CP =edProtocolCP.getText().toString() +edConnectorPath.getText().toString()+edFullPath.getText().toString();
        btnTest = V.findViewById(R.id.buttonTest);
        ButtonBack = V.findViewById(R.id.buttonBack);
//        param = getActivity().getIntent().getExtras().getString("PARAM");
//        if (param.equals(SettingFragment.HTTP)){
//
//        }
//        else{
//           // edConnectorPath.setText(SettingFragment.text);
//        }
        ButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if   (edUsername.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "Enter your UserName", Toast.LENGTH_SHORT).show();
                    edUsername.requestFocus();
                    // hideKeybaord(v);
                } else if (edPassword.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "Enter your Password", Toast.LENGTH_SHORT).show();
                    edPassword.requestFocus();
                    //  hideKeybaord(v);
                } else {

                    stringusername = edUsername.getText().toString();
                    stringpassword = edPassword.getText().toString();
                    // stringdomain = domain.getText().toString();
                    new TestFragment.LoginWS(TestFragment.this).execute();


                }

            }
        });

        return V;
    }

    private class LoginWS extends AsyncTask<Void, Void, WebService_GetSA_Login.WSResult> {

        private FragmentActivity mContextRef = getActivity();

        LoginWS(final TestFragment mContextRef) {
            this.mContextRef = getActivity();
        }

        @Override
        protected WebService_GetSA_Login.WSResult doInBackground(Void... voids) {
            WebService_GetSA_Login WSlogin = new WebService_GetSA_Login();
            //  System.out.println(loginsoapmethod + "," + soapAddress);
            WebService_GetSA_Login.WSResult wsResult = WSlogin.login(getActivity(), loginsoapmethod, CP, stringusername, stringpassword);
            loginResult = wsResult.getdocument();
            // Log.d(TAG, "LoginResult: " + loginResult);
            return wsResult;
        }

        @Override
        protected void onPostExecute(WebService_GetSA_Login.WSResult wsResult) {
            super.onPostExecute(wsResult);
            //btn.setEnabled(true);
            login = wsResult.getdocument();

            if (!login.equals("")) {
                Toast.makeText(getActivity(), "Connection established & user logged in", Toast.LENGTH_SHORT).show();
                getActivity().setResult(SettingFragment.RequestCOde);
                ConnectorFragment.tvConnector.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white));// use this to set color as background


                ConnectorFragment.tvSettings.setBackgroundResource(android.R.color.darker_gray);
                ConnectorFragment.tvSettings.setVisibility(View.VISIBLE);
                getActivity().finish();
                new TestFragment.CloseWS(TestFragment.this).execute();

            }
            else if (!stringusername.equals("") & !stringpassword.equals("password"))
            {
                Toast toast1 = Toast.makeText(getActivity(), "\n" + wsResult.getMessage(), Toast.LENGTH_LONG);
                toast1.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                toast1.show();
            }
            else if (!login.equals("no protocol:")){
                Toast toast1 = Toast.makeText(getActivity(), "\n" + wsResult.getMessage(), Toast.LENGTH_LONG);
                toast1.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                toast1.show();
                getActivity().onBackPressed();
            }
            else
            {

                Toast toast1 = Toast.makeText(getActivity(), "\n" + wsResult.getMessage(), Toast.LENGTH_LONG);
                toast1.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                toast1.show();
            }
        }
    }

    class CloseWS extends AsyncTask<Void, Void, WebService_GetSA_CloseSession.WSResult> {

        private FragmentActivity mContextRef = getActivity();

        CloseWS(final TestFragment mContextRef) {
            this.mContextRef = getActivity();
        }


        @Override
        protected WebService_GetSA_CloseSession.WSResult doInBackground(Void... voids) {
            WebService_GetSA_CloseSession WSClose = new WebService_GetSA_CloseSession();
            WebService_GetSA_CloseSession.WSResult wsResult = WSClose.close(getClosesession, CP, loginResult);
            return wsResult;
        }

        @Override
        protected void onPostExecute(WebService_GetSA_CloseSession.WSResult wsResult) {

            String close = wsResult.getlogOffresult();
            if (!close.equals("")) {

                //   getActivity().finish();

            }
        }

    }

}
