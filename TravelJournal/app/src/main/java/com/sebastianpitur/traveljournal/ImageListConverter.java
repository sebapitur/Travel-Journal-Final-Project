package com.sebastianpitur.traveljournal;

import android.net.Uri;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class ImageListConverter {
    @TypeConverter
    public static List<Image> fromString(String value) {
        // Implement the conversion logic from String to List<Uri>
        // Example implementation: Convert comma-separated URIs to a List<Uri>
        List<Image> images = new ArrayList<>();
        String[] uris = value.split(",");
        for (String uriString : uris) {
            images.add(new Image(Uri.parse(uriString)));
        }
        return images;
    }

    @TypeConverter
    public static String toString(List<Image> imageList) {
        // Implement the conversion logic from List<Uri> to String
        // Example implementation: Convert List<Uri> to a comma-separated String
        StringBuilder sb = new StringBuilder();
        for (Image uri : imageList) {
            sb.append(uri.getUriAddress().toString());
            sb.append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
