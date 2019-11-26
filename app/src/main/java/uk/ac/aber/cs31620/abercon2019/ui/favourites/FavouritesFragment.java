package uk.ac.aber.cs31620.abercon2019.ui.favourites;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import uk.ac.aber.cs31620.abercon2019.R;
import uk.ac.aber.cs31620.abercon2019.model.Session;
import uk.ac.aber.cs31620.abercon2019.model.SessionListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends Fragment {
    private LinkedList<Session> favouriteSessions = new LinkedList<>();

    public FavouritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_favourites, container, false);

        RecyclerView eventsRecycler = inflate.findViewById(R.id.favourites_recycler);

        String[] eventNames = new String[favouriteSessions.size()];
        for (int i = 0; i < eventNames.length; i++) {
            eventNames[i] = favouriteSessions.get(i).getTitle();
        }

        String[] eventDates = new String[favouriteSessions.size()];
        for (int i = 0; i < eventDates.length; i++) {
            String concatenated = "";
            concatenated += favouriteSessions.get(i).getSessionDate();
            concatenated += " ";
            concatenated += favouriteSessions.get(i).getTimeStart();
            eventDates[i] = concatenated;
        }

        SessionListAdapter adapter = new SessionListAdapter(eventNames, eventDates);
        eventsRecycler.setAdapter(adapter);


        return inflate;
    }

    public void updateFavourites(Session selectedFavourite) {
        favouriteSessions.add(selectedFavourite);
    }

}
