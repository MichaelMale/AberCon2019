package uk.ac.aber.cs31620.abercon2019.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.TypeConverters;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Location;
import uk.ac.aber.cs31620.abercon2019.model.util.SessionTypeConverter;

/**
 * LocationDao.java - The Data Access Object that manages the Location table. It utilises a
 * converter class to convert a String containing a SessionType into an Enum.
 *
 * @author Michael Male
 * @version 1.0 2019-12-03
 */
@Dao
@TypeConverters({SessionTypeConverter.class})
public interface LocationDao {

    /**
     * Queries the database to return all locations within that table.
     *
     * @return List of type Location containing all locations.
     */
    @Query("SELECT * FROM locations")
    LiveData<List<Location>> getAllLocations();

    /**
     * Queries the database to return a single location where its ID is the same as the given
     * String.
     *
     * @param id String containing a location ID.
     * @return LiveData of type Location containing the location pertaining to that ID.
     */
    @Query("SELECT * FROM locations WHERE :id = id")
    LiveData<Location> fetchLocationById(String id);


}
