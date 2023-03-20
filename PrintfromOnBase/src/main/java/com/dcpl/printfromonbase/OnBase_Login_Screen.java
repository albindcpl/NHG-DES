package com.dcpl.printfromonbase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.preference.PreferenceManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dcpl.printfromonbase.task.WebService_GetAD_Login;
import com.dcpl.printfromonbase.task.WebService_GetSA_Login;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OnBase_Login_Screen extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "login";
    public static final String DOMAINLIST = "DomainList";
    private Button btn;
    private android.widget.TextView TextView;
    private EditText username;
    private EditText password;
    static String stringusername = "";
    static String stringpassword = "";
    static String stringsessionID = "";
    static String ad_username = "";
    static String ad_password = "";
    static String ad_domain = "";
    String loginsoapmethod = "SA_Login";
    String loginadmethod = "AD_Login";
    private ImageView imageView;
    public static String soapAddress;
    // public static String soapAddress = "http://192.168.43.22/OBWebService/OnBaseWebService.asmx?WSDL/";
    public static String loginResult;
    private Activity activity;
    private ImageView imageview;
    public static Spinner spDomain;
    List<String> domainlist = new ArrayList();
    String spinnerDomain;
    TextView tvDomain;
    View view;

    String url = "http://localhost:8080/server/api/authn";
    public static Boolean adauth;
    private ImageView ivKey;
    private RequestQueue requestQueue;

    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";

    private static OnBase_Login_Screen _instance;
    private RequestQueue _requestQueue;
    private SharedPreferences _preferences;
    private String auth = "";
    private String API_KEY = "";
    private Context context;

    public static OnBase_Login_Screen get() {
        return _instance;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_login);



        username = findViewById(R.id.edUserName);
        username.clearFocus();
        password = findViewById(R.id.edPassword);
        tvDomain = findViewById(R.id.tvDomain);
        view = findViewById(R.id.Viewview);


        SharedPreferences prefs = getSharedPreferences("MYPref", MODE_PRIVATE);

        adauth = prefs.getBoolean("AD", false);

        if (adauth == true) {


            username.setHint("AD Username");
            username.setHintTextColor(R.color.skyblue);


            password.setHint("AD Password");
            password.setHintTextColor(R.color.skyblue);

            spDomain = findViewById(R.id.spSpinnerDomain);
            SharedPreferences pref = getSharedPreferences("MyPref", MODE_PRIVATE);
            if (pref.contains(DOMAINLIST)) {
                String jsondomain = pref.getString(DOMAINLIST, null);
                Gson gson = new Gson();
                String[] domains = gson.fromJson(jsondomain,
                        String[].class);

                domainlist = Arrays.asList(domains);
                domainlist = new ArrayList<String>(domainlist);
            }

            spinnerDomain = String.valueOf(spDomain);
            //          ArrayAdapter<String> adapter = new ArrayAdapter<String>(OnBase_Login_Screen.this, R.layout.ad_loginspinneritem, domainlist);
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                    R.layout.ad_loginspinneritem, domainlist);
            arrayAdapter.setDropDownViewResource(R.layout.ad_loginspinneritem);
            spDomain.setAdapter(arrayAdapter);
            spDomain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    //String myStr = spDomain.getSelectedItem().toString();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            if (domainlist.size() == 1) {
                spDomain.setVisibility(View.GONE);
                tvDomain.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
            } else {
                domainlist.add(0, "-Select Domain-");
                spDomain.setVisibility(View.VISIBLE);
                tvDomain.setVisibility(View.VISIBLE);
                view.setVisibility(View.VISIBLE);

            }
        } else if (adauth == false) {
            spDomain = findViewById(R.id.spSpinnerDomain);
            spDomain.setEnabled(false);
            tvDomain.setVisibility(View.GONE);

            username.setHint("OB Username");
            username.setHintTextColor(R.color.skyblue);


            password.setHint("OB Password");
            password.setHintTextColor(R.color.skyblue);


        }


        password.setHintTextColor(R.color.skyblue);
        btn = findViewById(R.id.btnogin);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//              getLogin();
