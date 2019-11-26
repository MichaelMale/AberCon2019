package uk.ac.aber.cs31620.abercon2019.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BuildingDao {

    @Insert(onConflict = REPLACE)
    void insertSingleBuilding(Building building);

    @Insert
    void insertMultipleBuildings(Building[] buildings);

    @Insert
    void insertMultipleBuildings(List<Building> buildings);

    @Update(onConflict = REPLACE)
    void updateBuilding(Building building);

    @Delete
    void deleteBuilding(Building building);

    @Query("DELETE FROM locations")
    void deleteAll();

    @Query("SELECT * FROM locations")
    LiveData<List<Building>> getAllBuildings();

    @Query("SELECT * FROM locations WHERE id = :id")
    Building fetchBuildingById(String id);

    @Query("SELECT * FROM locations WHERE description= :description")
    List<Building> fetchBuildingsByDescription(String description);


}
