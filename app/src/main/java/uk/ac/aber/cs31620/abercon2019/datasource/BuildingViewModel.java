package uk.ac.aber.cs31620.abercon2019.datasource;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Building;

public class BuildingViewModel extends AndroidViewModel {
    private ConferenceRepository repository;
    private LiveData<List<Building>> buildingList;

    public BuildingViewModel(@NonNull Application application) {
        super(application);
        repository = new ConferenceRepository(application);


    }
}
