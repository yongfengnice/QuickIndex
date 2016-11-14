package com.quickindex.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.quickindex.utils.BarUtils;

/**
 * Creator: syf(205205)
 * Date   : on 2016/11/14 0014
 * Desc   : 索引条
 */
public class IndexBar extends View {

    private static final String[] LETTERS = new String[]{
            BarUtils.sFirstChar, "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X",
            "Y", "Z", BarUtils.sLastChar};
    private int mCellWidth;//一个小格子的宽度
    private float mCellHeight;//一个小格子的高度
    private Paint mPaint;
    private int mPreIndex = -1;//上次索引
    private int mCurIndex = -1;//当前索引

    public IndexBar(Context context) {
        this(context, null);
    }

    public IndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(BarUtils.pxFromSp(context, 15));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCellWidth = getMeasuredWidth();
        int mHeight = getMeasuredHeight();
        mCellHeight = mHeight * 1.0f / LETTERS.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < LETTERS.length; i++) {
            String text = LETTERS[i];
            Rect bounds = new Rect();
            mPaint.getTextBounds(text, 0, text.length(), bounds);
            int x = (int) (mCellWidth / 2.0f - bounds.width() / 2.0f);
            int y = (int) (mCellHeight / 2.0f + bounds.height() / 2.0f + i * mCellHeight);
            mPaint.setColor(i == mCurIndex ? Color.GRAY : Color.BLACK);
            canvas.drawText(text, x,  y, mPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mCurIndex = (int) (event.getY() / mCellHeight);
                if (mCurIndex >= 0 && mCurIndex < LETTERS.length) {
                    if (mCurIndex != mPreIndex && mListener != null) {
                        mListener.onLetterUpdate(LETTERS[mCurIndex]);
                        mPreIndex = mCurIndex;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mCurIndex = -1;
                mPreIndex = -1;
                mListener.onLetterNone();
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    public interface OnLetterUpdateListener {
        void onLetterUpdate(String letter);

        void onLetterNone();
    }

    private OnLetterUpdateListener mListener;

    public void setOnLetterUpdateListener(OnLetterUpdateListener listener) {
        mListener = listener;
    }
}