//              loginUser();
//            }
//                if
//                (domain.getText().toString().trim().isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "Enter your Domain", Toast.LENGTH_SHORT).show();
//                   domain.requestFocus();
//                }
//             else

                if (adauth == true) {
                    if (spDomain.getSelectedItem().toString().matches("-Select Domain-")) {
                        Toast.makeText(getApplicationContext(), "Select a Domain !!!!", Toast.LENGTH_LONG).show();
                    } else if
                    (username.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Enter your UserName", Toast.LENGTH_SHORT).show();
                        username.requestFocus();
                        // hideKeybaord(v);
                    } else if (password.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Enter your Password", Toast.LENGTH_SHORT).show();
                        password.requestFocus();
                        //  hideKeybaord(v);
                    } else {
                        btn.setEnabled(false);
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                        String webserviceip = pref.getString("webserviceip", "");
                        String FullPath = pref.getString("FullPATH", "");
                        String Protocol = pref.getString("protocol", "");
                        soapAddress = Protocol + webserviceip + FullPath;
                        if (soapAddress.equals("")) {
                            Toast.makeText(getApplicationContext(), "Invalid Server Path!!! Please Contact to Administrator.", Toast.LENGTH_LONG).show();
                            btn.setEnabled(true);

                        } else {
                            ad_username = username.getText().toString();
                            ad_password = password.getText().toString();
                            ad_domain = spDomain.getSelectedItem().toString();
                            new LoginWebService(OnBase_Login_Screen.this).execute();
                        }
                    }
                } else {

                    if

                    (username.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Enter your UserName", Toast.LENGTH_SHORT).show();
                        username.requestFocus();
                        // hideKeybaord(v);
                    } else if (password.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Enter your Password", Toast.LENGTH_SHORT).show();
                        password.requestFocus();
                        //  hideKeybaord(v);
                    } else {
                        btn.setEnabled(false);
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                        String webserviceip = pref.getString("webserviceip", "");
                        String FullPath = pref.getString("FullPATH", "");
                        String Protocol = pref.getString("protocol", "");
                        soapAddress = Protocol + webserviceip + FullPath;
                        if (soapAddress.equals("")) {
                            Toast.makeText(getApplicationContext(), "Invalid Server Path!!! Please Contact to Administrator.", Toast.LENGTH_SHORT).show();
                            btn.setEnabled(true);

                        } else {
                            stringusername = username.getText().toString();
                            stringpassword = password.getText().toString();
                            // stringdomain = domain.getText().toString();
                            new LoginWS(OnBase_Login_Screen.this).execute();
                        }
                    }
                }
            }
        });


        TextView = findViewById(R.id.tvadmin);
        TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Admin_Proceed_PopUp.class);
                startActivity(intent);
                finish();


            }
        });

        ivKey = findViewById(R.id.ivKeys);
        ivKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setText("manager");
                password.setText("password");
            }
        });

//        ivKey = findViewById(R.id.ivKeys);
//        ivKey.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                username.setText("Reliance");
//                password.setText("password");
//            }
//        });

        imageview = findViewById(R.id.ivquestionMark);
        imageview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), UserGuideActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imageView = findViewById(R.id.ivMenuBar);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Information_Screen.class);
                startActivity(intent);
                finish();
            }
        });
    }
