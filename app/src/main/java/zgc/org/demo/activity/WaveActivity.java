package zgc.org.demo.activity;

import zgc.org.demo.R;
import zgc.org.demo.activity.base.BaseActivity;
import zgc.org.demo.widget.WaveView2;

/**
 * Author:Nick
 * Time2018/9/18 15:23
 * Description
 */
public class WaveActivity extends BaseActivity {
    private WaveView2 waveView2;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_wave;
    }

    @Override
    protected void initView() {
        waveView2 = findViewById(R.id.waveView2);

        waveView2.begainAnimation();
    }

    @Override
    public void initData() {

    }
}
