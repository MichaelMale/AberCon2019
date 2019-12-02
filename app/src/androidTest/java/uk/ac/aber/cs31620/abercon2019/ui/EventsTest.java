package uk.ac.aber.cs31620.abercon2019.ui;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.aber.cs31620.abercon2019.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * EventsTest.java - A series of Espresso UI tests that confirms that the events fragment is
 * working as intended.
 *
 * @author Michael Male
 * @version 1.0 2019-12-03
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EventsTest {

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void goToEventsTab() {
        onView(withText("Events"))
                .perform(click());
    }

    @Test
    public void clickOnEarliestEvent_OpensEventDetailsWithCorrectEventDisplayed() {
        onView(withId(R.id.short_session_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.view_event_details_activity))
                .check(matches(isDisplayed()));
        onView(withId(R.id.session_title))
                .check(matches(withText("Using ARKit with SpriteKit")));
    }

    @Test
    public void clickOnNonWorkshopTalkEvent_DoesNotOpenEventDetails() {
        onView(withId(R.id.short_session_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.view_event_details_activity))
                .check(doesNotExist());
    }

    @Test
    public void checkNoDatesBeforeEarliest() {
        onView(withId(R.id.date))
                .check(matches(withText("Tue, 10 Dec")));
        onView(withId(R.id.back_button))
                .perform(click());
        onView(withId(R.id.date))
                .check(matches(withText("Tue, 10 Dec")));
    }

    @Test
    public void checkNextDateButtonCyclesToNextDate() {
        onView(withId(R.id.date))
                .check(matches(withText("Tue, 10 Dec")));
        onView(withId(R.id.next_button))
                .perform(click());
        onView(withId(R.id.date))
                .check(matches(not(withText("Tue, 10 Dec"))));
    }


    @Test
    public void checkPreviousDateButtonCyclesToPreviousDate() {
        onView(withId(R.id.next_button))
                .perform(click());
        onView(withId(R.id.back_button))
                .perform(click());
        onView(withId(R.id.date))
                .check(matches(not(withText("Wed, 11 Dec"))));
    }

}
