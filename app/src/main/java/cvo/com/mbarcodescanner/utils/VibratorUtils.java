package cvo.com.mbarcodescanner.utils;

import android.content.Context;
import android.os.Vibrator;

public class VibratorUtils {
    public static final void vibrate(Context context, short vibrateMilliSeconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(vibrateMilliSeconds);
    }
}
