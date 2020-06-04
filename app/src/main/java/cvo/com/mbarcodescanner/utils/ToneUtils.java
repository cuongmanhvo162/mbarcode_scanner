package cvo.com.mbarcodescanner.utils;

import android.media.AudioManager;
import android.media.ToneGenerator;

public class ToneUtils {
    public static void toning() {
        ToneGenerator toneNotification =
                new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
        toneNotification.startTone(ToneGenerator.TONE_PROP_BEEP, 300);
    }
}
