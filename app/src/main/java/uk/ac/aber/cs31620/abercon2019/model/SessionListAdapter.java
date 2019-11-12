package uk.ac.aber.cs31620.abercon2019.model;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import uk.ac.aber.cs31620.abercon2019.R;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CardView cardView = holder.cardView;
        TextView name = (TextView)cardView.findViewById(R.id.session_name);
        TextView date = (TextView)cardView.findViewById(R.id.session_date);
        name.setText(sessionNames[position]);
        date.setText(sessionDates[position]);
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
