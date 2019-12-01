package uk.ac.aber.cs31620.abercon2019.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Favourite;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface FavouritesDao {

    @Insert(onConflict = REPLACE)
    void insertSingleFavourite(Favourite favourite);

    @Insert
    void insertMultipleFavourites(Favourite[] favourites);

    @Update(onConflict = REPLACE)
    int updateFavourite(Favourite favourite);

    @Delete
    void deleteFavourite(Favourite favourite);

    @Query("DELETE FROM favourites")
    void deleteAll();

    @Query("SELECT * FROM favourites")
    LiveData<List<Favourite>> getAllFavourites();


}
