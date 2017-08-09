package com.kingja.supercalendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/8/9 9:21
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class SuperCalendar extends LinearLayout {
    private Button mBtnPreMonth;
    private TextView mTvCurrentMonth;
    private Button mBtnNextMonth;
    private GridView mGvCalendar;
    private Calendar mCurrentCalendar;
    private List<Date> days = new ArrayList<>();
    private CalendarAdapter mCalendarAdapter;


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
        mCurrentCalendar = Calendar.getInstance();
    }

    private void initSuperCalendar(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_supercalendar, this);
        mBtnPreMonth = (Button) findViewById(R.id.btn_preMonth);
        mBtnNextMonth = (Button) findViewById(R.id.btn_nextMonth);
        mTvCurrentMonth = (TextView) findViewById(R.id.tv_currentMonth);
        mGvCalendar = (GridView) findViewById(R.id.gv_calendar);
        mCalendarAdapter = new CalendarAdapter();
        mGvCalendar.setAdapter(mCalendarAdapter);

        showCalendar();

        mBtnPreMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentCalendar.add(Calendar.MONTH, -1);
                showCalendar();
            }
        });
        mBtnNextMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentCalendar.add(Calendar.MONTH, 1);
                showCalendar();
            }
        });
    }

    private void showCalendar() {
        SimpleDateFormat format = new SimpleDateFormat("MM yyyy");
        mTvCurrentMonth.setText(format.format(mCurrentCalendar.getTime()));

        Calendar calendar = (Calendar) mCurrentCalendar.clone();
        int lines = 0;
        int totleDays = mCurrentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Log.e("showCalendar", "DAY_OF_WEEK: " + mCurrentCalendar.get(Calendar.DAY_OF_WEEK));
        int leftDays = mCurrentCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        int firstLineDayCount = 7 - leftDays;
        int currentLeftDays = totleDays - firstLineDayCount;
        int leftLines= (int) Math.ceil(currentLeftDays/7.0d);
        if (leftDays > 0) {
            lines=leftLines+1;
        }else{
            lines=leftLines;
        }

        calendar.add(Calendar.DAY_OF_MONTH, -leftDays);
        days.clear();
        while (days.size() < 7 * lines) {
            days.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        mCalendarAdapter.setData(days);

    }

    class CalendarAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return days.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return days.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(getContext(), R.layout.item_day, null);
                viewHolder.tv_item_day = (TextView) convertView.findViewById(R.id.tv_item_day);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_item_day.setText(days.get(position).getDate() + "");
            if (days.get(position).getMonth() == mCurrentCalendar.getTime().getMonth()) {
                viewHolder.tv_item_day.setTextColor(0xff333333);
            } else {
                viewHolder.tv_item_day.setTextColor(0xff999999);
            }

            if (days.get(position).getYear() == new Date().getYear() && days.get(position).getMonth
                    () == new Date().getMonth() && days.get(position).getDate() == new Date().getDate()) {
                viewHolder.tv_item_day.setTextColor(0xffff0000);
            }

            return convertView;
        }

        public void setData(List<Date> list) {
            days = list;
            notifyDataSetChanged();
        }

        class ViewHolder {
            private TextView tv_item_day;
        }
    }
}
