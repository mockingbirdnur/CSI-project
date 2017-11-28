package com.example.aidana.project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {

    DBHelper dbh;
    SQLiteDatabase db;
    String id;


    private int prePos=-1;

    CustomAdapter sa;
    ListView lv;
    final int MENU_UPDATE = 1;
    final int MENU_DELETE = 2;

    final int MENU_COLOR_RED = 1;
    final int MENU_COLOR_GREEN = 2;
    final int MENU_COLOR_BLUE = 3;
    RelativeLayout rel;


    ArrayList<HashMap<String, String>> data = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        lv = (ListView) findViewById(R.id.contacts);

        rel = (RelativeLayout) findViewById(R.id.phone);

        registerForContextMenu(lv);


        lv.setOnTouchListener(new OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView

            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        registerForContextMenu(lv);
        sa = new CustomAdapter(data);


        lv.setAdapter(sa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) ((HashMap) lv.getItemAtPosition(position)).get("id");
                Intent i = new Intent(getBaseContext(), CameraActivity.class);
                i.putExtra("id", str);
                startActivity(i);

            }
        });

//        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.activity_list,
//                R.id.contacts, data));
//        lv.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapter, View view,
//                                    int position, long arg3) {
//
//                for (int i = 0; i < adapter.getChildCount(); i++) {
//                    if (i == position) {
//
//                        if (position != prePos) {
//                            adapter.getChildAt(i).setBackgroundColor(Color.BLUE);
//
//                            prePos = position;
//
//                        } else {
//                            adapter.getChildAt(i).setBackgroundColor(Color.rgb(255, 76, 101));
//                            prePos = -1;
//                        }
//
//                    } else {
//
//                        adapter.getChildAt(i).setBackgroundColor(Color.rgb(255, 76, 101));
//
//                    }
//
//                }
//
//
//            }
//
//        });
//
        dbh = new DBHelper(this);
        db = dbh.getWritableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu.add(Menu.NONE, MENU_COLOR_RED, Menu.NONE, "Red");
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
        sa.notifyDataSetChanged();
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

    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo ad=(AdapterView.AdapterContextMenuInfo)menuInfo;
        id=(String) ((HashMap)lv.getItemAtPosition(ad.position)).get("id");

        menu.add(0, MENU_UPDATE, 0, "Update");
        menu.add(0, MENU_DELETE, 0, "Delete");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case MENU_UPDATE:
                Intent i = new Intent(this, CreateActivity.class);
                i.putExtra("action", "update");
                i.putExtra("id", id);
                startActivity(i);
                break;
            case MENU_DELETE:
                db.delete("contacts", "_id=?", new String[]{id+""});
                refresh(null);
                break;
        }
        return super.onContextItemSelected(item);
    }
}


