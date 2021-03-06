package zgc.org.demo.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.security.Key;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import zgc.org.demo.R;
import zgc.org.demo.activity.base.BaseActivity;
import zgc.org.demo.adapter.MenuAdapter;
import zgc.org.demo.adapter.decoration.CustomItemDecoration;

public class MainActivity extends BaseActivity {
    @BindView(R.id.rv_menu)
    RecyclerView rvMenu;

    private String[] titles = null;
    private List<String> mMenuList = null;
    private MenuAdapter mMenuAdapter = null;

    private Bundle mBundle = new Bundle();

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
        mMenuAdapter.setOnItemClickListener((adapter, view, position) -> {
            mBundle.clear();
            mBundle.putString("title", mMenuList.get(position));
            switch (position) {
                case 0:
                    gotoActivity(TabLayoutActivity.class, mBundle);
                    break;
                case 1:
                    gotoActivity(GsonTransformActivity.class, mBundle);
                    break;
                case 2:
                    gotoActivity(NotificationTipActivity.class, mBundle);
                    break;
                case 3:
                    gotoActivity(SudokuHelperActivity.class, mBundle);
                    break;
                case 4:
                    gotoActivity(AndroidAPKInstallActivity.class, mBundle);
                    break;
                case 5:
                    gotoActivity(KeyboardSwitchActivity.class, mBundle);
                    break;
                case 6:
                    gotoActivity(RxBusActivity.class, mBundle);
                    break;
                case 7:
                    gotoActivity(LHDisplayActivity.class, mBundle);
                    break;
                case 8:
                    gotoActivity(PermissionRequestActivity.class, mBundle);
                    break;
                case 9:
                    gotoActivity(WaveActivity.class, mBundle);
                    break;
                case 10:
                    gotoActivity(TagActivity.class, mBundle);
                    break;
                case 11:
                    gotoActivity(TaskDownloadActivity.class, mBundle);
                    break;
                case 12:
                    gotoActivity(TaskDownload2Activity.class, mBundle);
                    break;
            }
        });

        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        rvMenu.addItemDecoration(new CustomItemDecoration());
        rvMenu.setAdapter(mMenuAdapter);
    }

}
