package cvo.com.mbarcodescanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import cvo.com.mbarcodescanner.utils.IntentUtils;
import cvo.com.mbarcodescanner.utils.LogUtils;
import cvo.com.mbarcodescanner.utils.ToneUtils;

public class BarcodeScannerActivity extends AppCompatActivity {
    private String mScanResult;

    private SurfaceView mSurfaceScanner;
    private BarcodeDetector mBarcodeDetector;
    private CameraSource mCameraSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);
    }

    @Override
    protected void onResume() {
        super.onResume();

        init();
        setSurfaceListener();
        setBarcodeDetectorProcessor();
    }

    private void init() {
        LogUtils.logD("Initialize views on Barcode scanner");
        mSurfaceScanner = (SurfaceView) findViewById(R.id.activity_barcode_scanner_surface);
        mBarcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();

        try {
            requestCameraPermission();
        } catch (IOException e) {
            LogUtils.logE("Request camera permission got error " + e.getMessage());
        }
    }

    private void initCamera() {
        mCameraSource = new CameraSource.Builder(getApplicationContext(), mBarcodeDetector)
                .setRequestedPreviewSize(1024, 768)
                .setAutoFocusEnabled(true)
                .build();
    }

    private void setBarcodeDetectorProcessor() {
        mBarcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                // Retrieving QR Code
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() > 0) {
                    LogUtils.logD("Barcode data is exist, stop decoding barcode");
                    mBarcodeDetector.release();

                    LogUtils.logD("Play some sound");
                    ToneUtils.toning();

                    LogUtils.logD("Extract string data from Barcode");
                    mScanResult = String.valueOf(barcodes.valueAt(0).displayValue);

                    LogUtils.logD("Delivery extracted data");
                    Intent intent = new Intent();
                    intent.putExtra(IntentUtils.BUNDLE_SCAN_RESULT_ID, mScanResult);
                    setResult(IntentUtils.RESULT_BARCODE_SCAN, intent);

                    LogUtils.logD("Close scanner screen");
                    finish();
                }
            }
        });
    }

    private void setSurfaceListener() {
        mSurfaceScanner.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    LogUtils.logD("Surfaceview on create. Going to check camera permission");
                    requestCameraPermission();
                } catch (IOException e) {
                    LogUtils.logE(e.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                LogUtils.logD("Surfaceview has been destroyed, stop camera source");
                mCameraSource.stop();
            }
        });
    }

    private void requestCameraPermission() throws IOException {
        LogUtils.logD("Request for Camera permission");

        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            LogUtils.logD("Camera permission granted");

            initCamera();
            mCameraSource.start(mSurfaceScanner.getHolder());

        } else {
            LogUtils.logD("Camera permission not granted");
            ActivityCompat.requestPermissions(BarcodeScannerActivity.this,
                    new String[]{Manifest.permission.CAMERA}, 1024);
        }
    }
}
