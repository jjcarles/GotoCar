package com.jocajica.gotocar.savepoi.ui;

/**
 * Created by jcarles on 14/07/2016.
 */
public interface SavePoiView {
    void onUploadInit();
    void onUploadComplete();
    void onUploadError(String error);
}