//        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                hideKeybaord(view);
//            }
//        });
//
//
//    }
//    private void hideKeybaord(View v) {
//        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
//    }


    @Override
    public void onClick(View v) {
    }

    private class LoginWS extends AsyncTask<Void, Void, WebService_GetSA_Login.WSResult> {

        private final WeakReference<OnBase_Login_Screen> mContextRef;

        LoginWS(final OnBase_Login_Screen context) {
            this.mContextRef = new WeakReference<>(context);
        }

        @Override
        protected WebService_GetSA_Login.WSResult doInBackground(Void... voids) {
            WebService_GetSA_Login WSlogin = new WebService_GetSA_Login();
            System.out.println(loginsoapmethod + "," + soapAddress);
            WebService_GetSA_Login.WSResult wsResult = WSlogin.login(OnBase_Login_Screen.this, loginsoapmethod, soapAddress, stringusername, stringpassword);
            loginResult = wsResult.getdocument();
            // Log.d(TAG, "LoginResult: " + loginResult);
            return wsResult;
        }

        @Override
        protected void onPostExecute(WebService_GetSA_Login.WSResult wsResult) {
            super.onPostExecute(wsResult);
            btn.setEnabled(true);
            String login = wsResult.getdocument();
            if (!login.equals("")) {
                Intent intent = new Intent(OnBase_Login_Screen.this, FragmentActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("username", stringusername);
//                bundle.putString("password", stringpassword);
//                intent.putExtras(bundle);
                startActivity(intent);
                finish();

            } else {

                Toast toast1 = Toast.makeText(mContextRef.get(), "\n" + wsResult.getMessage(), Toast.LENGTH_LONG);
                toast1.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                toast1.show();
            }
        }
    }

    private class LoginWebService extends AsyncTask<Void, Void, WebService_GetAD_Login.WSResult> {

        private final WeakReference<OnBase_Login_Screen> mContextRef;

        LoginWebService(final OnBase_Login_Screen context) {
            this.mContextRef = new WeakReference<>(context);
        }

        @Override
        protected WebService_GetAD_Login.WSResult doInBackground(Void... voids) {
            WebService_GetAD_Login WSlogin = new WebService_GetAD_Login();
            System.out.println(loginadmethod + "," + soapAddress);
//            Log.d("Domain: ",ad_domain);
//            Log.d("UN: ",ad_username);
//            Log.d("PWD: ",ad_password);
            WebService_GetAD_Login.WSResult wsResult = WSlogin.login(OnBase_Login_Screen.this, loginadmethod, soapAddress, ad_username, ad_password, ad_domain);
            loginResult = wsResult.getdocument();
            //  Log.d(TAG, "LoginResult: " + loginResult);
            return wsResult;
        }

        @Override
        protected void onPostExecute(WebService_GetAD_Login.WSResult wsResult) {
            super.onPostExecute(wsResult);
            btn.setEnabled(true);
            String login = wsResult.getdocument();
            if (!login.equals("")) {
                Intent intent = new Intent(OnBase_Login_Screen.this, FragmentActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("username", ad_username);
//                bundle.putString("password",ad_password);
//                bundle.putString("domain", ad_domain);
//                intent.putExtras(bundle);
                startActivity(intent);
                finish();

            } else {

                Toast toast1 = Toast.makeText(mContextRef.get(), "\n" + wsResult.getMessage(), Toast.LENGTH_LONG);
                toast1.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                toast1.show();
            }
        }
    }
//    public RequestQueue getRequestQueue() {
//        return _requestQueue;
//    }


    private void getLogin() {
      String url = "http://192.168.1.9:8080/server/api/authn";

        RequestQueue queue = Volley.newRequestQueue(this);

        Log.i("INSIDE LOG IN",url);
        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("response",response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error",error.getMessage());
                    }
                })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getAuthHeader(context);
            }

            private Map<String, String> getAuthHeader(Context context) {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Cookie", auth);
                return headerMap;
            }


            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                // since we don't know which of the two underlying network vehicles
                // will Volley use, we have to handle and store session cookies manually
          //      Log.i("response",response.headers.toString());
                Map<String, String> responseHeaders = response.headers;
              //  HashMap<String, String> studentPerformanceMap = new HashMap<String, String>();

                responseHeaders.put("","");
                //Getting Set of keys

                Set<String> keySet = responseHeaders.keySet();

                //Creating an ArrayList of keys

                ArrayList<String> listOfKeys = new ArrayList<String>(keySet);

             //   System.out.println("ArrayList Of Keys :");

                for (String key : listOfKeys)
                {
                 //   System.out.println(key);
                }

            //    System.out.println("--------------------------");

                //Getting Collection of values

                Collection<String> values = responseHeaders.values();

                //Creating an ArrayList of values

                ArrayList<String> listOfValues = new ArrayList<String>(values);

               // System.out.println("ArrayList Of Values :");

                for (String value : listOfValues)
                {
                 //   System.out.println(value);
                }

              //  System.out.println("--------------------------");

                //Getting the Set of entries

                Set<Map.Entry<String, String>> entrySet = responseHeaders.entrySet();

                //Creating an ArrayList Of Entry objects

                ArrayList<Map.Entry<String, String>> listOfEntry = new ArrayList<Map.Entry<String,String>>(entrySet);

//                System.out.println("ArrayList of Key-Values :");



                for (Map.Entry<String, String> entry : listOfEntry)
                {
                 //   System.out.println(listOfEntry.get(Integer.parseInt("5")));

                  // System.out.println(entry.getKey()+" : "+entry.getValue());
                }

                System.out.println(listOfEntry.get(Integer.parseInt("6")));

       //         String rawCookies = responseHeaders.get("Cookie");
              //  Log.i("cookies",rawCookies);
                return super.parseNetworkResponse(response);
            }

        };

        queue.add(req);
        Log.i("req ",req.toString());

    }

    public void loginUser() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.9:8080/server/api/authn/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    //    getJsonData();
                        Log.d("Response", response);
                        Toast.makeText(OnBase_Login_Screen.this, response, Toast.LENGTH_LONG).show();
                    }

                    private void getJsonData() {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("username","test@edu.com");
                params.put("password","admin");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final HashMap<String, String> headers = new HashMap<>();
                headers.put("X-XSRF-TOKEN", "6");
                return headers;
            }
        };
        requestQueue.add(stringRequest);
        Log.i("reqQueue ",requestQueue.toString());
    }
}

