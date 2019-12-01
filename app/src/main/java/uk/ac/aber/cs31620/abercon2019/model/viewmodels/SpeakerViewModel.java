package uk.ac.aber.cs31620.abercon2019.model.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Speaker;
import uk.ac.aber.cs31620.abercon2019.model.datasource.SpeakerRepository;

public class SpeakerViewModel extends AndroidViewModel {
    private SpeakerRepository repository;
    private LiveData<List<Speaker>> allSpeakers;

    public SpeakerViewModel(@NonNull Application application) {
        super(application);
        repository = new SpeakerRepository(application);
        allSpeakers = repository.getAllSpeakers();
    }

    public LiveData<List<Speaker>> getAllSpeakers() {
        return allSpeakers;
    }

    public LiveData<Speaker> fetchSpeakerById(String id) {
        return repository.fetchSpeakerById(id);
    }


}
