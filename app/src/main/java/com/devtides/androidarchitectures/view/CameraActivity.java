package com.devtides.androidarchitectures.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.devtides.androidarchitectures.R;
import com.hbzhou.open.flowcamera.CustomCameraView;
import com.hbzhou.open.flowcamera.listener.ClickListener;
import com.hbzhou.open.flowcamera.listener.FlowCameraListener;
import com.otaliastudios.cameraview.controls.Hdr;
import com.otaliastudios.cameraview.controls.WhiteBalance;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.Calendar;

import static com.hbzhou.open.flowcamera.CustomCameraView.BUTTON_STATE_ONLY_CAPTURE;

public class CameraActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cameraactivity);


        CustomCameraView flowCamera = findViewById(R.id.customCamera);
        flowCamera.setBindToLifecycle(this);
        flowCamera.setWhiteBalance(WhiteBalance.AUTO);
        flowCamera.setCaptureMode(BUTTON_STATE_ONLY_CAPTURE);
        flowCamera.setHdrEnable(Hdr.ON);
        flowCamera.setRecordVideoMaxTime(10);
        flowCamera.setFlowCameraListener(new FlowCameraListener(){

            @Override
            public void captureSuccess(@NonNull File file) {
            //    Toast.makeText(CameraActivity.this, ""+file.getName(), Toast.LENGTH_SHORT).show();
                UCrop uCrop = UCrop.of(Uri.parse(file.getAbsoluteFile().toURI().toString()), Uri.fromFile(new File(getCacheDir(), Calendar.getInstance().getTimeInMillis()+".jpeg")));
                uCrop.start(CameraActivity.this);

            }

            @Override
            public void recordSuccess(@NonNull File file) {
                Toast.makeText(CameraActivity.this, ""+file.getName(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
                Toast.makeText(CameraActivity.this, ""+message, Toast.LENGTH_SHORT).show();

            }
        });
        flowCamera.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {

            final Uri resultUri = UCrop.getOutput(data);
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK,resultIntent.putExtra("data",new File(resultUri.getPath()).getAbsolutePath()));
            finish();
            Toast.makeText(CameraActivity.this, "" + resultUri, Toast.LENGTH_SHORT).show();
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            Toast.makeText(CameraActivity.this, "" + cropError, Toast.LENGTH_SHORT).show();

        }
    }
}
