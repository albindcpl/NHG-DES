package com.dcpl.printfromonbase;

import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * This import statement brings in the RecyclerView class from the androidx.recyclerview.widget package.
 * RecyclerView is a powerful widget in Android that allows for the display of large sets of data in a scrollable
 * list or grid format, while also recycling view instances to optimize performance
 */
import androidx.recyclerview.widget.RecyclerView;


/**
 * This import statement brings in the ArrayList class from the java.util package. ArrayList is a class in Java that
 * provides a resizable array implementation. It is commonly used for dynamic lists that need to grow or shrink in size.
 */
import java.util.ArrayList;

/**
 * This import statement brings in the Collections class from the java.util package. Collections is a class in
 * Java that provides various utility methods for manipulating and working with collections, such as sorting, shuffling, and searching.
 */
import java.util.Collections;


/**
 * This import statement brings in the List interface from the java.util package. List is a Java interface that defines a
 * collection of elements in an ordered sequence, allowing for duplicate elements and null values.
 * List is implemented by many classes in Java, including ArrayList.
 */
import java.util.List;

public class DuplexAdapter extends RecyclerView.Adapter<DuplexAdapter.ViewHolder> {
    // Define a class called DuplexAdapter that extends RecyclerView.Adapter
    // and uses ViewHolder as the generic type parameter.

    public static ArrayList<String> scanqueuelist = new ArrayList<>();
    // Define a public static ArrayList called scanqueuelist that holds String objects.

    public static OnItemClickListener listener;
    // Define a public static interface called OnItemClickListener.

    private static SparseBooleanArray sSelectedItems;
    // Define a private static SparseBooleanArray called sSelectedItems.

    public static String SelectedScanQueu = "";
    // Define a public static String called SelectedScanQueu and initialize it to an empty string.

    int row_index = 3;
    // Define an int variable called row_index and initialize it to 3.

    // Define the constructor for DuplexAdapter.
    public DuplexAdapter(ArrayList<String> scanqueuelist) {
        this.scanqueuelist = scanqueuelist;
    }

    // Define the OnItemClickListener interface.
    public interface OnItemClickListener {
        void onItemClick(List<String> item);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.listview_attributes ,parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Set the text of the ViewHolder's textView to the value at the given position in the scanqueuelist.
        holder.textView.setText(scanqueuelist.get(position));

        // Bind the scanqueuelist value at the given position to the ViewHolder's linearLayout, using the listener.
        holder.bind(Collections.singletonList(scanqueuelist.get(position)), listener);

        // Set an onClickListener for the ViewHolder's linearLayout.
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the SelectedScanQueu variable to the value at the given position in the scanqueuelist.
                SelectedScanQueu = scanqueuelist.get(position);
                // Set the row_index variable to the given position.
                row_index = position;
                // Notify the adapter that the data set has changed.
                notifyDataSetChanged();
                // Show a Toast message indicating which item was clicked.
                // Toast.makeText(view.getContext(),"click on item: "+ SelectedScanQueu,Toast.LENGTH_LONG).show();
            }
        });

        // If the current row index is equal to the given position...
        if (row_index == position) {
            // Set the background color of the ViewHolder's linearLayout to a light blue.
            holder.linearLayout.setBackgroundColor(Color.parseColor("#9ee6D6"));
            // Set the text color of the ViewHolder's tv1 (if it exists) to white.
            // holder.tv1.setTextColor(Color.parseColor("#ffffff"));
        } else {
            // Otherwise, set the background color of the ViewHolder's linearLayout to white.
            holder.linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }




    @Override
    public int getItemCount() {
        // Return the size of the scanqueuelist.
        return scanqueuelist.size();
    }


    public void setOnItemListener(OnItemClickListener listener) {
        // Set the adapter's listener to the given listener.
        this.listener = listener;
    }


    @Override
    public int getItemViewType(int position) {
        // Return the given position as the itemViewType.
        return position;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public LinearLayout linearLayout;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            // Call the superclass's constructor with the given itemView.
            super(itemView);
            // Set the ViewHolder's textView to the textView with the id tvListItem in the itemView.
            this.textView = (TextView) itemView.findViewById(R.id.tvListItem);
            // Set the ViewHolder's linearLayout to the linearLayout with the id LinearLayoutll in the itemView.
            this.linearLayout = itemView.findViewById(R.id.LinearLayoutll);
            // Set the ViewHolder's progressBar to the progressBar with the id ProgressBarScanOption in the itemView (if it exists).
            // this.progressBar = itemView.findViewById(R.id.ProgressBarScanOption);
        }

        public void bind(List<String> item, final OnItemClickListener listener) {
            // Set an onClickListener for the ViewHolder's itemView.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Call the onItemClick method of the given listener, passing in the given item.
                    listener.onItemClick(item);
                }
            });
        }
    }
}
