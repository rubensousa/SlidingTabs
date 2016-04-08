package com.github.rubensousa.slidingtabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TabFragment extends Fragment {

    public static final String TAB_NUMBER = "tab";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(com.github.rubensousa.slidingtabs.R.layout.tab_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(com.github.rubensousa.slidingtabs.R.id.recyclerView);
        String tab = getArguments().getString(TAB_NUMBER);
        CustomRecyclerViewAdapter adapter = new CustomRecyclerViewAdapter(tab);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

}
