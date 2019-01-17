package it.univr.mb.magazza.Activity.PrendiLasciaFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import it.univr.mb.magazza.Activity.PrendiActivity;
import it.univr.mb.magazza.Database.ObjectBuilder;
import it.univr.mb.magazza.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CausaleFragment extends Fragment {

    private static final String TAG = "causalefragment";
    private TextView mCausaleLabel;
    private EditText mCausaleText;
    private Button mNextButton;
    private Spinner mEventSpinner;
    private ArrayAdapter<String> mSpinnerAdapter;
    private String mEvento;


    public CausaleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_causale, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCausaleLabel = view.findViewById(R.id.causale_label);
        mCausaleText = view.findViewById(R.id.causale_textArea);
        mNextButton = view.findViewById(R.id.next_button);
        mNextButton.setOnClickListener(v -> onNextButtonCLick());
        mEventSpinner = view.findViewById(R.id.causale_spinner);

        mSpinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.event_row);
        ObjectBuilder.getInstance().getEvents(((PrendiActivity)getActivity()));

        mEventSpinner.setAdapter(mSpinnerAdapter);
        mEventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView event = view.findViewById(R.id.rowtext);
                String causale = event.getText().toString();
                Log.d(TAG, "selezionato: " + causale);
                if(causale.equals("NUOVO EVENTO")) {
                    mCausaleText.setVisibility(View.VISIBLE);
                    mCausaleText.setEnabled(true);
                }
                else {
                    mCausaleText.setVisibility(View.GONE);
                    mCausaleText.setEnabled(false);
                    mEvento = causale;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void onNextButtonCLick() {
        if(mCausaleText.isEnabled())
            mEvento = mCausaleText.getText().toString();

        ObjectBuilder.getInstance().setBorrowDescription(mEvento);
        ((PrendiActivity)getActivity()).launchQrFragment();
    }

    public void setEvents(ArrayList<String> events) {
        mSpinnerAdapter.addAll(events);
        mSpinnerAdapter.add("NUOVO EVENTO");
    }
}
