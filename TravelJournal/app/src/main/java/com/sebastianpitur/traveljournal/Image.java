package com.sebastianpitur.traveljournal;

import android.net.Uri;

public class Image {
    private Uri uriAddress;

    public Image(Uri address) {
        uriAddress = address;
    }

    public Uri getUriAddress() {
        return uriAddress;
    }
}
