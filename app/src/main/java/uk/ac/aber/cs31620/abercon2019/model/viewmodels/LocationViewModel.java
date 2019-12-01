package uk.ac.aber.cs31620.abercon2019.model.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Location;
import uk.ac.aber.cs31620.abercon2019.model.datasource.LocationRepository;

public class LocationViewModel extends AndroidViewModel {
    private LocationRepository repository;
    private LiveData<List<Location>> allLocations;

    public LocationViewModel(@NonNull Application application) {
        super(application);
        repository = new LocationRepository(application);
        allLocations = repository.getAllLocations();
    }

    public LiveData<List<Location>> getAllLocations() {
        return allLocations;
    }

    public LiveData<Location> fetchLocationById(String id) {
        return repository.fetchLocationById(id);
    }
}
