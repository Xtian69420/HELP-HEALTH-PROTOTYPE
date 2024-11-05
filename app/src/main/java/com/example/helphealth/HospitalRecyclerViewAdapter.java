package com.example.helphealth;

// HospitalRecyclerViewAdapter.java

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HospitalRecyclerViewAdapter extends RecyclerView.Adapter<HospitalRecyclerViewAdapter.ViewHolder> {

    private List<String> hospitalNames;
    private List<String> hospitalLocations;
    private OnHospitalClickListener clickListener;

    public HospitalRecyclerViewAdapter(List<String> hospitalNames, List<String> hospitalLocations, OnHospitalClickListener clickListener) {
        this.hospitalNames = hospitalNames;
        this.hospitalLocations = hospitalLocations;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.header.setText(hospitalNames.get(position));
        holder.description.setText(hospitalLocations.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onHospitalClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hospitalNames.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView header;
        TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            description = itemView.findViewById(R.id.description);
        }
    }
}
