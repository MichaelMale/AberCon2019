package uk.ac.aber.cs31620.abercon2019.ui.favourites;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.R;
import uk.ac.aber.cs31620.abercon2019.model.EventsAdapter;
import uk.ac.aber.cs31620.abercon2019.model.Session;
import uk.ac.aber.cs31620.abercon2019.model.viewmodels.FavouritesViewModel;
import uk.ac.aber.cs31620.abercon2019.model.viewmodels.SessionViewModel;

/**
 * FavouritesFragment.java - A class to represent the 'Favourites' fragment of the UI.
 *
 * @author Michael Male
 * @version 1.0 2019-12-02
 */
public class FavouritesFragment extends Fragment {
    private EventsAdapter adapter;

    public FavouritesFragment() {
        // Required empty public constructor
    }


    /**
     * Creates a view for the favourites fragment. Further inline comments can be found in the code.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_favourites, container, false);

        final RecyclerView favouritesRecycler = inflate.findViewById(R.id.favourites_recycler);
        final TextView noFavourites = inflate.findViewById(R.id.no_favourites_prompt);
        SessionViewModel sessionViewModel =
                ViewModelProviders.of(this).get(SessionViewModel.class);

        // An observer to the live data of all sessions in the favourites is put into place. If
        // there are no favourites text is instead shown to the user explaining this. If there
        // are favourites then the recycler is set to show these favourites. The observer
        // continues to observe changes to the table and updates the recycler view accordingly.
        LiveData<List<Session>> favourites = sessionViewModel.getSessionsByFavourites();
        favourites.observe(this, faves -> {
            if (faves.isEmpty()) {
                favouritesRecycler.setVisibility(View.GONE);
                noFavourites.setVisibility(View.VISIBLE);
            } else {
                favouritesRecycler.setVisibility(View.VISIBLE);
                noFavourites.setVisibility(View.GONE);
            }
            adapter = new EventsAdapter(getContext(), faves, false);
            favouritesRecycler.setAdapter(adapter);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        favouritesRecycler.setLayoutManager(layoutManager);

        // This ItemTouchHelper allows for the user to swipe left on the session that they would
        // like to remove from their favourites. It then displays a prompt asking if the user is
        // sure. If the user confirms, then the session is deleted, and it is available to be
        // re-added in the events fragment. If the user cancels the operation the session is
        // 'swiped' back into place.
        ItemTouchHelper.SimpleCallback helper =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    FavouritesViewModel mFavouritesViewModel =
                            ViewModelProviders.of(FavouritesFragment.this).get(FavouritesViewModel.class);

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Session toDelete = adapter.getSessionAtPosition(position);

                        DialogInterface.OnClickListener dialogClickListener =
                                (dialog, which) -> {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            mFavouritesViewModel.deleteFavouriteById(toDelete.getId());
                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                                            dialog.dismiss();
                                            break;
                                    }
                                };

                        AlertDialog.Builder builder = new AlertDialog.Builder(inflate.getContext());
                        builder.setTitle("Remove from favourites")
                                .setMessage("Do you really want to remove '" + toDelete.getTitle() +
                                        "' from your favourites?")
                                .setNegativeButton("Cancel", dialogClickListener)
                                .setPositiveButton("Delete", dialogClickListener)
                                .show();
                    }
                };

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(helper);
        mItemTouchHelper.attachToRecyclerView(favouritesRecycler);


        return inflate;
    }


}
