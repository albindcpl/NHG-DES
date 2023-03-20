package com.dcpl.printfromonbase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class SettingFragment extends Fragment {

    TextView IpAddress;
    Button btnTEST;
    public static final String ipaddress = "webserviceip";
    public static final String PROTOCOL = "protocol";
    public static String fullPath = "FullPATH";
    public static String text;
    public static String protocol;
    public static String paths;


    private Button btnCancel;
    ;

    RadioButton authenticationTY;
    RadioGroup radioGroup;
    RadioButton AD;
    RadioButton SA;
    TextView tvDomain;
    EditText editTextdomain;
    ImageView ivPlus;
    View view;
    LinearLayout layone;
    private SharedPreferences sharedPreferences;


    public static Domain_Adapter domainAdapter;
    public static List<String> Domain = new ArrayList<>();
    public static ListView listView;
    private String name = "";
    public static final String DOMAINLIST = "DomainList";

    android.app.AlertDialog.Builder builder;

    private View V;

    public static final Integer RequestCOde = 21;
    public static Spinner spProtocol;
    String[]  protocolList = { "HTTP://", "HTTPS://"};
    public static String cpText;
    TextView edFullPath;
    public static String FullPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        new LoadCapabilitiesTask(context,PrintPropertyScreen.mFragment).execute();
        V = inflater.inflate(R.layout.firstfragmentconnectorpath, container, false);

        IpAddress = V.findViewById(R.id.edIpAddress);
        btnTEST = V.findViewById(R.id.btnTEST);
        tvDomain = V.findViewById(R.id.tvvDomain);
        edFullPath = V.findViewById(R.id.edFullPath);
        FullPath = edFullPath.getText().toString();
        ivPlus = V.findViewById(R.id.ivPlus);
        listView = V.findViewById(R.id.lvListView);
        editTextdomain = V.findViewById(R.id.edittextDomain);
        layone= V.findViewById(R.id.llLinearLayout);
        spProtocol = V.findViewById(R.id.spProtocol);
        ArrayAdapter aa_prtocol = new ArrayAdapter(getActivity(), R.layout.spinner_connectorpath, protocolList);
        aa_prtocol.setDropDownViewResource(R.layout.spinner_connectorpath);
        spProtocol.setAdapter(aa_prtocol);
//        ArrayAdapter aa_prtocol = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,protocolList);
//        aa_prtocol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spProtocol.setAdapter(aa_prtocol);

        spProtocol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                cpText = parent.getItemAtPosition(position).toString();
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
//        Test = V.findViewById(R.id.btnTest);
//        Test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveData();
//              loadData();
//              updateViews();
//              Intent intent1 = new Intent(getActivity(),TestActivity.class);
//              startActivityForResult(intent1,RequestCOde);
//
//            }
//        });





//        imageView = V.findViewById(R.id.ivBackk);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(getActivity(),LoginActivity.class);
//                startActivity(intent1);
//                getActivity().finish();
//            }
//        });

        radioGroup = (RadioGroup) V.findViewById(R.id.radioGroup);

        AD = V.findViewById(R.id.radioAD);
        SA = V.findViewById(R.id.radioSA);
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
                SharedPreferences pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
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
                if(editTextdomain.getText().toString().equals("") || editTextdomain.getText().toString().equals(null))
                {

                }
                else
                {
                    String editTextDomain = editTextdomain.getText().toString();
                    Domain.add(editTextDomain);
                    domainAdapter.notifyDataSetChanged();
                    editTextdomain.setText("");

                    builder = new android.app.AlertDialog.Builder(getActivity());
                    builder.setCancelable(false);
                    builder.setMessage("Do you want to add another Domain");

                    builder.setPositiveButton("Save & Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveDomainData(Domain);
                            saveData();
                            saveRadioButtons();

                            Intent intent1 = new Intent(getActivity(), TestActivity.class);
                            //   intent1.putExtra("position", positionOfSelectedDataFromSpinner);
                            startActivityForResult(intent1, RequestCOde);
                            //  Toast.makeText(getActivity(), "Save Authentication and Connector Path .. Select Default Settings..!", Toast.LENGTH_SHORT).show();
//                            Intent mainIntent = new Intent(getActivity(),LoginActivity.class);
//                            startActivity(mainIntent);
//                            saveData();
//                            saveDomainData(Domain);
                            //   finishAffinity();
//                            getActivity().finish();


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

        btnCancel = V.findViewById(R.id.btncancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), OnBase_Login_Screen.class);
                startActivity(intent1);
                getActivity().finish();
            }
        });
        btnTEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                authenticationTY = (RadioButton) V.findViewById(selectedId);
                if (IpAddress.getText().toString().trim().isEmpty()) {
                    IpAddress.setError("Enter your IpAddress");
                    IpAddress.requestFocus();
                }
                if (selectedId != R.id.radioAD && selectedId != R.id.radioSA) {
                    Toast toast1 = Toast.makeText(getActivity(), "Select an Authentication Type !!!", Toast.LENGTH_LONG);
                    toast1.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast1.show();


                }else if (selectedId == R.id.radioAD) {
                    if(Domain.size()>0)
                    {
                        saveDomainData(Domain);
                        saveData();
                        saveRadioButtons();
                        loadData();
                        Intent intent1 = new Intent(getActivity(), TestActivity.class);
                        //   intent1.putExtra("position", positionOfSelectedDataFromSpinner);
                        startActivityForResult(intent1, RequestCOde);


                    }
                    else
                    {
                        Toast toast1 = Toast.makeText(getActivity(), "Please enter atleast one Domain !!!", Toast.LENGTH_LONG);
                        toast1.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast1.show();
                    }


                } else if (selectedId == R.id.radioSA) {
                    saveData();
                    saveRadioButtons();
                    loadData();
                    Intent intent1 = new Intent(getActivity(), TestActivity.class);
                    //   intent1.putExtra("position", positionOfSelectedDataFromSpinner);
                    startActivityForResult(intent1, RequestCOde);
                }

            }


        });

        loadData();
        loadRadioButtons();
        updateViews();
