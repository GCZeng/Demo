package zgc.org.demo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import zgc.org.demo.R;
import zgc.org.demo.activity.base.BaseActivity;
import zgc.org.demo.util.LogUtil;

/**
 * Author:Nick
 * Time2018/7/11 15:29
 * Description
 */
public class SudokuHelperActivity extends BaseActivity {
    @BindView(R.id.ll_content)
    LinearLayout llContent;

    private int[][] arrays = new int[9][9];
    private EditText[][] editTexts = new EditText[9][9];

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_sudoku_helper;
    }


    @Override
    protected void initView() {
        for (int i = 0; i < 9; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            for (int j = 0; j < 9; j++) {
                LinearLayout view = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.soduku_input_item, linearLayout, true);
                EditText editText = (EditText) view.getChildAt(j);
                editText.addTextChangedListener(textWatcher);
                editTexts[i][j] = editText;
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            llContent.addView(linearLayout, lp);
        }

        refresh();
    }


    @Override
    public void initData() {

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            refresh();
        }
    };

    private void refresh() {
        for (int i = 0; i < llContent.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) llContent.getChildAt(i);
            for (int j = 0; j < linearLayout.getChildCount(); j++) {
                arrays[i][j] = getNum((EditText) linearLayout.getChildAt(j));

                if (arrays[i][j] == 0) {
                    String tempStr = "123456789";
                    EditText editText = editTexts[i][j];
                    tempStr = calcH(i, tempStr);
                    tempStr = calcV(j, tempStr);
                    tempStr = calcRound(i, j, tempStr);
                    if(tempStr.length()==1){
                        editText.setText(tempStr);
                    }else {
                        editText.setHint(tempStr);
                    }
                }
            }
        }
    }

    private String calcRound(int iI, int jI, String hintStr) {
        int tI = iI + 1;
        int tJ = jI + 1;

        if (tI > 3) {
            while (true) {
                if (tI > 3) {
                    tI -= 3;
                } else {
                    break;
                }
            }
        }

        if (tJ > 3) {
            while (true) {
                if (tJ > 3) {
                    tJ -= 3;
                } else {
                    break;
                }
            }
        }


        int i = iI - tI + 1;
        if (i < 0) {
            i = 0;
        }
        int j = jI - tJ + 1;
        if (j < 0) {
            j = 0;
        }


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
            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP,8);
            return 0;
        }
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP,8);
        return Integer.parseInt(editText.getText().toString());
    }

}
