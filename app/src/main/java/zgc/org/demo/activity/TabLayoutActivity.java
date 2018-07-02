package zgc.org.demo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zgc.org.demo.R;
import zgc.org.demo.activity.base.BaseActivity;
import zgc.org.demo.util.ToastUtil;
import zgc.org.demo.widget.CustomTabLayout;

/**
 * Author:Nick
 * Time2018/7/2 11:43
 * Description
 */
public class TabLayoutActivity extends BaseActivity {
    private String[] mTabTitles = null;
    @BindView(R.id.ctl_menu)
    CustomTabLayout ctlMenu;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_tab_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void initData() {
        mTabTitles = getResources().getStringArray(R.array.tab_layout_menu);


        ctlMenu.getTabLayout().setTabGravity(TabLayout.GRAVITY_CENTER);
        ctlMenu.getTabLayout().setTabMode(TabLayout.MODE_SCROLLABLE);
        ctlMenu.getTabLayout().setTabTextColors(getResources().getColor(R.color.color_808080), getResources().getColor(R.color.color_46C0A7));
        ctlMenu.getTabLayout().setSelectedTabIndicatorColor(getResources().getColor(R.color.color_46C0A7));
        ctlMenu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tvContent.setText(mTabTitles[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (int i = 0; i < mTabTitles.length; i++) {
            ctlMenu.addTab(mTabTitles[i]);
        }

    }

}
