package com.song.samples.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * date utils
 */

public class DateUtils {

    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE = "yyyy-MM-dd";

    public static final String TIME = "HH:mm:ss";

    public static final SimpleDateFormat DATE_TIME_SDF = new SimpleDateFormat(DATE_TIME);

    public static final SimpleDateFormat DATE_SDF = new SimpleDateFormat(DATE);

    public static final SimpleDateFormat TIME_SDF = new SimpleDateFormat(TIME);

    public static String format(Date date, String patten) {
        return new SimpleDateFormat(patten).format(date);
    }

    public static String formatDateTime(Date date) {
        return DATE_TIME_SDF.format(date);
    }

    public static String formatDate(Date date) {
        return DATE_SDF.format(date);
    }

    public static String formatTime(Date date) {
        return TIME_SDF.format(date);
    }

    public static Date parse(String dateStr, String patten) throws ParseException {
        return new SimpleDateFormat(patten).parse(dateStr);
    }

    public static Date parseDateTime(String dateStr) throws ParseException {
        return DATE_TIME_SDF.parse(dateStr);
    }

    public static Date parseDate(String dateStr) throws ParseException {
        return DATE_SDF.parse(dateStr);
    }

    public static Date parseTime(String dateStr) throws ParseException {
        return TIME_SDF.parse(dateStr);
    }
}
