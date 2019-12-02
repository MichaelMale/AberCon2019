package uk.ac.aber.cs31620.abercon2019.model.datasource;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Favourite;
import uk.ac.aber.cs31620.abercon2019.model.dao.FavouritesDao;

/**
 * FavouritesRepository.java - A class containing repository methods for the favourites table,
 * following the Android Repository Pattern.
 *
 * @author Michael Male
 * @version 1.0 2019-12-03
 */
public class FavouritesRepository {
    private FavouritesDao favouritesDao; // Contains an instance variable linking to the DAO
    // interface

    /**
     * Constructor for objects of type FavouritesRepository.
     *
     * @param application Object of type Application pertaining to the app that is currently
     *                    running.
     */
    public FavouritesRepository(Application application) {
        AberConRoomDatabase db = AberConRoomDatabase.getDatabase(application);
        favouritesDao = db.getFavouritesDao();
    }


    /* Methods implemented from DAO interface */

    public LiveData<List<Favourite>> getAllFavourites() {
        return favouritesDao.getAllFavourites();
    }

    public LiveData<Integer> checkIfFavourite(String id) {
        return favouritesDao.checkIfFavourite(id);
    }

    public void insert(Favourite favourite) {
        new InsertAsyncTask(favouritesDao).execute(favourite);
    }

    public void deleteFavouriteById(String id) {
        favouritesDao.deleteFavouriteById(id);
    }

    public void deleteFavourite(Favourite favourite) {
        new DeleteFavouriteAsyncTask(favouritesDao).execute(favourite);
    }

    /**
     * InsertAsyncTask - facilitates an asynchronous insert operation into the SQLite database.
     */
    private static class InsertAsyncTask extends AsyncTask<Favourite, Void, Void> {

        private FavouritesDao mAsyncTaskDao;

        /**
         * Constructor for objects of type InsertAsyncTask.
         *
         * @param favouritesDao The DAO to be used during this task.
         */
        InsertAsyncTask(FavouritesDao favouritesDao) {
            mAsyncTaskDao = favouritesDao;
        }

        /**
         * Inserts one to many Favourite objects
         * @param favourites Either a single Favourite variable or Favourite[] containing objects
         *                  to be added into the database.
         * @return null
         */
        @Override
        protected Void doInBackground(Favourite... favourites) {
            mAsyncTaskDao.insertMultipleFavourites(favourites);
            return null;
        }
    }

    /**
     * DeleteFavouriteAsyncTask - facilitates an asynchronous delete operation on the SQLite
     * database.
     */
    private static class DeleteFavouriteAsyncTask extends AsyncTask<Favourite, Void, Void> {
        private FavouritesDao mAsyncTaskDao;

        /**
         * Constructor for objects of type DeleteFavouriteAsyncTask.
         * @param dao   The DAO to be used during this task.
         */
        DeleteFavouriteAsyncTask(FavouritesDao dao) {
            mAsyncTaskDao = dao;
        }

        /**
         * Deletes one favourite object
         * @param favourites Favourite[]. It is intended that there is only one variable passed
         *                   to this method, therefore only the first member of the array will be
         *                   deleted.
         * @return null
         */
        @Override
        protected Void doInBackground(Favourite... favourites) {
            mAsyncTaskDao.deleteFavourite(favourites[0]);
            return null;
        }
    }
}
