package zgc.org.demo.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.library.flowlayout.FlowLayoutManager;
import com.library.flowlayout.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zgc.org.demo.R;
import zgc.org.demo.activity.base.BaseActivity;
import zgc.org.demo.adapter.TagAdapter;
import zgc.org.demo.util.DisplayUtil;
import zgc.org.demo.widget.tag.FlowLayout;
import zgc.org.demo.widget.tag.FlowLayoutAdapter;

/**
 * Author:Nick
 * Time2018/11/5 14:24
 * Description
 */
public class TagActivity extends BaseActivity {
    @BindView(R.id.rv_tag)
    RecyclerView rvTag;
    @BindView(R.id.fl_tag)
    FlowLayout fl_tag;

    private TagAdapter mTagAdapter = null;
    private List<String> mTagList = null;

    private FlowLayoutAdapter mFlowLayoutAdapter = null;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_tag;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void initData() {
        mTagList = new ArrayList();
        String[] tags = getResources().getStringArray(R.array.tags);

        for (int i = 0; i < tags.length; i++) {
            mTagList.add(tags[i]);
        }
        mTagAdapter = new TagAdapter(mTagList);

        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        rvTag.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dp2px(this, 10)));
        rvTag.setLayoutManager(flowLayoutManager);

        rvTag.setAdapter(mTagAdapter);


        mFlowLayoutAdapter = new FlowLayoutAdapter(this, mTagList);
        fl_tag.setAdapter(mFlowLayoutAdapter);
//        fl_tag.setItemClickListener(new TagCloudLayoutItemOnClick(1));
    }

}
