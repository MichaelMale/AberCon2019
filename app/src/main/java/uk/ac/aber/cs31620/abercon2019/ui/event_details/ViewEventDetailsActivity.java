package uk.ac.aber.cs31620.abercon2019.ui.event_details;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

import uk.ac.aber.cs31620.abercon2019.R;
import uk.ac.aber.cs31620.abercon2019.model.Building;
import uk.ac.aber.cs31620.abercon2019.model.Session;
import uk.ac.aber.cs31620.abercon2019.model.SessionType;
import uk.ac.aber.cs31620.abercon2019.model.Speaker;

/**
 * ViewEventDetailsActivity.java - A class that defines the event detail activity. This activity
 * is intended to be called from the events fragment, where one clicks on an event in that
 * fragment's recycler view. It shows details of the event and allows you to add the event to
 * your favourites.
 *
 * @author Michael Male mim39@aber.ac.uk
 * @see AppCompatActivity
 * @see OnMapReadyCallback
 * @see GoogleMap
 */
public class ViewEventDetailsActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    Button favouriteButton;

    public static final String EXTRA_SESSION_ID = "id";
    private Session selectedSession;

    /**
     * Method to run setup methods in preparation for the execution of the program.
     *
     * @param savedInstanceState Any bundle that has saved the instance of the program.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_details);

        int sessionId = (Integer) getIntent().getExtras().get(EXTRA_SESSION_ID);
        selectedSession = Session.sessions[sessionId];

        // Set the toolbar
        Toolbar toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Event");

        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);

        // Fetch the favourite button - this needs to be hidden for events that aren't talks and
        // workshops
        favouriteButton = findViewById(R.id.favourite_button);

        setupViews();
    }

    /**
     * Method to set up the respective views within the design. It takes pointers from the
     * Session global variable and adds them to specific parts of the design.
     * TODO: Consider making this an asynchronous task as large sets of data are introduced
     *
     * @see Session
     * @see Building
     * @see Speaker
     */
    private void setupViews() {

        // Firstly check if the event is a talk or a workshop. The button needs to be hidden
        // if it is not.

        if (selectedSession.getSessionType() == SessionType.WORKSHOP ||
                selectedSession.getSessionType() == SessionType.TALK) {
            favouriteButton.setVisibility(View.VISIBLE);
        }

        Building sessionBuilding = selectedSession.getBuildingId();
        Speaker sessionSpeaker = selectedSession.getSpeaker();


        TextView viewForTitle = findViewById(R.id.session_title);
        viewForTitle.setText(selectedSession.getTitle());

        TextView viewForDescription = findViewById(R.id.session_description);
        viewForDescription.setText(selectedSession.getContent());

        String speakerNameResource = getString(R.string.speaker_formatted_string,
                sessionSpeaker.getName());
        TextView viewForName = findViewById(R.id.speaker_card_name);
        viewForName.setText(speakerNameResource);

        TextView viewForSpeakerDescription = findViewById(R.id.speaker_card_description);
        viewForSpeakerDescription.setText(sessionSpeaker.getBiography());

        String whenResource = getString(R.string.when_formatted_string,
                selectedSession.getSessionDate());
        TextView viewForWhen = findViewById(R.id.when);
        viewForWhen.setText(whenResource);

        String timeResource = getString(R.string.time_formatted_string,
                selectedSession.getTimeStart(), selectedSession.getTimeEnd());
        TextView viewForTime = findViewById(R.id.time);
        viewForTime.setText(timeResource);

        String whereResource = getString(R.string.where_formatted_string,
                sessionBuilding.getName());
        TextView viewForWhere = findViewById(R.id.where);
        viewForWhere.setText(whereResource);

        // MapView - this requires the Google Play Services API
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
    }


    /**
     * This is an implemented method that is intended to prepare the Google Map part of the
     * program. It does this by taking a reference to the building that the session is in, and
     * setting a marker to be where its recorded latitude and longitude is.
     * <p>
     * The API key for the Google Map API has been included in the Android manifest, but the key
     * is not linked to a billing account. The map is only using Place mode, which, according to
     * Google's website, is free, and does not use advanced requests (see
     * <a href="https://developers.google.com/maps/documentation/embed/usage-and-billing">here</a>)
     * - I turned it off from the console anyway.
     *
     * @param googleMap Object of type GoogleMap containing the current map
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        // get the coordinates of where the event is
        Building currentBuilding = selectedSession.getBuildingId();
        double latitude = currentBuilding.getLatitude();
        double longitude = currentBuilding.getLongitude();

        LatLng currentLoc = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(currentLoc).title("Event location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLoc));
        googleMap.setMinZoomPreference(15); // Zooms in close enough to see where the building is, not
        // too far outwards as all locations are intended to be in Aberystwyth
    }

    /**
     * Opens the 'Add to favourites' dialog box. This is called when the user presses the 'Add to
     * favourites' button.
     *
     * @param view The view that was clicked
     */
    public void openFavouritesDialog(View view) {

        DialogInterface.OnClickListener dialogClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                addSessionToFavourites();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                // Cancel button clicked
                                dialog.dismiss();
                                break;
                        }
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(getString(R.string.add_to_favourites))
                .setMessage(getString(R.string.favourite_dialog_text))
                .setNegativeButton(getString(R.string.cancel), dialogClickListener)
                .setPositiveButton(getString(R.string.ok), dialogClickListener)
                .show();

    }

    /**
     * Adds the current Session into a list of favourites that are on a separate fragment.
     */
    private void addSessionToFavourites() {
        // TODO: Pass the favourite object to the list in FavouritesFragment. First going to link
        //  up SQLite database.
    }
}
