package com.devtides.androidarchitectures.repository;

import com.devtides.androidarchitectures.RoomDB.dao.MediaUploadDetailsDao;
import com.devtides.androidarchitectures.RoomDB.entities.MediaUploadDetailsTable;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class DatabaseRepository {

    private final MediaUploadDetailsDao mediaUploadDetailsDao;

    @Inject
    public DatabaseRepository(MediaUploadDetailsDao mediaUploadDetailsDao) {
        this.mediaUploadDetailsDao = mediaUploadDetailsDao;
    }

    public Single<List<MediaUploadDetailsTable>> getimageListdata() {
        return mediaUploadDetailsDao.getImageList();
    }

    public void insert(MediaUploadDetailsTable mediaUploadDetailsTable) {
        mediaUploadDetailsDao.insert(mediaUploadDetailsTable);
    }
}
