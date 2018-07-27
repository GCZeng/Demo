package zgc.org.demo.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.internal.Utils;

/**
 * Author:Nick
 * Time2018/7/19 15:22
 * Description
 */
public class FileUtil {
    /**
     * 复制文件到SD卡
     *
     * @param context
     * @param fileName 复制的文件名
     * @param path     保存的目录路径
     * @return
     */
    public static Uri copyAssetsFile(Context context, String fileName, String path) {
        InputStream mInputStream = null;
        FileOutputStream mFileOutputStream = null;
        Uri uri = null;
        try {
            mInputStream = context.getAssets().open(fileName);
            File file = new File(path);
            if (!file.exists()) {
                try {
                    file.mkdir();
                } catch (SecurityException e) {
                    LogUtil.e(e.getMessage());
                } catch (NullPointerException e) {
                    LogUtil.e(e.getMessage());
                }
            }
            File mFile = new File(path + File.separator + "app-debug.apk");
            if (!mFile.exists()) {
                try {
                    mFile.createNewFile();
                } catch (SecurityException e) {
                    LogUtil.d(e.getMessage());
                } catch (NullPointerException e) {
                    LogUtil.e(e.getMessage());
                }
            }
            LogUtil.e("开始拷贝");
            mFileOutputStream = new FileOutputStream(mFile);
            byte[] mbyte = new byte[1024];
            int i = 0;
            while ((i = mInputStream.read(mbyte)) > 0) {
                mFileOutputStream.write(mbyte, 0, i);
            }

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //包名.fileprovider
                    uri = FileProvider.getUriForFile(context, "zgc.org.demo.fileprovider", mFile);
                } else {
                    uri = Uri.fromFile(mFile);
                }
            } catch (ActivityNotFoundException anfe) {
                LogUtil.e(anfe.getMessage());
            }
            MediaScannerConnection.scanFile(context, new String[]{mFile.getAbsolutePath()}, null, null);
            LogUtil.e("拷贝完毕：" + uri);
        } catch (IOException e) {
            LogUtil.e(e.getMessage());
            LogUtil.e(fileName + "not exists" + "or write err");
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        } finally {
            if (mInputStream != null) {
                try {
                    mInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (mFileOutputStream != null) {
                try {
                    mFileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return uri;
    }
}
