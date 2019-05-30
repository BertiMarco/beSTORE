package it.univr.mb.magazza.Activity.CSVExplorerFragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univr.mb.magazza.Database.ObjectBuilder;
import it.univr.mb.magazza.Model.GenericCsv;
import it.univr.mb.magazza.R;

public class CsvItemAdapter extends RecyclerView.Adapter<CsvItemHolder> {

    private static final String TAG = "CSVAdapter";
    private ShowCSVFragment owner;
    private List<GenericCsv> mCsvItems;

    public CsvItemAdapter(ShowCSVFragment owner) {
        this.owner = owner;
        mCsvItems = new ArrayList<>(ObjectBuilder.getInstance().getCsvList());
        Log.d(TAG, "items_numer: " + mCsvItems.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CsvItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.csv_item_listitem, viewGroup, false);
        return new CsvItemHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CsvItemHolder csvItemHolder, int position) {
        GenericCsv genericCsv = mCsvItems.get(position);
        csvItemHolder.setCsvItem(genericCsv);
    }

    @Override
    public int getItemCount() {
        return mCsvItems.size();
    }


    public void changeFragment(GenericCsv i) {
        //TODO-> chiama fragment di dettaglio su item

    }

    public String getFirstFieldToShow() {
        return owner.getFirstFieldToShow();
    }

    public String getSecondFieldToShow() {
        return owner.getSecondFieldToShow();
    }
}

