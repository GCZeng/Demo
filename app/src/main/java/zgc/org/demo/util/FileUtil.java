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
        try {
            InputStream mInputStream = context.getAssets().open(fileName);
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            File mFile = new File(path + File.separator + "app-debug.apk");
            if (!mFile.exists())
                mFile.createNewFile();
            LogUtil.e("开始拷贝");
            FileOutputStream mFileOutputStream = new FileOutputStream(mFile);
            byte[] mbyte = new byte[1024];
            int i = 0;
            while ((i = mInputStream.read(mbyte)) > 0) {
                mFileOutputStream.write(mbyte, 0, i);
            }
            mInputStream.close();
            mFileOutputStream.close();
            Uri uri = null;
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
            return uri;
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e(fileName + "not exists" + "or write err");
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
