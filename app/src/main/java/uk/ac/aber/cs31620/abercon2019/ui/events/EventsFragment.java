package uk.ac.aber.cs31620.abercon2019.ui.events;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import uk.ac.aber.cs31620.abercon2019.R;
import uk.ac.aber.cs31620.abercon2019.model.Session;
import uk.ac.aber.cs31620.abercon2019.model.SessionListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {


    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // hmm
        View sessions = inflater.inflate(R.layout.fragment_events, container, false);
        RecyclerView eventsRecycler =
                sessions.findViewById(R.id.short_session_recycler);

        String[] eventNames = new String[Session.sessions.length];
        for (int i = 0; i < eventNames.length; i ++) {
            eventNames[i] = Session.sessions[i].getTitle();
        }

        String[] eventDates = new String[Session.sessions.length];
        for (int i = 0; i < eventDates.length; i ++) {
            String concatenated = "";
            concatenated += Session.sessions[i].getSessionDate();
            concatenated += " ";
            concatenated += Session.sessions[i].getTimeStart();
            eventDates[i] = concatenated;
        }

        SessionListAdapter adapter = new SessionListAdapter(eventNames, eventDates);
        eventsRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        eventsRecycler.setLayoutManager(layoutManager);

        return sessions;
    }

}
