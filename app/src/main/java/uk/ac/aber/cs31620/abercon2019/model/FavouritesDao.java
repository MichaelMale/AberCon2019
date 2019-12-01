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
public interface FavouritesDao {

    @Insert(onConflict = REPLACE)
    void insertSingleFavourite(Favourites favourite);

    @Update(onConflict = REPLACE)
    int updateFavourite(Favourites favourites);

    @Delete
    void deleteFavourite(Favourites favourites);

    @Query("DELETE FROM favourites")
    void deleteAll();

    @Query("SELECT * FROM favourites")
    LiveData<List<Favourites>> getAllFavourites();


}
