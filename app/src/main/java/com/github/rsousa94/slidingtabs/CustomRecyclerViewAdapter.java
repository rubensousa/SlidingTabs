package com.github.rsousa94.slidingtabs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder>{

    private String mTab;

    public CustomRecyclerViewAdapter(String tab){
        mTab = tab;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;

        public ViewHolder(View layout){
            super(layout);
            mTextView = (TextView) layout.findViewById(R.id.item_textview);
        }

    }

    @Override
    public CustomRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mTextView.setText(mTab);
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
