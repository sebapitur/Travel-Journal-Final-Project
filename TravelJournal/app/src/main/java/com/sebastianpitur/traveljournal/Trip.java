package com.sebastianpitur.traveljournal;

import android.net.Uri;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import androidx.room.PrimaryKey;

import java.util.LinkedList;
import java.util.List;

import com.sebastianpitur.traveljournal.ImageListConverter;

@Entity
public class Trip implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "images")
    List<Image> images;

    @ColumnInfo(name = "trip_name")
    String name;

    @ColumnInfo(name = "rating")
    float rating;

    @ColumnInfo(name = "favorite")
    Boolean favorite;

    @ColumnInfo(name = "user")
    String user;

    public Trip() {

    }
    public Trip(String user, String tripName) {
        images = new LinkedList<>();
        this.user = user;
        this.name = tripName;
        this.favorite = false;
        this.rating = 0.0F;
    }

    public Trip(Parcel in) {
        images = ImageListConverter.fromString(in.readString());
        name = in.readString();
        user = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            favorite = in.readBoolean();
        }
        rating = in.readFloat();
    }

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
        dest.writeString(ImageListConverter.toString(images));
        dest.writeString(name);
        dest.writeString(user);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(favorite);
        }
        dest.writeFloat(rating);
    }
}
