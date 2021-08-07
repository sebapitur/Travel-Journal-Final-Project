package com.sebastianpitur.traveljournal;

import android.net.Uri;

import java.util.LinkedList;
import java.util.List;

public class Trip {

    List<Uri> images;
    String name;

    public Trip() {
        images = new LinkedList<>();
    }

    public void setName(String name) {
        this.name = name;
    }
}
