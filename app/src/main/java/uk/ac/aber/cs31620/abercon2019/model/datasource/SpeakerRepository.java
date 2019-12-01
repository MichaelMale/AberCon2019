package uk.ac.aber.cs31620.abercon2019.model.datasource;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Speaker;
import uk.ac.aber.cs31620.abercon2019.model.dao.SpeakerDao;

public class SpeakerRepository {
    private SpeakerDao speakerDao;

    public SpeakerRepository(Application application) {
        AberConRoomDatabase db = AberConRoomDatabase.getDatabase(application);
        speakerDao = db.getSpeakerDao();
    }

    public LiveData<Speaker> fetchSpeakerById(String id) {
        return speakerDao.fetchSpeakerById(id);
    }

    public LiveData<List<Speaker>> getAllSpeakers() {
        return speakerDao.getAllSpeakers();
    }

}
