package it.univr.mb.magazza.Activity.CSVExplorerFragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import it.univr.mb.magazza.Model.GenericCsv;
import it.univr.mb.magazza.R;

public class CsvItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final static String TAG = "CsvItemHolder";
    private final TextView mFirstField;
    private final TextView mSecondField;
    private CsvItemAdapter owner;
    private GenericCsv mCsvItem;


    public CsvItemHolder(@NonNull View itemView, CsvItemAdapter owner) {
        super(itemView);
        this.owner = owner;
        mFirstField = itemView.findViewById(R.id.id);
        mSecondField = itemView.findViewById(R.id.second_field);
        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        //TODO->
        Log.d(TAG, "element clicked");
        changeFragment(mCsvItem);
    }

    private void changeFragment(GenericCsv i) {
        owner.changeFragment(i);
    }

    public void setCsvItem(GenericCsv i) {
        mCsvItem = i;
        mFirstField.setText(mCsvItem.getField(owner.getFirstFieldToShow()));
        mSecondField.setText(mCsvItem.getField(owner.getSecondFieldToShow()));
    }
}

