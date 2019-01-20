package it.univr.mb.magazza.Activity.LasciaFragments;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import it.univr.mb.magazza.Activity.LasciaActivity;
import it.univr.mb.magazza.Activity.PrendiActivity;
import it.univr.mb.magazza.Database.ObjectBuilder;
import it.univr.mb.magazza.R;
import it.univr.mb.magazza.Thread.CameraThread;

/**
 * A simple {@link Fragment} subclass.
 */
public class QrLasciaFragment extends Fragment {

    private static final String TAG = "QrLasciaFragment";
    private BarcodeDetector mBarcodeDetector;
    private CameraSource mCameraSource;
    private SurfaceView mCameraView;
    private View mScannerLayout;
    private View mScannerBar;
    private ObjectAnimator mAnimator;
    private Button mDoneButton;


    public QrLasciaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBarcodeDetector = new BarcodeDetector.Builder(getContext()).setBarcodeFormats(Barcode.QR_CODE).build();
        mCameraSource = new CameraSource.Builder(getContext(), mBarcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .setRequestedFps(15.0f)
                .setAutoFocusEnabled(true)
                .build();
        mAnimator = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qr_lascia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDoneButton = view.findViewById(R.id.done_button);
        mDoneButton.setOnClickListener(v -> onDoneButtonClick());
        mCameraView = view.findViewById(R.id.camera_view);
        mScannerLayout = view.findViewById(R.id.scannerLayout);
        mScannerBar = view.findViewById(R.id.scannerBar);

        mCameraView.getHolder().addCallback(new QrLasciaFragment.MySurfaceHolder());
        QrLasciaFragment.MyDetector detector = new QrLasciaFragment.MyDetector(this);
        mBarcodeDetector.setProcessor(detector);

        ViewTreeObserver vto = mScannerLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mScannerLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                float destination = (mScannerLayout.getY() + mScannerLayout.getHeight());

                mAnimator = ObjectAnimator.ofFloat(mScannerBar, "translationY",
                        mScannerLayout.getY(), destination);

                mAnimator.setRepeatMode(ValueAnimator.REVERSE);
                mAnimator.setRepeatCount(ValueAnimator.INFINITE);
                mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                mAnimator.setDuration(3000);
                mAnimator.start();
            }
        });
    }

    private void onDoneButtonClick() {
        ((LasciaActivity)getActivity()).commitLeave();
    }

    private class MySurfaceHolder implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            try {
                if (((LasciaActivity) getActivity()).checkCameraPermission())
                    mCameraSource.start(surfaceHolder);
                else
                    Toast.makeText(getContext(), "Devi consentire l'accesso alla fotocamera", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                Log.e(TAG, "camera source: " + e.getMessage());
            }

        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            mCameraSource.stop();
        }
    }

    private class MyDetector implements Detector.Processor<Barcode>{

        QrLasciaFragment owner;
        String precedentValue = "";

        private MyDetector(QrLasciaFragment owner){
            this.owner = owner;
        }

        @Override
        public void release() {

        }

        @Override
        public void receiveDetections(Detector.Detections<Barcode> detections) {
            SparseArray barcodes = detections.getDetectedItems();
            if(barcodes.size() != 0) {
                Barcode b =(Barcode) barcodes.valueAt(0);
                if (! b.rawValue.equals(precedentValue)){
                    Log.d(TAG, "Value: " + b.rawValue);
                    Log.d(TAG, "sparse size: " + barcodes.size() + "\n\nvalue: " + barcodes.toString());
                    ObjectBuilder.getInstance().getItemToLeave(b.rawValue.trim(), (LasciaActivity)getActivity());
                    new CameraThread(mCameraSource).start();

                }
                precedentValue = b.rawValue;
            }
        }
    }

    public void startCamera() {
        Log.d(TAG, "Resstarting camera...");
        SurfaceHolder surfaceHolder = mCameraView.getHolder();
        try {
            if(((LasciaActivity)getActivity()).checkCameraPermission())
                mCameraSource.start(surfaceHolder);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Camera started");
    }
}
