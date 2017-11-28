package com.example.aidana.project;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int CAMERA_REQUEST = 10;
    public static final int ImageGalleryRequest = 20;
//    Button btn1;
//    Button btn2;
    final int MENU_DELETE = 1;
    final int MENU_UPDATE = 2;
    ImageView imgPicture;
Button btn;
    DBHelper dbh;
    SQLiteDatabase db;

    private ViewGroup mainLayout;
    SharedPreferences sharedPreferences;

    int idOfTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
//        btn1 = (Button) findViewById(R.id.button);
//        btn2 = (Button) findViewById(R.id.button2);
        imgPicture = (ImageView) findViewById(R.id.imageView);
        btn=(Button)findViewById(R.id.button2) ;
        btn.setOnClickListener(this);
        dbh = new DBHelper(this);
        db = dbh.getWritableDatabase();

        idOfTask = Integer.parseInt(getIntent().getStringExtra("id"));


        Cursor c = db.query("contacts", null,"_id=?", new String[]{idOfTask+""}, null, null, null);
        if(c.moveToFirst()){

            long id = c.getLong(c.getColumnIndex("_id"));
          //  String name = c.getString(c.getColumnIndex("name"));
            String phone = c.getString(c.getColumnIndex("phone"));
            String image = c.getString(c.getColumnIndex("image"));
            if(!image.equals("")){
                byte[] d = Base64.decode(image, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(d, 0, d.length);
                imgPicture.setImageBitmap(bitmap);
            }
        }
        c.close();

        registerForContextMenu(imgPicture);
        mainLayout = (RelativeLayout) findViewById(R.id.main);

    }

    public void take1() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureName = getPictureName();
        File imageFile = new File(pictureDirectory, pictureName);
        Uri pictureUri = Uri.fromFile(imageFile);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        startActivityForResult(camera, CAMERA_REQUEST);
    }

    public void alarm(View v){
        Intent i=new Intent(this,ClockActivity.class);
        startActivity(i);
    }
    public void paint(View v){
        Intent paint=new Intent(this,PaintActivity.class);
        startActivity(paint);
    }

    private String getPictureName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return "PlantPlacesImage" + timestamp + ".jpg";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ImageGalleryRequest) {
                Uri imageUri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap realImage = BitmapFactory.decodeStream(inputStream);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    realImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] b = baos.toByteArray();

                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                    ContentValues cv = new ContentValues();
                    cv.put("image", encodedImage);
                    db.update("contacts", cv, "_id=?", new String[]{idOfTask+""});

                    imgPicture.setImageBitmap(realImage);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // show a message to the user indictating that the image is unavailable.
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }

            }
        }

    }


    public void save(View v) {
        //invokke the img gallery
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        //REFERENCE WHERE WE FIND THE DATA
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);
        photoPicker.setDataAndType(data, "image/*");
        startActivityForResult(photoPicker, ImageGalleryRequest);

    }




//    private OnTouchListener onTouchListener() {
//        return new OnTouchListener() {
//
//
//
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//
//                final int x = (int) event.getRawX();
//                final int y = (int) event.getRawY();
//
//                switch (event.getAction() & MotionEvent.ACTION_MASK) {
//
//                    case MotionEvent.ACTION_MOVE:
//                        imgPicture.setX(event.getRawX() - 330);
//                        imgPicture.setY(event.getRawY() - 330);
//
//
//                            break;
//                }
//                mainLayout.invalidate();
//                return true;
//            }
//        };
//
//
//    }


    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.imageView:
                menu.add(0, MENU_DELETE, 0, "Delete");
                menu.add(0, MENU_UPDATE, 0, "Update");
                break;

        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            // пункты меню для tvColor
            case MENU_DELETE:
                ContentValues cv = new ContentValues();
                cv.put("image", "");
                db.update("contacts", cv, "_id=?", new String[]{idOfTask+""});
                imgPicture.setImageBitmap(null);
                break;
            case MENU_UPDATE:
                save(null);
                break;


        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        take1();
    }
}




