package uk.ac.aber.cs31620.abercon2019.ui.whatson;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.aber.cs31620.abercon2019.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WhatsOnFragment extends Fragment {


    public WhatsOnFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_whats_on, container, false);
    }

}
