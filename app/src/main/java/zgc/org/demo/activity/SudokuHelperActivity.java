package zgc.org.demo.activity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zgc.org.demo.R;
import zgc.org.demo.activity.base.BaseActivity;
import zgc.org.demo.bean.SudokuData;
import zgc.org.demo.util.LogUtil;
import zgc.org.demo.widget.SudokuEditText;

/**
 * Author:Nick
 * Time2018/7/11 15:29
 * Description
 */
public class SudokuHelperActivity extends BaseActivity {
    @BindView(R.id.ll_content)
    LinearLayout llContent;

    private Integer[][] arrays = new Integer[9][9];
    private EditText[][] editTexts = new EditText[9][9];

    private List<String> list = new ArrayList<>();
    private SudokuData mSudokuData = null;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_sudoku_helper;
    }


    @Override
    protected void initView() {
        mSudokuData = new SudokuData();

        for (int i = 0; i < 9; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            for (int j = 0; j < 9; j++) {
                LinearLayout view = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.soduku_input_item, linearLayout, true);
                SudokuEditText editText = (SudokuEditText) view.getChildAt(j);
                editText.setTag(i + "," + j);
                editText.setCallback(new SudokuEditText.Callback() {
                    @Override
                    public void refresh(int i, int j) {
                        tip(i, j);
                        mSudokuData.getData().add(arrays);
                        Gson gson = new Gson();
                        list.add(gson.toJson(mSudokuData));
                    }
                });
                editTexts[i][j] = editText;
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            llContent.addView(linearLayout, lp);
        }

        refresh();
    }

    @OnClick({R.id.btn_rollback})
    void click(View view) {
        switch (view.getId()) {
            case R.id.btn_rollback:
                roolback(list.get(list.size() - 1));
                break;
        }
    }

    private void roolback(String json) {
        Gson gson = new Gson();
        SudokuData sudokuData = gson.fromJson(json, SudokuData.class);
        List<Integer[][]> data = sudokuData.getData();

        if (data.size() > 1) {
            Integer[][] array = data.get(data.size() - 2);

            LogUtil.d(gson.toJson(array));
            this.arrays = array;
            refresh();
        }

    }

    @Override
    public void initData() {

    }

    private void refresh() {
        for (int i = 0; i < llContent.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) llContent.getChildAt(i);
            for (int j = 0; j < linearLayout.getChildCount(); j++) {
                tip(i, j);
            }
        }
    }

    private void tip(int i, int j) {
        for (int t = 0; t < 9; t++) {
            calc(i, t);
            calc(t, j);
        }
        tipRound(i, j);
    }

    private void tipRound(int iI, int jI) {
        int tI = iI;
        int tJ = jI;

        if (tI > 2) {
            while (true) {
                if (tI > 2) {
                    tI -= 3;
                } else {
                    break;
                }
            }
        }

        if (tJ > 2) {
            while (true) {
                if (tJ > 2) {
                    tJ -= 3;
                } else {
                    break;
                }
            }
        }


        int i = (iI - tI) < 0 ? 0 : (iI - tI);
        int j = (jI - tJ) < 0 ? 0 : (jI - tJ);
        int iLen = i + 3;
        int jLen = j + 3;

        for (; i < iLen; i++) {
            for (; j < jLen; j++) {
                calc(i, j);
            }
            j -= 3;
        }
    }

    private void calc(int i, int j) {
        arrays[i][j] = getNum(editTexts[i][j]);

        if (arrays[i][j] == 0) {
            String tempStr = "123456789";
            EditText editText = editTexts[i][j];
            tempStr = calcH(i, tempStr);
            tempStr = calcV(j, tempStr);
            tempStr = calcRound(i, j, tempStr);
            if (tempStr.length() == 1) {
                editText.setText(tempStr);
            } else {
                editText.setHint(tempStr);
            }
        }
    }

    private String calcRound(int iI, int jI, String hintStr) {
        int tI = iI;
        int tJ = jI;

        if (tI > 2) {
            while (true) {
                if (tI > 2) {
                    tI -= 3;
                } else {
                    break;
                }
            }
        }

        if (tJ > 2) {
            while (true) {
                if (tJ > 2) {
                    tJ -= 3;
                } else {
                    break;
                }
            }
        }


        int i = (iI - tI) < 0 ? 0 : (iI - tI);
        int j = (jI - tJ) < 0 ? 0 : (jI - tJ);
        int iLen = i + 3;
        int jLen = j + 3;

        for (; i < iLen; i++) {
            for (; j < jLen; j++) {
                int num = getNum(editTexts[i][j]);
                if (num != 0) {
                    hintStr = hintStr.replace(num + "", "");
                }
            }
            j -= 3;
        }


        return hintStr;
    }

    private String calcH(int i, String hintStr) {
        for (int j = 0; j < 9; j++) {
            int num = getNum(editTexts[i][j]);
            if (num != 0) {
                hintStr = hintStr.replace(num + "", "");
            }
        }
        return hintStr;
    }

    private String calcV(int j, String hintStr) {
        for (int i = 0; i < 9; i++) {
            int num = getNum(editTexts[i][j]);
            if (num != 0) {
                hintStr = hintStr.replace(num + "", "");
            }
        }
        return hintStr;
    }

    private int getNum(EditText editText) {
        if (TextUtils.isEmpty(editText.getText())) {
            return 0;
        }
        return Integer.parseInt(editText.getText().toString());
    }

}
