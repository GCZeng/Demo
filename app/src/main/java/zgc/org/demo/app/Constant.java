package zgc.org.demo.app;

import com.dpuntu.downloader.Downloader;
import com.liulishuo.filedownloader.BaseDownloadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: ZGC
 * Time: 2019/10/13 0:55
 * Description:
 */
public class Constant {
    public static Map<String, List<Downloader>> sMapDownloadMap = new HashMap<>();
    public static Map<String, List<BaseDownloadTask>> sMapDownloadMap2 = new HashMap<>();

    public static int task_count = 0;
}
