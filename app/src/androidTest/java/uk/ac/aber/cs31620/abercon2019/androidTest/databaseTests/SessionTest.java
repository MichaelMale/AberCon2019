package uk.ac.aber.cs31620.abercon2019.androidTest.databaseTests;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.Date;

import uk.ac.aber.cs31620.abercon2019.model.Location;
import uk.ac.aber.cs31620.abercon2019.model.Session;
import uk.ac.aber.cs31620.abercon2019.model.SessionType;
import uk.ac.aber.cs31620.abercon2019.model.Speaker;
import uk.ac.aber.cs31620.abercon2019.model.dao.LocationDao;
import uk.ac.aber.cs31620.abercon2019.model.dao.SessionDao;
import uk.ac.aber.cs31620.abercon2019.model.dao.SpeakerDao;
import uk.ac.aber.cs31620.abercon2019.model.datasource.AberConRoomDatabase;
import uk.ac.aber.cs31620.abercon2019.model.datasource.util.LiveDataTestUtil;
import uk.ac.aber.cs31620.abercon2019.model.util.DateTimeConverter;

/**
 * SessionTest.java - A series of tests to confirm that the sessions table works.
 *
 * @author Michael Male
 * @version 1.0 2019-12-03
 */
@RunWith(AndroidJUnit4.class)
public class SessionTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private SessionDao sessionDao;
    private LocationDao locationDao;
    private SpeakerDao speakerDao;

    private AberConRoomDatabase db;

    @Before
    public void getData() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        db = AberConRoomDatabase.getDatabase(context);
        sessionDao = db.getSessionDao();
        locationDao = db.getLocationDao();
        speakerDao = db.getSpeakerDao();
    }

    @Test
    public void onQueryingSessionSpeaker_checkThat_correctSpeakerIsReturned()
            throws InterruptedException {
        final String sessionId = "coreml";
        LiveData<Session> test = sessionDao.fetchSessionById(sessionId);
        LiveData<Speaker> testSpeaker =
                speakerDao.fetchSpeakerById(LiveDataTestUtil.getValue(test).getSpeakerId());

        Assert.assertEquals(LiveDataTestUtil.getValue(test).getSpeakerId(),
                LiveDataTestUtil.getValue(testSpeaker).getId());
    }

    @Test
    public void onQueryingSessionById_checkThat_correctTitleIsReturned()
            throws InterruptedException {
        final String sessionId = "coreml";
        final String sessionTitle = "I'll tell you what you can do with Core ML";
        LiveData<Session> test = sessionDao.fetchSessionById(sessionId);

        Assert.assertEquals(LiveDataTestUtil.getValue(test).getTitle(),
                sessionTitle);
    }

    @Test
    public void onQueryingSessionDate_checkThat_correctDateIsParsedAndReturned()
            throws InterruptedException {
        final String sessionId = "coreml";
        final Date testDate = DateTimeConverter.toDate("2019-12-11");

        LiveData<Session> test = sessionDao.fetchSessionById(sessionId);

        Assert.assertEquals(testDate,
                LiveDataTestUtil.getValue(test).getSessionDate());

    }

    @Test
    public void onQueryingSessionType_checkThat_correctTypeIsParsedAndReturned()
            throws InterruptedException {
        final String registrationId = "registration";
        final String talkId = "serverswift";
        LiveData<Session> registration = sessionDao.fetchSessionById(registrationId);
        LiveData<Session> talk = sessionDao.fetchSessionById(talkId);

        Assert.assertEquals(LiveDataTestUtil.getValue(registration).getSessionType(),
                SessionType.REGISTRATION);

        Assert.assertEquals(LiveDataTestUtil.getValue(talk).getSessionType(),
                SessionType.TALK);
    }

    @Test
    public void onQueryingSessionLocation_checkThat_correctLocationIsReturned()
            throws InterruptedException {
        final String spoons = "spoons";
        LiveData<Session> wetherspoons = sessionDao.fetchSessionById(spoons);
        LiveData<Location> pub = locationDao.fetchLocationById(spoons);

        Assert.assertEquals(LiveDataTestUtil.getValue(wetherspoons).getLocationId(),
                LiveDataTestUtil.getValue(pub).getId());
    }


}
