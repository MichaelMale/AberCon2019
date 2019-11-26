package uk.ac.aber.cs31620.abercon2019.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import uk.ac.aber.cs31620.abercon2019.R;
import uk.ac.aber.cs31620.abercon2019.ui.event_details.ViewEventDetailsActivity;

public class SessionListAdapter extends RecyclerView.Adapter<SessionListAdapter.ViewHolder> {

    private String[] sessionNames;
    private String[] sessionDates;

    public SessionListAdapter(String[] sessionNames, String[] sessionDates) {
        this.sessionNames = sessionNames;
        this.sessionDates = sessionDates;
    }


    @NonNull
    @Override
    public SessionListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                           int viewType) {

        CardView cv =
                (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_session,
                        parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final CardView cardView = holder.cardView;
        TextView name = (TextView)cardView.findViewById(R.id.session_name);
        TextView date = (TextView)cardView.findViewById(R.id.session_date);
        name.setText(sessionNames[position]);
        date.setText(sessionDates[position]);

        cardView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cardView.getContext(), ViewEventDetailsActivity.class);
                intent.putExtra(ViewEventDetailsActivity.EXTRA_SESSION_ID, position);
                cardView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sessionNames.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }

    }


}
