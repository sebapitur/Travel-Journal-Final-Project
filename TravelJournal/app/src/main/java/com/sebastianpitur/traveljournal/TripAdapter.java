package com.sebastianpitur.traveljournal;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class TripAdapter extends RecyclerView.Adapter {
    private Trip trip;

    public TripAdapter(Trip trip) {
        this.trip = trip;
    }

    public Trip getTrip() {
        return trip;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Inflate the custom layout
        View imageView = inflater.inflate(R.layout.image_element, parent, false);

        // Return a new holder instance
        return new ViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Image image = trip.images.get(position);
        TripAdapter.ViewHolder imageHolder = (TripAdapter.ViewHolder) holder;
        // Set item views based on your views and data model
        ImageView imageView = imageHolder.imageView;



        imageView.setImageURI(image.getUriAddress());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        ImageView imageView;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return trip.images.size();
    }


}
