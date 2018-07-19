package zgc.org.demo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Author:Nick
 * Time2018/7/18 10:26
 * Description
 */
public class PermissionUtil {
    public static Disposable requestEachCombined(Activity activity, Consumer<Permission> consumer, String... permission) {
        return new RxPermissions(activity)
                .requestEachCombined(permission
                ).subscribe(consumer);
    }

    public static void openAppSetting(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(localIntent);
    }
}
