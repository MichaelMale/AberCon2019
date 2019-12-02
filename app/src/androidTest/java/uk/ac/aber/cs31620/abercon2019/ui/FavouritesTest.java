package uk.ac.aber.cs31620.abercon2019.ui;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.aber.cs31620.abercon2019.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * FavouritesTest.java - A series of Espresso UI tests to confirm that the favourites fragment is
 * working as intended.
 *
 * @author Michael Male
 * @version 1.0 2019-12-03
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class FavouritesTest {

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkIfEventIsAddedToFavourites() {
        onView(withText("Events"))
                .perform(click());
        onView(withText("Using ARKit with SpriteKit"))
                .perform(click());
        onView(withId(R.id.favourite_button))
                .perform(click());

        onView(withText(R.string.favourite_dialog_text))
                .check(matches(isDisplayed()));

        onView(withText("OK"))
                .perform(click());

        pressBack();

        onView(withText("Favourites"))
                .perform(click());
        onView(withId(R.id.favourites_recycler))
                .check(matches(isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.session_title))
                .check(matches(withText("Using ARKit with SpriteKit")));

    }

    @Test
    public void clickOnNonWorkshopTalkEvent_checkEventCannotBeFavourited() {
        onView(withText("Events"))
                .perform(click());

    }

}
