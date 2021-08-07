package com.sebastianpitur.traveljournal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class Trips extends AppCompatActivity {
    List<Trip> trips;
    TripsAdapter adapter;
    private ImageAdapter currentImageAdapter;
    private static final int PICK_IMAGE = 100;

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Uri imageUri;
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            currentImageAdapter.addImage(imageUri);
        }
    }

    public Trips() {
        trips = new LinkedList<>();
    }


    @Override
    protected void onResume() {

        Log.e("finished adding image","On resume method was called");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e("adding image","On pause method was called");
        super.onPause();
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

    public void addImageToTrip(View view) {
        View parentView = (View) view.getParent().getParent();
        RecyclerView recyclerView = parentView.findViewById(R.id.tripElement);
        currentImageAdapter = (ImageAdapter) recyclerView.getAdapter();
        openGallery();
    }

    public void changeName(View view) {

    }
}
