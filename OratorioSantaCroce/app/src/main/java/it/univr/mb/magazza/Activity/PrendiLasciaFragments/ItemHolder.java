package it.univr.mb.magazza.Activity.PrendiLasciaFragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import it.univr.mb.magazza.Model.Item;
import it.univr.mb.magazza.R;

public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final static String TAG = "ItemHolder";
    private ItemAdapter owner;
    private TextView mItemName;
    private Button mCancelButton;
    private Item mItem;


    public ItemHolder(@NonNull View itemView, ItemAdapter owner) {
        super(itemView);
        this.owner = owner;
        mItemName = itemView.findViewById(R.id.item);
        mCancelButton = itemView.findViewById(R.id.cancel_button);
        itemView.setOnClickListener(this);
        mCancelButton.setOnClickListener(v->onCancelButtonClick());
    }

    private void onCancelButtonClick() {
        Log.d(TAG, "cancel buton clickato");
        owner.removeItem(mItem);

    }

    @Override
    public void onClick(View v) {
        //TODO->
        Log.d(TAG, "Elemento clickato");
        changeFragment(mItem);
    }

    private void changeFragment(Item i) {
        owner.changeFragment(i);
    }

    public void setItem(Item i) {
        mItem = i;
        mItemName.setText(mItem.getName());
    }
}
