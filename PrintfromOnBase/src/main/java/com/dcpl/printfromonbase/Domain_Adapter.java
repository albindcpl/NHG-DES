package com.dcpl.printfromonbase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.dcpl.printfromonbase.R.layout.domain_list;


class Domain_Adapter extends ArrayAdapter<String> {
    Context mcontext;
    List<String> Title = new ArrayList<>();


    public Domain_Adapter(@NonNull Context context, List <String>title) {
        super(context, domain_list,R.id.tvDomainn,title);
        mcontext = context;
        Title = title;

    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
      //  Log.d("Clicked Position: ",Integer.toString(position));
        View view = convertView;
        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mcontext);
            view = vi.inflate(domain_list, null);
        }
        TextView title = view.findViewById(R.id.tvDomainn);
        title.setText(String.valueOf(Title.get(position)));

        Button btnCnacel = (Button)view.findViewById(R.id.ivCancel);
        btnCnacel.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                OBConnector_Settings_Screen.Domain.remove(position); //or some other task
                notifyDataSetChanged();
               // Log.d("Value deleted !!!","");
            }

        });

        return super.getView(position, convertView, parent);
    }

}


