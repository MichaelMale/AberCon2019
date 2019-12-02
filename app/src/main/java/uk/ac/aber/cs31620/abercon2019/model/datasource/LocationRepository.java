package uk.ac.aber.cs31620.abercon2019.model.datasource;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Location;
import uk.ac.aber.cs31620.abercon2019.model.dao.LocationDao;

/**
 * LocationRepository.java - A class containing repository methods for the location table,
 * following the Android Repository pattern.
 *
 * @author Michael Male
 * @version 1.0 2019-12-03
 */
public class LocationRepository {
    private LocationDao locationDao; // Instance variable containing the DAO

    /**
     * Constructor for objects of type LocationRepository.
     *
     * @param application Object of type Application pertaining to the app that is currently
     *                    running.
     */
    public LocationRepository(Application application) {
        AberConRoomDatabase db = AberConRoomDatabase.getDatabase(application);
        locationDao = db.getLocationDao();
    }

    /* Interface methods */

    public LiveData<Location> fetchLocationById(String id) {
        return locationDao.fetchLocationById(id);
    }

    public LiveData<List<Location>> getAllLocations() {
        return locationDao.getAllLocations();
    }

}
