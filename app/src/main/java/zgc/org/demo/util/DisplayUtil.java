package zgc.org.demo.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Author:Nick
 * Time2018/5/21 9:52
 * Description
 */
public class DisplayUtil {
    public static int sScreenWidth = 0;
    public static int sScreenHeight = 0;

    public static void init(Context context) {
        //2、通过Resources获取
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        sScreenHeight = dm.heightPixels;
        sScreenWidth = dm.widthPixels;
    }

    /**
     * 将px装换成dp，保证尺寸不变
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context, float pxValue) {
        float density = context.getResources().getDisplayMetrics().density;//得到设备的密度
        return (int) (pxValue / density + 0.5f);
    }

    public static int dp2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        float scaleDensity = context.getResources().getDisplayMetrics().scaledDensity;//缩放密度
        return (int) (pxValue / scaleDensity + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        float scaleDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaleDensity + 0.5f);
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        LogUtil.d("height:" + result);
        return px2dp(context, result);
    }

}
