package com.dcpl.printfromonbase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.dcpl.printfromonbase.R;

import java.util.ArrayList;


class FragmentAdapter_Documents extends ArrayAdapter<String> {
    Context mcontext;
    static ArrayList Title;


    public FragmentAdapter_Documents(@NonNull Context context, ArrayList title) {
        super(context, R.layout.layout_list,R.id.tvTitle,title);
        mcontext = context;
        Title = title;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mcontext);
            view = vi.inflate(R.layout.layout_list, null);
        }
        TextView title = view.findViewById(R.id.tvTitle);
        title.setText(String.valueOf(Title.get(position)));

        return super.getView(position, convertView, parent);
    }

}


