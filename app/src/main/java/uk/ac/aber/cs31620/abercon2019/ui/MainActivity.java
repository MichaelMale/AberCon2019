package uk.ac.aber.cs31620.abercon2019.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import uk.ac.aber.cs31620.abercon2019.R;
import uk.ac.aber.cs31620.abercon2019.ui.home.HomeFragment;
import uk.ac.aber.cs31620.abercon2019.ui.favourites.FavouritesFragment;
import uk.ac.aber.cs31620.abercon2019.ui.speakers.SpeakersFragment;
import uk.ac.aber.cs31620.abercon2019.ui.events.EventsFragment;

/**
 * The MainActivity method. This method controls the main part of the app and manages fragments.
 *
 * @author Michael Male
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This method generates the required layouts as the app is created.
     * @param savedInstanceState Bundle containing any saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sets up the toolbar
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Sets up the ViewPager
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        // Sets up the tab layout
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }

    /**
     * This class manages the correct fragment to be displayed as the user swipes through
     * different parts of the app.
     */
    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Method containing a switch statement
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return new HomeFragment(); // HOME
                case 1: return new EventsFragment(); // EVENTS
                case 2: return new FavouritesFragment(); // FAVOURITES
                case 3: return new SpeakersFragment(); // SPEAKERS
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch(position) {
                case 0: return getText(R.string.nav_home);
                case 1: return getText(R.string.nav_whatson);
                case 2: return getText(R.string.nav_favourites);
                case 3: return getText(R.string.nav_speakers);
            }
            return null;
        }
    }
}
