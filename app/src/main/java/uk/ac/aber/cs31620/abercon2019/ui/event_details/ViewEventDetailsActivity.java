package uk.ac.aber.cs31620.abercon2019.ui.event_details;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

import uk.ac.aber.cs31620.abercon2019.R;
import uk.ac.aber.cs31620.abercon2019.model.Favourite;
import uk.ac.aber.cs31620.abercon2019.model.Location;
import uk.ac.aber.cs31620.abercon2019.model.Session;
import uk.ac.aber.cs31620.abercon2019.model.SessionType;
import uk.ac.aber.cs31620.abercon2019.model.Speaker;
import uk.ac.aber.cs31620.abercon2019.model.util.DateTimeConverter;
import uk.ac.aber.cs31620.abercon2019.model.viewmodels.FavouritesViewModel;
import uk.ac.aber.cs31620.abercon2019.model.viewmodels.LocationViewModel;
import uk.ac.aber.cs31620.abercon2019.model.viewmodels.SpeakerViewModel;

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
public class ViewEventDetailsActivity extends AppCompatActivity {
    public static final String SELECTED_SESSION = "SELECTED_SESSION";
    private Button favouriteButton;
    private Session selectedSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_event_details);

        Toolbar toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Event");

        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);

        favouriteButton = findViewById(R.id.favourite_button);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            selectedSession = (Session) bundle.getSerializable(SELECTED_SESSION);
            this.setDetails(Objects.requireNonNull(selectedSession));
        } else {
            Log.e("NO_DATA", "No data was passed to the event details activity.");
        }


    }


    private void setDetails(Session session) {
        // Firstly check if the event is a talk or a workshop. Both the button and the speaker
        // card need to be hidden if not
        boolean isTalkOrWorkshop = false;
        if (session.getSessionType() == SessionType.WORKSHOP || session.getSessionType() == SessionType.TALK) {
            isTalkOrWorkshop = true;
        }

        if (isTalkOrWorkshop) { // Events that aren't talks and workshops can't be favourited,
            // therefore there isn't any need for a favourite button.
            favouriteButton.setVisibility(View.VISIBLE);

        }

        // Set the title
        TextView viewForTitle = findViewById(R.id.session_title);
        viewForTitle.setText(session.getTitle());

        // Set the description
        TextView viewForDescription = findViewById(R.id.session_description);
        viewForDescription.setText(session.getContent());

        // Get a speaker LiveData by the speaker ID in the Session, only if the session has one

        SpeakerViewModel speakerViewModel = ViewModelProviders.of(this).get(SpeakerViewModel.class);
        LiveData<Speaker> speakerOfSession =
                speakerViewModel.fetchSpeakerById(session.getSpeakerId());
        speakerOfSession.observe(ViewEventDetailsActivity.this, speaker -> {
            if (speaker != null) {
                // Set the speaker name
                String speakerNameResource = getString(R.string.speaker_formatted_string,
                        speaker.getName());
                TextView viewForName = findViewById(R.id.speaker_card_name);
                viewForName.setText(speakerNameResource);

                // Set the speaker description
                TextView viewForSpeakerDescription = findViewById(R.id.speaker_card_description);
                viewForSpeakerDescription.setText(speaker.getBiography());

                // Set the speaker image
                ImageView viewForSpeakerImage = findViewById(R.id.speaker_card_image);
                Bitmap speakerImage = speaker.getImagePath(viewForSpeakerImage.getContext());
                if (speakerImage != null) {
                    viewForSpeakerImage.setImageBitmap(speakerImage);
                    viewForSpeakerImage.setContentDescription(speaker.getName());
                } else {
                    Log.w("IMAGE_MISSING", speaker.getId() + " doesn't appear to" +
                            " have an associated image.");
                }
            } else {
                CardView cardView = findViewById(R.id.speaker_details);
                cardView.setVisibility(View.GONE);
            }
        });


        // Set the session date
        String whenResource = getString(R.string.when_formatted_string,
                DateTimeConverter.toString(session.getSessionDate()));
        TextView viewForWhen = findViewById(R.id.when);
        viewForWhen.setText(whenResource);

        // Set the session time
        String timeResource = getString(R.string.time_formatted_string, session.getTimeStart(),
                session.getTimeEnd());
        TextView viewForTime = findViewById(R.id.time);
        viewForTime.setText(timeResource);

        // Get a location LiveData by the location ID in the Session. Sessions should always have
        // a location ID
        LocationViewModel locationViewModel =
                ViewModelProviders.of(this).get(LocationViewModel.class);
        LiveData<Location> locationOfSession =
                locationViewModel.fetchLocationById(session.getLocationId());
        locationOfSession.observe(ViewEventDetailsActivity.this, location -> {
            if (location != null) {
                // Set the session location
                String whereResource = getString(R.string.where_formatted_string,
                        location.getName());
                TextView viewForWhere = findViewById(R.id.where);
                viewForWhere.setText(whereResource);

                // Set up the map, requiring the Google Play Services API. This works by passing
                // the latitude and longitude of the location to the API through a lambda expression
                SupportMapFragment mapFragment =
                        (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.google_map);
                Objects.requireNonNull(mapFragment)
                        .getMapAsync(googleMap -> {
                            // Get the co-ordinates of where the session is
                            LatLng currentLoc = new LatLng(location.getLatitude(),
                                    location.getLongitude());

                            googleMap.addMarker(new MarkerOptions()
                                    .position(currentLoc)
                                    .title(location.getName()));

                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLoc));

                            googleMap.setMinZoomPreference(15); // Zooms in close enough to see
                            // where the building is, but not too far outwards as all locations
                            // are intended to be in Aberystwyth.
                        });
            }
        });

    }

    public void openFavouritesDialog(View view) {
        DialogInterface.OnClickListener dialogClickListener =
                (dialog, which) -> {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            addSessionToFavourites();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            // Cancel button clicked
                            dialog.dismiss();
                            break;
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(getString(R.string.add_to_favourites))
                .setMessage(getString(R.string.favourite_dialog_text))
                .setNegativeButton(getString(R.string.cancel), dialogClickListener)
                .setPositiveButton(getString(R.string.ok), dialogClickListener)
                .show();
    }

    private void addSessionToFavourites() {
        Favourite fave = new Favourite();
        fave.setSessionId(selectedSession.getId());
        fave.setLocationId(selectedSession.getLocationId());
        fave.setSpeakerId(selectedSession.getSpeakerId());
        FavouritesViewModel favouritesViewModel
                = ViewModelProviders.of(this).get(FavouritesViewModel.class);
        favouritesViewModel.insert(fave);
    }
}
