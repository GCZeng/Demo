package zgc.org.demo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dpuntu.downloader.Downloader;

import java.util.List;

import zgc.org.demo.R;

/**
 * Author: ZGC
 * Time: 2019/10/12 23:30
 * Description:
 */
public class TaskDownloadAdapter extends BaseQuickAdapter<Downloader, BaseViewHolder> {
    public TaskDownloadAdapter(@Nullable List<Downloader> data) {
        super(R.layout.rv_task_download_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Downloader item) {
        helper.setText(R.id.tv_name, item.getTaskId());
        if(item.getTotalSize()>0) {
            helper.setProgress(R.id.pb_persent, (int) (item.getLoadedSize() * 100 / item.getTotalSize()));
        }
    }
}
