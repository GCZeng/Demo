package zgc.org.demo.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;

import com.gyf.barlibrary.ImmersionBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import zgc.org.demo.R;

/**
 * Author:Nick
 * Time2018/7/2 11:29
 * Description
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder mUnBinder = null;

    private ImmersionBar mImmersionBar;

//    private CompositeSubscription mCompositeSubscription;

    private CompositeDisposable mCompositeDisposable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏

        super.onCreate(savedInstanceState);
        setContentView(provideContentViewId());

        mUnBinder = ButterKnife.bind(this);

        if (!TextUtils.isEmpty(getIntent().getStringExtra("title"))) {
            setTitle(getIntent().getStringExtra("title"));
        }

        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.transparentStatusBar()
                .statusBarColor(provideStatusBarColor())
                .statusBarDarkFont(provideStatusBarTextStyle())
                .init();   //所有子类都将继承这些相同的属性

        lh();

        initView();
        initData();
    }

    private void lh() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        getWindow().setAttributes(lp);
    }

    /**
     * 提供状态栏颜色
     *
     * @return
     */
    protected int provideStatusBarColor() {
        return android.R.color.white;
    }

    /**
     * 状态栏字体是深色，不写默认为亮色
     *
     * @return
     */
    protected boolean provideStatusBarTextStyle() {
        return true;
    }

    /**
     * 提供布局xml
     *
     * @return
     */
    protected abstract int provideContentViewId();

    /**
     * 初始化UI
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    public void addDisposable(Disposable disposableObserver) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(disposableObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
        if (mImmersionBar != null) {
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        }
    }

    protected void gotoActivity(Class<? extends Activity> activity) {
        gotoActivity(activity, null);
    }

    protected void gotoActivity(Context context, Class<? extends Activity> activity) {
        gotoActivity(context, activity, null);
    }

    protected void gotoActivity(Class<? extends Activity> activity, Bundle bundle) {
        gotoActivity(this, activity, bundle);
    }

    protected void gotoActivity(Context context, Class<? extends Activity> activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    protected void gotoActivityForRequest(int requestCode, Class<? extends Activity> activity) {
        gotoActivityForRequest(requestCode, activity, null);
    }

    protected void gotoActivityForRequest(int requestCode, Class<? extends Activity> activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    protected void replaceFragment(int layout, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(layout, fragment);
        transaction.commit();
    }

}
