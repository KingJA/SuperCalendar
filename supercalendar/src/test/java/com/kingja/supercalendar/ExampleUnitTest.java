package com.kingja.supercalendar;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Calendar calendar = Calendar.getInstance();
        int actualMaximum = calendar.getActualMaximum(Calendar.DATE);//最大
        System.out.println(actualMaximum);
        Date time = calendar.getTime();
        time.getDay();
    }
}