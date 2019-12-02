package uk.ac.aber.cs31620.abercon2019.model.datasource;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Session;
import uk.ac.aber.cs31620.abercon2019.model.dao.SessionDao;

/**
 * SessionRepository.java - A class containing repository methods for the session table,
 * following the Android repository pattern.
 *
 * @author Michael Male
 * @version 1.0 2019-12-03
 */
public class SessionRepository {
    private SessionDao sessionDao; // Instance variable containing the DAO.

    /**
     * Constructor for objects of type SessionRepository.
     *
     * @param application Object of type Application pertaining to the app that is currently
     *                    running.
     */
    public SessionRepository(Application application) {
        AberConRoomDatabase db = AberConRoomDatabase.getDatabase(application);
        sessionDao = db.getSessionDao();
    }

    /* Interface methods */

    public void insert(Session session) {
        new InsertAsyncTask(sessionDao).execute(session);
    }

    @Deprecated
    public Session fetchSessionBySessionId(String id) {
        return sessionDao.fetchSessionBySessionId(id);
    }

    public LiveData<Session> fetchSessionById(String id) {
        return sessionDao.fetchSessionById(id);
    }

    public LiveData<List<Session>> getAllSessions() {
        return sessionDao.getAllSessions();
    }

    public LiveData<List<Session>> getSessionsOnDate(Date sessionDate) {
        return sessionDao.getSessionsOnDate(sessionDate);
    }

    public LiveData<List<Date>> getSessionDates() {
        return sessionDao.getSessionDates();
    }

    public LiveData<Session[]> getOrderedSessionsOnDate(Date sessionDate) {
        return sessionDao.getOrderedSessionsOnDate(sessionDate);
    }

    public LiveData<List<Session>> getSessionsByFavourites() {
        return sessionDao.getSessionsByFavourites();
    }

    public List<Session> getSessionsBetweenDatesSync(Date startDate, Date endDate) {
        return sessionDao.getSessionsBetweenDatesSync(startDate, endDate);
    }


    /**
     * InsertAsyncTask - facilitates an asynchronous insert operation into the SQLite database.
     */
    private static class InsertAsyncTask
            extends AsyncTask<Session, Void, Void> {

        private SessionDao mAsyncTaskDao;

        /**
         * Constructor for objects of type InsertAsyncTask.
         * @param dao The DAO to be used during this task.
         */
        InsertAsyncTask(SessionDao dao) {
            mAsyncTaskDao = dao;
        }

        /**
         * Inserts one to many Session objects
         * @param sessions Either a single Session variable or Session[] containing objects
         *                  to be added into the database.
         * @return null
         */
        @Override
        protected Void doInBackground(final Session... sessions) {
            mAsyncTaskDao.insertMultipleSessions(sessions);
            return null;
        }
    }
}
