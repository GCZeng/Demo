package zgc.org.demo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.BindView;
import zgc.org.demo.R;
import zgc.org.demo.activity.base.BaseActivity;

/**
 * Author:Nick
 * Time2018/7/25 14:44
 * Description
 */
public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.iv_welcome)
    ImageView iv_welcome;

    private Animation welcome_alpha;//动画

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        welcome_alpha = new AnimationUtils().loadAnimation(this, R.anim.welcome_alpha);
    }

    @Override
    public void initData() {
        showDefault();
    }

    private void showDefault() {
        welcome_alpha.setFillEnabled(true); //启动Fill保持
        welcome_alpha.setFillAfter(true);  //设置动画的最后一帧是保持在View上面
        iv_welcome.setAnimation(welcome_alpha);
        welcome_alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handler.sendEmptyMessageDelayed(1, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });  //为动画设置监听
    }


    private Handler handler = new Handler(msg -> {
        gotoActivity(MainActivity.class);
        finish();
        return false;
    });


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

}
