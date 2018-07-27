package zgc.org.demo.app;

import android.app.Application;
import android.content.Context;

import zgc.org.demo.util.LogUtil;

/**
 * Author:Nick
 * Time2018/7/2 12:15
 * Description
 */
public class APP extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        setContext(this.getApplicationContext());

        LogUtil.init();
    }

    public static void setContext(Context context) {
        APP.context = context;
    }

    public static Context getContext() {
        return context;
    }
}
