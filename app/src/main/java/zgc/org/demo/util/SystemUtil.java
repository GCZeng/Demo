package zgc.org.demo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

/**
 * Author:Nick
 * Time2018/7/19 15:23
 * Description
 */
public class SystemUtil {
    public final static int INSTALL_APK_STATE = 2000;

    public static void openApk(Uri uri, Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
//        context.startActivity(intent);
        ((Activity) context).startActivityForResult(intent, INSTALL_APK_STATE);
    }
}
