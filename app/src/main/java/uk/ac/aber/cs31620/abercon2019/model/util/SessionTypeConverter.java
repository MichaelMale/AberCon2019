package uk.ac.aber.cs31620.abercon2019.model.util;

import androidx.room.TypeConverter;

import uk.ac.aber.cs31620.abercon2019.model.SessionType;

public class SessionTypeConverter {

    @TypeConverter
    public static String toString(SessionType sessionType) {
        String result = sessionType == null ? null : sessionType.toString();
        return result;
    }

    @TypeConverter
    public static SessionType toSessionType(String sessionType) {
        sessionType = sessionType.toUpperCase();
        SessionType result = sessionType == null ? null : SessionType.valueOf(sessionType);
        return result;
    }
}
