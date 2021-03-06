package com.devtides.androidarchitectures.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.devtides.androidarchitectures.AppApplication;
import com.devtides.androidarchitectures.RoomDB.entities.MediaUploadDetailsTable;
import com.devtides.androidarchitectures.repository.DatabaseRepository;
import com.devtides.androidarchitectures.uploademanager.MatchUploadWorkManager;
import com.devtides.androidarchitectures.wrapperclass.ImageViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Imageview_viewmodel extends ViewModel {

    private final MutableLiveData<List<ImageViewModel>> imagelist = new MutableLiveData<>();
    @Inject
    DatabaseRepository databaseRepo;

    public Imageview_viewmodel() {
        AppApplication.getInstance().appComponent().inject(this);
    }


    public LiveData<List<ImageViewModel>> getImageViewList() {
        return imagelist;
    }

    private void fetchImages() {

        databaseRepo.getimageListdata()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<MediaUploadDetailsTable>>() {
                    @Override
                    public void onSuccess(List<MediaUploadDetailsTable> value) {
                        List<ImageViewModel> countryNames = new ArrayList<>();
                        for (MediaUploadDetailsTable imagelist : value) {
                            ImageViewModel data = new ImageViewModel();
                            data.setMediaFileName(imagelist.getMediaFileName());
                            data.setIsUploaded(imagelist.getIsUploaded());
                            data.setMediaFileUri(imagelist.getMediaFileUri());
                            data.setProcessUid(imagelist.getProcessUid());
                            data.setTotalbytes(imagelist.getTotalbytes());
                            data.setUploadedbytes(imagelist.getUploadedbytes());
                            countryNames.add(data);
                            Log.e("sasassa", imagelist.getMediaFileUri() + "---");

                        }
                        imagelist.setValue(countryNames);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    public void onRefresh() {
        fetchImages();
    }

    public void addtaskinbd(File path) {
        MediaUploadDetailsTable data = new MediaUploadDetailsTable();
        data.setIsUploaded(0);
        data.setMediaFileName(path.getName());
        data.setMediaFileUri(path.getAbsolutePath());
        data.setProcessUid(getProcessid());
        data.setTotalbytes(path.getTotalSpace());
        data.setUploadedbytes(0);

        databaseRepo.insert(data);
        onRefresh();
        startupload();
    }

    public String getProcessid() {
        return String.valueOf(Calendar.getInstance().getTimeInMillis());
    }

    public void startupload() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresDeviceIdle(false)
                .setRequiredNetworkType(NetworkType.CONNECTED).build();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MatchUploadWorkManager.class)
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance().enqueue(oneTimeWorkRequest);
    }

}
