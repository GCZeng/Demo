package zgc.org.demo.activity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import butterknife.OnClick;
import zgc.org.demo.R;
import zgc.org.demo.activity.base.BaseActivity;

/**
 * Author:Nick
 * Time2018/7/10 16:03
 * Description 通知提示
 */
public class NotificationTipActivity extends BaseActivity {
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_notification_tip;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.btn_send_notification})
    void click(View view) {
        switch (view.getId()) {
            case R.id.btn_send_notification:
//                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                Notification notification = new NotificationCompat.Builder(this)
//                        .setVisibility(Notification.VISIBILITY_PRIVATE)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
//                        .setFullScreenIntent(PendingIntent.getActivity(this, 0, new Intent(this, GsonTransformActivity.class), 0), false)
//                        .setContentTitle("标题").setContentText("内容").build();
//                manager.notify(1, notification);
                createFloatView("这里是通知" + System.currentTimeMillis());
                break;
        }
    }

    @Override
    public void initData() {
        initWindowManager();
    }

    private View view;
    private WindowManager wm;
    private boolean showWm = true;//默认是应该显示悬浮通知栏
    private WindowManager.LayoutParams params;

    private void initWindowManager() {
        wm = (WindowManager) getApplicationContext().getSystemService(
                Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        //注意是TYPE_SYSTEM_ERROR而不是TYPE_SYSTEM_ALERT
        //前面有SYSTEM才可以遮挡状态栏，不然的话只能在状态栏下显示通知栏
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        params.format = PixelFormat.TRANSPARENT;
        //设置必须触摸通知栏才可以关掉
        params.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        // 设置通知栏的长和宽
        params.width = wm.getDefaultDisplay().getWidth();
        params.height = 200;
        params.gravity = Gravity.TOP;
    }


    private void createFloatView(String str) {

        view = LayoutInflater.from(this).inflate(R.layout.notification_tip_item, null);
        //在这里你可以解析你的自定义的布局成一个View
        if (showWm) {
            wm.addView(view, params);
            showWm = false;
        } else {
            wm.updateViewLayout(view, params);
        }


        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        wm.removeViewImmediate(view);
                        view = null;
                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                }
                return true;
            }
        });

    }
}
