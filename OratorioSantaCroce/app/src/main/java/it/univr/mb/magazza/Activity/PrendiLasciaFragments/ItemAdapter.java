package it.univr.mb.magazza.Activity.PrendiLasciaFragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import it.univr.mb.magazza.Database.ObjectBuilder;
import it.univr.mb.magazza.Model.Borrow;
import it.univr.mb.magazza.Model.Item;
import it.univr.mb.magazza.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

    private RecapFragment owner;
    private List<Item> mItems;

    public ItemAdapter(RecapFragment owner) {
        this.owner = owner;
        mItems = new ArrayList<>(ObjectBuilder.getInstance().getBorrow().getItems());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_listitem, viewGroup, false);
        return new ItemHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int position) {
        Item i = mItems.get(position);
        itemHolder.setItem(i);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void removeItem(Item item) {
        ObjectBuilder.getInstance().removeItem(item);
        mItems = new ArrayList<>(ObjectBuilder.getInstance().getBorrow().getItems());
        notifyDataSetChanged();

    }

    public void changeFragment(Item i) {
        //TODO-> chiama fragment di dettaglio su item

    }
}
