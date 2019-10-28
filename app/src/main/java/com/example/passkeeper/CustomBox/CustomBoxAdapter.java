package com.example.passkeeper.CustomBox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.passkeeper.R;

import java.util.ArrayList;

public class CustomBoxAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<CustomBoxModel> objects;

    public CustomBoxAdapter(Context context, ArrayList<CustomBoxModel> objects) {
        this.context = context;
        this.objects = objects;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.layout_custom_box, parent, false);
        }
        CustomBoxModel model = getResource(position);
        ((TextView) view.findViewById(R.id.textView_cBoxTitle)).setText(model.getTitle());
        ((TextView) view.findViewById(R.id.textView_cBoxLogin)).setText(model.getLogin());
        ((TextView) view.findViewById(R.id.textView_cBoxPassword)).setText(model.getPassword());
        return view;
    }

    //region get/set
    private CustomBoxModel getResource(int position) {
        return ((CustomBoxModel) getItem(position));
    }
    //endregion
}
