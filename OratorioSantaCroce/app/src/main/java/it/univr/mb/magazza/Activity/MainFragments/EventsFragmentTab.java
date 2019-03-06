package it.univr.mb.magazza.Activity.MainFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import it.univr.mb.magazza.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventsFragmentTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsFragmentTab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ViewPager mViewPager;
    private TabLayout mTabs;
    private Adapter mAdapter;



    public EventsFragmentTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventsFragmentTab.
     */
    // TODO: Rename and change types and number of parameters
    public static EventsFragmentTab newInstance(String param1, String param2) {
        EventsFragmentTab fragment = new EventsFragmentTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events_tab, container, false);
        // Setting ViewPager for each Tabs
        mViewPager = view.findViewById(R.id.viewpager);
        setupViewPager(mViewPager);
        // Set Tabs inside Toolbar
        mTabs = view.findViewById(R.id.result_tabs);
        mTabs.setupWithViewPager(mViewPager);

        return view;

    }


    private void setupViewPager(ViewPager viewPager) {
        mAdapter = new Adapter(getChildFragmentManager());
        mAdapter.addFragment(new EventsFragment(), "Eventi in corso");
        mAdapter.addFragment(new EndedEventsFragment(), "Eventi terminati");
        mAdapter.addFragment(new MyItemsFragment(), "I miei oggetti in prestito");
        viewPager.setAdapter(mAdapter);
    }

    static class Adapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
