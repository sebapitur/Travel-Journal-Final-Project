package com.sebastianpitur.traveljournal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter {

    private final Context cxt;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        ImageView imageView;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }


    private List<Image> mImages;

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View imageView = inflater.inflate(R.layout.image_element, parent, false);

        // Return a new holder instance
        RecyclerView.ViewHolder viewHolder = new ViewHolder(imageView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        // Get the data model based on position
        Image image = mImages.get(position);
        ImageAdapter.ViewHolder imageHolder = (ImageAdapter.ViewHolder) holder;
        // Set item views based on your views and data model
        ImageView imageView = imageHolder.imageView;
        imageView.setImageURI(image.getUriAddress());
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }


    public ImageAdapter(List<Image> list, Context mContext) {
        this.cxt = mContext;
        this.mImages = list;
    }
}
