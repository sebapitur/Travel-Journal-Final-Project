package com.sebastianpitur.traveljournal;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TripDao {
    @Query("SELECT * FROM trip")
    List<Trip> getAll();

    @Query("SELECT * FROM trip WHERE uid IN (:tripIds)")
    List<Trip> loadAllByIds(int[] tripIds);

    @Query("SELECT * FROM trip WHERE trip_name LIKE :tripName")
    Trip findByName(String tripName);

    @Insert
    void insert(Trip trip);

    @Delete
    void delete(Trip trip);
}
