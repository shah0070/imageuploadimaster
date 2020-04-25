package com.devtides.androidarchitectures.di;

import android.content.Context;

import androidx.room.Room;

import com.devtides.androidarchitectures.RoomDB.dao.MediaUploadDetailsDao;
import com.devtides.androidarchitectures.RoomDB.room.ImageViewDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kotlin.jvm.JvmStatic;

@Module
public class ApiModule {
    private Context context;

    public ApiModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return context;
    }

    @JvmStatic
    @Singleton
    @Provides
    public ImageViewDatabase provideImageViewDatabase(Context context) {
        return Room.databaseBuilder(context, ImageViewDatabase.class, "ImageViewDatabase")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    public MediaUploadDetailsDao providedatabase(ImageViewDatabase imageViewDatabase) {
        return imageViewDatabase.getMediaUploadDetailsDao();
    }
}
