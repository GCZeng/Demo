package zgc.org.demo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import zgc.org.demo.R;

/**
 * Author:Nick
 * Time2018/7/2 11:36
 * Description
 */
public class MenuAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MenuAdapter(@Nullable List<String> data) {
        super(R.layout.rv_menu_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_title, item);
    }
}
