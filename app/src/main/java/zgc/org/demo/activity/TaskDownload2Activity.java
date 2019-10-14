package zgc.org.demo.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.dpuntu.downloader.DownloadManager;
import com.dpuntu.downloader.Downloader;
import com.dpuntu.downloader.Observer;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zgc.org.demo.R;
import zgc.org.demo.activity.base.BaseActivity;
import zgc.org.demo.adapter.TaskDownload2Adapter;
import zgc.org.demo.adapter.TaskDownloadAdapter;
import zgc.org.demo.app.Constant;
import zgc.org.demo.util.LogUtil;
import zgc.org.demo.util.rx.RxTimer;

/**
 * Author: ZGC
 * Time: 2019/10/12 23:27
 * Description: 多线程下载
 */
public class TaskDownload2Activity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    public static final String[] URL = {

    };

    private TaskDownload2Adapter taskDownloadAdapter = null;
    private List<BaseDownloadTask> taskDownloadList = null;

    private RxTimer rxTimer = null;

    private FileDownloadQueueSet queueSet = null;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_task_download2;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void initData() {

        taskDownloadList = new ArrayList<>();

        if (Constant.sMapDownloadMap2.containsKey("id")) {
            taskDownloadList.addAll(Constant.sMapDownloadMap2.get("id"));
        }

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        taskDownloadAdapter = new TaskDownload2Adapter(taskDownloadList);
        recyclerview.setAdapter(taskDownloadAdapter);

        Constant.sMapDownloadMap2.put("id", taskDownloadList);

        rxTimer = new RxTimer();
        rxTimer.timer(1000, number -> taskDownloadAdapter.notifyDataSetChanged());

        initDownload();
    }

    private void initDownload() {
        if (taskDownloadList.size() > 0) {

        } else {
            FileDownloadListener queueTarget = new FileDownloadListener() {
                @Override
                protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    LogUtil.d("pending:" + task.getTag());
                }

                @Override
                protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                    LogUtil.d("connected:" + task.getTag());
                }

                @Override
                protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    LogUtil.d("progress:" + task.getTag());
                }

                @Override
                protected void blockComplete(BaseDownloadTask task) {
                    LogUtil.d("blockComplete:" + task.getFilename());
                }

                @Override
                protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    LogUtil.d("retry:" + task.getTag());
                }

                @Override
                protected void completed(BaseDownloadTask task) {
                    LogUtil.d("completed:" + task.getTag());
                }

                @Override
                protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    LogUtil.d("paused:" + task.getTag());
                }

                @Override
                protected void error(BaseDownloadTask task, Throwable e) {
                    LogUtil.d("error:" + task.getTag());
                }

                @Override
                protected void warn(BaseDownloadTask task) {
                    LogUtil.d("warn:" + task.getTag());
                }
            };

            queueSet = new FileDownloadQueueSet(queueTarget);

            for (int i = 0; i < URL.length; i++) {
                taskDownloadList.add(
                        FileDownloader.getImpl()
                                .create(URL[i])
                                .setPath(getFilePath(), true)
                                .setTag(System.currentTimeMillis() + ".mp4")
                );
            }

            queueSet.disableCallbackProgressTimes(); // 由于是队列任务, 这里是我们假设了现在不需要每个任务都回调`FileDownloadListener#progress`, 我们只关系每个任务是否完成, 所以这里这样设置可以很有效的减少ipc.

            // 所有任务在下载失败的时候都自动重试一次
            queueSet.setAutoRetryTimes(1);

            queueSet.downloadTogether(taskDownloadList);
            // 如果你的任务不是一个List，可以考虑使用下面的方式，可读性更强
//    queueSet.downloadTogether(
//            FileDownloader.getImpl().create(url).setPath(...),
//            FileDownloader.getImpl().create(url).setPath(...),
//            FileDownloader.getImpl().create(url).setSyncCallback(true)
//    );

            // 最后你需要主动调用start方法来启动该Queue
            queueSet.start();
        }
    }

    private String getFilePath() {
        File file = new File(getExternalCacheDir().getPath());
        if (!file.exists()) {
            file.mkdirs();
        }
        LogUtil.d(file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rxTimer.cancel();
    }
}
