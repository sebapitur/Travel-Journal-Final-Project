package com.sebastianpitur.traveljournal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

    public void updateDataBase(List<Trip> trips){
       mTrips = trips;
       this.notifyDataSetChanged();
    }
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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(new ImageAdapter(new LinkedList<>(), holder.tripElement.getContext()));
    }

    @Override
    public int getItemCount() {
        return mTrips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView tripElement;
        public TextView tripName;
        public View buttons;
        public Button addButton;
        public Button changeNamebutton;
        public EditText changeNameEdit;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tripElement = itemView.findViewById(R.id.tripElement);
            tripName = itemView.findViewById(R.id.tripName);
            buttons = itemView.findViewById(R.id.buttons);
            addButton = buttons.findViewById(R.id.add_trip_button);
            changeNamebutton = buttons.findViewById(R.id.changeNameButton);
            changeNameEdit = buttons.findViewById(R.id.changeNameEdit);
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
