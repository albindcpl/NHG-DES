package com.dcpl.printfromonbase;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class FirstFragmentAdapter extends RecyclerView.Adapter<FirstFragmentAdapter.MyViewholder>{

    private static final String TAG = "";
    public static Context context;
    List<String> keywordnameList = new ArrayList<>();

    ArrayList<String>keyworddataTypeList = new ArrayList<>();
    ArrayList<String>keyworddetailsList = new ArrayList<>();
    public static String enteredText ="" ;


    public static HashMap<Integer,String> edittextlist=new HashMap<>();

    public FirstFragmentAdapter(Context context, List<String> name, ArrayList<String>dataType, ArrayList<String>details) {
        this.keywordnameList = name;
        this.keyworddataTypeList = dataType;
        this.keyworddetailsList = details;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new MyViewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewholder holder, final int position) {

        holder.tvKeywordType.setText(keywordnameList.get(position));
        holder.tvKeywordType.setTextColor(Color.BLACK);


        try
        {

            holder.edKeywordValue.setText(edittextlist.get(position));
            holder.edKeywordValue.setError(null);

            switch (keyworddataTypeList.get(position)) {
                case "Numeric9":

                case "Numeric20":
                    holder.edKeywordValue.setInputType(InputType.TYPE_CLASS_NUMBER);
                    holder.edKeywordValue.setHint("Numeric (Max: " + keyworddetailsList.get(position) + ")");
                    holder.edKeywordValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.parseInt(keyworddetailsList.get(position)))});
                    break;

                case "Date":

                    holder.edKeywordValue.setHint("Date (Format: " + keyworddetailsList.get(position) + ")");
//                    holder.ivImageView.setVisibility(View.INVISIBLE);
                    final Calendar myCalendar = Calendar.getInstance();
                    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            // TODO Auto-generated method stub
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, monthOfYear);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            String myFormat = "MM/dd/yyyy"; //In which you need put here
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                            holder.edKeywordValue.setText(sdf.format(myCalendar.getTime()));

                        }

                    };

                    holder.edKeywordValue.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            new DatePickerDialog(context, date, myCalendar
                                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        }
                    });

                    break;

                case "DateTime":
                    holder.edKeywordValue.setHint("DateTime (Format: " + keyworddetailsList.get(position) + ")");
//                    holder.ivImageView.setVisibility(View.INVISIBLE);

                    holder.edKeywordValue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Calendar Date;
                            final Calendar currentDate = Calendar.getInstance();
                            Date = Calendar.getInstance();
                            new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    Date.set(year, monthOfYear, dayOfMonth);
                                    new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                            Date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                            Date.set(Calendar.MINUTE, minute);
                                            //   Log.v(TAG, "The choosen one " + Date.getTime());
                                        }
                                    }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
                                }
                            }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
                            String myFormat = "MM/dd/yyyy hh:mm:ss"; //In which you need put here
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                            holder.edKeywordValue.setText(sdf.format(Date.getTime()));
                        }
                    });

                    break;

                case "Currency":
                    holder.edKeywordValue.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    holder.edKeywordValue.setHint("Currency (Example: " + keyworddetailsList.get(position) + ")");
//                    holder.ivImageView.setVisibility(View.INVISIBLE);
                    holder.edKeywordValue.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String enteredtext = holder.edKeywordValue.getText().toString();
                            if (enteredtext == null || enteredtext.matches("^$?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?$")) {
                                System.out.println("yes");
                            } else {
                                System.out.println("no");
                                holder.edKeywordValue.setError("Invalid Format !!!");
                            }

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

//                            String enteredtext = holder.edKeywordValue.getText().toString();
//                            if(enteredtext==null || enteredtext.matches("^$?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?$"))
//                            {
//                                System.out.println("yes");
//                            }
//                            else
//                            {
//                                System.out.println("no");
//                                holder.edKeywordValue.setError("Invalid Format !!!");

                            //   }

                        }

                    });

                    break;

                case "AlphaNumeric":
                    holder.edKeywordValue.setInputType(InputType.TYPE_CLASS_TEXT);
                    holder.edKeywordValue.setHint("AlphaNumeric (Max: " + keyworddetailsList.get(position) + ")");
                    holder.edKeywordValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.parseInt(keyworddetailsList.get(position)))});
//                    holder.ivImageView.setVisibility(View.INVISIBLE);
                    break;

                case "FloatingPoint":
                    holder.edKeywordValue.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    holder.edKeywordValue.setHint("Float (Max: " + keyworddetailsList.get(position) + ")");
                    holder.edKeywordValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.parseInt(keyworddetailsList.get(position)))});
//                    holder.ivImageView.setVisibility(View.INVISIBLE);
                    holder.edKeywordValue.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            String enteredtext = holder.edKeywordValue.getText().toString();
                            if (enteredtext == null || enteredtext.matches("[-+]?[0-9]*\\.?[0-9]+")) {
                                System.out.println("yes");
                            } else {
                                System.out.println("no");
                                holder.edKeywordValue.setError("Invalid Format !!!");
                            }

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
//                            String enteredtext = holder.edKeywordValue.getText().toString();
//                            if(enteredtext==null || enteredtext.matches("[-+]?[0-9]*\\.?[0-9]+"))
//                            {
//                                System.out.println("yes");
//                            }
//                            else
//                            {
//                                System.out.println("no");
//                                holder.edKeywordValue.setError("Invalid Format !!!");
//                            }

                        }
                    });
                    break;

                case "SpecificCurrency":
                    holder.edKeywordValue.setHint("Currency (Example: " + keyworddetailsList.get(position) + ")");
//                        holder.ivImageView.setVisibility(View.INVISIBLE);

                    holder.edKeywordValue.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            String enteredtext = holder.edKeywordValue.getText().toString();
                            if (enteredtext == null || enteredtext.matches("^$?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?$")) {
                                System.out.println("yes");
                            } else {
                                System.out.println("no");
                                holder.edKeywordValue.setError("Invalid Format !!!");
                            }

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

//                            String enteredtext = holder.edKeywordValue.getText().toString();
//                            if(enteredtext==null || enteredtext.matches("^$?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?$"))
//                            {
//                                System.out.println("yes");
//                            }
//                            else
//                            {
//                                System.out.println("no");
//                                holder.edKeywordValue.setError("Invalid Format !!!");
//                            }

                        }

                    });

                    break;

            }

//            holder.edKeywordValue.getText().toString().trim().length() < sizeList.get(position);
        }
        catch (Exception e){

        }

        holder.edKeywordValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
            }


            @Override
            public void afterTextChanged(Editable s) {
                try {
                    edittextlist.put(position, s.toString());

                }catch (Exception e){
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return keywordnameList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void clear() {

    }

    class MyViewholder extends RecyclerView.ViewHolder{
       TextView tvKeywordType;
       EditText edKeywordValue;
        ImageView ivImageView;
       MyViewholder(@NonNull View itemView) {
            super(itemView);
            edKeywordValue = itemView.findViewById(R.id.edKeyword);
            tvKeywordType = itemView.findViewById(R.id.tvKeyword);

        }
    }
}
