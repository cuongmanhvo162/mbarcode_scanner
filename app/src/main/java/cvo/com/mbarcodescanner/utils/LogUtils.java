package cvo.com.mbarcodescanner.utils;

import android.util.Log;

import cvo.com.mbarcodescanner.BuildConfig;

public class LogUtils {
    private static final String LOG_DEBUG = "debug_barcode";
    private static final String LOG_ERROR = "error_barcode";

    public static final int LOG_TYPE_DEBUG = 1;
    public static final int LOG_TYPE_ERROR = 2;

    private static void log(int logType, String message) {
        if(BuildConfig.DEBUG) {
            switch (logType){
                case 1:
                    Log.d(LOG_DEBUG, message);
                    break;
                case 2:
                    Log.e(LOG_ERROR, message);
                    break;
            }
        }
    }

    public static void logD(String message) {
        log(LOG_TYPE_DEBUG, message);
    }

    public static void logE(String message) {
        log(LOG_TYPE_ERROR, message);
    }
}
