package uk.ac.aber.cs31620.abercon2019.model.datasource;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Speaker;
import uk.ac.aber.cs31620.abercon2019.model.dao.SpeakerDao;

/**
 * SpeakerRepository.java - A class containing repository methods for the speaker table,
 * following the Android repository pattern.
 *
 * @author Michael Male
 * @version 1.0 2019-12-03
 */
public class SpeakerRepository {
    private SpeakerDao speakerDao; // Instance variable containing the DAO.

    /**
     * Constructor for objects of type SpeakerRepository.
     *
     * @param application Object of type Application pertaining to the app that is currently
     *                    running.
     */
    public SpeakerRepository(Application application) {
        AberConRoomDatabase db = AberConRoomDatabase.getDatabase(application);
        speakerDao = db.getSpeakerDao();
    }

    /* Interface methods */

    public LiveData<Speaker> fetchSpeakerById(String id) {
        return speakerDao.fetchSpeakerById(id);
    }

    public LiveData<List<Speaker>> getAllSpeakers() {
        return speakerDao.getAllSpeakers();
    }

}
