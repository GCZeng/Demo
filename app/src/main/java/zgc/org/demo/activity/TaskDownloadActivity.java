package zgc.org.demo.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.dpuntu.downloader.DownloadManager;
import com.dpuntu.downloader.Downloader;
import com.dpuntu.downloader.Observer;

import java.security.DomainCombiner;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zgc.org.demo.R;
import zgc.org.demo.activity.base.BaseActivity;
import zgc.org.demo.adapter.TaskDownloadAdapter;
import zgc.org.demo.app.Constant;
import zgc.org.demo.util.LogUtil;
import zgc.org.demo.util.rx.RxCountDown;
import zgc.org.demo.util.rx.RxTimer;

/**
 * Author: ZGC
 * Time: 2019/10/12 23:27
 * Description: 多线程下载
 */
public class TaskDownloadActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private TaskDownloadAdapter taskDownloadAdapter = null;
    private List<Downloader> taskDownloadList = null;

    private RxTimer rxTimer = null;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_task_download;
    }

    @Override
    protected void initView() {


        recyclerview.setLayoutManager(new LinearLayoutManager(this));

    }

    @OnClick({R.id.btn_add_task})
    void click(View view) {
        switch (view.getId()) {
            case R.id.btn_add_task:
                Downloader downloader = getDownload();
                taskDownloadList.add(downloader);
                taskDownloadAdapter.notifyDataSetChanged();
                DownloadManager.addDownloader(downloader);
                DownloadManager.start(downloader.getTaskId());
                DownloadManager.subjectTask(downloader.getTaskId(), new Observer() {
                    @Override
                    public void onCreate(String s) {
                        LogUtil.d("onCreate:"+s);
                    }

                    @Override
                    public void onReady(String s) {
                        LogUtil.d("onReady:"+s);
                    }

                    @Override
                    public void onLoading(String s, String s1, long l, long l1) {
                        LogUtil.d("onLoading:"+s);
                    }

                    @Override
                    public void onPause(String s, long l, long l1) {
                        LogUtil.d("onPause:"+s);
                    }

                    @Override
                    public void onFinish(String s) {
                        LogUtil.d("onFinish:"+s);
                    }

                    @Override
                    public void onError(String s, String s1, long l, long l1) {
                        LogUtil.d("onError:"+s);
                    }
                });
                break;
        }
    }

    @Override
    public void initData() {

        taskDownloadList = new ArrayList<>();

//        for (int i = 0; i < 10; i++) {
//            taskDownloadList.add(getDownload());
//        }

        if (Constant.sMapDownloadMap.containsKey("id")) {
            taskDownloadList.addAll(Constant.sMapDownloadMap.get("id"));
        }

        taskDownloadAdapter = new TaskDownloadAdapter(taskDownloadList);
        recyclerview.setAdapter(taskDownloadAdapter);


        //添加到下载队列
        for (Downloader downloader : taskDownloadList) {
            DownloadManager.addDownloader(downloader);
            DownloadManager.start(downloader.getTaskId());
            //监听
            DownloadManager.subjectTask(downloader.getTaskId(), new Observer() {
                @Override
                public void onCreate(String s) {
                    LogUtil.d("onCreate");
                }

                @Override
                public void onReady(String s) {
                    LogUtil.d("onReady");
                }

                @Override
                public void onLoading(String s, String s1, long l, long l1) {
                    LogUtil.d("onLoading:"+s);
                }

                @Override
                public void onPause(String s, long l, long l1) {
                    LogUtil.d("onPause");
                }

                @Override
                public void onFinish(String s) {
                    LogUtil.d("onFinish");
                }

                @Override
                public void onError(String s, String s1, long l, long l1) {
                    LogUtil.d("onError:"+s1);
                }
            });
        }
        //开始下载全部任务
//        DownloadManager.startAll();

        Constant.sMapDownloadMap.put("id", taskDownloadList);

        rxTimer = new RxTimer();
        rxTimer.timer(1000, number -> taskDownloadAdapter.notifyDataSetChanged());

    }

    private Downloader getDownload() {
        return new Downloader.Builder()
//                .client(client) //这就是第二部配置的OkHttpClient，你也可以不配置，下载器内部有个默认的OkHttpClient
                .fileName(System.currentTimeMillis() + ".mp4")  // 这是你下载的文件需要存储的磁盘上的名字，必须设置项
//                .filePath("xxx") // 设置文件存储的路径，可省略，默认为根目录下 Android/data/你的app applicationId/files
//                .loadedSize(mLoadedSize)  // 这是下砸文件已经下载的大小，可以不设置，默认是0，如果是断点的话就必须设置，否则无法断点
//                .totalSize(mTotalSize) // 这是下载文件的文件总大小，可以不设置，默认是0，如果是断点的话就必须设置，否则无法断点
                .taskId(System.currentTimeMillis() + "_" + Constant.task_count++) //下载文件的任务对应的id，用于标识单一任务，不可重复，必须设置
                .url("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4") //下载文件的下载地址，必须配置
                .build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rxTimer.cancel();
//        for (Downloader downloader : taskDownloadList) {
//            DownloadManager.remove(downloader.getTaskId());
//        }
    }
}
