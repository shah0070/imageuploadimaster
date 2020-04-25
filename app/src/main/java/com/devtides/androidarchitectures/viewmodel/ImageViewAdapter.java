package com.devtides.androidarchitectures.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devtides.androidarchitectures.R;
import com.devtides.androidarchitectures.wrapperclass.ImageViewModel;

import java.io.File;
import java.util.List;

public class ImageViewAdapter extends RecyclerView.Adapter<ImageViewAdapter.MyViewHolder> {

    Context context;
    private List<ImageViewModel> imagelist;

    public ImageViewAdapter(Context context, List<ImageViewModel> imagelist) {
        this.imagelist = imagelist;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.imageviewadapter_iteam, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (imagelist.get(position).getMediaFileUri() != null) {
            Log.e("sasassa", imagelist.get(position).getMediaFileUri() + "---");
            Glide.with(context)
                    .load(new File(imagelist.get(position).getMediaFileUri()))
                    .centerCrop()
                    .into(holder.imageView);
        }
        holder.name.setText("Name : " + imagelist.get(position).getMediaFileName());

        if (imagelist.get(position).getIsUploaded() == 1) {
            holder.status.setText("Status : Uploaded");
        } else {
            holder.status.setText("Status : Pending");
        }
    }

    @Override
    public int getItemCount() {
        return imagelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, status;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageview);
            name = view.findViewById(R.id.name);
            status = view.findViewById(R.id.status);
        }
    }
}