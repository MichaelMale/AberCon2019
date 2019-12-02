package uk.ac.aber.cs31620.abercon2019.model.util;

import android.util.Log;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * DateTimeConverter.java - A class that converts a String representation of a date into a Date
 * object, and vice versa.
 *
 * @author Michael Male
 * @version 1.0 2019-11-29
 */
public class DateTimeConverter {
    private static final String DATE_ERROR_TAG = "DATE FORMAT ERROR"; // Tag is used to log any
    // errors with the format of the date.

    /**
     * Converts a String into a Date
     *
     * @param stringDate A string containing an ISO representation of the date (yyyy-MM-dd).
     * @return Object of type Date that contains the given Date, or null if the Date could not
     * be parsed.
     */
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

    /**
     * Converts a Date into a String
     * @param date  Object of type Date
     * @return The passed object as a String, in ISO format (yyyy-MM-dd).
     */
    @TypeConverter
    public static String toString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(date);
    }


}
