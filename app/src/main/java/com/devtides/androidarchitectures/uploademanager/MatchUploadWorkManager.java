package com.devtides.androidarchitectures.uploademanager;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferService;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.devtides.androidarchitectures.AppApplication;
import com.devtides.androidarchitectures.util.Constants;
import com.devtides.androidarchitectures.wrapperclass.ImageViewEventBus;
import com.devtides.androidarchitectures.RoomDB.entities.MediaUploadDetailsTable;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import static com.devtides.androidarchitectures.util.Apputil.getCredentialsProvider;
import static com.devtides.androidarchitectures.util.Apputil.getS3;
import static com.devtides.androidarchitectures.util.Apputil.getTransferService;

public class MatchUploadWorkManager extends Worker {

    public MatchUploadWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String title = getInputData().getString("data");

        try {
            if (AppApplication.getImageViewDatabase().getMediaUploadDetailsDao().getincompletetasklist(0) != null &&
                    AppApplication.getImageViewDatabase().getMediaUploadDetailsDao().getincompletetasklist(0).size() > 0) {

                MediaUploadDetailsTable mediaUploadDetailsTable = AppApplication.getImageViewDatabase().getMediaUploadDetailsDao().getincompletetasklist(0).get(0);
                uploadImagesUsingS3(mediaUploadDetailsTable);
            }

        }catch (Exception e){}

        return Worker.Result.success();
    }



    private void uploadImagesUsingS3(MediaUploadDetailsTable mediaUploadDetailsTable) {

        AmazonS3 s3;
        TransferUtility transferUtility;
        String imageUri = mediaUploadDetailsTable.getMediaFileUri();

        CognitoCachingCredentialsProvider credentialsProvider = getCredentialsProvider(getApplicationContext());
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSocketTimeout(1000 * 60 * 60);
        clientConfiguration.setMaxErrorRetry(20);

        s3 = getS3(credentialsProvider, clientConfiguration);

        transferUtility = getTransferService(getApplicationContext(), s3);

        File file = new File(Uri.parse(imageUri).getPath());


//https://s3.ap-south-1.amazonaws.com/funngage/1587825619440

        final TransferObserver uploadObserver = transferUtility.upload(
                Constants.BUCKET_NAME,     /* The bucket to upload to */
                mediaUploadDetailsTable.getProcessUid(),    /* The key for the uploaded object */
                file,       /* The file where the data to upload exists */
                CannedAccessControlList.PublicRead    // Making the upload on s3 public
        );

        EventBus.getDefault().post(new ImageViewEventBus.Refresh(mediaUploadDetailsTable.getProcessUid(),0));

        getApplicationContext().startService(new Intent(getApplicationContext(), TransferService.class));

        uploadObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int i, TransferState transferState) {

                if (transferState.equals(TransferState.COMPLETED)) {

                    Log.i("UploadImagess3", "uploadToS3 - Transfer complete " + mediaUploadDetailsTable.getProcessUid());


                    MediaUploadDetailsTable mediaUploadDetailsTable1 = AppApplication
                            .getImageViewDatabase()
                            .getMediaUploadDetailsDao()
                            .findDataByUniqueMediaName(mediaUploadDetailsTable.getProcessUid());

                    if (mediaUploadDetailsTable1 != null) {
                        mediaUploadDetailsTable1.setIsUploaded(1);

                        mediaUploadDetailsTable1.setTotalbytes(bytesTotal);
                        mediaUploadDetailsTable1.setUploadedbytes(uplosdedbytes);
                        AppApplication.getImageViewDatabase().getMediaUploadDetailsDao().updateMediaDetails(mediaUploadDetailsTable1);


                        EventBus.getDefault().post(new ImageViewEventBus.Refresh(mediaUploadDetailsTable.getProcessUid(),1));

                    }
                }
            }
            long bytesTotal;
            float uplosdedbytes;
            @Override
            public void onProgressChanged(int i, long bytesCurrent, long bytesTotal) {
                Log.i("UploadImagess3", " ---------------" + i);
                float uplosdedbytes = ((float) bytesCurrent / (float) bytesTotal) * 100;
                this.bytesTotal=bytesTotal;
                this.uplosdedbytes=uplosdedbytes;

            }

            @Override
            public void onError(int i, Exception e) {
                Log.i("UploadImagess3", "Transfer Failed " + e);
                uploadImagesUsingS3(mediaUploadDetailsTable);
            }

        });
    }
}

