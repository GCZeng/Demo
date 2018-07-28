package zgc.org.demo.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zgc.org.demo.R;
import zgc.org.demo.activity.base.BaseActivity;
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
    private int PERMISSIONS_STORAGE_REQUEST_CODE = 1001;

    private boolean flag = false;

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
                request(2);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (flag) {
            flag = false;
            request(1);
        }
    }

    /**
     * @param type 1. 存储权限 2. 全部权限
     */
    private void request(int type) {
        String[] permissions = null;
        int request = 0;
        switch (type) {
            case 1:
                permissions = new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                };
                request = PERMISSIONS_STORAGE_REQUEST_CODE;
                break;
            case 2:
                permissions = new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                };
                request = PERMISSIONS_REQUEST_CODE;
                break;
            default:
                break;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, request);
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
        List<String> unPermissions = new ArrayList<>();
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED && !ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    unPermissions.add(permissions[i]);
                }
            }
            //未授权的
            if (unPermissions.contains(Manifest.permission.READ_EXTERNAL_STORAGE) || unPermissions.contains(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ToastUtil.showShort("请授予存储权限");

                flag = true;
                PermissionUtil.openAppSetting(PermissionRequestActivity.this);
            }
        }

        if (requestCode == PERMISSIONS_STORAGE_REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    ToastUtil.showShort("请授予" + permissions[i] + "权限");

                    flag = true;
                    PermissionUtil.openAppSetting(PermissionRequestActivity.this);
                }
            }
        }
    }
}
