package it.univr.mb.magazza.Activity.MainFragments;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import it.univr.mb.magazza.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<String> mTitleList;
    private HashMap<String, List<String>> mItemsMap;
    private final static String TAG = "ExpandableListAdapter";

//TODO-> vedere se si può fare costruttore vuto e modificare le mappe dopo. Oppure costruttore con solo titoli per caricamento veloce
    public ExpandableListAdapter(Context context, List<String> titleList, HashMap<String, List<String>> itemsMap) {
        this.mContext = context;
        mTitleList = titleList;
        mItemsMap = itemsMap;
    }

    /*public ExpandableListAdapter() {

    }*/

    @Override
    public int getGroupCount() {
        return mTitleList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        try {
            return mItemsMap.get(mTitleList.get(groupPosition)).size();
        } catch (NullPointerException e){
            e.printStackTrace();
            Log.e(TAG, "Element not in map");
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mTitleList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mItemsMap.get(mTitleList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = convertView
                .findViewById(R.id.expandable_list_group);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = convertView
                .findViewById(R.id.expandable_list_item);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
