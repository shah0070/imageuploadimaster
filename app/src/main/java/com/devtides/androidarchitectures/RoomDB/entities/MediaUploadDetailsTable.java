package com.devtides.androidarchitectures.RoomDB.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MediaUploadDetailsTable")
public class MediaUploadDetailsTable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "media_id")
    private int media_id;

    @ColumnInfo(name = "processUid")
    private String processUid;

    @ColumnInfo(name = "mediaFileUri")
    private String mediaFileUri;

    @ColumnInfo(name = "mediaFileName")
    private String mediaFileName;

    @ColumnInfo(name = "isUploaded")
    private int isUploaded;

    @ColumnInfo(name = "totalbytes")
    private long totalbytes;

    @ColumnInfo(name = "uploadedbytes")
    private float uploadedbytes;

    @ColumnInfo(name = "snumber")
    private int snumber;

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
    }

    public int getSnumber() {
        return snumber;
    }

    public void setSnumber(int snumber) {
        this.snumber = snumber;
    }

    public long getTotalbytes() {
        return totalbytes;
    }

    public void setTotalbytes(long totalbytes) {
        this.totalbytes = totalbytes;
    }

    public float getUploadedbytes() {
        return uploadedbytes;
    }

    public void setUploadedbytes(float uploadedbytes) {
        this.uploadedbytes = uploadedbytes;
    }

    public String getProcessUid() {
        return processUid;
    }

    public void setProcessUid(String processUid) {
        this.processUid = processUid;
    }

    public String getMediaFileUri() {
        return mediaFileUri;
    }

    public void setMediaFileUri(String mediaFileUri) {
        this.mediaFileUri = mediaFileUri;
    }

    public String getMediaFileName() {
        return mediaFileName;
    }

    public void setMediaFileName(String mediaFileName) {
        this.mediaFileName = mediaFileName;
    }

    public int getIsUploaded() {
        return isUploaded;
    }

    public void setIsUploaded(int isUploaded) {
        this.isUploaded = isUploaded;
    }
}
