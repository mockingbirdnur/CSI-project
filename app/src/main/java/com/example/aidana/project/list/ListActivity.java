package com.example.aidana.project.list;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.aidana.project.CreateActivity;
import com.example.aidana.project.DBHelper;
import com.example.aidana.project.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {

    DBHelper dbh;
    SQLiteDatabase db;

    MyRecycleAdapter sa;
    RecyclerView lv;

    final int MENU_COLOR_RED = 1;
    final int MENU_COLOR_GREEN = 2;
    final int MENU_COLOR_BLUE = 3;
    RelativeLayout rel;

    ArrayList<HashMap<String, String>> data = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        lv = (RecyclerView) findViewById(R.id.contacts);
        rel = (RelativeLayout) findViewById(R.id.phone);
        sa = new MyRecycleAdapter(data, this);
        lv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lv.setAdapter(sa);
        dbh = new DBHelper(this);
        db = dbh.getWritableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, MENU_COLOR_GREEN, Menu.NONE, "Violet");
        menu.add(Menu.NONE, MENU_COLOR_BLUE, Menu.NONE, "Blue");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_COLOR_RED:
                rel.setBackgroundColor(Color.RED);
                break;
            case MENU_COLOR_GREEN:
                rel.setBackgroundColor(Color.rgb(62, 67, 124));
                break;
            case MENU_COLOR_BLUE:
                rel.setBackgroundColor(Color.rgb(63, 93, 125));

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void addContact(View v){
        Intent i = new Intent(this, CreateActivity.class);
        i.putExtra("action", "create");
        startActivity(i);
    }

    public void refresh(View v){
        data.clear();
        Cursor c = db.query("contacts", null, null, null, null, null, "phone");
        if(c.moveToFirst()){
            do{
                long id = c.getLong(c.getColumnIndex("_id"));
                String checkbox = c.getString(c.getColumnIndex("checked"));
                String phone = c.getString(c.getColumnIndex("phone"));
                String image = c.getString(c.getColumnIndex("image"));

                HashMap<String, String> map = new HashMap();
                map.put("id", id+"");
                map.put("checked", checkbox);
                map.put("phone", "Date:"+phone);
                map.put("image", image);
                data.add(0,map);

            }while(c.moveToNext());
        }
        c.close();
        sa.notifyDataSetChanged();
    }

    public void updateCheck(boolean is, String id ){
        ContentValues cv = new ContentValues();
        cv.put("checked", is ? "yes" : "no");
        db.update("contacts", cv, "_id=?", new String[]{id});
        refresh(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbh.close();
    }

    public void onUpdateClick(int id){
        Intent i = new Intent(this, CreateActivity.class);
        i.putExtra("action", "update");
        i.putExtra("id", id);
        startActivity(i);
    }

    public void onDeleteItemClicked(int id){
        db.delete("contacts", "_id=?", new String[]{id+""});
        refresh(null);
    }
}


