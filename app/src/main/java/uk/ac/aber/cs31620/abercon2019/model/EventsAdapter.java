package uk.ac.aber.cs31620.abercon2019.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import uk.ac.aber.cs31620.abercon2019.R;
import uk.ac.aber.cs31620.abercon2019.ui.event_details.ViewEventDetailsActivity;

/**
 * EventsAdapter.java - A class to manage a RecyclerView utilising the CardView structure.
 *
 * @author Michael Male
 * @version 1.0 2019-12-03
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private final Context context;
    private List<Session> sessions;
    private boolean isEventsFragment;

    /**
     * Constructor for objects of type EventsAdapter.
     *
     * @param context          Context pertaining to the app that is currently running.
     * @param sessions         A list containing the sessions that need to be inputted into the recycler
     *                         view
     * @param isEventsFragment boolean that is true if this class is being used in the
     *                         EventsFragment. False if not.
     */
    public EventsAdapter(Context context, List<Session> sessions, boolean isEventsFragment) {
        this.context = context;
        this.sessions = sessions;
        this.isEventsFragment = isEventsFragment;
    }


    /**
     * A ViewHolder that facilitates the CardView that is used in this recycler.
     * @param parent    The parent ViewGroup.
     * @param viewType  Integer representing the view type.
     * @return
     */
    @NonNull
    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_session, parent, false);
        return new EventsAdapter.ViewHolder(view);
    }

    /**
     * Binds a Session object to the recycler, using a cursor to go through the list passed to
     * the recycler.
     * @param holder    ViewHolder containing the view that will hold this data.
     * @param position  Integer representing the position of the cursor.
     */
    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.ViewHolder holder, int position) {
        final CardView cardView = (CardView) holder.itemView;
        Session session = sessions.get(position);
        holder.setDetails(session);
        cardView.setOnClickListener(view -> {
            /* Passes the Session object, which implements the Serializable interface, to the
            ViewEventDetailsActivity and then starts that activity. If the card clicked doesn't
            hold a talk or a workshop, then the user is informed that there are no further details.
             */
            if (session.getSessionType() == SessionType.WORKSHOP ||
                    session.getSessionType() == SessionType.TALK) {
                Intent intent = new Intent(cardView.getContext(), ViewEventDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("SELECTED_SESSION", session);
                intent.putExtras(bundle);
                cardView.getContext().startActivity(intent);
            } else {
                Toast.makeText(context, "No further details for this event.",
                        Toast.LENGTH_SHORT).show();
            }

        });
    }

    /**
     * Gets the number of items that have been passed to the recycler.
     * @return Integer representing the size of the list.
     */
    @Override
    public int getItemCount() {
        return sessions.size();
    }

    /**
     * Gets a session at the given position
     * @param position  Position in the list for the desired session
     * @return Object of type Session at the given position in the list.
     */
    public Session getSessionAtPosition(int position) {
        return sessions.get(position);
    }

    /**
     * ViewHolder that facilitates the creation of a CardView.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView sessionName, sessionDate;

        /**
         * Constructor for objects of type ViewHolder.
         * @param itemView  View that this holder is paired to.
         */
        ViewHolder(View itemView) {
            super(itemView);
            sessionName = itemView.findViewById(R.id.session_name);
            sessionDate = itemView.findViewById(R.id.session_date);
        }

        /**
         * Sets the data up, linking it to the various views within the card.
         * @param session   Object of type Session that needs to be paired.
         */
        void setDetails(Session session) {
            sessionName.setText(session.getTitle());
            StringBuilder out = new StringBuilder();
            /* If this recycler is being used in a fragment that isn't the events fragment, and
            the boolean has been passed as false, then the date of the session won't be displayed
            . This is because the events recycler is already split by date, thus making the date
            redundant.
             */
            if (!isEventsFragment) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE d MMMM",
                        Locale.getDefault());
                out.append(dateFormatter.format(session.getSessionDate()));
                out.append(" - ");
            }
            out.append(session.getTimeStart())
                    .append(" to ")
                    .append(session.getTimeEnd());

            sessionDate.setText(out.toString());
        }
    }
}
