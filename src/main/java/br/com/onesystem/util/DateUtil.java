package br.com.onesystem.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    public static Date getCurrentDateTime() {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        calendar.setTime(date);
        return date;
    }

}
