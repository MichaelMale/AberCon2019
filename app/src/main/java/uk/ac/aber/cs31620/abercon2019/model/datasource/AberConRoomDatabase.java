package uk.ac.aber.cs31620.abercon2019.model.datasource;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import uk.ac.aber.cs31620.abercon2019.model.Favourite;
import uk.ac.aber.cs31620.abercon2019.model.Location;
import uk.ac.aber.cs31620.abercon2019.model.Session;
import uk.ac.aber.cs31620.abercon2019.model.Speaker;
import uk.ac.aber.cs31620.abercon2019.model.dao.FavouritesDao;
import uk.ac.aber.cs31620.abercon2019.model.dao.LocationDao;
import uk.ac.aber.cs31620.abercon2019.model.dao.SessionDao;
import uk.ac.aber.cs31620.abercon2019.model.dao.SpeakerDao;
import uk.ac.aber.cs31620.abercon2019.model.datasource.util.AssetSQLiteOpenHelperFactory;

/**
 * AberConRoomDatabase.java - A class to hold a database, utilising the Room data persistence API.
 * <p>
 * This database contains four tables:
 * <ul>
 * <li><b>Session</b> - Details of the sessions that are being held</li>
 * <li><b>Speaker</b> - Details of everyone who is speaking at the conference</li>
 * <li><b>Location</b> - Details of all buildings that are being used throughout the
 * conference</li>
 * <li><b>Favourites</b> - Details of any favourite sessions that the user has selected</li>
 * </ul>
 * <p>
 * Methods within this class use method calls to the API such that the database is generated and
 * correctly stored within the application's storage.
 * <p>
 * Code has been adapted from workshop 11 in CS31620 Mobile Development with Android at
 * Aberystwyth University.
 *
 * @author Michael Male
 * @version 2.0 PRODUCTION
 * @see Room
 * @see RoomDatabase
 */
@Database(entities = {Session.class, Speaker.class, Location.class, Favourite.class}, version = 2
        , exportSchema = false)
public abstract class AberConRoomDatabase extends RoomDatabase {
    private static final String DB_NAME = "abercon_database"; // Constant that holds the name
    // given to the database

    /**
     * Facilitates a migration from version 1 to version 2, that was used previously in the app's
     * creation.
     */
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.d("migrate", "Doing a migrate from version 1 to 2");
            // May require explicit SQL
        }
    };

    @SuppressLint("StaticFieldLeak")
    private static AberConRoomDatabase INSTANCE;

    /**
     * Method to retrieve the database instance and create a database if required.
     *
     * @param context Context of the application
     * @return An instance of this class that now has a generated database.
     */
    public static AberConRoomDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (AberConRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = createDatabase(context);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Creates a database. This uses an SQLite package to build a database from any given data.
     *
     * @param context The context of this application
     * @return An instance of this class that has a newly-created database.
     */
    private static AberConRoomDatabase createDatabase(Context context) {
        RoomDatabase.Builder<AberConRoomDatabase> builder = Room
                .databaseBuilder(context.getApplicationContext(), AberConRoomDatabase.class,
                        DB_NAME);

        return (builder.openHelperFactory(new AssetSQLiteOpenHelperFactory())
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .build());
    }

    /**
     * Gets the session DAO
     *
     * @return SessionDAO containing the session DAO
     */
    public abstract SessionDao getSessionDao();

    /**
     * Gets the location DAO
     *
     * @return LocationDAO containing the location DAO
     */
    public abstract LocationDao getLocationDao();

    /**
     * Gets the speaker DAO
     *
     * @return SpeakerDAO containing the speaker DAO
     */
    public abstract SpeakerDao getSpeakerDao();

    /**
     * Gets the favourites DAO
     *
     * @return FavouritesDAO containing the favourites DAO
     */
    public abstract FavouritesDao getFavouritesDao();
}
