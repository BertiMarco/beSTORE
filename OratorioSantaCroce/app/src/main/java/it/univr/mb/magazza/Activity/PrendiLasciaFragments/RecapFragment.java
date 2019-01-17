package it.univr.mb.magazza.Activity.PrendiLasciaFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.univr.mb.magazza.Activity.MainActivity;
import it.univr.mb.magazza.Activity.PrendiActivity;
import it.univr.mb.magazza.Database.ObjectBuilder;
import it.univr.mb.magazza.Model.Borrow;
import it.univr.mb.magazza.Model.Item;
import it.univr.mb.magazza.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecapFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String EDITABLE = "param1";

    // TODO: Rename and change types of parameters
    private Borrow mBorrow;
    //private TextView mTitleTextView;
    //private TextView mCausaleLabelTextView;
    private TextView mCausaleTextView;
    //private TextView mUserLabelTextView;
    private TextView mUserTextVIew;
    //private TextView mDateLabelTextView;
    private TextView mDateTextView;
    private RecyclerView mRecyclerView;
    private ItemAdapter mItemAdapter;
    private Button mAddButton;
    private Button mEndButton;
    private Boolean mEditable;
    private Button mEditButton;


    public RecapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment RecapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecapFragment newInstance(Boolean param1) {
        RecapFragment fragment = new RecapFragment();
        Bundle args = new Bundle();
        args.putBoolean(EDITABLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEditable = getArguments().getBoolean(EDITABLE);
        }
        mBorrow = ObjectBuilder.getInstance().getBorrow();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recap, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCausaleTextView = view.findViewById(R.id.recap_causale);
        mUserTextVIew = view.findViewById(R.id.recap_user);
        mDateTextView = view.findViewById(R.id.recap_date);
        mRecyclerView = view.findViewById(R.id.recap_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mItemAdapter = new ItemAdapter( this));

        mCausaleTextView.setText(mBorrow.getDescription());
        mUserTextVIew.setText(mBorrow.getUser().getName());
        if(mBorrow.getDate() != null)
            mDateTextView.setText(mBorrow.getDate().toString());

        mAddButton = view.findViewById(R.id.back_button);
        mAddButton.setOnClickListener(v -> onAddButtonClick());

        mEndButton = view.findViewById(R.id.end_button);
        mEndButton.setOnClickListener(v -> onEndButtonClick());

        mEditButton = view.findViewById(R.id.edit_button);
        mEditButton.setOnClickListener(v -> onEditButtonClick());

        if (!mEditable) {
            mAddButton.setVisibility(View.GONE);
            mEndButton.setVisibility(View.GONE);
            mEditButton.setVisibility(View.VISIBLE);
        }
        else {
            mAddButton.setVisibility(View.VISIBLE);
            mEndButton.setVisibility(View.VISIBLE);
            mEditButton.setVisibility(View.GONE);
        }


    }

    private void onEditButtonClick() {
        ((MainActivity)getActivity()).launchPrendiActivity();
    }

    private void onEndButtonClick() {
        ObjectBuilder.getInstance().commitBorrow((PrendiActivity)getActivity());
    }

    private void onAddButtonClick() {
        ((PrendiActivity)getActivity()).launchQrFragment();
    }
}
