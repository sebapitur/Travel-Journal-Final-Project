package com.sebastianpitur.traveljournal;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Trips extends AppCompatActivity {
    TripsAdapter adapter;
    private static final int PICK_IMAGE = 100;
    public String accountName;
    private TripAdapter currentTripAdapter;

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Uri imageUri;
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            currentTripAdapter.getTrip().images.add(new Image(imageUri));
            TripDataBase.databaseWriteExecutor.execute(() -> {
                TripDataBase.getDatabase(getApplicationContext()).tripDao().update(currentTripAdapter.getTrip());
            });

            currentTripAdapter.notifyDataSetChanged();
            adapter.notifyDataSetChanged();
        }
    }

    public Trips() {    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("", "resume");
        populateView();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("", "pause");
        populateView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);
        Intent intent = getIntent();
        accountName = intent.getStringExtra("account");
        adapter = new TripsAdapter();
        // Check if the permission is already granted
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    2);
        } else {
            populateView();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateView();
            } else {
                // Permission denied, handle the situation accordingly
            }
        }
    }


    private void populateView() {
        RecyclerView rvTrips = findViewById(R.id.tripsList);
        TripDao tripDao = TripDataBase.getDatabase(getApplicationContext()).tripDao();
        // Initialize trips

        if (tripDao != null)
            TripDataBase.databaseWriteExecutor.execute(() -> {
                adapter.setTrips((ArrayList<Trip>) tripDao.getAll());
            });

        rvTrips.setAdapter(adapter);
        // Set layout manager to position the items
        rvTrips.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addTrip(View view) {
        adapter.addTrip(new Trip(accountName, "Trip " + (adapter.getTrips().size() - 1)));
        TripDataBase.databaseWriteExecutor.execute(() -> {
                TripDataBase.getDatabase(getApplicationContext()).tripDao().insert(adapter.getTrips().get(adapter.getTrips().size() - 1));
       });
    }

    public void addImageToTrip(View view) {
        View parentView = (View) view.getParent().getParent();
        RecyclerView recyclerView = parentView.findViewById(R.id.tripElement);
        currentTripAdapter = (TripAdapter) recyclerView.getAdapter();
        openGallery();
    }

    public void changeName(View view) {
        View parentView = (View) view.getParent().getParent();
        TextView textView = parentView.findViewById(R.id.tripName);
        EditText name = parentView.findViewById(R.id.buttons).findViewById(R.id.changeNameEdit);
        textView.setText(name.getText());
        hideSoftKeyboard(this);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    public void clearDataBase(View view) {
        adapter.getTrips().clear();
        adapter.addTrips(adapter.getTrips());
        TripDataBase.databaseWriteExecutor.execute(() -> {
            TripDataBase.getDatabase(getApplicationContext()).clearAllTables();
        });
    }
}
