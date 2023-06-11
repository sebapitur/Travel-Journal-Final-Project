package com.sebastianpitur.traveljournal;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TripDao {
    @Query("SELECT * FROM trip")
    List<Trip> getAll();

    @Insert
    void insert(Trip trip);

    @Update
    void update(Trip trip);


    @Query("SELECT * FROM trip WHERE uid IN (:tripIds)")
    List<Trip> loadAllByIds(int[] tripIds);

    @Query("SELECT * FROM trip WHERE trip_name LIKE :tripName")
    Trip findByName(String tripName);

    @Delete
    void delete(Trip trip);
}
