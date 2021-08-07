package com.sebastianpitur.traveljournal;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;
import java.util.List;

public class Trip implements Parcelable {

    List<Uri> images;
    String name;

    public Trip() {
        images = new LinkedList<>();
    }

    protected Trip(Parcel in) {
        images = in.createTypedArrayList(Uri.CREATOR);
        name = in.readString();
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
        dest.writeTypedList(images);
        dest.writeString(name);
    }
}
