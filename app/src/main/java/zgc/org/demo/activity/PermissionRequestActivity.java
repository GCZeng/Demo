package zgc.org.demo.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.JsonNull;

import butterknife.BindView;
import butterknife.OnClick;
import zgc.org.demo.R;
import zgc.org.demo.activity.base.BaseActivity;
import zgc.org.demo.util.LogUtil;
import zgc.org.demo.util.PermissionUtil;
import zgc.org.demo.util.ToastUtil;

/**
 * Author:Nick
 * Time2018/7/27 16:02
 * Description
 */
public class PermissionRequestActivity extends BaseActivity {
    @BindView(R.id.btn_permission_request)
    Button btnPermissionRequest;

    private int PERMISSIONS_REQUEST_CODE = 1000;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_permission_request;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.btn_permission_request})
    void click(View view) {
        switch (view.getId()) {
            case R.id.btn_permission_request:
                request();
                break;
        }
    }


    private void request() {
        String[] permissions = new String[]{Manifest.permission.CAMERA};
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_REQUEST_CODE);
        } else {
            ToastUtil.showShort("有权限");
        }

    }

    @Override
    public void initData() {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED && !ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    ToastUtil.showShort("请授予" + permissions[i] + "权限");
                    PermissionUtil.openAppSetting(PermissionRequestActivity.this);
                }
            }
        }
    }
}
