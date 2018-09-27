package com.kos.work.moneytracker;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kos.work.moneytracker.api.Api;
import com.kos.work.moneytracker.api.BalanceResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceFragment extends Fragment {
    private static final String TAG = "BalanceFragment";
    private static final String TYPE_KEY = "type";

    private TextView total;
    private TextView expense;
    private TextView income;

    private DiagramView diagram;

    private Api api;
    private App app;


    public BalanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (App) getActivity().getApplication();
        api = app.getApi();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_balance, container, false);
        return view;
    }
//
//    public static BalanceFragment createItemsFragment(int type){
//        BalanceFragment fragment = new BalanceFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt(BalanceFragment.TYPE_KEY, type);
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        total = view.findViewById(R.id.total);
        expense = view.findViewById(R.id.expense);
        income = view.findViewById(R.id.income);
        diagram = view.findViewById(R.id.diagram);


        updateData();

    }

    private void updateData() {
        Call<BalanceResult> call = api.balance();
        call.enqueue(new Callback<BalanceResult>() {
            @Override
            public void onResponse(Call<BalanceResult> call, Response<BalanceResult> response) {
                BalanceResult result = response.body();

                total.setText(getString(R.string.price, result.income-result.expense ));
                income.setText(getString(R.string.price, result.income));
                expense.setText(getString(R.string.price, result.expense));
                diagram.update(result.income,result.expense);
            }

            @Override
            public void onFailure(Call<BalanceResult> call, Throwable t) {

            }
        });




    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser && isResumed()) {
//            updateData();
//        }
//    }
}
