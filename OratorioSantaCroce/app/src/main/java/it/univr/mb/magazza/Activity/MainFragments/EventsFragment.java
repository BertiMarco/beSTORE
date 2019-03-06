package it.univr.mb.magazza.Activity.MainFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univr.mb.magazza.Database.ObjectBuilder;
import it.univr.mb.magazza.Model.Item;
import it.univr.mb.magazza.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {
    private static final String TAG = "EventsFragment";
    private ExpandableListAdapter mListAdapter;
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
        Log.e(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);

        ObjectBuilder.getInstance().getEventsItems(this);

        mExpandableListView = view.findViewById(R.id.events_expandable_list_view);

        //TODO-> l'idea è quella di creare prima l'adapter con i titoli facendo la query e poi popolarlo. anche se pensandoci la query restituisce già tutto facendo group by
        //mExpandableListView.setAdapter(mListAdapter);

        //TODO-> capise se fare query da qui o dalla main activity, considerando che qui simo in un frgment DENTRO un altro fragment.


    }

    public void adapterReady(HashMap<String, ArrayList<Item>> eventsItems) {

        Log.d(TAG, "adapterReady. Map -> " + eventsItems);

        HashMap<String, List<String>> converted = new HashMap<>();
        for(String key : eventsItems.keySet()) {
            ArrayList<Item> tmp = eventsItems.get(key);
            ArrayList<String> value = new ArrayList<>();
            for(Item i : tmp) {
                value.add(i.getName());
            }
            converted.put(key, value);
        }
        mListAdapter = new ExpandableListAdapter(this.getContext(), new ArrayList<>(eventsItems.keySet()), converted);
        mExpandableListView.setAdapter(mListAdapter);
        mListAdapter.notifyDataSetChanged();

    }
}
