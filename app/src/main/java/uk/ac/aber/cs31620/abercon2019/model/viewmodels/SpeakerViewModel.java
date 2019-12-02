package uk.ac.aber.cs31620.abercon2019.model.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Speaker;
import uk.ac.aber.cs31620.abercon2019.model.datasource.SpeakerRepository;

/**
 * SpeakerViewModel.java - A view model to manage calls to the database on the UI thread.
 *
 * @author Michael Male
 * @version 1.0 2019-12-03
 * @see AndroidViewModel
 */
public class SpeakerViewModel extends AndroidViewModel {
    private SpeakerRepository repository;
    private LiveData<List<Speaker>> allSpeakers;

    /**
     * Constructor for objects of type SpeakerViewModel.
     *
     * @param application Object of type Application pertaining to the app that is currently
     *                    running.
     */
    public SpeakerViewModel(@NonNull Application application) {
        super(application);
        repository = new SpeakerRepository(application);
        allSpeakers = repository.getAllSpeakers();
    }

    /* Interface methods */

    public LiveData<List<Speaker>> getAllSpeakers() {
        return allSpeakers;
    }

    public LiveData<Speaker> fetchSpeakerById(String id) {
        return repository.fetchSpeakerById(id);
    }


}
