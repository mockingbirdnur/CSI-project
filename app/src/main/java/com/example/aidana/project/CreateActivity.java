package com.example.aidana.project;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CreateActivity extends AppCompatActivity {

    DBHelper dbh;
    SQLiteDatabase db;

    EditText nameET;
    EditText phoneET;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        dbh = new DBHelper(this);
        db = dbh.getWritableDatabase();

       // nameET = (EditText)findViewById(R.id.nameET);
        phoneET = (EditText)findViewById(R.id.phoneET);
        textView=(TextView)findViewById(R.id.textiew);
        Typeface tp=Typeface.createFromAsset(getAssets(),"fonts/Pacifico.ttf");

        textView.setTypeface(tp);



    }





    public void save(View v){
        ContentValues cv = new ContentValues();
       // cv.put("name", nameET.getText().toString());
        cv.put("phone", phoneET.getText().toString());
        cv.put("checked", "no");
        cv.put("image", "");

        if (getIntent().getStringExtra("action").equals("update")) {
            db.update("contacts", cv, "_id=?", new String[]{getIntent().getStringExtra("id")});
        } else {
            db.insert("contacts", null, cv);
        }
        finish();

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbh.close();
    }
}


