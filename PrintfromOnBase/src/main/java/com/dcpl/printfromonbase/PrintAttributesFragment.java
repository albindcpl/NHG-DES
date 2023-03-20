package com.dcpl.printfromonbase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class PrintAttributesFragment extends Fragment {

    Button btnSave;
    RadioButton radioBond,radioPlain,radioBandW,radioColor,radioDuplexMode,radioSimplexMode,radioA3,radioA4,radioNone,radioStaple,radioTrue,radioFalse;
    RadioGroup radioPaperType,radioGroupColorMode,radioMode,radioPaperSize,radioStapleMode,radioGroupAutoFit;
    RadioButton DocumentFormat,ColorMode,Mode,Orientation,Resolution,Preview;
    View V;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //new LoadCapabilitiesTask(context,PrintPropertyScreen.mFragment).execute();
        V = inflater.inflate(R.layout.printattributefragment, container, false);

        radioPaperType = V.findViewById(R.id.radioPaperType);
        radioPlain = V.findViewById(R.id.radioPlain);
        radioBond = V.findViewById(R.id.radioBond);

        radioGroupColorMode = V.findViewById(R.id.radioGroupColorMode);
        radioBandW = V.findViewById(R.id.radioBandW);
        radioColor = V.findViewById(R.id.radioColor);

        radioMode = V.findViewById(R.id.radioGroupDuplexMode);
        radioDuplexMode = V.findViewById(R.id.radioBook);
        radioSimplexMode = V.findViewById(R.id.radioFlip);

        radioPaperSize = V.findViewById(R.id.radioGroupPaperSize);
        radioA3 = V.findViewById(R.id.radioA3);
        radioA4= V.findViewById(R.id.radioA4);

        radioStapleMode = V.findViewById(R.id.radioGroupStapleMode);
        radioNone = V.findViewById(R.id.radioNone);
        radioStaple = V.findViewById(R.id.radioStaple);

        radioGroupAutoFit = V.findViewById(R.id.radioGroupAutofit);
        radioTrue = V.findViewById(R.id.radioTrue);
        radioFalse = V.findViewById(R.id.radioFalse);

        btnSave = V.findViewById(R.id.saveBtn);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedid = radioPaperType.getCheckedRadioButtonId();
                DocumentFormat= (RadioButton) V.findViewById(selectedid);

                int Selectedid = radioGroupColorMode.getCheckedRadioButtonId();
                ColorMode= (RadioButton) V.findViewById(Selectedid);

                int selectedId = radioMode.getCheckedRadioButtonId();
                Mode= (RadioButton) V.findViewById(selectedId);

                int SelectedId = radioPaperSize.getCheckedRadioButtonId();
                Orientation= (RadioButton) V.findViewById(SelectedId);

                int SelectedID = radioStapleMode.getCheckedRadioButtonId();
                Resolution= (RadioButton) V.findViewById(SelectedID);

                int SelecteDID = radioStapleMode.getCheckedRadioButtonId();
                Preview= (RadioButton) V.findViewById(SelecteDID);



                if (selectedid == R.id.radioPlain){
                    SaveRadioButtons();
                    Intent intent = new Intent(getActivity(), OnBase_Login_Screen.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else if(Selectedid == R.id.radioBandW){
                    SaveRadioButtons();
                    Intent intent = new Intent(getActivity(), OnBase_Login_Screen.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else if(selectedId == R.id.radioBook){
                    SaveRadioButtons();
                    Intent intent = new Intent(getActivity(), OnBase_Login_Screen.class);
                    startActivity(intent);
                    getActivity().finish();
                }

                else if(SelectedId == R.id.radioA3) {
                    SaveRadioButtons();
                    Intent intent = new Intent(getActivity(), OnBase_Login_Screen.class);
                    startActivity(intent);
                    getActivity().finish();

                }

                else if(SelecteDID == R.id.radioFalse) {
                    SaveRadioButtons();
                    Intent intent = new Intent(getActivity(), OnBase_Login_Screen.class);
                    startActivity(intent);
                    getActivity().finish();

                }


            }

        });
        LoadRadioButton();

        return V;
    }

    public  void SaveRadioButtons() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MYPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Plain", radioPlain.isChecked());
        editor.putBoolean("Bond", radioBond.isChecked());
        editor.putBoolean("BandW", radioBandW.isChecked());
        editor.putBoolean("Color", radioColor.isChecked());
        editor.putBoolean("Book", radioDuplexMode.isChecked());
        editor.putBoolean("Flip", radioSimplexMode.isChecked());
        editor.putBoolean("A3", radioA3.isChecked());
        editor.putBoolean("A4", radioA4.isChecked());
        editor.putBoolean("None", radioNone.isChecked());
        editor.putBoolean("Staple", radioStaple.isChecked());
        editor.putBoolean("false", radioFalse.isChecked());
        editor.putBoolean("true", radioTrue.isChecked());
        editor.apply();
    }

    public void LoadRadioButton() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MYPref", MODE_PRIVATE);
        radioPlain.setChecked(sharedPreferences.getBoolean("Plain", false));
        radioBond.setChecked(sharedPreferences.getBoolean("Bond", false));
        radioBandW.setChecked(sharedPreferences.getBoolean("BandW", false));
        radioColor.setChecked(sharedPreferences.getBoolean("Color", false));
        radioDuplexMode.setChecked(sharedPreferences.getBoolean("Book", false));
        radioSimplexMode.setChecked(sharedPreferences.getBoolean("Flip", false));
        radioA3.setChecked(sharedPreferences.getBoolean("A3", false));
        radioA4.setChecked(sharedPreferences.getBoolean("A4", false));
        radioNone.setChecked(sharedPreferences.getBoolean("None", false));
        radioStaple.setChecked(sharedPreferences.getBoolean("Staple", false));
        radioFalse.setChecked(sharedPreferences.getBoolean("false", false));
        radioTrue.setChecked(sharedPreferences.getBoolean("true", false));
    }
}
