package com.example.aidana.project.list;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.aidana.project.CameraActivity;
import com.example.aidana.project.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aidana on 11/28/17.
 */

public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.MyViewHolder>
        implements View.OnCreateContextMenuListener,
        MenuItem.OnMenuItemClickListener  {

    final int MENU_UPDATE = 1;
    final int MENU_DELETE = 2;

    ListActivity activity ;
    int selectedItemId;

    private ArrayList<HashMap<String,String>> data;
    MyRecycleAdapter(ArrayList<HashMap<String,String>> data, ListActivity listActivity ){
        this.data= data;
        activity = listActivity;
    }

    @Override
    public MyRecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(parent.getContext(), R.layout.item_sa,null));
    }

    @Override
    public void onBindViewHolder(MyRecycleAdapter.MyViewHolder holder, final int position) {
        holder.checkBox.setChecked(data.get(position).get("checked").equals("yes"));
        holder.text.setText(data.get(position).get("phone"));
        if(data.get(position).get("checked").equals("yes"))
            holder.text.setPaintFlags(holder.text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        else{
            holder.text.setPaintFlags(0);
        }
        holder.itemView.setOnCreateContextMenuListener(this);
        holder.setListeners();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo ad=(AdapterView.AdapterContextMenuInfo)menuInfo;
        selectedItemId = Integer.parseInt(data.get(ad.position).get("id"));

        menu.add(0, MENU_UPDATE, 0, "Update");
        menu.add(0, MENU_DELETE, 0, "Delete");
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case MENU_UPDATE:
                activity.onUpdateClick(selectedItemId);
                break;
            case MENU_DELETE:
                activity.onDeleteItemClicked(selectedItemId);
                break;
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public CheckBox checkBox;
        public TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.checked);
            text = (TextView) itemView.findViewById(R.id.phone);
        }

        public void setListeners(){
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    activity.updateCheck(isChecked, data.get(getAdapterPosition()).get("id"));
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = data.get(getAdapterPosition()).get("id");
                    Intent i = new Intent(v.getContext(), CameraActivity.class);
                    i.putExtra("id", str);
                    v.getContext().startActivity(i);
                }
            });
        }
    }

    public void updateData(ArrayList<HashMap<String,String>> data){
        this.data.clear();
        this.data.addAll(data);
    }
}
