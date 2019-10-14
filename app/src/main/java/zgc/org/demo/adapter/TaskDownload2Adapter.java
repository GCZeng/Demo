package zgc.org.demo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dpuntu.downloader.Downloader;
import com.liulishuo.filedownloader.BaseDownloadTask;

import java.util.List;

import zgc.org.demo.R;

/**
 * Author: ZGC
 * Time: 2019/10/12 23:30
 * Description:
 */
public class TaskDownload2Adapter extends BaseQuickAdapter<BaseDownloadTask, BaseViewHolder> {
    public TaskDownload2Adapter(@Nullable List<BaseDownloadTask> data) {
        super(R.layout.rv_task_download2_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseDownloadTask item) {
        helper.setText(R.id.tv_name, item.getTag().toString());
        if (item.getSmallFileTotalBytes() > 0) {
            helper.setProgress(R.id.pb_persent, (int) (item.getSmallFileSoFarBytes() * 100 / item.getSmallFileTotalBytes()));
        }
    }
}
