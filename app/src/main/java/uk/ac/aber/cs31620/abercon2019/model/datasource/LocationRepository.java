package uk.ac.aber.cs31620.abercon2019.model.datasource;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Location;
import uk.ac.aber.cs31620.abercon2019.model.dao.LocationDao;


public class LocationRepository {
    private LocationDao locationDao;

    public LocationRepository(Application application) {
        AberConRoomDatabase db = AberConRoomDatabase.getDatabase(application);
        locationDao = db.getLocationDao();
    }

    public LiveData<Location> fetchLocationById(String id) {
        return locationDao.fetchLocationById(id);
    }

    public LiveData<List<Location>> getAllLocations() {
        return locationDao.getAllLocations();
    }

}
