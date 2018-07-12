package zgc.org.demo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * Author:Nick
 * Time2018/7/12 10:08
 * Description
 */
public class SudokuEditText extends AppCompatEditText {
    private Paint mBgPaint;//背景画笔
    private Paint mBgBorderPaint;//背景框画笔
    private Paint mNumPaint;//数字画笔
    private int mWidth = 0;
    private int mHeight = 0;
    private int mRectWidth = 0;
    private int mRectHeight = 0;

    private Callback mCallback = null;

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public SudokuEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化数字画笔
        mNumPaint = new Paint();
        mNumPaint.setColor(Color.BLACK);
        mNumPaint.setStyle(Paint.Style.FILL);
        mNumPaint.setAntiAlias(true);
        mNumPaint.setTextSize(50);
        //背景画笔
        mBgPaint = new Paint();
        mBgPaint.setColor(Color.WHITE);
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setAntiAlias(true);
        //背景框画笔
        mBgBorderPaint = new Paint();
        mBgBorderPaint.setColor(Color.BLACK);
        mBgBorderPaint.setStyle(Paint.Style.STROKE);
        mBgBorderPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mWidth == 0) {
            mWidth = getWidth();
            mHeight = getHeight();

            mRectWidth = mWidth / 3;
            mRectHeight = mHeight / 3;
        }

        Rect rect = new Rect(0, 0, mWidth, mHeight);
        canvas.drawRect(rect, mBgPaint);
        canvas.drawRect(rect, mBgBorderPaint);


        if (TextUtils.isEmpty(getText())) {
            if (!TextUtils.isEmpty(getHint())) {
                String text = getHint().toString();
                mNumPaint.setTextSize(getTextSize() / 3);

                int count = 1;
                int x = 0;
                int y = 0;

                for (int i = 0; i < 3; i++) {

                    if (text.contains(count + "")) {
                        dText(canvas, mNumPaint, count + "", x, y, mRectWidth, mRectHeight);
                    }
                    x += mRectWidth;
                    count++;

                    if (text.contains(count + "")) {
                        dText(canvas, mNumPaint, count + "", x, y, mRectWidth, mRectHeight);
                    }
                    x += mRectWidth;
                    count++;

                    if (text.contains(count + "")) {
                        dText(canvas, mNumPaint, count + "", x, y, mRectWidth, mRectHeight);
                    }
                    x = 0;
                    count++;
                    y += mRectHeight;
                }
            }
        } else {
            String text = getText().toString();
            mNumPaint.setTextSize(getTextSize());
            dText(canvas, mNumPaint, text, 0, 0, mWidth, mHeight);
        }


    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (mCallback != null) {
            String tag = getTag().toString();
            mCallback.refresh(Integer.parseInt(tag.split(",")[0]), Integer.parseInt(tag.split(",")[1]));
        }
    }

    private void dText(Canvas canvas, Paint paint, String text, int x, int y, int width, int height) {
        Rect rect = new Rect(x, y, x + width, y + height);
        float textWidth = paint.measureText(text);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        int baseLineY = (int) (rect.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式
        canvas.drawText(text, x + (width - textWidth) / 2, baseLineY, paint);
    }

    public interface Callback {
        void refresh(int i, int j);
    }
}
