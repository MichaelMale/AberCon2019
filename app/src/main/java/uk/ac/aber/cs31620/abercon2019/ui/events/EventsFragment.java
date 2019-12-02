package uk.ac.aber.cs31620.abercon2019.ui.events;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import uk.ac.aber.cs31620.abercon2019.R;
import uk.ac.aber.cs31620.abercon2019.model.EventsAdapter;
import uk.ac.aber.cs31620.abercon2019.model.Session;
import uk.ac.aber.cs31620.abercon2019.model.util.DateComparator;
import uk.ac.aber.cs31620.abercon2019.model.viewmodels.SessionViewModel;

/**
 * EventsFragment.java - A class to represents the events fragment part of the user interface.
 * This class contains a recycler view, that contains basic details of each session, with the
 * user being given the opportunity to click on a session that is a workshop or a talk to find
 * out more information.
 *
 * @author Michael Male
 * @version 1.0 2019-12-02
 */
public class EventsFragment extends Fragment {
    // Global variables for use both in the onCreate, the Observer and the OnClickListener
    private Date currentDate = null;
    private Map<Date, List<Session>> sessionsSortedByDate;

    public EventsFragment() {
        // Required empty public constructor
    }


    /**
     * Creates the view of the events fragment, linking up resources with objects within the code
     * . Further inline comments can be found.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     * @see Fragment
     * <p>
     * The code in this view also populates a linked hash map with the database's session details
     * (TODO: This is probably better as an asynchronous task)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events, container, false);
        final RecyclerView eventsRecycler =
                view.findViewById(R.id.short_session_recycler);

        SessionViewModel sessionViewModel = ViewModelProviders.of(this)
                .get(SessionViewModel.class);

        LiveData<List<Session>> allSessions = sessionViewModel.getAllSessions();

        // The sessions are being split by date. Two stacks are used for navigation between these
        // dates, allowing for as little as one date and as many dates as can be practicably held.
        final Stack<Date> forwardStack = new Stack<>();
        final Stack<Date> backwardsStack = new Stack<>();

        // This date format represents a date like "Tue, 03 Dec"
        final SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM",
                Locale.getDefault());

        final TextView dateSelector = view.findViewById(R.id.date);

        allSessions.observe(this, sessions -> {
            if (sessions != null && !sessions.isEmpty()) {
                // Moves list into map sorted and grouped by Date in ascending order

                sessionsSortedByDate =
                        sessions.stream().collect(Collectors.groupingBy(Session::getSessionDate,
                                LinkedHashMap::new, Collectors.toList()));

                LinkedList<Date> datesInDatabase = new LinkedList<>(sessionsSortedByDate.keySet());
                currentDate = datesInDatabase.getFirst();
                dateSelector.setText(format.format(currentDate));

                // If nothing is on the forward stack, push all dates apart from the earliest
                // date onto it. Sort it in reverse order to ensure the stack works properly.
                if (forwardStack.empty()) {
                    forwardStack.addAll(datesInDatabase.subList(1, datesInDatabase.size()));
                    forwardStack.sort(new DateComparator());
                }

                EventsAdapter adapter = new EventsAdapter(getContext(),
                        sessionsSortedByDate.get(currentDate), true);
                eventsRecycler.setAdapter(adapter);


                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                eventsRecycler.setLayoutManager(layoutManager);

                // If the back button is pressed, the date that is contained on the backwards
                // stack is popped, the current date is pushed onto the forward stack, the popped
                // date is held in a global 'current date' variable and the adapter is swapped
                // over to that date's value. The same strategy applies for the next button.
                ImageButton backButton = view.findViewById(R.id.back_button);
                backButton.setOnClickListener(view1 -> {
                    Date goingTo;
                    if (!backwardsStack.empty()) {
                        goingTo = backwardsStack.pop();
                        forwardStack.push(currentDate);
                        currentDate = goingTo;
                        dateSelector.setText(format.format(currentDate));

                        EventsAdapter newAdapter =
                                new EventsAdapter(getContext(),
                                        sessionsSortedByDate.get(currentDate), true);
                        eventsRecycler.swapAdapter(newAdapter, false);
                    } else {
                        Toast.makeText(getContext(), "No previous date", Toast.LENGTH_SHORT).show();
                    }
                });

                ImageButton nextButton = view.findViewById(R.id.next_button);
                nextButton.setOnClickListener(view1 -> {
                    Date goingTo;

                    if (!forwardStack.empty()) {
                        goingTo = forwardStack.pop();
                        backwardsStack.push(currentDate);
                        currentDate = goingTo;
                        dateSelector.setText(format.format(currentDate));

                        EventsAdapter newAdapter =
                                new EventsAdapter(getContext(),
                                        sessionsSortedByDate.get(currentDate), true);
                        eventsRecycler.swapAdapter(newAdapter, false);
                    } else {
                        Toast.makeText(getContext(), "No next date", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Log.e("NO_SESSIONS", "The session list passed to the observer in" +
                        " EventsFragment is empty.");
            }
        });

        return view;
    }

}
