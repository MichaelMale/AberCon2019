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

/**
 * SessionDao.java - The Data Access Object that manages the Session table. This utilises a
 * relatively extensive number of queries, as the Session class is used extensively throughout
 * the lifecycle of the application.
 *
 * @author Michael Male
 * @version 1.0 2019-12-03
 * @see DateTimeConverter Utilised to convert a Date class to and from a String representation of
 * it.
 * @see SessionTypeConverter Utilised to convert a SessionType enum to and from a String
 * representation of it.
 */
@Dao
@TypeConverters({DateTimeConverter.class, SessionTypeConverter.class})
public interface SessionDao {

    /**
     * Performs an SQL query that inserts an array of type Session into the table.
     *
     * @param sessions Array of type Session containing sessions to be added to the table.
     */
    @Insert
    void insertMultipleSessions(Session[] sessions);

    /**
     * This is a synchronous call that gets sessions between a certain date. It returns an object
     * that is not of LiveData. This approach is discouraged due to the fact that large data sets
     * could be called using this method, which would cause the application to not respond if
     * called on the main thread. The List would also not react to any changes. This has been
     * kept in here in case a need arises for a synchronous call.
     *
     * @param startDate The beginning date to be queried
     * @param endDate   The end date to be queried
     * @return List of type Session containing all Sessions that contain a startDate or and
     * endDate given.
     */
    @Query("SELECT * FROM sessions WHERE sessionDate BETWEEN :startDate AND :endDate")
    List<Session> getSessionsBetweenDatesSync(Date startDate, Date endDate);

    /**
     * Queries the database to return a single Session which has the given session ID. This is a
     * synchronous call and shouldn't generally be used in production. See
     * getSessionsBetweenDateSync for further details.
     *
     * @param id String containing the session ID to be queried.
     * @return Object of type Session that pertains to the given session ID.
     */
    @Query("SELECT * FROM sessions WHERE id = :id")
    Session fetchSessionBySessionId(String id);

    /**
     * An asynchronous query to the database that returns a single Session which has the given
     * session ID
     *
     * @param id String containing the session ID to be queried.
     * @return Object of type Session that pertains to the given session ID.
     */
    @Query("SELECT * FROM sessions WHERE id = :id")
    LiveData<Session> fetchSessionById(String id);

    /**
     * Queries the database to return all sessions that are represented in the table.
     *
     * @return LiveData of type List, of type Session, containing all sessions in the table.
     */
    @Query("SELECT * FROM sessions")
    LiveData<List<Session>> getAllSessions();

    /**
     * Queries the database to return all sessions that are being held on the given date.
     *
     * @param sessionDate Object of type Date containing a Date to be queried
     * @return LiveData of type List, of type Session, containing all session being held on the
     * given date. This may be empty if no sessions were found on this date.
     */
    @Query("SELECT * FROM sessions WHERE sessionDate = :sessionDate")
    LiveData<List<Session>> getSessionsOnDate(Date sessionDate);

    /**
     * Queries the database to return a list of all session dates.
     *
     * @return LiveData of type List, of type Date, containing all dates that are in the table.
     */
    @Query("SELECT DISTINCT sessionDate FROM sessions")
    LiveData<List<Date>> getSessionDates();

    /**
     * Queries the database to return an array of all sessions on a given date.
     *
     * @param sessionDate Date to be queried
     * @return An array of all sessions on the given date. This is ordered by the session order,
     * in ascending order.
     */
    @Query("SELECT * FROM sessions WHERE sessionDate = :sessionDate" +
            " ORDER BY sessionOrder ASC")
    LiveData<Session[]> getOrderedSessionsOnDate(Date sessionDate);

    /**
     * Queries the database to return a list of all sessions that are in the favourites table. It
     * does this via an inner join, joining favourites on the session ID.
     *
     * @return A LiveData of type List, of type Session, that provides all sessions that are
     * also in the favourites table.
     */
    @Query("SELECT * FROM sessions INNER JOIN favourites ON favourites.id = sessions.id")
    LiveData<List<Session>> getSessionsByFavourites();

}
