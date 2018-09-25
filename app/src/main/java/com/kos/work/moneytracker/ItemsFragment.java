package com.kos.work.moneytracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kos.work.moneytracker.api.AddItemsResult;
import com.kos.work.moneytracker.api.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsFragment   extends Fragment {
    private static final String TAG = "ItemsFragment";

    private static final String TYPE_KEY = "type";

    public static final int ADD_ITEM_REQUEST_CODE = 123;


    public static ItemsFragment createItemsFragment(String type) {
        ItemsFragment fragment = new ItemsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ItemsFragment.TYPE_KEY, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String type;

    private RecyclerView recycler;
    private FloatingActionButton fab;
    private ItemsAdapter adapter;
    private SwipeRefreshLayout refresh;

    private Api api;
    private App app;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ItemsAdapter();
        adapter.setListiner(new AdapterListener());
        Bundle bundle = getArguments();
        type = bundle.getString(TYPE_KEY, Item.TYPE_UNKNOWN);

        if (type.equals(Item.TYPE_UNKNOWN)) {
            throw new IllegalArgumentException("Unknown type");
        }
        app = (App) getActivity().getApplication();

        api = app.getApi();
    }

    // ActionMode
    //////////////////////////////////////////////////////////////////////

    private ActionMode actionMode = null;
    private void removeSelectedItems() {
        for (int i= adapter.getSelectedItems().size()-1; i>= 0; i--) {
            adapter.remove(adapter.getSelectedItems().get(i));
        }
        actionMode.finish();
    }
    private class AdapterListener implements ItemsAdapterListener {


        @Override
        public void onItemClick(Item item, int position) {
            if (isInActionMode()) {
                toggleSelection(position);
            }
        }

        @Override
        public void onItemLongClick(Item item, int position) {
            if (isInActionMode()) {
                return;
            }
          actionMode =  ((AppCompatActivity)getActivity()).startSupportActionMode(actionModeCallBack);
            toggleSelection(position);
        }
        private boolean isInActionMode() {
            return actionMode != null;
        }

        private void toggleSelection(int position) {
         adapter.toggleSelection(position);
        }
    }

    private ActionMode.Callback actionModeCallBack = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = new MenuInflater(getContext());
            inflater.inflate(R.menu.items_menu, menu);
            return true;
        }


        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.remove: showDialog();
                    break;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelections();
            actionMode = null;
        }
    };

    private void showDialog() {
        ConfirmationDialog dialog = new ConfirmationDialog();
        dialog.show(getFragmentManager(),"ConfirmationDialog");

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler = view.findViewById(R.id.list);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
//        fab = view.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // не явный интент
////                Intent intent = new Intent();
////                intent.setAction(Intent.ACTION_VIEW);
////                intent.setData(Uri.parse("http://pikabu.ru"));
////                startActivity(intent);
//                // явный интент
//                Intent intent = new Intent(getContext(),AddItemActivity.class);
//                intent.putExtra(AddItemActivity.TYPE_KEY, type);
//                startActivityForResult(intent, ADD_ITEM_REQUEST_CODE);
//            }
//        });

        refresh =  view.findViewById(R.id.refresh);
        refresh.setColorSchemeColors(Color.BLUE, Color.CYAN, Color.GREEN);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });

        loadItems();
    }
    private void loadItems() {
        Call<List<Item>> call = api.getItems(type);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
               adapter.setData(response.body());
               refresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                refresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_ITEM_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
           Item item = data.getParcelableExtra("item");
           if(item.type.equals(type)) {
//               adapter.addItem(item);
            addItem(item);

           }

        }
      //  super.onActivityResult(requestCode, resultCode, data);
    }

    private void addItem(final Item item) {
       Call<AddItemsResult> call = api.addItem(item.price, item.name, item.type);
       call.enqueue(new Callback<AddItemsResult>() {
           @Override
           public void onResponse(Call<AddItemsResult> call, Response<AddItemsResult> response) {
               AddItemsResult result = response.body();
               if (result.status.equals("success")) {
                   adapter.addItem(item);
               }
           }

           @Override
           public void onFailure(Call<AddItemsResult> call, Throwable t) {

           }
       });
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    //    private void loadItems() {
//       AsyncTask<Void, Void, List<Item>> task =  new AsyncTask<Void, Void, List<Item>>() {
//            @Override
//            protected void onPreExecute() {
//                Log.d(TAG, "onPreExecute: thread name = "+Thread.currentThread().getName());
//            }
//
//            @Override
//            protected List<Item> doInBackground(Void... voids) {
//                Call<List<Item>> call = api.getItems(type);
//            try {
//                List<Item> items = call.execute().body();
//                return items;
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//            }
//
//            @Override
//            protected void onPostExecute(List<Item> items) {
//                if (items != null) {
//                    adapter.setData(items);
//                }
//            }
//        };
//
//       task.execute();
//    }

    //// Thread and Handler
//
//    private void loadItems() {
//        new LoadItemsTask( new Handler(callback)).start();
//
//    }
//    private Handler.Callback callback = new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            if (msg.what == DATA_LOADED) {
//                List<Item> items = (List<Item>) msg.obj;
//                adapter.setData(items);
//            }
//            return true;
//        }
//    };
//
//    private final static int DATA_LOADED = 123;
//
//    private class LoadItemsTask implements Runnable {
//        private Thread thread;
//        private Handler handler;
//        public LoadItemsTask(Handler handler) {
//            thread = new Thread(this);
//            this.handler = handler;
//
//        }
//
//        public void start() {
//            thread.start();
//        }
//
//        @Override
//        public void run() {
//            //  Log.d(TAG, "run: loadItems: current thread "+ Thread.currentThread().getName());
//            Call<List<Item>> call = api.getItems(type);
//            try {
//                List<Item> items = call.execute().body();
//                handler.obtainMessage(DATA_LOADED, items).sendToTarget();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


}

