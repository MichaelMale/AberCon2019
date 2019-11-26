package uk.ac.aber.cs31620.abercon2019.ui.events;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.Stack;
import java.util.TreeMap;

import uk.ac.aber.cs31620.abercon2019.R;
import uk.ac.aber.cs31620.abercon2019.model.Session;
import uk.ac.aber.cs31620.abercon2019.model.SessionListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    private TreeMap<Date, Session[]> allSessions = new TreeMap<>();
    private SimpleDateFormat testFormat = new SimpleDateFormat("EEE d MMM", Locale.UK);
    private Date currentDate = null;

    private static final String CURRENT_DATE = "CD";
    private static final String CURRENT_BACK_STACK = "CBS";
    private static final String CURRENT_FORWARD_STACK = "CFS";

    private Stack<Date> forwardStack = new Stack<>();
    private Stack<Date> backStack = new Stack<>();

    private TextView dateSelector = null;

    public EventsFragment() {
        // Required empty public constructor
    }


    /**
     * A method to generate an object of type SessionListAdapter that populates a recycler view.
     *
     * @param toPopulate An array of type Session containing all Sessions that need to be
     *                   populated into the recycler view.
     * @return An object of type SessionListAdapter that binds a view holder to the given data.
     * @see SessionListAdapter
     */
    private SessionListAdapter generateRecyclerAdapter(Session[] toPopulate) {
        int len = toPopulate.length;

        String[] eventNames = new String[len];
        for (int i = 0; i < len; i++) {
            eventNames[i] = toPopulate[i].getTitle();
        }

        String[] eventDates = new String[len];
        for (int i = 0; i < len; i++) {
            eventDates[i] = toPopulate[i].getTimeStart();
        }


        return new SessionListAdapter(eventNames, eventDates);
    }

    /**
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

        View sessions = inflater.inflate(R.layout.fragment_events, container, false);
        final RecyclerView eventsRecycler =
                sessions.findViewById(R.id.short_session_recycler);


        /* TESTING DATA - Once you have successfully implemented the SQLite database this should
        be removed. Obviously onCreateView is a bad place to put code like this, and like the
        below to be honest.
         */
        Date testDate = new Date();
        allSessions.put(testDate, Session.sessions);
        allSessions.put(new Date(1000166400), Session.sessions2);
        allSessions.put(new Date(864432000), Session.sessions3);
        /* END OF TESTING DATA */

        dateSelector = sessions.findViewById(R.id.date);
        final LinkedList<Date> datesInDatabase = new LinkedList<>(allSessions.keySet());
        if (currentDate == null) {
            currentDate = datesInDatabase.getFirst();
        }
        dateSelector.setText(testFormat.format(currentDate));


        if (forwardStack.empty()) {
            forwardStack.addAll(datesInDatabase.subList(1, datesInDatabase.size())); // Initially add
            // all dates into the forward stack bar the first one
        }


        eventsRecycler.setAdapter(generateRecyclerAdapter(Objects.requireNonNull
                (allSessions.get(currentDate))));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        eventsRecycler.setLayoutManager(layoutManager);

        ImageButton backButton = sessions.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date goingTo;
                // If there are items on the back stack, pop the stack and change the current
                // date to the returned value, then add the current date to the forward stack. If
                // no items are on the back stack, then nothing happens.
                if (!backStack.empty()) {
                    goingTo = backStack.pop();
                    forwardStack.push(currentDate);
                    currentDate = goingTo;
                    dateSelector.setText(testFormat.format(currentDate));
                    SessionListAdapter adapter = generateRecyclerAdapter
                            (Objects.requireNonNull(allSessions.get(currentDate)));
                    eventsRecycler.swapAdapter(adapter, false);
                } else {
                    Toast.makeText(getContext(), "No previous entry", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton nextButton = sessions.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date goingTo;
                // If there are items on the forward stack, pop the stack and change the current
                // date to the returned value, then add the current date to the back stack. If no
                // items are on the back stack, then nothing happens.
                if (!forwardStack.empty()) {
                    goingTo = forwardStack.pop();
                    backStack.push(currentDate);
                    currentDate = goingTo;
                    dateSelector.setText(testFormat.format(currentDate));
                    SessionListAdapter adapter = generateRecyclerAdapter
                            (Objects.requireNonNull(allSessions.get(currentDate)));
                    eventsRecycler.swapAdapter(adapter, false);
                } else {
                    Toast.makeText(getContext(), "No next entry", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return sessions;
    }

    /*
    TODO: Make these methods retain the date that was selected before the screen was moved. The
     below code was causing a NullPointerException but then you had to go to an Agile Development
      class after having a shower so
     */
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putSerializable(CURRENT_DATE, currentDate);
//        outState.putSerializable(CURRENT_BACK_STACK, backStack);
//        outState.putSerializable(CURRENT_FORWARD_STACK, forwardStack);
//    }

//    @Override
//    @SuppressWarnings("unchecked")
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        if (savedInstanceState != null) {
//            this.currentDate = (Date) savedInstanceState.getSerializable(CURRENT_DATE);
//            this.backStack = (Stack) savedInstanceState.getSerializable(CURRENT_BACK_STACK);
//            this.forwardStack = (Stack) savedInstanceState.getSerializable(CURRENT_FORWARD_STACK);
//        }
//    }

}
