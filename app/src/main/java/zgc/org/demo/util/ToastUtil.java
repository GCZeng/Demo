package zgc.org.demo.util;

import android.widget.Toast;

import zgc.org.demo.app.APP;


/**
 * Toast Util
 * Created by FHW
 * on 2017/11/04
 */
public class ToastUtil {
    private ToastUtil() {
    }

    public static void showShort( CharSequence message) {
        Toast.makeText(APP.sContext, message, Toast.LENGTH_SHORT).show();
    }

    public static void showShort( int message) {
        Toast.makeText(APP.sContext, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong( CharSequence message) {
        Toast.makeText(APP.sContext, message, Toast.LENGTH_LONG).show();
    }

    public static void showLong( int message) {
        Toast.makeText(APP.sContext, message, Toast.LENGTH_LONG).show();
    }

    public static void show( CharSequence message, int duration) {
        Toast.makeText(APP.sContext, message, duration).show();
    }

    public static void show( int message, int duration) {
        Toast.makeText(APP.sContext, message, duration).show();
    }


}
