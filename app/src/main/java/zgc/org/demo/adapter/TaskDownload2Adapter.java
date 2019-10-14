package zgc.org.demo.adapter;

import android.support.annotation.Nullable;
import android.support.v4.os.IResultReceiver;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.JsonNull;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.StatusUtil;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;

import java.util.List;

import zgc.org.demo.R;
import zgc.org.demo.bean.DownloadBean;
import zgc.org.demo.util.LogUtil;

/**
 * Author: ZGC
 * Time: 2019/10/12 23:30
 * Description:
 */
public class TaskDownload2Adapter extends BaseQuickAdapter<DownloadBean, BaseViewHolder> {
    public TaskDownload2Adapter(@Nullable List<DownloadBean> data) {
        super(R.layout.rv_task_download2_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DownloadBean item) {
        helper.setText(R.id.tv_name, item.getDownloadTask().getTag().toString());
        helper.setProgress(R.id.pb_persent, (int) (item.getCurrentOffset() * 100 / item.getTotalLength()));
    }
}
