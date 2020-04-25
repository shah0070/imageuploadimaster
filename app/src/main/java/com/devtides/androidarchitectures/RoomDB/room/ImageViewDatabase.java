package com.devtides.androidarchitectures.RoomDB.room;


import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.devtides.androidarchitectures.RoomDB.dao.MediaUploadDetailsDao;
import com.devtides.androidarchitectures.RoomDB.entities.MediaUploadDetailsTable;
import com.devtides.androidarchitectures.RoomDB.typeconverters.IntegerArrayConverter;
import com.devtides.androidarchitectures.RoomDB.typeconverters.TimestampConverter;


@Database(entities = {MediaUploadDetailsTable.class,}, version = 1, exportSchema = false)
@TypeConverters({IntegerArrayConverter.class, TimestampConverter.class})
public abstract class ImageViewDatabase extends RoomDatabase {
    public abstract MediaUploadDetailsDao getMediaUploadDetailsDao();

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
