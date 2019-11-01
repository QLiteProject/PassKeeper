package com.example.passkeeper.Main;

import android.view.View;
import android.widget.ListView;

import com.example.passkeeper.Main.CustomBox.CustomBoxAdapter;
import com.example.passkeeper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainView {
    private final MainListener listener;
    private ListView listView_cBoxList;
    private FloatingActionButton btnAddRecord;
    private FloatingActionButton btnSynchronization;

    public MainView(View view, MainListener listener) {
        this.listener = listener;
        initComponents(view);
        initEvents();
    }

    private void initComponents(View view) {
        listView_cBoxList = (ListView) view.findViewById(R.id.listBox_cBoxList);
        btnAddRecord = (FloatingActionButton) view.findViewById(R.id.floating_mBtnAddRecord);
        btnSynchronization = (FloatingActionButton) view.findViewById(R.id.floating_mBtnSynchronization);
    }

    private void initEvents() {
        btnAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickFloatingAddRecord();
            }
        });
        btnSynchronization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickFloatingSynchronization();
            }
        });
    }

    //region get/set
    public void setAdapter(CustomBoxAdapter adapter) {
        listView_cBoxList.setAdapter(adapter);
    }
    //endregion
}
