package com.kingja.supercalendar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.Date;

/**
 * Description:TODO
 * Create Time:2017/8/9 13:36
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class SuperViewPager extends ViewPager {

    private static final String TAG = "SuperViewPager";
    private int mFirstPosition;
    private Calendar mFirstCalendar;
    private Calendar mDisplayCalendar;
    private OnCalendarOperatorListener onCalendarOperatorListener;
    private int maxHeight;

    public SuperViewPager(Context context) {
        this(context, null);
    }

    public SuperViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSuperViewPager();
        mFirstCalendar = (Calendar) Calendar.getInstance().clone();
        mFirstCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        mFirstPosition = Integer.MAX_VALUE / 2;
        addOnPageChangeListener(onPageChangeListener);
        setAdapter(new SuperPagerAdapter());
        setCurrentItem(mFirstPosition);
    }

    private void initSuperViewPager() {
    }

    private OnPageChangeListener onPageChangeListener = new SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            mDisplayCalendar = (Calendar) mFirstCalendar.clone();
            mDisplayCalendar.add(Calendar.MONTH, position - mFirstPosition);
            if (onCalendarOperatorListener != null) {
                onCalendarOperatorListener.onMonthChanged(mDisplayCalendar);
            }
        }
    };


    class SuperPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            SuperView superView = new SuperView(getContext());
            superView.setOnCalendarSelectedListener(new SuperView.OnCalendarSelectedListener() {
                @Override
                public void onCalendarSelected(Date date) {
                    if (onCalendarOperatorListener != null) {
                        onCalendarOperatorListener.onCalendarSelected(date);
                    }
                }
            });
            Calendar calender = (Calendar) mFirstCalendar.clone();
            calender.add(Calendar.MONTH, position - mFirstPosition);
            superView.setData(calender);
            container.addView(superView);
            return superView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public interface OnCalendarOperatorListener {
        void onMonthChanged(Calendar calender);
        void onCalendarSelected(Date date);
    }

    public void setOnCalendarOperatorListener(OnCalendarOperatorListener onCalendarOperatorListener) {
        this.onCalendarOperatorListener = onCalendarOperatorListener;
    }

    public void preMonth() {
        setCurrentItem(getCurrentItem() - 1, true);
    }


    public void nextMonth() {
        setCurrentItem(getCurrentItem() + 1, true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > maxHeight)
                maxHeight = h;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
