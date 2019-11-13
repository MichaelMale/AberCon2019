package uk.ac.aber.cs31620.abercon2019.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import uk.ac.aber.cs31620.abercon2019.R;
import uk.ac.aber.cs31620.abercon2019.model.Session;

public class ViewEventDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_SESSION_ID = "id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_details);

        int sessionId = (Integer) getIntent().getExtras().get(EXTRA_SESSION_ID);
        String sessionName = Session.sessions[sessionId].getTitle();

        // Set the toolbar
        Toolbar toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(sessionName);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Display details of the sessions

        TextView textView = findViewById(R.id.session_details);
        textView.setText(sessionName);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
