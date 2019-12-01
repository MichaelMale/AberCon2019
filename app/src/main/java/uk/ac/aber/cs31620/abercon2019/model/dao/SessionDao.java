package uk.ac.aber.cs31620.abercon2019.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Session;
import uk.ac.aber.cs31620.abercon2019.model.util.DateTimeConverter;
import uk.ac.aber.cs31620.abercon2019.model.util.SessionTypeConverter;

@Dao
@TypeConverters({DateTimeConverter.class, SessionTypeConverter.class})
public interface SessionDao {

    @Insert
    void insertMultipleSessions(Session[] sessions);

    // Not production code - avoid using synchronous calls from the main thread
    @Query("SELECT * FROM sessions WHERE sessionDate BETWEEN :startDate AND :endDate")
    List<Session> getSessionsBetweenDatesSync(Date startDate, Date endDate);

    @Query("SELECT * FROM sessions WHERE id = :id")
    Session fetchSessionBySessionId(String id);

    @Query("SELECT * FROM sessions WHERE id = :id")
    LiveData<Session> fetchSessionById(String id);

    @Query("SELECT * FROM sessions")
    LiveData<List<Session>> getAllSessions();

    @Query("SELECT * FROM sessions WHERE sessionDate = :sessionDate")
    LiveData<List<Session>> getSessionsOnDate(Date sessionDate);

    @Query("SELECT sessionDate FROM sessions")
    LiveData<List<Date>> getSessionDates();

    @Query("SELECT * FROM sessions WHERE sessionDate = :sessionDate" +
            " ORDER BY sessionOrder ASC")
    LiveData<Session[]> getOrderedSessionsOnDate(Date sessionDate);

    @Query("SELECT * FROM sessions INNER JOIN favourites ON favourites.sessionId = sessions.id")
    LiveData<List<Session>> getSessionsByFavourites();

}
