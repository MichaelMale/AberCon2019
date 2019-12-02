package uk.ac.aber.cs31620.abercon2019.androidTest.databaseTests;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Favourite;
import uk.ac.aber.cs31620.abercon2019.model.Session;
import uk.ac.aber.cs31620.abercon2019.model.dao.FavouritesDao;
import uk.ac.aber.cs31620.abercon2019.model.dao.SessionDao;
import uk.ac.aber.cs31620.abercon2019.model.datasource.AberConRoomDatabase;
import uk.ac.aber.cs31620.abercon2019.model.datasource.util.LiveDataTestUtil;

/**
 * InsertFavouriteTest.java - A series of tests to confirm that the favourites functionality works.
 *
 * @author Michael Male
 * @version 1.0 2019-12-03
 */
@RunWith(AndroidJUnit4.class)
public class InsertFavouriteTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private SessionDao sessionDao;
    private FavouritesDao favouritesDao;
    private AberConRoomDatabase db;

    @Before
    public void getData() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        db = AberConRoomDatabase.getDatabase(context);
        sessionDao = db.getSessionDao();
        favouritesDao = db.getFavouritesDao();

        favouritesDao.deleteAll();
    }

    @After
    public void closeDb() {
        favouritesDao.deleteAll();
    }

    @Test
    public void onInsertingAFavourite_checkThat_FavouriteWasInserted()
            throws InterruptedException {
        Favourite test = new Favourite("coreml");
        favouritesDao.insertSingleFavourite(test);
        LiveData<List<Session>> favouriteSessions = sessionDao.getSessionsByFavourites();

        Assert.assertEquals(1, LiveDataTestUtil.getValue(favouriteSessions).size());
    }

    @Test
    public void onInsertingAFavourite_checkThat_CorrectSessionWasFoundAndInserted()
            throws InterruptedException {
        Favourite test = new Favourite("coreml");
        favouritesDao.insertSingleFavourite(test);
        LiveData<List<Session>> favouriteSessions = sessionDao.getSessionsByFavourites();
        LiveData<Session> testSession = sessionDao.fetchSessionById("coreml");

        Assert.assertEquals(LiveDataTestUtil.getValue(testSession),
                LiveDataTestUtil.getValue(favouriteSessions).get(0));
    }

    @Test
    public void onRemovingAFavourite_checkThat_FavouriteWasRemoved()
            throws InterruptedException {
        Favourite test = new Favourite("coreml");
        favouritesDao.insertSingleFavourite(test);
        LiveData<List<Session>> favouriteSessions = sessionDao.getSessionsByFavourites();
        favouritesDao.deleteFavouriteById("coreml");

        Assert.assertEquals(0, LiveDataTestUtil.getValue(favouriteSessions).size());
    }

    @Test
    public void onRemovingASpecificFavourite_checkThat_SpecificFavouriteWasRemoved()
            throws InterruptedException {

        Favourite[] tests = new Favourite[]{
                new Favourite("coreml"),
                new Favourite("havoc")
        };
        favouritesDao.insertMultipleFavourites(tests);
        LiveData<List<Session>> favouriteSessions = sessionDao.getSessionsByFavourites();
        favouritesDao.deleteFavouriteById("coreml");

        Assert.assertEquals(1, LiveDataTestUtil.getValue(favouriteSessions).size());
        Assert.assertEquals(tests[1].getId(),
                LiveDataTestUtil.getValue(favouriteSessions).get(0).getId());
    }


}
