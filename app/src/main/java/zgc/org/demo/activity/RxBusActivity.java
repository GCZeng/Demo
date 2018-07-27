package zgc.org.demo.activity;

import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import zgc.org.demo.R;
import zgc.org.demo.activity.base.BaseActivity;
import zgc.org.demo.bean.event.Event1;
import zgc.org.demo.bean.event.Event2;
import zgc.org.demo.bean.event.Event3;
import zgc.org.demo.util.ToastUtil;
import zgc.org.demo.util.rx.RxBus;

/**
 * Author:Nick
 * Time2018/7/24 10:56
 * Description
 */
public class RxBusActivity extends BaseActivity {
    @BindView(R.id.btn_send1)
    Button btnSend1;
    @BindView(R.id.btn_send2)
    Button btnSend2;
    @BindView(R.id.btn_send3)
    Button btnSend3;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_rxbus;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.btn_send1, R.id.btn_send2, R.id.btn_send3})
    void click(View view) {
        switch (view.getId()) {
            case R.id.btn_send1:
                RxBus.getDefault().post(new Event1());
                break;
            case R.id.btn_send2:
                RxBus.getDefault().post(new Event2());
                break;
            case R.id.btn_send3:
                RxBus.getDefault().post(new Event3());
                break;
        }
    }

    @Override
    public void initData() {
        addDisposable(RxBus.getDefault().toObservable()
                .subscribe(o -> {
                    if (o instanceof Event1) {
                        tvContent.setText("Evnet1");
                    }

                    if (o instanceof Event2) {
                        tvContent.setText("Event2");
                    }

                    if (o instanceof Event3) {
                        tvContent.setText("Event3");
                    }
                }));

        addDisposable(RxBus.getDefault().toObservable(Event1.class).subscribe(event1 -> ToastUtil.showShort("Event1")));
        addDisposable(RxBus.getDefault().toObservable(Event2.class).subscribe(event2 -> ToastUtil.showShort("Event2")));
        addDisposable(RxBus.getDefault().toObservable(Event3.class).subscribe(event3 -> ToastUtil.showShort("Event3")));

    }

}