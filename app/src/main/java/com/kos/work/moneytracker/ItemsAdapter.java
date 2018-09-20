package com.kos.work.moneytracker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<Item> data = new ArrayList<>();
    private ItemsAdapterListener listiner = null;

    public void setListiner(ItemsAdapterListener listiner) {
        this.listiner= listiner;
    }

//    public ItemsAdapter() {
//        createData();
//    }

    public void setData(List<Item> data) {
        this.data = data;
        notifyDataSetChanged();

    }
    public void addItem(Item item) {
        data.add(item);
        notifyItemInserted(data.size());
    }

    @NonNull
    @Override
    public ItemsAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemsAdapter.ItemViewHolder holder, int position) {
        Item record = data.get(position);
        holder.bind(record, position, listiner, selections.get(position, false));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private SparseBooleanArray selections = new SparseBooleanArray();
    public void toggleSelection(int position) {
        if (selections.get(position, false)) {
            selections.delete(position);
        } else {
            selections.put(position, true);
        }
        notifyItemChanged(position);
    }
    void clearSelections() {
        selections.clear();
        notifyDataSetChanged();
    }

    int getSelectedItemCount() {
        return selections.size();
    }
    List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selections.size());
        for (int i=0;i<selections.size();i++) {
            items.add(selections.keyAt(i));
        }
        return items;
    }
    Item remove(int pos) {
        final Item item = data.remove(pos);
        notifyItemRemoved(pos);
        return item;
    }
    private void createData() {
//
    }    // data.add(new Item("Молоко",35));
//        data.add(new Item("Жизнь",1));
//        data.add(new Item("Курсы",50));
//        data.add(new Item("Хлеб",26));
//        data.add(new Item("Тот самый ужин который я оплатил за всех потому что платил картой",100));
//        data.add(new Item("",0));
//        data.add(new Item("Тот самый умный",604));
//        data.add(new Item("ракета Falcon Heavy ",1));
//        data.add(new Item("Лего Тысячелетний сокол",1000000));
//        data.add(new Item("Монитор",100));
//        data.add(new Item("MackBook Pro",100));
//        data.add(new Item("Шоколодка",100));
//        data.add(new Item("Шкаф",100));


    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView price;
        public ItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
        }

        public void bind(final Item item, final int position, final ItemsAdapterListener listiner, boolean selected) {
            title.setText(item.getTitle());
            price.setText(item.getPriceTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listiner != null) {
                        listiner.onItemClick(item, position);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listiner != null) {
                        listiner.onItemLongClick(item, position);
                    }
                    return true;
                }
            });
            itemView.setActivated(selected);

        }
    }
}
