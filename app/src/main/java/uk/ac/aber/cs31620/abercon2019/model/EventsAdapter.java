package uk.ac.aber.cs31620.abercon2019.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.R;
import uk.ac.aber.cs31620.abercon2019.model.util.DateTimeConverter;
import uk.ac.aber.cs31620.abercon2019.ui.event_details.ViewEventDetailsActivity;

/**
 * EventsAdapter.java - A class to manage a RecyclerView utilising the CardView structure.
 *
 * @author Michael Male
 * @version 1.0 PRODUCTION
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private final Context context;
    private List<Session> sessions;

    public EventsAdapter(Context context, List<Session> sessions) {
        this.context = context;
        this.sessions = sessions;
    }


    @NonNull
    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_session, parent, false);
        return new EventsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.ViewHolder holder, int position) {
        final CardView cardView = (CardView) holder.itemView;
        Session session = sessions.get(position);
        holder.setDetails(session);
        cardView.setOnClickListener(view -> {
            /* Passes the Session object, which implements the Serializable interface, to the
            ViewEventDetailsActivity and then starts that activity.
            Might be quicker to just pass a String and fetch the data using LiveData in that
            activity, or to use Parcelable instead of Serializable.
             */
            Intent intent = new Intent(cardView.getContext(), ViewEventDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("SELECTED_SESSION", session);
            intent.putExtras(bundle);
            cardView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView sessionName, sessionDate;

        ViewHolder(View itemView) {
            super(itemView);
            sessionName = itemView.findViewById(R.id.session_name);
            sessionDate = itemView.findViewById(R.id.session_date);
        }

        void setDetails(Session session) {
            sessionName.setText(session.getTitle());
            String concat = DateTimeConverter.toString(session.getSessionDate()) +
                    " at " + session.getTimeStart() + " to " + session.getTimeEnd();
            sessionDate.setText(concat);
        }
    }
}
