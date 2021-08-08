package com.sebastianpitur.traveljournal;

import android.app.Activity;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Dao;
import androidx.room.Room;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Trips extends AppCompatActivity {
    ArrayList<Trip> trips;
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
        trips = new ArrayList<>();
    }


    @Override
    public void onSaveInstanceState(@NotNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("OldTrips", (ArrayList<? extends Parcelable>) trips);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        trips.addAll(savedInstanceState.getParcelableArrayList("OldTrips"));
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        // Lookup the recyclerview in activity layout
        RecyclerView rvTrips = findViewById(R.id.tripsList);
        TripDao tripDao = TripDataBase.getDatabase(getApplicationContext()).tripDao();
        // Initialize trips

        if (tripDao != null)
             TripDataBase.databaseWriteExecutor.execute(() -> {
                trips = (ArrayList<Trip>) tripDao.getAll();
            });
        if (trips == null)
            trips = new ArrayList<>();
        // Create adapter passing in the sample user data
        adapter = new TripsAdapter();
        if(trips.size() != 0)
            adapter.updateDataBase(trips);
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
        TripDataBase.databaseWriteExecutor.execute(() -> {
                TripDataBase.getDatabase(getApplicationContext()).tripDao().insert(trips.get(trips.size() - 1));
//                Log.e("insert", "add trip to database");
       });
    }

    public void addImageToTrip(View view) {
        View parentView = (View) view.getParent().getParent();
        RecyclerView recyclerView = parentView.findViewById(R.id.tripElement);
        currentImageAdapter = (ImageAdapter) recyclerView.getAdapter();
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
        trips.clear();
        adapter.updateDataBase(trips);
        TripDataBase.databaseWriteExecutor.execute(() -> {
            TripDataBase.getDatabase(getApplicationContext()).clearAllTables();
        });
    }
}
