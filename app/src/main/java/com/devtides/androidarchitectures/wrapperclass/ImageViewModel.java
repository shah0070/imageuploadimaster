package com.devtides.androidarchitectures.wrapperclass;


import java.io.Serializable;

public class ImageViewModel implements Serializable {
    private int media_id;

    private String bitmap;
    private String processUid;

    private String mediaFileUri;

    private String mediaFileName;

    private int isUploaded;

    private long totalbytes;

    private float uploadedbytes;

    private int snumber;

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
    }

    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
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
