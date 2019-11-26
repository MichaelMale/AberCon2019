package uk.ac.aber.cs31620.abercon2019.datasource;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aber.cs31620.abercon2019.datasource.util.AssetSQLiteOpenHelperFactory;
import uk.ac.aber.cs31620.abercon2019.model.Building;
import uk.ac.aber.cs31620.abercon2019.model.BuildingDao;

@Database(entities = {Building.class}, version = 1, exportSchema = false)
public abstract class ConferenceRoomDatabase extends RoomDatabase {

    public abstract BuildingDao getBuildingDao();

    private static ConferenceRoomDatabase INSTANCE;
    private static Context myContext;

    private static final String DB_NAME = "conference_database";

    public static ConferenceRoomDatabase getDatabase(final Context context) {
        // Use of Singleton design pattern
        myContext = context;

        if (INSTANCE == null) {
            synchronized (ConferenceRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = createDatabase(context);

                    // Code to migrate to a new version of the database

                    /*
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    ConferenceRoomDatabase.class, "conference_database").addMigrations
                    (MIGRATION_1_2, MIGRATION_2_3).build();
                     */
                }
            }
        }
        return INSTANCE;
    }

    private static ConferenceRoomDatabase createDatabase(Context context) {
        ConferenceRoomDatabase.Builder<ConferenceRoomDatabase> builder =
                Room.databaseBuilder(context.getApplicationContext(),
                        ConferenceRoomDatabase.class, DB_NAME);

        return (builder.openHelperFactory(new AssetSQLiteOpenHelperFactory())
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .build());
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.d("migrate", "Doing a migrate from version 1 to 2");
            /* May require an explicit query to create a table */
        }
    };


    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    //new PopulateDbASync(INSTANCE).execute();
                }
            };

    private static class PopulateDbASync extends AsyncTask<Void, Void, Void> {
        private final BuildingDao buildingDao;

        PopulateDbASync(ConferenceRoomDatabase db) {
            buildingDao = db.getBuildingDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            List<Building> buildingList = new ArrayList<>();
            buildingList.add(new Building("test", "The Test Building", 39.0290544, 125.6020276,
                    "A buidling" +
                            " " +
                            "catering towards testing of database implementations."));
            buildingDao.insertMultipleBuildings(buildingList);
            return null;
        }
    }
}
