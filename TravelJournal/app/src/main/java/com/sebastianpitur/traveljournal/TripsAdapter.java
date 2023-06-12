package com.sebastianpitur.traveljournal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import java.util.LinkedList;
import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViewHolder> {
    Context context;
    private List<Trip> mTrips;

    public TripsAdapter() {
        mTrips = new LinkedList<>();
    }

    public void addTrips(List<Trip> trips){
       mTrips = trips;
       this.notifyDataSetChanged();
    }

    public List<Trip> getTrips(){
        return mTrips;
    }

    public void setTrips(List<Trip> trips){
        mTrips = trips;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_element, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripsAdapter.ViewHolder holder, int position) {
        Trip trip = mTrips.get(position);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = holder.tripElement;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(new TripAdapter(trip));
        Log.i("get", trip.name);
        Log.i("get", trip.favorite.toString());
        Log.i("get", Float.toString(trip.rating));
        holder.tripName.setText(trip.name);
        holder.favorite.setChecked(trip.favorite);
        holder.bar.setRating(trip.rating);
    }

    @Override
    public int getItemCount() {
        return mTrips.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView tripElement;
        public TextView tripName;
        public View buttons;
        public Button addButton;
        public Button changeNamebutton;
        public EditText changeNameEdit;
        public RatingBar bar;
        public CheckBox favorite;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            tripElement = itemView.findViewById(R.id.tripElement);
            tripName = itemView.findViewById(R.id.tripName);
            buttons = itemView.findViewById(R.id.buttons);
            addButton = buttons.findViewById(R.id.add_trip_button);
            changeNamebutton = buttons.findViewById(R.id.changeNameButton);
            changeNameEdit = buttons.findViewById(R.id.changeNameEdit);
            bar = itemView.findViewById(R.id.ratingBar);
            favorite = itemView.findViewById(R.id.favoriteCheck);
        }

    }


    public void addTrip(Trip trip) {
        mTrips.add(trip);
        this.notifyItemInserted(mTrips.size() - 1);
    }

}
