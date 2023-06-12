package com.sebastianpitur.traveljournal;

import android.content.Context;
import android.net.Uri;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@TypeConverters(ImageListConverter.class)
@Database(entities = {Trip.class}, version = 3)
public abstract class TripDataBase extends RoomDatabase {
    private static volatile TripDataBase INSTANCE;

    private static final int NUMBER_OF_THREADS = 2;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

     static TripDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TripDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TripDataBase.class, "trip_database").fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    public abstract TripDao tripDao();
}





