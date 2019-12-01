package uk.ac.aber.cs31620.abercon2019.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.TypeConverters;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Location;
import uk.ac.aber.cs31620.abercon2019.model.util.SessionTypeConverter;

@Dao
@TypeConverters({SessionTypeConverter.class})
public interface LocationDao {

    @Query("SELECT * FROM locations")
    LiveData<List<Location>> getAllLocations();

    @Query("SELECT * FROM locations WHERE :id = id")
    LiveData<Location> fetchLocationById(String id);


}
