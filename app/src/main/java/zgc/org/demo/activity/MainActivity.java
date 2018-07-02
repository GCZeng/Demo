package zgc.org.demo.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import zgc.org.demo.R;
import zgc.org.demo.activity.base.BaseActivity;
import zgc.org.demo.adapter.MenuAdapter;

public class MainActivity extends BaseActivity {
    @BindView(R.id.rv_menu)
    RecyclerView rvMenu;

    private String[] titles = null;
    private List<String> mMenuList = null;
    private MenuAdapter mMenuAdapter = null;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void initData() {
        titles = getResources().getStringArray(R.array.menu);
        mMenuList = Arrays.asList(titles);
        mMenuAdapter = new MenuAdapter(mMenuList);
        mMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        gotoActivity(TabLayoutActivity.class);
                        break;
                }
            }
        });

        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        rvMenu.setAdapter(mMenuAdapter);
    }

}
