package com.sebastianpitur.traveljournal;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class Trips extends AppCompatActivity {
    List<Trip> trips;
    TripsAdapter adapter;

    public Trips() {
        trips = new LinkedList<>();
    }

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        // Lookup the recyclerview in activity layout
        RecyclerView rvTrips = findViewById(R.id.tripsList);

        // Initialize contacts
        trips = new LinkedList<>();
        // Create adapter passing in the sample user data
        adapter = new TripsAdapter();
        // Attach the adapter to the recyclerview to populate items
        rvTrips.setAdapter(adapter);
        // Set layout manager to position the items
        rvTrips.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
    }

    public void addTrip(View view) {
        trips.add(new Trip());
        trips.get(trips.size() - 1).setName("Trip " + (trips.size() - 1));
        adapter.addTrip();
    }
}
