package com.example.hai.eventfinder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.vision.text.TextRecognizer;

import java.util.logging.Logger;

import static com.example.hai.eventfinder.ViewHolder.context;

public class OcrCameraActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ListView listView;
    ImageView imageView;
    TextRecognizer textRecognizer;
    Logger log = Logger.getAnonymousLogger();
    private static final int PHOTO_MEDIA_REQUEST = 5;
    private static final int REQUEST_WRITE_PERMISSIONS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_camera);
        fab = (FloatingActionButton) findViewById(R.id.camButton);
        listView = (ListView) findViewById(R.id.textListView);
        imageView = (ImageView) findViewById(R.id.imageView);

        //Need this in order to read text from image
        textRecognizer = new TextRecognizer.Builder(context).build();

        //Gets permission to write to storage. If storage is successful, then call the takePhoto() method
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(OcrCameraActivity.this, new
                        String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSIONS);
            }
        });
    }

    /**
     * Get permission to read/write to external storage
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case REQUEST_WRITE_PERMISSIONS:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    //Take a picture
                    log.info("Permission to write to external storage granted.");
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(context, "Sorry, you must enable write permissions to use this feature.", Toast.LENGTH_LONG);
                    log.info("Permission to write to external storage denied.");
                }
                break;
            // other 'case' lines to check for other
            // permissions this app might request
            default:
                log.info("onRequestPermissionsResult(): Sorry, no cases matched.");
                break;
        }
    }

    public void takePhoto(){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PHOTO_MEDIA_REQUEST);
    }





}
