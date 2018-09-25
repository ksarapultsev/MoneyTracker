package com.kos.work.moneytracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceFragment extends Fragment {

    private static final String TYPE_KEY = "type";

    public BalanceFragment() {
        // Required empty public constructor
    }

    


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }
    public static BalanceFragment createItemsFragment(int type){
        BalanceFragment fragment = new BalanceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BalanceFragment.TYPE_KEY, type);
        fragment.setArguments(bundle);
        return fragment;
    }

}
