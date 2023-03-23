package com.dcpl.printfromonbase;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * This is an adapter class for a RecyclerView in an Android application. The RecyclerView displays a list of items,
 * where each item represents a queue for scanning. The adapter class extends the RecyclerView.Adapter class and is
 * responsible for creating and binding the views for each item in the list.
 *
 * The class contains a constructor that takes an ArrayList of Strings, which represents the list of
 * queues to be displayed. It also contains a static OnItemClickListener interface and a static SparseBooleanArray
 * for keeping track of selected items.
 *
 * The onCreateViewHolder method inflates the layout for each item in the list using the LayoutInflater and
 * returns a ViewHolder object that holds references to the views in the layout.
 *
 * The onBindViewHolder method binds the data for each item to its corresponding view. It sets the text of
 * the TextView in the layout to the queue name and also sets an OnClickListener on the LinearLayout that
 * represents the item. When an item is clicked, the position of the item is stored in the "row_index" variable,
 * and the adapter is notified that the data has changed. This causes the view to be redrawn, highlighting the selected item.
 *
 * The getItemCount method returns the number of items in the list.
 *
 * The setOnItemListener method sets the OnItemClickListener for the adapter.
 *
 * The ViewHolder class holds references to the views in the layout for each item.
 * It also contains a bind method that sets an OnClickListener on the item view and calls the onItemClick method
 * of the OnItemClickListener when the item is clicked.
 */
public class ListViewItemAdapter extends RecyclerView.Adapter<ListViewItemAdapter.ViewHolder> {

    public static ArrayList<String>scanqueuelist = new ArrayList<>();
    public static OnItemClickListener listener;
    private static SparseBooleanArray sSelectedItems;
    public static String SelectedScanQueu = "";
    int row_index = 1;




    // RecyclerView recyclerView;
    public ListViewItemAdapter(ArrayList<String> scanqueuelist) {
        this.scanqueuelist = scanqueuelist;
    }

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
        holder.textView.setText(scanqueuelist.get(position));

        holder.bind(Collections.singletonList(scanqueuelist.get(position)), listener);
//        holder.textView.setText(ColorList);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SelectedScanQueu = scanqueuelist.get(position);
                row_index=position;
                notifyDataSetChanged();

            }
        });

        if(row_index==position){
            holder.linearLayout.setBackgroundColor(Color.parseColor("#9ee6D6"));
            // holder.tv1.setTextColor(Color.parseColor("#ffffff"));
        }
        else
        {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
            //    holder.tv1.setTextColor(Color.parseColor("#000000"));
        }
    }



    @Override
    public int getItemCount() {
        return scanqueuelist.size();
    }


    public void setOnItemListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public LinearLayout linearLayout;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.tvListItem);
            this.linearLayout = itemView.findViewById(R.id.LinearLayoutll);
        //    this.progressBar = itemView.findViewById(R.id.ProgressBarScanOption);
        }

        public void bind(List<String> item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
