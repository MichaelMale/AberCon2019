package uk.ac.aber.cs31620.abercon2019.model.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Session;
import uk.ac.aber.cs31620.abercon2019.model.datasource.SessionRepository;

/**
 * SessionViewModel.java - A view model to manage calls to the database on the UI thread.
 *
 * @version 1.0 2019-12-03
 * @see AndroidViewModel
 */
public class SessionViewModel extends AndroidViewModel {
    private SessionRepository repository;
    private LiveData<List<Session>> allSessions;

    /**
     * Constructor for objects of type SessionViewModel.
     *
     * @param application Object of type Application pertaining to the app that is currently
     *                    running.
     */
    public SessionViewModel(@NonNull Application application) {
        super(application);
        repository = new SessionRepository(application);
        allSessions = repository.getAllSessions();
    }

    /* Interface methods */

    public LiveData<List<Session>> getAllSessions() {
        return allSessions;
    }

    public LiveData<List<Date>> getSessionDates() {
        return repository.getSessionDates();
    }

    public LiveData<Session[]> getOrderedSessionsOnDate(Date sessionDate) {
        return repository.getOrderedSessionsOnDate(sessionDate);
    }

    public LiveData<List<Session>> getSessionsByFavourites() {
        return repository.getSessionsByFavourites();
    }

    /* Synchronous call, should not be used during production. */
    @Deprecated
    public Session fetchSessionBySessionId(String id) {
        return repository.fetchSessionBySessionId(id);
    }

    public LiveData<Session> fetchSessionById(String id) {
        return repository.fetchSessionById(id);
    }
}
