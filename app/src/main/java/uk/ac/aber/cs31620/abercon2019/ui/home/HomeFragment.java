package uk.ac.aber.cs31620.abercon2019.ui.home;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;
import java.util.Random;

import uk.ac.aber.cs31620.abercon2019.R;
import uk.ac.aber.cs31620.abercon2019.model.Speaker;
import uk.ac.aber.cs31620.abercon2019.model.viewmodels.SpeakerViewModel;


/**
 * HomeFragment.java - This class contains the methods necessary for the operation of the home
 * fragment. The home fragment is the first fragment that the user will see, containing some text
 * introducing them to the app and cycling through a random speaker's name and photo.
 *
 * @author Michael Male
 * @version 1.0 2019-12-01
 */
public class HomeFragment extends Fragment {
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Creates the view for the home fragment. This method finds a random speaker image, name,
     * and description, and puts this onto the home screen each time the fragment is loaded. A
     * side effect of this is that it is loaded each time the state is changed.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_home,
                container, false);

        SpeakerViewModel speakerViewModel = ViewModelProviders.of(this)
                .get(SpeakerViewModel.class);

        final Random rand = new Random();
        final TextView viewForSpeakerName = inflate.findViewById(R.id.speaker_name);
        final TextView viewForSpeakerDescription = inflate.findViewById(R.id.speaker_description);
        final ImageView viewForSpeakerImage = inflate.findViewById(R.id.speaker_image);

        LiveData<List<Speaker>> allSpeakers = speakerViewModel.getAllSpeakers();

        allSpeakers.observe(HomeFragment.this, speakers -> {
            if (speakers != null && !speakers.isEmpty()) {
                final int randomSpeakerInt = rand.nextInt(speakers.size() - 1);
                Speaker randomSpeaker = speakers.get(randomSpeakerInt);
                viewForSpeakerName.setText(randomSpeaker.getName());
                viewForSpeakerDescription.setText(randomSpeaker.getBiography());

                Bitmap bitmap = randomSpeaker.getImagePath(viewForSpeakerImage.getContext());
                if (bitmap != null) {
                    viewForSpeakerImage.setImageBitmap(bitmap);
                    viewForSpeakerImage.setContentDescription(randomSpeaker.getName());
                } else {
                    Log.w("IMAGE_MISSING", randomSpeaker.getId() + " doesn't appear to" +
                            " have an associated image.");
                }
            }
        });

        return inflate;
    }

}
