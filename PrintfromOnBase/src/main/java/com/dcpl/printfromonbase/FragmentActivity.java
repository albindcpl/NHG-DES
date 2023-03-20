package com.dcpl.printfromonbase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dcpl.printfromonbase.fragments.PrintConfigureFragment;
import com.dcpl.printfromonbase.task.InitializationTask;
import com.dcpl.printfromonbase.task.LoadCapabilitiesTask;
import com.dcpl.printfromonbase.task.WebService_GetSA_CloseSession;

import java.lang.ref.WeakReference;


public class FragmentActivity extends AppCompatActivity {

    public static String username,password,domain;
    public  static String sessionID;
    private TextView textView;
    private ImageView imageView;
    private ImageView imageview;
    public static boolean ad;
    public static PrintConfigureFragment Fragment = null;
    private InitializationTask mInitializationTask;
    public  static ProgressBar pbDocuments;
    public static String getClosesession= "SA_Closesession";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        SharedPreferences prefs = getSharedPreferences("MYPref", MODE_PRIVATE);
        ad = prefs.getBoolean("AD", false);
//        if(ad==true){
//            domain = getIntent().getExtras().getString("domain");
//            username = getIntent().getExtras().getString("username");
//            password = getIntent().getExtras().getString("password");
            sessionID= OnBase_Login_Screen.loginResult;
//        }
//        else {
////            username = getIntent().getExtras().getString("username");
////            password = getIntent().getExtras().getString("password");
//            sessionID= OnBase_Login_Screen.loginResult;
//            Log.d("sessionID",sessionID);


        setContentView(R.layout.activity_fragment);
        textView = findViewById(R.id.tvtext);

        if(OnBase_Login_Screen.adauth==true){
            textView.setText(OnBase_Login_Screen.stringusername);
            textView.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.greetingman, 0, 0, 0);
        }
        else if(OnBase_Login_Screen.adauth==false) {

            textView.setText(OnBase_Login_Screen.stringusername);
            textView.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.greetingman, 0, 0, 0);
        }

        pbDocuments= findViewById(R.id.ProgressBarDocument);

        imageView = findViewById(R.id.ivLogout);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_DocType.doctypegrouplist.clear();
                Fragment_DocType.alldoctypelist.clear();
                new CloseWS(FragmentActivity.this).execute();
//                Intent intent = new Intent(getApplicationContext(), OnBase_Login_Screen.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//               finish();
            }
        });
        imageview = findViewById(R.id.ivMenuBarr);
        imageview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Information_Screen.class);
                startActivity(intent);
            }
        });

        if(savedInstanceState == null){
            Fragment_Documents fragment_documents = new Fragment_Documents();
            getFragmentManager().beginTransaction().add(R.id.fragment2, fragment_documents).commit();
        }

//        mInitializationTask = new InitializationTask(context);
//        mInitializationTask.execute();
//        new LoadCapabilitiesTask(context,PrintPropertyScreen.mFragment).execute();


    }
    class CloseWS extends AsyncTask<Void, Void, WebService_GetSA_CloseSession.WSResult> {

        public  WeakReference<FragmentActivity> mContextRef;

        CloseWS( FragmentActivity context) {
            this.mContextRef = new WeakReference<>(context);
        }


        @Override
        protected WebService_GetSA_CloseSession.WSResult doInBackground(Void... voids) {
            WebService_GetSA_CloseSession WSClose = new WebService_GetSA_CloseSession();
            WebService_GetSA_CloseSession.WSResult wsResult = WSClose.close(getClosesession, OnBase_Login_Screen.soapAddress, sessionID);
            return wsResult;
        }

        @Override
        protected void onPostExecute(WebService_GetSA_CloseSession.WSResult wsResult) {

            String close = wsResult.getlogOffresult();
            if (!close.equals("")) {
                finish();

            }
        }
    }}
