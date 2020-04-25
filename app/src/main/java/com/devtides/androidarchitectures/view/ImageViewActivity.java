package com.devtides.androidarchitectures.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devtides.androidarchitectures.R;
import com.devtides.androidarchitectures.viewmodel.ImageViewAdapter;
import com.devtides.androidarchitectures.viewmodel.Imageview_viewmodel;
import com.devtides.androidarchitectures.wrapperclass.ImageViewEventBus;
import com.devtides.androidarchitectures.wrapperclass.ImageViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ImageViewActivity extends AppCompatActivity {


    int REQUEST_CROP = 1011;
    private Imageview_viewmodel viewModel;
    private RecyclerView recyclerView;
    private ImageViewAdapter mAdapter;
    private List<ImageViewModel> imagelist = new ArrayList<ImageViewModel>();

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageview_activity);
        setTitle("Image View");
        viewModel = ViewModelProviders.of(this).get(Imageview_viewmodel.class);

        recyclerView = findViewById(R.id.recycler_view);

        mAdapter = new ImageViewAdapter(ImageViewActivity.this, imagelist);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        viewModel.onRefresh();

        observeViewModel();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(ImageViewEventBus.Refresh obj) {
        if (obj != null) {
            viewModel.onRefresh();
        }
        ImageViewEventBus.Refresh event = EventBus.getDefault().getStickyEvent(ImageViewEventBus.Refresh.class);
        if (event != null) {
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

    private void observeViewModel() {
        viewModel.getImageViewList().observe(this, _imagelist -> {
            imagelist.clear();
            imagelist.addAll(_imagelist);
            mAdapter.notifyDataSetChanged();
        });
    }

    public void onRetry(View view) {
        viewModel.onRefresh();
    }

    public void openCameraActivity(View v) {
        startActivityForResult(new Intent(ImageViewActivity.this, CameraActivity.class), REQUEST_CROP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CROP) {
            Toast.makeText(this, "" + data.getStringExtra("data"), Toast.LENGTH_SHORT).show();
            viewModel.addtaskinbd(new File(data.getStringExtra("data")));
        }
    }
}
