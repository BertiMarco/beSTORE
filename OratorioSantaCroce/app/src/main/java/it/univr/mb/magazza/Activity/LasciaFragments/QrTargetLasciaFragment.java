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
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import it.univr.mb.magazza.Activity.LasciaActivity;
import it.univr.mb.magazza.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QrTargetLasciaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QrTargetLasciaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "QrTargetLasciaFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private BarcodeDetector mBarcodeDetector;
    private CameraSource mCameraSource;
    private SurfaceView mCameraView;
    private View mScannerLayout;
    private View mScannerBar;
    private ObjectAnimator mAnimator;


    public QrTargetLasciaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QrTargetLasciaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QrTargetLasciaFragment newInstance(String param1, String param2) {
        QrTargetLasciaFragment fragment = new QrTargetLasciaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        return inflater.inflate(R.layout.fragment_qr_target_lascia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCameraView = view.findViewById(R.id.camera_view);
        mScannerLayout = view.findViewById(R.id.scannerLayout);
        mScannerBar = view.findViewById(R.id.scannerBar);

        mCameraView.getHolder().addCallback(new QrTargetLasciaFragment.MySurfaceHolder());
        QrTargetLasciaFragment.MyDetector detector = new QrTargetLasciaFragment.MyDetector(this);
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

        QrTargetLasciaFragment owner;
        String precedentValue = "";

        private MyDetector(QrTargetLasciaFragment owner){
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
                    //ObjectBuilder.getInstance().getItem(b.rawValue, (PrendiActivity)getActivity());
                }
                precedentValue = b.rawValue;
            }
        }
    }
}
