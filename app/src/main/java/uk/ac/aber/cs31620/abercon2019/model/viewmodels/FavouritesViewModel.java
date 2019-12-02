package uk.ac.aber.cs31620.abercon2019.model.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Favourite;
import uk.ac.aber.cs31620.abercon2019.model.datasource.FavouritesRepository;

/**
 * FavouritesViewModel.java - A view model to manage calls to the database on the UI thread.
 *
 * @author Michael Male
 * @version 1.0 2019-12-03
 * @see AndroidViewModel
 */
public class FavouritesViewModel extends AndroidViewModel {
    private FavouritesRepository repository;
    private LiveData<List<Favourite>> allFavourites;

    /**
     * Constructor for objects of type FavouritesViewModel.
     *
     * @param application Object of type Application pertaining to the app that is currently
     *                    running.
     */
    public FavouritesViewModel(@NonNull Application application) {
        super(application);
        repository = new FavouritesRepository(application);
        allFavourites = repository.getAllFavourites();
    }

    /* Interface methods */

    public LiveData<List<Favourite>> getAllFavourites() {
        return allFavourites;
    }

    public LiveData<Integer> checkIfFavourite(String id) {
        return repository.checkIfFavourite(id);
    }

    public void insert(Favourite favourite) {
        repository.insert(favourite);
    }

    public void deleteFavourite(Favourite favourite) {
        repository.deleteFavourite(favourite);
    }

    public void deleteFavouriteById(String id) {
        repository.deleteFavouriteById(id);
    }
}
