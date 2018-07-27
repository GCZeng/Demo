package zgc.org.demo.util.rx;

import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Author:Nick
 * Time2018/5/21 15:53
 * Description 倒计时工具类
 */
public class RxCountDown {
    /**
     * 倒计时
     *
     * @param time          时间(秒)
     * @param textView      显示的textview
     * @param countDownText 倒计时提示文字
     * @param finishText    倒计时后显示的文件
     * @return
     */
    public static Disposable countDown(int time, TextView textView, String countDownText, String finishText) {
        if (time < 0) time = 0;

        int finalTime = time;
        textView.setEnabled(false);
        return Flowable.intervalRange(0, time, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> textView.setText(finalTime - aLong.intValue() + countDownText))
                .doOnComplete(() -> {
                    textView.setEnabled(true);
                    textView.setText(finishText);
                })
                .subscribe();
    }

    /**
     * 倒计时
     *
     * @param time     时间(秒)
     * @param callback 回调
     * @return
     */
    public static Disposable countDown(int time, Callback callback) {
        if (time < 0) time = 0;

        int finalTime = time;
        return Flowable.intervalRange(0, time, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> {
                })
                .doOnComplete(() -> {
                    if (callback != null)
                        callback.action();
                })
                .subscribe();
    }

    public interface Callback {
        void action();
    }
}
