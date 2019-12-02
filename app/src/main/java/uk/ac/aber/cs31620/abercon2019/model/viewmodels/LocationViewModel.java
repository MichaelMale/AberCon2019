package uk.ac.aber.cs31620.abercon2019.model.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Location;
import uk.ac.aber.cs31620.abercon2019.model.datasource.LocationRepository;

/**
 * LocationViewModel.java - A view model to manage calls to the database on the UI thread.
 *
 * @author Michael Male
 * @version 1.0 2019-12-03
 * @see AndroidViewModel
 */
public class LocationViewModel extends AndroidViewModel {
    private LocationRepository repository;
    private LiveData<List<Location>> allLocations;

    /**
     * Constructor for objects of type LocationViewModel.
     *
     * @param application Object of type Application pertaining to the app that is currently
     *                    running.
     */
    public LocationViewModel(@NonNull Application application) {
        super(application);
        repository = new LocationRepository(application);
        allLocations = repository.getAllLocations();
    }

    /* Interface methods */

    public LiveData<List<Location>> getAllLocations() {
        return allLocations;
    }

    public LiveData<Location> fetchLocationById(String id) {
        return repository.fetchLocationById(id);
    }
}
