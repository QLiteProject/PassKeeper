package com.example.passkeeper.Main;

import android.view.View;
import android.widget.ListView;

import com.example.passkeeper.CustomBox.CustomBoxAdapter;
import com.example.passkeeper.R;

public class MainView {
    private final MainListener listener;
    private ListView listView_cBoxList;

    public MainView(View view, MainListener listener) {
        this.listener = listener;
        initComponents(view);
        initEvents();
    }

    private void initComponents(View view) {
        listView_cBoxList = (ListView) view.findViewById(R.id.listBox_cBoxList);
    }

    private void initEvents() {
    }

    //region get/set
    public void setAdapter(CustomBoxAdapter adapter) {
        listView_cBoxList.setAdapter(adapter);
    }
    //endregion
}
