package com.example.ptocalc11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    // creating a variable for our array list and context.
    private ArrayList<RecyclerData> ptoDataArrayList;
    private Context mcontext;

    // creating a constructor class.
    public RecyclerViewAdapter(Context mcontext, ArrayList<RecyclerData> recyclerDataArrayList) {
        this.mcontext = mcontext;
        this.ptoDataArrayList = recyclerDataArrayList;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview from our modal class.
        RecyclerData recyclerData = ptoDataArrayList.get(position);
        holder.tvPtoDate.setText(recyclerData.getDate());
        //holder.courseDescTV.setText(recyclerData.getHours());
    }

    @Override
    public int getItemCount() {
        // this method returns
        // the size of recyclerview
        return ptoDataArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        // creating a variable for our text view.
        private TextView tvPtoDate;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            tvPtoDate = itemView.findViewById(R.id.dateElement);
        }

        public TextView getTvPtoDate(){
            return tvPtoDate;
        }
    }

}