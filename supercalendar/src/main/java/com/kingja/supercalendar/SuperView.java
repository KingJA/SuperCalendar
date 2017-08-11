package com.kingja.supercalendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

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
public class SuperView extends GridView implements AdapterView.OnItemClickListener {
    private Calendar mCurrentCalendar;
    private List<Date> days = new ArrayList<>();
    private CalendarAdapter mCalendarAdapter;
    private boolean showTable = true;
    private OnCalendarSelectedListener onCalendarSelectedListener;


    public SuperView(Context context) {
        this(context, null);
    }

    public SuperView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCalendar();
        initSuperCalendar(context);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    private void initCalendar() {
        mCurrentCalendar = Calendar.getInstance();
    }

    private void initSuperCalendar(Context context) {
        setNumColumns(Calendar.DAY_OF_WEEK);
        setSelector(new ColorDrawable(Color.TRANSPARENT));
        setHorizontalSpacing(Util.dp2px(context, 1));
        setVerticalSpacing(Util.dp2px(context, 1));
        if (showTable) {
            setPadding(0, 0, 0, Util.dp2px(context, 1));
            setBackgroundColor(0xffbebebe);
        }
        mCalendarAdapter = new CalendarAdapter();
        setAdapter(mCalendarAdapter);
        setOnItemClickListener(this);
    }

    public void setData(Calendar calendar) {
        mCurrentCalendar = calendar;
        showCalendar();
    }


    private void showCalendar() {
        Calendar calendar = (Calendar) mCurrentCalendar.clone();
        int lines;
        int totleDays = mCurrentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int leftDays = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int firstLineDayCount = Calendar.DAY_OF_WEEK - leftDays;
        int currentLeftDays = totleDays - firstLineDayCount;
        int leftLines = (int) Math.ceil(currentLeftDays / 7.0d);
        if (leftDays > 0) {
            lines = leftLines + 1;
        } else {
            lines = leftLines;
        }

        calendar.add(Calendar.DAY_OF_MONTH, -leftDays);
        days.clear();
        while (days.size() < Calendar.DAY_OF_WEEK * lines) {
            days.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        mCalendarAdapter.setData(days);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mCalendarAdapter.selectItem(position);
//        Date selectedDate = (Date) parent.getItemAtPosition(position);
//        Toast.makeText(getContext(), Util.getFormatDate("M yyyy", selectedDate), Toast.LENGTH_SHORT).show();
        if (onCalendarSelectedListener != null) {
            onCalendarSelectedListener.onCalendarSelected((Date) parent.getItemAtPosition(position));
        }

    }

    class CalendarAdapter extends BaseAdapter {
        private int selectedPosition = -1;

        @Override
        public int getCount() {
            return days.size();
        }

        @Override
        public Object getItem(int position) {
            return days.get(position);
        }

        @Override
        public long getItemId(int position) {
            return days.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            Date date = days.get(position);
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(getContext(), R.layout.item_day, null);
                viewHolder.tv_item_day = (DayView) convertView.findViewById(R.id.tv_item_day);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_item_day.setText(date.getDate() + "");
            if (Util.isSameMonth(date, mCurrentCalendar.getTime())) {
                viewHolder.tv_item_day.setNormal();
            } else {
                viewHolder.tv_item_day.setOtherManth();
            }

            if (Util.isToday(date)) {
                viewHolder.tv_item_day.setToday();
            }

            if (position == selectedPosition) {
                viewHolder.tv_item_day.setSelected();
            }

            return convertView;
        }

        public void selectItem(int position) {
            selectedPosition = position;
            notifyDataSetChanged();
        }

        public void setData(List<Date> list) {
            days = list;
            notifyDataSetChanged();
        }

        class ViewHolder {
            private DayView tv_item_day;
        }
    }

    public interface OnCalendarSelectedListener {
        void onCalendarSelected(Date date);
    }

    public void setOnCalendarSelectedListener(OnCalendarSelectedListener onCalendarSelectedListener) {
        this.onCalendarSelectedListener = onCalendarSelectedListener;
    }
}
