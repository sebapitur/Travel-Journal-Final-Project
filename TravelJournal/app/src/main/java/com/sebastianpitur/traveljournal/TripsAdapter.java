package com.sebastianpitur.traveljournal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.transition.Hold;

import org.jetbrains.annotations.NotNull;
import java.util.LinkedList;
import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViewHolder> {
    Context context;
    private List<Trip> mTrips;

    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tripView = inflater.inflate(R.layout.trip_element, parent, false);

        ViewHolder viewHolder = new ViewHolder(tripView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TripsAdapter.ViewHolder holder, int position) {
        Trip trip = mTrips.get(position);

        // Set item views based on your views and data model
        RecyclerView recyclerView = holder.tripElement;
        String name = "Trip " + position;
        TextView tripName = holder.tripName;
        trip.setName(name);
        tripName.setText(name);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.tripElement.setLayoutManager(layoutManager);
        holder.tripElement.setHasFixedSize(false);
        holder.tripElement.setAdapter(new ImageAdapter(new LinkedList<>(), holder.tripElement.getContext()));
    }

    @Override
    public int getItemCount() {
        return mTrips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView tripElement;
        public TextView tripName;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tripElement = itemView.findViewById(R.id.tripElement);
            tripName = itemView.findViewById(R.id.tripName);
        }
    }

    public void addTrip() {
        mTrips.add(new Trip());
        this.notifyItemInserted(mTrips.size() - 1);
    }

    public TripsAdapter() {
        mTrips = new LinkedList<>();
    }
}
