package zgc.org.demo.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.liulishuo.okdownload.DownloadContext;
import com.liulishuo.okdownload.DownloadContextListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zgc.org.demo.R;
import zgc.org.demo.activity.base.BaseActivity;
import zgc.org.demo.adapter.TaskDownload2Adapter;
import zgc.org.demo.app.Constant;
import zgc.org.demo.bean.DownloadBean;
import zgc.org.demo.util.LogUtil;

/**
 * Author: ZGC
 * Time: 2019/10/12 23:27
 * Description: 多线程下载
 */
public class TaskDownload2Activity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_add_task)
    Button btn_add_task;

    private boolean isPause = true;
    private DownloadContext context = null;

    public static final String[] URL = {
            "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
            "http://14.29.54.146:48010/videoPresent/1571033624951.mp4",
            "http://14.29.54.146:48010/videoPresent/1571033638060.mp4",
            "http://14.29.54.146:48010/videoPresent/1571033665553.mp4"
    };

    private TaskDownload2Adapter taskDownloadAdapter = null;
    private List<DownloadBean> taskDownloadList = null;

//    private RxTimer rxTimer = null;

    //    private FileDownloadQueueSet queueSet = null;


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_task_download2;
    }

    @Override
    protected void initView() {
        deleteFile();
    }

    @OnClick({R.id.btn_add_task})
    void click(View view) {
        switch (view.getId()) {
            case R.id.btn_add_task:
                if (isPause) {
                    context.startOnParallel(downloadListener1);
                    btn_add_task.setText("暂停");
                } else {
//                    FileDownloader.getImpl().pauseAll();
                    context.stop();
                    btn_add_task.setText("开始下载");
                }
                isPause = !isPause;
                break;
        }
    }

    DownloadListener1 downloadListener1 = new DownloadListener1() {
        @Override
        public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist.Listener1Model model) {
            LogUtil.d("taskStart" + task.getTag());
        }

        @Override
        public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {
            LogUtil.d("retry" + task.getTag());
        }

        @Override
        public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {
            LogUtil.d("connected" + task.getTag());
        }

        @Override
        public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
            LogUtil.d("progress" + task.getTag());
//                            if (task.getInfo() != null) {
//                                LogUtil.d((task.getInfo().getTotalOffset() * 100 / task.getInfo().getTotalLength()) + "");
//                                LogUtil.d(
//                                        task.getInfo().getTotalOffset() + "_" + task.getInfo().getTotalLength() + "\n" +
//                                                currentOffset + "_" + totalLength);
//                            }
//                            LogUtil.d(task.getTag()+":"+task.hashCode());
            if (Constant.mTaskMap.containsKey(task.getId())) {
                Constant.mTaskMap.get(task.getId()).setCurrentOffset(currentOffset);
                Constant.mTaskMap.get(task.getId()).setTotalLength(totalLength);
            }
            taskDownloadAdapter.notifyDataSetChanged();
        }

        @Override
        public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull Listener1Assist.Listener1Model model) {
            LogUtil.d("taskEnd" + task.getTag());
            if (task.getInfo() != null) {
                LogUtil.d((task.getInfo().getTotalOffset() * 100 / task.getInfo().getTotalLength()) + "");
                LogUtil.d(
                        task.getInfo().getTotalOffset() + "_" + task.getInfo().getTotalLength());
            }
        }

//                        @Override
//                        public void taskStart(@NonNull DownloadTask task) {
//                            LogUtil.d("taskStart:" + task.getTag());
//                        }

