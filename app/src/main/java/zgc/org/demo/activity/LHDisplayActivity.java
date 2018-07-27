package zgc.org.demo.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.Window;
import android.view.WindowManager;

import butterknife.BindView;
import zgc.org.demo.R;
import zgc.org.demo.activity.base.BaseActivity;

/**
 * Author:Nick
 * Time2018/7/25 17:38
 * Description
 */
public class LHDisplayActivity extends BaseActivity {

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_lh_display;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected int provideStatusBarColor() {
        return android.R.color.holo_green_light;
    }

    @Override
    public void initData() {

    }
}
