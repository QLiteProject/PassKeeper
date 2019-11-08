package com.example.passkeeper.Main.CustomBox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.passkeeper.R;

import java.util.ArrayList;

public class CustomBoxAdapter extends BaseAdapter {
    CustomBoxListener listener;
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<RecordModel> objects;

    public CustomBoxAdapter(Context context, ArrayList<RecordModel> objects, CustomBoxListener listener) {
        this.context = context;
        this.objects = objects;
        this.listener = listener;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.layout_custom_box, parent, false);
        }
        RecordModel model = getResource(position);

        ((TextView) view.findViewById(R.id.textView_cBoxTitle)).setText(model.getTitle());
        ((TextView) view.findViewById(R.id.textView_cBoxLogin)).setText(model.getLogin());
        ((TextView) view.findViewById(R.id.textView_cBoxPassword)).setText(model.getPassword());
        ((ImageButton) view.findViewById(R.id.imageButton_cBoxBtnDelete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickDelete(position);
            }
        });
        ((ImageButton) view.findViewById(R.id.imageButton_cBoxBtnEdit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickEdit(position);
            }
        });

        return view;
    }

    //region get/set
    private RecordModel getResource(int position) {
        return ((RecordModel) getItem(position));
    }
    //endregion
}
