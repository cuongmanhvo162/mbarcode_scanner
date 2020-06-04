package cvo.com.mbarcodescanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cvo.com.mbarcodescanner.utils.IntentUtils;
import cvo.com.mbarcodescanner.utils.LogUtils;
import cvo.com.mbarcodescanner.views.BarcodeResultView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mScan;
    private BarcodeResultView mBarcodeResultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBarcodeResultView = (BarcodeResultView) findViewById(R.id.activity_main_result_data);
        mBarcodeResultView.hideBarResultView();

        mScan = (Button)findViewById(R.id.main_activity_scan);
        mScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.logD("Open barcode scanner screen");
                Intent intent = new Intent(MainActivity.this, BarcodeScannerActivity.class);
                intent.setAction(IntentUtils.ACTION_OPEN_SCANNER);
                startActivityForResult(intent, IntentUtils.REQUEST_OPEN_SCANNER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == IntentUtils.RESULT_BARCODE_SCAN) {
            LogUtils.logD("Received barcode result");

            if(data != null && data.getExtras() != null) {
                LogUtils.logD("Barcode result is existed");
                Bundle bundle = data.getExtras();
                String result = bundle.getString(IntentUtils.BUNDLE_SCAN_RESULT_ID);
                mBarcodeResultView.displayBarcodeData(result);

            } else {
                LogUtils.logD("Barcode result is empty");
                mBarcodeResultView.displayNoDataMessage();
            }
        }
    }
}