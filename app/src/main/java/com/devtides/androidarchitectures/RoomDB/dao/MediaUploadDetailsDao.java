package com.devtides.androidarchitectures.RoomDB.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.devtides.androidarchitectures.RoomDB.entities.MediaUploadDetailsTable;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface MediaUploadDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MediaUploadDetailsTable mediaUploadDetail);

    @Query("SELECT * FROM MediaUploadDetailsTable")
    Single<List<MediaUploadDetailsTable>> getImageList();

    @Update
    int updateMediaDetails(MediaUploadDetailsTable mediaUploadDetailsTable);

    @Query("SELECT * FROM MediaUploadDetailsTable WHERE processUid=:processUid AND isUploaded=:isUploaded")
    List<MediaUploadDetailsTable> findMediaDetailsByProcessIdAndStatus(final String processUid, int isUploaded);

    @Query("SELECT * FROM MediaUploadDetailsTable WHERE  isUploaded=:isUploaded")
    List<MediaUploadDetailsTable> getincompletetasklist( int isUploaded);

    @Query("SELECT * FROM MediaUploadDetailsTable WHERE processUid=:processUid")
    MediaUploadDetailsTable findDataByUniqueMediaName(final String processUid);


    @Query("DELETE FROM MediaUploadDetailsTable where processUid LIKE  :processUid")
    void deleteMediaDetailsAfterUpload(String processUid);

    @Query("SELECT * FROM MediaUploadDetailsTable")
    List<MediaUploadDetailsTable> getAllMediaDetailsDataList();

    @Query("DELETE FROM MediaUploadDetailsTable")
    void deleteMediaDetailsTable();

    @Query("SELECT * FROM MediaUploadDetailsTable WHERE processUid=:processUid AND mediaFileUri=:mediaFileUri")
    List<MediaUploadDetailsTable> findMediaDetailsByProcessIdAndMediaFileUri(final String processUid, String mediaFileUri);

}
