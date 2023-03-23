package com.dcpl.printfromonbase;
//this adapter is used to bind a list of strings to a list view
// or spinner and allows the user to remove items from the list by clicking a button associated with each item.
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



//The class Domain_Adapter extends the ArrayAdapter class, which is used to bind an array or list of data to a view element.
// In this case, the Domain_Adapter class is bound to a list of strings, which are stored in the Title list.
class Domain_Adapter extends ArrayAdapter<String> {
    Context mcontext;
    List<String> Title = new ArrayList<>();

//The constructor of the Domain_Adapter class takes the context of the activity, as well as the list of strings, as parameters.
// It calls the constructor of the parent ArrayAdapter class, passing in the layout file, domain_list, and the ID of the TextView
// in that layout file, R.id.tvDomainn.
    public Domain_Adapter(@NonNull Context context, List <String>title) {
        super(context, domain_list,R.id.tvDomainn,title);
        mcontext = context;
        Title = title;

    }



    //The getView() method is called when the list view or spinner needs to display a view for a particular position in the list. The
    // method inflates the domain_list layout file and sets the text of the TextView with ID R.id.tvDomainn
    // to the string at the current position in the Title list.
    @NonNull
    @Override

    /**
     * This is a method definition for a getView function,  within Domatin adapter class that extends
     * the BaseAdapter class in Android. The purpose of this function is to create a view to represent a single item in a list or grid.
     * The function takes three parameters:
     * position which is the position of the item within the list or grid,
     * convertView which is a previously created view that can be reused to optimize performance,
     * and parent which is the parent view group for the view being created.
     */
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
      //  Log.d("Clicked Position: ",Integer.toString(position));


        /**
         * The function first checks if the convertView is null, which means that a new view needs to be created.
         * If it is null, it uses the LayoutInflater to create a new view from a layout resource called domain_list.
         */
        View view = convertView;
        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mcontext);
            view = vi.inflate(domain_list, null);
        }

        /**
         * Next, the function finds a TextView within the view
         * using its ID tvDomainn and sets its text to the value of Title at the current position.
         */
        TextView title = view.findViewById(R.id.tvDomainn);
        title.setText(String.valueOf(Title.get(position)));


        /**
         * This is a method definition for a getView function, likely within an adapter class that
         * extends the BaseAdapter class in Android. The purpose of this function is to create a view to represent
         * a single item in a list or grid.
         */
        Button btnCnacel = (Button)view.findViewById(R.id.ivCancel);
        btnCnacel.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                OBConnector_Settings_Screen.Domain.remove(position); //or some other task
                notifyDataSetChanged();
               // Log.d("Value deleted !!!","");
            }

        });

        //Finally, the function returns the view that was created or reused.

        return super.getView(position, convertView, parent);
    }

}


