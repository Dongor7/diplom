package com.itsupportme.gis.component.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    public static Date parseDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        return formatter.parse(date);
    }

    public static Date setTime(final Date date, final int hourOfDay, final int minute, final int second, final int ms) {

        final GregorianCalendar gregorianCalendar = new GregorianCalendar();

        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        gregorianCalendar.set(Calendar.MINUTE, minute);
        gregorianCalendar.set(Calendar.SECOND, second);
        gregorianCalendar.set(Calendar.MILLISECOND, ms);

        return gregorianCalendar.getTime();
    }

    public static Date increase(Date date, Integer seconds) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);

        return calendar.getTime();
    }

    public static String SQLDateTime(Date date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return simpleDateFormat.format(date);
    }


    public static String USDateTime(Date date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        return simpleDateFormat.format(date);
    }

    public static Integer getDiff(Date endDate, Date startDate) {

        Long diff        = endDate.getTime() - startDate.getTime();
        Long diffSeconds = diff / 1000;

        return Math.abs(diffSeconds.intValue());
    }

    public static String getRestTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd'T'HH:mm:ssZ");
        return dateFormat.format(Calendar.getInstance().getTime());
    }
}