//                        @Override
//                        public void connectTrialStart(@NonNull DownloadTask task, @NonNull Map<String, List<String>> requestHeaderFields) {
//                            LogUtil.d("connectTrialStart" + task.getTag());
//                        }
//
//                        @Override
//                        public void connectTrialEnd(@NonNull DownloadTask task, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {
//                            LogUtil.d("connectTrialEnd" + task.getTag());
//                        }
//
//                        @Override
//                        public void downloadFromBeginning(@NonNull DownloadTask task, @NonNull BreakpointInfo info, @NonNull ResumeFailedCause cause) {
//                            LogUtil.d("downloadFromBeginning" + task.getTag());
//                        }
//
//                        @Override
//                        public void downloadFromBreakpoint(@NonNull DownloadTask task, @NonNull BreakpointInfo info) {
//                            LogUtil.d("downloadFromBreakpoint" + task.getTag());
//                        }
//
//                        @Override
//                        public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {
//                            LogUtil.d("connectStart" + task.getTag());
//                        }
//
//                        @Override
//                        public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {
//                            LogUtil.d("connectEnd" + task.getTag());
//                        }
//
//                        @Override
//                        public void fetchStart(@NonNull DownloadTask task, int blockIndex, long contentLength) {
//                            LogUtil.d("fetchStart" + task.getTag());
//                        }
//
//                        @Override
//                        public void fetchProgress(@NonNull DownloadTask task, int blockIndex, long increaseBytes) {
//                            LogUtil.d("fetchProgress" + task.getTag());
//                        }
//
//                        @Override
//                        public void fetchEnd(@NonNull DownloadTask task, int blockIndex, long contentLength) {
//                            LogUtil.d("fetchEnd" + task.getTag());
//                        }
//
//                        @Override
//                        public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause) {
//                            LogUtil.d("taskEnd:" + task.getTag());
//                        }
    };

    @Override
    public void initData() {

        taskDownloadList = new ArrayList<>();

        if (Constant.sMapDownloadMap2.containsKey("id")) {
            taskDownloadList.addAll(Constant.sMapDownloadMap2.get("id"));
        }

        for (DownloadBean downloadBean : taskDownloadList) {
            Constant.mTaskMap.put(downloadBean.getDownloadTask().getId(), downloadBean);
        }

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        taskDownloadAdapter = new TaskDownload2Adapter(taskDownloadList);
        recyclerview.setAdapter(taskDownloadAdapter);

        Constant.sMapDownloadMap2.put("id", taskDownloadList);

//        rxTimer = new RxTimer();
//        rxTimer.timer(1000, number -> taskDownloadAdapter.notifyDataSetChanged());

        initDownload();
    }

    private void initDownload() {
        DownloadContext.Builder builder = new DownloadContext.QueueSet()
                .setParentPathFile(getFilePath())
                .setMinIntervalMillisCallbackProcess(150)
                .commit();
        for (int i = 0; i < URL.length; i++) {
            builder.bind(URL[i]).setTag(System.currentTimeMillis() + ".mp4");
        }
        builder.setListener(new DownloadContextListener() {
            @Override
            public void taskEnd(@NonNull DownloadContext context, @NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, int remainCount) {

            }

            @Override
            public void queueEnd(@NonNull DownloadContext context) {

            }
        });

        context = builder.build();

        if (taskDownloadList.size() == 0) {
            for (DownloadTask downloadTask : context.getTasks()) {
                DownloadBean downloadBean = new DownloadBean(downloadTask);
                taskDownloadList.add(downloadBean);
                Constant.mTaskMap.put(downloadTask.getId(), downloadBean);
            }
            taskDownloadAdapter.notifyDataSetChanged();
        }


//        context.stop();

//        FileDownloadListener queueTarget = new FileDownloadListener() {
//            @Override
//            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                LogUtil.d("pending:" + task.getTag());
//            }
//
//            @Override
//            protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
//                LogUtil.d("connected:" + task.getTag());
//            }
//
//            @Override
//            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                LogUtil.d("progress:" + task.getTag());
//            }
//
//            @Override
//            protected void blockComplete(BaseDownloadTask task) {
//                LogUtil.d("blockComplete:" + task.getFilename());
//            }
//
//            @Override
//            protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
//                LogUtil.d("retry:" + task.getTag());
//            }
//
//            @Override
//            protected void completed(BaseDownloadTask task) {
//                LogUtil.d("completed:" + task.getTag());
//            }
//
//            @Override
//            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                LogUtil.d("paused:" + task.getTag());
//            }
//
//            @Override
//            protected void error(BaseDownloadTask task, Throwable e) {
//                LogUtil.d("error:" + task.getTag());
//            }
//
//            @Override
//            protected void warn(BaseDownloadTask task) {
//                LogUtil.d("warn:" + task.getTag());
//            }
//        };
//
//        queueSet = new FileDownloadQueueSet(queueTarget);
//
//        if (taskDownloadList.size() > 0) {
//            queueSet.downloadTogether(taskDownloadList);
//        } else {
//            for (int i = 0; i < URL.length; i++) {
//                taskDownloadList.add(
//                        FileDownloader.getImpl()
//                                .create(URL[i])
//                                .setPath(getFilePath(), true)
//                                .setTag(System.currentTimeMillis() + ".mp4")
//                );
//            }
//
//            queueSet.disableCallbackProgressTimes(); // 由于是队列任务, 这里是我们假设了现在不需要每个任务都回调`FileDownloadListener#progress`, 我们只关系每个任务是否完成, 所以这里这样设置可以很有效的减少ipc.
//
//            // 所有任务在下载失败的时候都自动重试一次
//            queueSet.setAutoRetryTimes(1);
//
//            queueSet.downloadTogether(taskDownloadList);
//            // 如果你的任务不是一个List，可以考虑使用下面的方式，可读性更强
////    queueSet.downloadTogether(
////            FileDownloader.getImpl().create(url).setPath(...),
////            FileDownloader.getImpl().create(url).setPath(...),
////            FileDownloader.getImpl().create(url).setSyncCallback(true)
////    );
//
//            // 最后你需要主动调用start方法来启动该Queue
////            queueSet.start();
//        }
    }

    private File getFilePath() {
        File file = new File(getExternalCacheDir().getPath());
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private boolean deleteFile() {
        File file = new File(getExternalCacheDir().getPath());
        if (!file.exists()) return false;
        if (file.isFile()) {
            file.delete();
            return true;
        }
        File[] files = file.listFiles();
        if (files == null || files.length == 0) return false;
        for (File f : files) {
            if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            } else {
                f.delete();
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        rxTimer.cancel();
    }
}
