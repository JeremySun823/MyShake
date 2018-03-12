package com.example.sun.myshake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sun on 18/1/3.
 */

public class RoundView extends View {
    private Paint mCirclePaint;
    private Paint mTextPaint;
    private String mText;

    public RoundView(Context context) {
        super(context);
        init(context);
    }

    public RoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.BLUE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(240);
        mTextPaint.setStrokeWidth(3);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.BLACK);
        mText = "1024";
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mCirclePaint);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int baseLineY = (int) (getHeight() / 2 - top / 2 - bottom / 2);

        canvas.drawText(mText, getWidth() / 2, baseLineY, mTextPaint);
    }

    public void setText(String mText) {
        this.mText = mText;
        invalidate();
    }

    public void setRoundColor(int color) {
        mCirclePaint.setColor(color);
        invalidate();
    }

    public String getText() {
        return mText;
    }

}
