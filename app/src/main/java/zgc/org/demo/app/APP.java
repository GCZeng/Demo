package zgc.org.demo.app;

import android.app.Application;
import android.content.Context;

/**
 * Author:Nick
 * Time2018/7/2 12:15
 * Description
 */
public class APP extends Application {
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
