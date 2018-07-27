package zgc.org.demo.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
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
    private NotificationManager manager = null;

    final String CHANNEL_ID = "channel_id_1";
    final String CHANNEL_NAME = "channel_name_1";

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

                NotificationCompat.Builder builder= new NotificationCompat.Builder(this,CHANNEL_ID);


                builder.setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("通知标题")
                        .setContentText("通知内容")
                        .setAutoCancel(true);

                manager.notify(10, builder.build());


                break;
            default:
                break;
        }
    }

    @Override
    public void initData() {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //只在Android O之上需要渠道
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，
            //通知才能正常弹出
            manager.createNotificationChannel(notificationChannel);
        }
    }

}
