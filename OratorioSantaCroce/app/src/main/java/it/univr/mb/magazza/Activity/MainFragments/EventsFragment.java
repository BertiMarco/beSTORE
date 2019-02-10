package it.univr.mb.magazza.Activity.MainFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univr.mb.magazza.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {
    private ExpandableListAdapter mListAdapter = new ExpandableListAdapter(getContext(), new ArrayList<>(), new HashMap<>());
    private ExpandableListView mExpandableListView;



    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mExpandableListView = view.findViewById(R.id.events_expandable_list_view);

        //TODO-> l'idea è quella di creare prima l'adapter con i titoli facendo la query e poi popolarlo. ance se pensandoci la query restituisce già tutto facendo group by
        mExpandableListView.setAdapter(mListAdapter);
        //TODO-> capise se fare query da qui o dalla main activity, considerand che qui simo in un frgment DENTRO un altro fragment.


    }
}
