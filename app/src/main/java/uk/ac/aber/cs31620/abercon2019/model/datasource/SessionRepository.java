package uk.ac.aber.cs31620.abercon2019.model.datasource;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Session;
import uk.ac.aber.cs31620.abercon2019.model.dao.SessionDao;

public class SessionRepository {
    private SessionDao sessionDao;

    public SessionRepository(Application application) {
        AberConRoomDatabase db = AberConRoomDatabase.getDatabase(application);
        sessionDao = db.getSessionDao();
    }

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


    private static class InsertAsyncTask
            extends AsyncTask<Session, Void, Void> {

        private SessionDao mAsyncTaskDao;

        InsertAsyncTask(SessionDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Session... sessions) {
            mAsyncTaskDao.insertMultipleSessions(sessions);
            return null;
        }
    }
}
