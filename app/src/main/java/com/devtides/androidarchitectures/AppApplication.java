package com.devtides.androidarchitectures;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.camera.camera2.Camera2Config;
import androidx.camera.core.CameraXConfig;
import androidx.room.Room;

import com.blankj.utilcode.util.Utils;
import com.devtides.androidarchitectures.RoomDB.room.ImageViewDatabase;

public class AppApplication extends Application implements  CameraXConfig.Provider{
    private static AppApplication sInstance;
    private static ImageViewDatabase imageViewDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
        Utils.init(getApplicationContext());
        getImageViewDatabase();
    }
    public static synchronized AppApplication getInstance() {
        return sInstance;
    }
    public static ImageViewDatabase getImageViewDatabase() {
        if(imageViewDatabase==null){
            imageViewDatabase = Room.databaseBuilder(getInstance(), ImageViewDatabase.class, "ImageViewDatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return imageViewDatabase;
    }

    @NonNull
    @Override
    public CameraXConfig getCameraXConfig() {
        return Camera2Config.defaultConfig();
    }
}
