package com.dcpl.printfromonbase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ConnectorFragment extends Fragment {

    View V;
    public static TextView tvConnector;
    public static TextView tvSettings;
    ImageView ivSettings;
    ImageView ivCP;
    String attributes;
    //int color =  Color.parseColor("#ffcc66");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


//        new LoadCapabilitiesTask(context,PrintPropertyScreen.mFragment).execute();
        V= inflater.inflate(R.layout.connectorandauthenticationfragment, container, false);


        tvConnector = V.findViewById(R.id.tvConnectorPath);
//        ivCP = V.findViewById(R.id.ivCP);
//        ivSettings = V.findViewById(R.id.ivSetting);
//        tvConnector.setHighlightColor(Color.GRAY);
//        tvConnector.setBackgroundResource(R.color.colorPrimaryDark);
        tvConnector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //btnMale.setBackgroundResource(R.drawable.background);// change background your button like this

                tvConnector.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.preference_summary_color));// use this to set color as background
              //  ivCP.setVisibility(View.VISIBLE);
                tvSettings.setBackgroundResource(android.R.color.white);
                Fragment fragment= new SettingFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment2, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();

            }
        });

        tvSettings = V.findViewById(R.id.tvDefaultSettings);
        tvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvConnector.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white));// use this to set color as background


                tvSettings.setBackgroundResource(android.R.color.darker_gray);
             //   ivSettings.setVisibility(View.VISIBLE);
//                Fragment fragment= new ScanAttributesFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragment2, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
//                transaction.addToBackStack(null);  // this will manage backstack
//                transaction.commit();


            }
        });



        return V;
    }
}
