package it.univr.mb.magazza.Activity.CSVExplorerFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import it.univr.mb.magazza.Activity.CSVExplorerActivity;
import it.univr.mb.magazza.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CsvFieldSelectionFragment extends Fragment {

    private static final String ARG_PARAM1 = "idList";
    private static final String ARG_PARAM2 = "param2";

    private Spinner mIdSpinner;
    private Spinner mLeftSpinner;
    private Spinner mRightSpinner;
    private ArrayAdapter<String> mIdSpinnerAdapter;
    private ArrayAdapter<String> mLeftSpinnerAdapter;
    private ArrayAdapter<String> mRightSpinnerAdapter;
    private String mId;
    private String mLeftField;
    private String mRightField;
    private ArrayList<String> mIdList;
    private Button mNextButton;


    public CsvFieldSelectionFragment() {
        // Required empty public constructor
    }

    public static CsvFieldSelectionFragment newInstance(ArrayList<String> idList) {

        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, idList);
        CsvFieldSelectionFragment fragment = new CsvFieldSelectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIdList = getArguments().getStringArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_csv_field_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNextButton = view.findViewById(R.id.csv_next_button);
        mNextButton.setOnClickListener(v-> onNextButtonClicked());
        mIdSpinner = view.findViewById(R.id.id_spinner);
        mIdSpinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.event_row);
        mIdSpinner.setAdapter(mIdSpinnerAdapter);
        mIdSpinnerAdapter.addAll(mIdList);
        mIdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView selected = view.findViewById(R.id.rowtext);
                mId = selected.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mLeftSpinner = view.findViewById(R.id.spinner_left);
        mLeftSpinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.event_row);
        mLeftSpinner.setAdapter(mLeftSpinnerAdapter);
        mLeftSpinnerAdapter.addAll(mIdList);
        mLeftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView selected = view.findViewById(R.id.rowtext);
                mLeftField = selected.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mRightSpinner = view.findViewById(R.id.spinner_right);
        mRightSpinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.event_row);
        mRightSpinner.setAdapter(mRightSpinnerAdapter);
        mRightSpinnerAdapter.addAll(mIdList);
        mRightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView selected = view.findViewById(R.id.rowtext);
                mRightField = selected.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void onNextButtonClicked() {
        ((CSVExplorerActivity)getActivity()).setFiedsToShow(mLeftField, mRightField);
        ((CSVExplorerActivity)getActivity()).createCsvItem(mId);
        ((CSVExplorerActivity)getActivity()).launchShowFragment();
    }
}
