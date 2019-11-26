package uk.ac.aber.cs31620.abercon2019.datasource;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Building;
import uk.ac.aber.cs31620.abercon2019.model.BuildingDao;

public class ConferenceRepository {
    private BuildingDao buildingDao;

    public ConferenceRepository(Application application) {
        ConferenceRoomDatabase db = ConferenceRoomDatabase.getDatabase(application);
        buildingDao = db.getBuildingDao();
    }

    public LiveData<List<Building>> getAllBuildings() {
        return buildingDao.getAllBuildings();
    }

    public void insert(Building building) {
        new InsertAsyncTask(buildingDao).execute(building);
    }

    private static class InsertAsyncTask extends AsyncTask<Building, Void, Void> {

        private BuildingDao mAsyncTaskDao;

        InsertAsyncTask(BuildingDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Building... params) {
            mAsyncTaskDao.insertMultipleBuildings(params);
            return null;
        }
    }
}
