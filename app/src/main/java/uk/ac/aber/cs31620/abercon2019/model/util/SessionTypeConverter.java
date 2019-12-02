package uk.ac.aber.cs31620.abercon2019.model.util;

import androidx.room.TypeConverter;

import uk.ac.aber.cs31620.abercon2019.model.SessionType;

/**
 * SessionTypeConverter.java - A class to convert a String representation of a session type into
 * a SessionType enum.
 *
 * @author Michael Male
 * @version 1.0 2019-12-03
 */
public class SessionTypeConverter {

    /**
     * Converts a SessionType into a String.
     *
     * @param sessionType Enum containing a SessionType
     * @return String containing a textual representation of the session type, or null if it
     * can't be converted.
     */
    @TypeConverter
    public static String toString(SessionType sessionType) {
        String result = sessionType == null ? null : sessionType.toString();
        return result;
    }

    /**
     * Converts a String into a session type
     * @param sessionType String containing a textual representation of a SessionType.
     * @return SessionType enum pertaining to the given String, or null if it can't be converted.
     */
    @TypeConverter
    public static SessionType toSessionType(String sessionType) {
        sessionType = sessionType.toUpperCase();
        SessionType result = sessionType == null ? null : SessionType.valueOf(sessionType);
        return result;
    }
}
