package uk.ac.aber.cs31620.abercon2019.model.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Session;
import uk.ac.aber.cs31620.abercon2019.model.datasource.SessionRepository;

public class SessionViewModel extends AndroidViewModel {
    private SessionRepository repository;
    private LiveData<List<Session>> allSessions;

    public SessionViewModel(@NonNull Application application) {
        super(application);
        repository = new SessionRepository(application);
        allSessions = repository.getAllSessions();
    }

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

    /* Bad: Going to try to use LiveData instead */
    @Deprecated
    public Session fetchSessionBySessionId(String id) {
        return repository.fetchSessionBySessionId(id);
    }

    public LiveData<Session> fetchSessionById(String id) {
        return repository.fetchSessionById(id);
    }
}
