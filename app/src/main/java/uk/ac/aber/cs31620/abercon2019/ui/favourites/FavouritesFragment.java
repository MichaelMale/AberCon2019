package uk.ac.aber.cs31620.abercon2019.ui.favourites;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.R;
import uk.ac.aber.cs31620.abercon2019.model.EventsAdapter;
import uk.ac.aber.cs31620.abercon2019.model.Session;
import uk.ac.aber.cs31620.abercon2019.model.viewmodels.SessionViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends Fragment {

    public FavouritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_favourites, container, false);

        final RecyclerView favouritesRecycler = inflate.findViewById(R.id.favourites_recycler);
        SessionViewModel sessionViewModel =
                ViewModelProviders.of(this).get(SessionViewModel.class);

        LiveData<List<Session>> favourites = sessionViewModel.getSessionsByFavourites();
        favourites.observe(this, faves -> {
            EventsAdapter adapter = new EventsAdapter(getContext(), faves);
            favouritesRecycler.setAdapter(adapter);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        favouritesRecycler.setLayoutManager(layoutManager);




        return inflate;
    }

}
