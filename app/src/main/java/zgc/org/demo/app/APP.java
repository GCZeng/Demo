package zgc.org.demo.app;

import android.app.Application;
import android.content.Context;

import com.dpuntu.downloader.DownloadManager;

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

        DownloadManager.initDownloader(this);
        DownloadManager.setCorePoolSize(1);

        LogUtil.init();
    }

    public static void setContext(Context context) {
        APP.context = context;
    }

    public static Context getContext() {
        return context;
    }
}
