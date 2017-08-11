package com.kingja.supercalendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Description:TODO
 * Create Time:2017/8/10 10:45
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DayView extends TextView {

    private Paint paint;

    public DayView(Context context) {
        this(context, null);
    }

    public DayView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDayView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }

    private void initDayView() {
        setTextColor(0xffffffff);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (showDraw) {
            canvas.drawCircle(getMeasuredHeight() / 2, getMeasuredHeight() / 2, getMeasuredHeight() / 2, paint);
        }
        super.onDraw(canvas);
    }

    private boolean showDraw;

    public void setToday() {
        showDraw = true;
        setTextColor(0xffffffff);
        paint.setColor(0xFFCE2D38);
        invalidate();
    }

    public void setSelected() {
        showDraw = true;
        setTextColor(0xffffffff);
        paint.setColor(0xffd798a4);
        invalidate();
    }

    public void setNormal() {
        showDraw = false;
        setTextColor(0xff333333);
        invalidate();
    }

    public void setOtherManth() {
        showDraw = false;
        setTextColor(0xff999999);
        invalidate();
    }
}
