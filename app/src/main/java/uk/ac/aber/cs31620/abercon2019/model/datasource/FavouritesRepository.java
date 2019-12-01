package uk.ac.aber.cs31620.abercon2019.model.datasource;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Favourite;
import uk.ac.aber.cs31620.abercon2019.model.dao.FavouritesDao;

public class FavouritesRepository {
    private FavouritesDao favouritesDao;

    public FavouritesRepository(Application application) {
        AberConRoomDatabase db = AberConRoomDatabase.getDatabase(application);
        favouritesDao = db.getFavouritesDao();
    }

    public LiveData<List<Favourite>> getAllFavourites() {
        return favouritesDao.getAllFavourites();
    }

    public void insert(Favourite favourite) {
        new InsertAsyncTask(favouritesDao).execute(favourite);
    }

    private static class InsertAsyncTask extends AsyncTask<Favourite, Void, Void> {

        private FavouritesDao mAsyncTaskDao;

        InsertAsyncTask(FavouritesDao favouritesDao) {
            mAsyncTaskDao = favouritesDao;
        }

        @Override
        protected Void doInBackground(Favourite... favourites) {
            mAsyncTaskDao.insertMultipleFavourites(favourites);
            return null;
        }
    }
}
