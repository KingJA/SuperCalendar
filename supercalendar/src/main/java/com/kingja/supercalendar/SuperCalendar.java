package com.kingja.supercalendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description:TODO
 * Create Time:2017/8/9 9:21
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class SuperCalendar extends LinearLayout {
    private ImageView mIvPreMonth;
    private TextView mTvCurrentMonth;
    private ImageView mIvNextMonth;
    private SuperViewPager mSvpCalendar;
    private OnCalendarSelectedListener onCalendarSelectedListener;


    public SuperCalendar(Context context) {
        this(context, null);
    }

    public SuperCalendar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCalendar();
        initSuperCalendar(context);

    }

    private void initCalendar() {
    }

    private void initSuperCalendar(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_supercalendar, this);
        mIvPreMonth = (ImageView) findViewById(R.id.iv_preMonth);
        mIvNextMonth = (ImageView) findViewById(R.id.iv_nextMonth);
        mTvCurrentMonth = (TextView) findViewById(R.id.tv_currentMonth);
        mSvpCalendar = (SuperViewPager) findViewById(R.id.svp_calendar);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM yyyy");
        mTvCurrentMonth.setText(simpleDateFormat.format(new Date().getTime()));
        mSvpCalendar.setOnCalendarOperatorListener(new SuperViewPager.OnCalendarOperatorListener() {
            @Override
            public void onMonthChanged(Calendar calender) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM yyyy");
                mTvCurrentMonth.setText(simpleDateFormat.format(calender.getTime()));

            }

            @Override
            public void onCalendarSelected(Date date) {
                if (onCalendarSelectedListener != null) {
                    onCalendarSelectedListener.onCalendarSelected(date);
                }
            }
        });
        mIvPreMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSvpCalendar.preMonth();
            }
        });
        mIvNextMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSvpCalendar.nextMonth();
            }
        });
    }

    public interface OnCalendarSelectedListener {
        void onCalendarSelected(Date date);
    }

    public void setOnCalendarSelectedListener(OnCalendarSelectedListener onCalendarSelectedListener) {
        this.onCalendarSelectedListener = onCalendarSelectedListener;
    }
}
