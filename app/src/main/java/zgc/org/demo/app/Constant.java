package zgc.org.demo.app;

import com.dpuntu.downloader.Downloader;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zgc.org.demo.bean.DownloadBean;

/**
 * Author: ZGC
 * Time: 2019/10/13 0:55
 * Description:
 */
public class Constant {
    public static Map<String, List<Downloader>> sMapDownloadMap = new HashMap<>();
    public static Map<String, List<DownloadBean>> sMapDownloadMap2 = new HashMap<>();

    public static int task_count = 0;

    public static Map<Integer, DownloadBean> mTaskMap = new HashMap<>();
}
