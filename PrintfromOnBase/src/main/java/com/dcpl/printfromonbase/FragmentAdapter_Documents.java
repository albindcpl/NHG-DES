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

    // declare a variable to store the context of the activity
    Context mcontext;

    // declare a static ArrayList to hold the titles of the documents
    static ArrayList Title;

    // Constructor to initialize the adapter
    public FragmentAdapter_Documents(@NonNull Context context, ArrayList title) {


        // Call the constructor of the ArrayAdapter class with layout and TextView id
        super(context, R.layout.layout_list, R.id.tvTitle, title);

        // Assign the context to the declared variable
        mcontext = context;

        // Assign the title ArrayList to the static variable
        Title = title;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi;

            // Get the LayoutInflater from the context
            vi = LayoutInflater.from(mcontext);

            // Inflate the layout for the list item
            view = vi.inflate(R.layout.layout_list, null);
        }

        // Find the TextView for the title
        TextView title = view.findViewById(R.id.tvTitle);

        // Set the title for the TextView
        title.setText(String.valueOf(Title.get(position)));

        // Return the view
        return super.getView(position, convertView, parent);
    }
}


