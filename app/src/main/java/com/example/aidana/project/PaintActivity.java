package com.example.aidana.project;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

public class PaintActivity extends AppCompatActivity implements View.OnClickListener {


        ImageButton red;
        ImageButton blue;
        ImageButton white;
        ImageButton black;
        ImageButton green;
        PaintSecondActivity second;
        Integer first;
        Integer secon;
        Integer third;
        Integer perfecto;
        ImageButton traf;
        ImageButton save;
        ImageButton delete;
        ImageButton button;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_paint);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            red=(ImageButton)findViewById(R.id.colorred);
            blue=(ImageButton)findViewById(R.id.colorblue);
            green=(ImageButton)findViewById(R.id.colorgreen);
            black=(ImageButton)findViewById(R.id.colorblack);
            white=(ImageButton)findViewById(R.id.colorwhite);

            button=(ImageButton)findViewById(R.id.button);
            traf=(ImageButton)findViewById(R.id.traf);
            delete=(ImageButton)findViewById(R.id.delete);
            save=(ImageButton)findViewById(R.id.save);



            red.setOnClickListener(this);
            blue.setOnClickListener(this);
            green.setOnClickListener(this);
            black.setOnClickListener(this);
            white.setOnClickListener(this);
            button.setOnClickListener(this);
            traf.setOnClickListener(this);
            delete.setOnClickListener(this);
            save.setOnClickListener(this);

            second=(PaintSecondActivity)findViewById(R.id.secondactivity);

            first=10;
            secon=20;
            third=30;

            perfecto=secon;


        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu, menu);
            return true;
        }


        @Override
        public void onClick(View view) {
            String color=null;

            switch (view.getId()){
                case R.id.colorblack:
                    color=view.getTag().toString();
                    second.setColor(color);
                    break;
                case R.id.colorblue:
                    color=view.getTag().toString();
                    second.setColor(color);
                    break;
                case R.id.colorgreen:
                    color=view.getTag().toString();
                    second.setColor(color);
                    break;
                case R.id.colorred:
                    color=view.getTag().toString();
                    second.setColor(color);
                    break;
                case R.id.colorwhite:
                    color=view.getTag().toString();
                    second.setColor(color);
                    break;
                case R.id.traf:
                    color=view.getTag().toString();
                    second.setColor(color);
                    break;
//                    final Dialog brushDialog=new Dialog(this);
//                    brushDialog.setTitle("new");
//                    brushDialog.setContentView(R.layout.activity_paint_second);
//
//                    TextView smallBtn=(TextView)brushDialog.findViewById(R.id.textView);
//                    smallBtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            PaintSecondActivity.setB(false);
//                            PaintSecondActivity.setTP(first);
//                            brushDialog.dismiss();
//                        }
//                    });
//                    TextView mediumBtn=(TextView)brushDialog.findViewById(R.id.textView2);
//                    mediumBtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            PaintSecondActivity.setB(false);
//                            PaintSecondActivity.setTP(secon);
//                        }
//                    });
//                    TextView largeBtn=(TextView)brushDialog.findViewById(R.id.textView3);
//                    largeBtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            PaintSecondActivity.setB(false);
//                            PaintSecondActivity.setTP(third);
//                            brushDialog.dismiss();
//                        }
//                    });
//                    brushDialog.show();
//                    break;

                case R.id.delete:
                    AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
                    newDialog.setTitle("Delete");
                    newDialog.setMessage("Delete?");
                    newDialog.setPositiveButton("Accept", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){

                            second.ND();
                            dialog.dismiss();
                        }
                    });
                    newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                            dialog.cancel();
                        }
                    });
                    newDialog.show();


                    break;

                case R.id.button:

                    final Dialog borrarpunto = new Dialog(this);

                    borrarpunto.setContentView(R.layout.activity_paint_second);
//listen for clicks
                    TextView smallBtnBorrar = (TextView)borrarpunto.findViewById(R.id.textView);
                    smallBtnBorrar.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            PaintSecondActivity.setB(true);
                            PaintSecondActivity.setTP(first);

                            borrarpunto.dismiss();
                        }
                    });
                    TextView mediumBtnBorrar = (TextView)borrarpunto.findViewById(R.id.textView2);
                    mediumBtnBorrar.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            PaintSecondActivity.setB(true);
                            PaintSecondActivity.setTP(secon);

                            borrarpunto.dismiss();
                        }
                    });
                    TextView largeBtnBorrar = (TextView)borrarpunto.findViewById(R.id.textView3);
                    largeBtnBorrar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PaintSecondActivity.setB(true);
                            PaintSecondActivity.setTP(third);

                            borrarpunto.dismiss();
                        }
                    });
//show and wait for user interaction
                    borrarpunto.show();


                    break;
                case R.id.save:

                    AlertDialog.Builder salvarDibujo = new AlertDialog.Builder(this);
                    salvarDibujo.setTitle("Saving");
                    salvarDibujo.setMessage("Save?");
                    salvarDibujo.setPositiveButton("Accept", new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialog, int which){

//Save
                            second.setDrawingCacheEnabled(true);
//attempt to save
                            String imgSaved = MediaStore.Images.Media.insertImage(
                                    getContentResolver(), second.getDrawingCache(),
                                    UUID.randomUUID().toString()+".png", "drawing");

                            if(imgSaved!=null){
                                Toast savedToast = Toast.makeText(getApplicationContext(),
                                        "Saved", Toast.LENGTH_SHORT);
                                savedToast.show();
                            }
                            else{
                                Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                        "Error", Toast.LENGTH_SHORT);
                                unsavedToast.show();
                            }
                            second.destroyDrawingCache();


                        }
                    });
                    salvarDibujo.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                            dialog.cancel();
                        }
                    });
                    salvarDibujo.show();

                    break;
                default:

                    break;
            }
        }
    }


