package cvo.com.mbarcodescanner.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import cvo.com.mbarcodescanner.R;

public class BarcodeResultView  extends LinearLayout {
    private Context mContext;
    private View mViewContainer;
    private TextView mBarcodeResultData;

    public BarcodeResultView(Context context) {
        super(context);
        init(context);
    }

    public BarcodeResultView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BarcodeResultView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public BarcodeResultView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    protected void init(Context context){
        this.mContext = context;
        mViewContainer = inflate(mContext, R.layout.view_barcode_result, this);

        mBarcodeResultData = (TextView)mViewContainer.findViewById(R.id.view_barcode_result_data);
    }

    public View getViewContainer() {
        return this.mViewContainer;
    }

    public void hideBarResultView() {
        mViewContainer.setVisibility(View.GONE);
    }

    public void showBarResultView() {
        mViewContainer.setVisibility(View.VISIBLE);
    }

    public void displayBarcodeData(String data) {
        showBarResultView();
        if(TextUtils.isEmpty(data)) {
            mBarcodeResultData.setText(R.string.barcode_is_empty);
        } else {
            mBarcodeResultData.setText(data);
        }
    }

    public void displayNoDataMessage() {
        showBarResultView();
        mBarcodeResultData.setText(R.string.barcode_is_empty);
    }
}
