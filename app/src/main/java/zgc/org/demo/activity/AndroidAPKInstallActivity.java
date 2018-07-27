package zgc.org.demo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.view.View;

import butterknife.OnClick;
import zgc.org.demo.R;
import zgc.org.demo.activity.base.BaseActivity;
import zgc.org.demo.util.FileUtil;
import zgc.org.demo.util.LogUtil;
import zgc.org.demo.util.PermissionUtil;
import zgc.org.demo.util.SystemUtil;
import zgc.org.demo.util.ToastUtil;

import static zgc.org.demo.util.SystemUtil.INSTALL_APK_STATE;

/**
 * Author:Nick
 * Time2018/7/19 11:54
 * Description
 */
public class AndroidAPKInstallActivity extends BaseActivity {

    private Uri uri = null;

    private int count = 0;

    private final static int REQUEST_INSTALL_APK = 10001;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_android_apk_install;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.btn_update})
    void click(View view) {
        switch (view.getId()) {
            case R.id.btn_update:
                addDisposable(PermissionUtil.requestEachCombined(this, permission -> {
                    if (permission.granted) {
                        installProcess(FileUtil.copyAssetsFile(AndroidAPKInstallActivity.this, "app-debug.apk", Environment.getExternalStorageDirectory().getPath()));
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        ToastUtil.showShort("请授予存储权限"+count++);
                    } else {
                        ToastUtil.showShort("请授予存储权限"+count++);
                        PermissionUtil.openAppSetting(AndroidAPKInstallActivity.this);
                    }
                }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE));
                break;
        }
    }

    @Override
    public void initData() {

    }


    private void installProcess(Uri uri) {
        boolean haveInstallPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先获取是否有安装未知来源应用的权限
            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {//没有权限
                this.uri = uri;//保存一下，回调的时候如果授权成功就调用安装
                startInstallPermissionSettingActivity();
                return;
            }
        }
        //有权限，开始安装应用程序
        SystemUtil.openApk(uri, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        ToastUtil.showShort("请允许安装应用");
        Uri packageURI = Uri.parse("package:" + getPackageName());
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, REQUEST_INSTALL_APK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d("requestCode:" + requestCode + ",resultCode:" + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_INSTALL_APK:
                    if (uri != null) {
                        SystemUtil.openApk(uri, this);
                    }
                    break;
                case INSTALL_APK_STATE:
                    //其实安装成功APP就要重新打开，这里是不会显示的
                    ToastUtil.showShort("安装成功");
                    break;
                default:
                    break;
            }
        } else {
            switch (requestCode) {
                case INSTALL_APK_STATE:
                    ToastUtil.showShort("用户取消了安装");
                    break;
                default:
                    break;
            }
        }
    }


}
