package uk.ac.aber.cs31620.abercon2019.model.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Favourite;
import uk.ac.aber.cs31620.abercon2019.model.datasource.FavouritesRepository;

public class FavouritesViewModel extends AndroidViewModel {
    private FavouritesRepository repository;
    private LiveData<List<Favourite>> allFavourites;

    public FavouritesViewModel(@NonNull Application application) {
        super(application);
        repository = new FavouritesRepository(application);
        allFavourites = repository.getAllFavourites();
    }

    public LiveData<List<Favourite>> getAllFavourites() {
        return allFavourites;
    }

    public void insert(Favourite favourite) {
        repository.insert(favourite);
    }
}
