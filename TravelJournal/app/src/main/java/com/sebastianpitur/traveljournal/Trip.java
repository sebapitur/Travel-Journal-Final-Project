package com.sebastianpitur.traveljournal;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.LinkedList;
import java.util.List;

@Entity
public class Trip implements Parcelable {
    public Trip() {
        images = new LinkedList<>();
    }

    public Trip(int uid) {
        images = new LinkedList<>();
        this.uid = uid;
    }

    protected Trip(Parcel in) {
        images = in.createTypedArrayList(Uri.CREATOR);
        name = in.readString();
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @Ignore
    List<Uri> images;

    @ColumnInfo(name = "trip_name")
    String name;

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(images);
        dest.writeString(name);
    }
}
