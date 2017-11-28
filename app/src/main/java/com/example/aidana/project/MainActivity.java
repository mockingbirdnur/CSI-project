package com.example.aidana.project;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aidana.project.list.ListActivity;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    Button btn;
    Button btn1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface tp=Typeface.createFromAsset(getAssets(),"fonts/TinyShack.ttf");
        tv=(TextView)findViewById(R.id.tv);
        tv.setTypeface(tp);
        btn=(Button)findViewById(R.id.btn);
        btn.setText("Notes");
        btn.setTypeface(tp);
//        btn1=(Button)findViewById(R.id.btn1);
//        btn1.setText("List");
//        btn1.setTypeface(tp);


    }
    public void onclick(View v){
        Intent i=new Intent(this,ListActivity.class);
        startActivity(i);

    }
}
