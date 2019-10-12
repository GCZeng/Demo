package zgc.org.demo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import zgc.org.demo.R;

/**
 * Author:Nick
 * Time2018/6/25 17:06
 * Description
 */
public class TagAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public TagAdapter( List<String> data) {
        super(R.layout.rv_tag_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_key, item);
    }
}
