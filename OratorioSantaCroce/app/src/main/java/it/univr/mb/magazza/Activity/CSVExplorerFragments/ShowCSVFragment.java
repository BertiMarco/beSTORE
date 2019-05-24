package it.univr.mb.magazza.Activity.CSVExplorerFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.univr.mb.magazza.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ShowCSVFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowCSVFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "StringFromCSVFile";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String stringFromCSVFile;
    private TextView CSVContent;


    public ShowCSVFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 result from reading csv file.
     * @return A new instance of fragment ShowCSVFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowCSVFragment newInstance(String param1) {
        ShowCSVFragment fragment = new ShowCSVFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stringFromCSVFile = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_csv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CSVContent = view.findViewById(R.id.csv_content);
        CSVContent.setText(stringFromCSVFile);


    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
