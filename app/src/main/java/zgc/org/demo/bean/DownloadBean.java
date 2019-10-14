package zgc.org.demo.bean;

import com.liulishuo.okdownload.DownloadTask;

/**
 * Author: ZGC
 * Time: 2019/10/14 21:02
 * Description:
 */
public class DownloadBean {
    private DownloadTask downloadTask;
    private long currentOffset = 0;
    private long totalLength = 1;

    public DownloadBean(DownloadTask downloadTask) {
        this.downloadTask = downloadTask;
    }

    public DownloadTask getDownloadTask() {
        return downloadTask;
    }

    public void setDownloadTask(DownloadTask downloadTask) {
        this.downloadTask = downloadTask;
    }

    public long getCurrentOffset() {
        return currentOffset;
    }

    public void setCurrentOffset(long currentOffset) {
        this.currentOffset = currentOffset;
    }

    public long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }
}
