package uk.ac.aber.cs31620.abercon2019.model.util;

import android.util.Log;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateTimeConverter {
    private static final String DATE_ERROR_TAG = "DATE FORMAT ERROR";

    @TypeConverter
    public static Date toDate(String stringDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            return format.parse(stringDate);
        } catch (ParseException e) {
            Log.w(DATE_ERROR_TAG, e.toString());
        }
        return null;
    }

    @TypeConverter
    public static String toString(Date dateDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(dateDate);
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillis = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }

    public static String previousDateAsString(int numDays) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DATE, -numDays);
        return df.format(cal1.getTime());
    }


}
