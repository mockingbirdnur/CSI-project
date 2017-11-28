package com.example.aidana.project;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aidana on 11/28/17.
 */

public class CustomAdapter extends BaseAdapter {
    ArrayList<HashMap<String,String>> data;
    CustomAdapter(ArrayList<HashMap<String,String>> data){
        this.data= data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = View.inflate(viewGroup.getContext(),R.layout.item_sa,null);
        HashMap<String,String> item = data.get(i);
        ((CheckBox)view.findViewById(R.id.checked)).setChecked(item.get("checked").equals("yes"));
        ((TextView)view.findViewById(R.id.phone)).setText(item.get("phone"));
        return view;
    }
}
