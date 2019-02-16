package id.hamasah.annisarif.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.hamasah.annisarif.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThanksFragment extends Fragment {


    public ThanksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thanks, container, false);
    }

}