//        Log.d("","Domain size: " + Domain.size());

        domainAdapter = new Domain_Adapter(getActivity(), Domain);
        listView.setAdapter(domainAdapter);
//        imageVieww = V.findViewById(R.id.ivMenuBarr);
//        imageVieww.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(getActivity(), MenuActivity.class);
//                startActivity(intent);
//            }
//        });
        IpAddress.clearFocus();
        return V;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RequestCOde){
            Fragment fragment= new PrintAttributesFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment2, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
            transaction.addToBackStack(null);  // this will manage backstack
            transaction.commit();


        }

    }

    public void saveRadioButtons() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MYPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("AD", AD.isChecked());
        editor.putBoolean("SA", SA.isChecked());
        editor.apply();
    }

    public void loadRadioButtons() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MYPref", MODE_PRIVATE);
        AD.setChecked(sharedPreferences.getBoolean("AD", false));
        SA.setChecked(sharedPreferences.getBoolean("SA", false));
        if(sharedPreferences.getBoolean("AD", false)){
            layone.setVisibility(View.VISIBLE);
            loadDomainData();


        }
        else if(sharedPreferences.getBoolean("SA", false)){
            layone.setVisibility(View.GONE);
        }

    }
    private void saveData() {
        String protocol = spProtocol.getSelectedItem().toString();
        String editText = IpAddress.getText().toString();
        String fullpath = "/pfob/service.asmx?wsdl";
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PROTOCOL, protocol);
        editor.putString(ipaddress, editText);
        editor.putString(fullPath, fullpath);
        editor.commit();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        text = sharedPreferences.getString(ipaddress, "");
        protocol = sharedPreferences.getString(PROTOCOL, "");
        paths = sharedPreferences.getString(fullPath, "");
    }

    public void updateViews() {
        IpAddress.setText(text);
        edFullPath.setText(paths);
        protocol= spProtocol.getSelectedItem().toString();
    }


    private void saveDomainData(List<String> domainlist) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(domainlist);

        editor.putString(DOMAINLIST, jsonFavorites);
        editor.commit();
    }

    private void loadDomainData() {
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
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
