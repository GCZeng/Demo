package zgc.org.demo.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Author:Nick
 * Time2018/7/2 11:29
 * Description
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder mUnBinder = null;

//    private CompositeSubscription mCompositeSubscription;

    private CompositeDisposable mCompositeDisposable = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏

        super.onCreate(savedInstanceState);
        setContentView(provideContentViewId());

        mUnBinder = ButterKnife.bind(this);

        if (!TextUtils.isEmpty(getIntent().getStringExtra("title"))) {
            setTitle(getIntent().getStringExtra("title"));
        }

        initView();
        initData();
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


    protected void replaceFragment(@IdRes int layout, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(layout, fragment);
        transaction.commit();
    }

}
