package uk.ac.aber.cs31620.abercon2019.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Favourite;

/**
 * FavouritesDao.java - A Room interface to hold SQL queries pertaining to certain required
 * actions within the Favourites table.
 *
 * @author Michael Male
 * @version 1.0 2019-12-03
 */
@Dao
public interface FavouritesDao {


    /**
     * Performs an SQLite insert operation for a single object.
     *
     * @param favourite Object of type Favourite to be inserted into the database.
     */
    @Insert
    void insertSingleFavourite(Favourite favourite);

    /**
     * Performs an SQLIte insert operation for multiple objects in the form of an array.
     *
     * @param favourites Array of type Favourite to be inserted into the database.
     */
    @Insert
    void insertMultipleFavourites(Favourite[] favourites);

    /**
     * Performs an SQLite delete operation for a single object.
     *
     * @param favourite The object to be removed.
     */
    @Delete
    void deleteFavourite(Favourite favourite);

    /**
     * Deletes all items from favourites.
     */
    @Query("DELETE FROM favourites")
    void deleteAll();

    /**
     * Selects all items in the favourites relation.
     *
     * @return List containing objects of type Favourite.
     */
    @Query("SELECT * FROM favourites")
    LiveData<List<Favourite>> getAllFavourites();

    /**
     * Utilises the 'SELECT EXISTS' SQLite operation. The method selects all tuples from the
     * favourites relation where the session ID is equal to the given String.
     *
     * @param id String containing a session ID
     * @return LiveData of type Integer that contains how many rows have been found with that
     * session ID. In the given implementation of the table, this should always either be 0 or 1.
     */
    @Query("SELECT EXISTS (SELECT * FROM favourites WHERE id = :id)")
    LiveData<Integer> checkIfFavourite(String id);

    /**
     * Utilising the 'DELETE FROM' SQLite operation, the method deletes a specific favourite by
     * its primary key, the ID.
     *
     * @param id String containing the ID of the favourite.
     */
    @Query("DELETE FROM favourites WHERE id = :id")
    void deleteFavouriteById(String id);


}
