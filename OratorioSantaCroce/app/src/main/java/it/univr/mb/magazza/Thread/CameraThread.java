package it.univr.mb.magazza.Thread;

import com.google.android.gms.vision.CameraSource;

public class CameraThread extends Thread {

    private CameraSource mCameraSource;

    public CameraThread(CameraSource mCameraSource) {
        this.mCameraSource = mCameraSource;

    }

    @Override
    public void run() {
        mCameraSource.stop();

    }
}
