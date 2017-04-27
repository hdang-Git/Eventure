package com.example.hai.eventfinder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hai.eventfinder.Formatting.FormatUtils;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import static com.example.hai.eventfinder.ViewHolder.context;

public class OcrCameraActivity extends AppCompatActivity {

    FloatingActionButton fab;
    TextView scannedOutput;
    ImageView imageView;
    TextRecognizer textRecognizer;
    Bitmap bitmap;
    Logger log = Logger.getAnonymousLogger();
    private static final int PHOTO_MEDIA_REQUEST = 5;
    private static final int REQUEST_WRITE_PERMISSIONS = 10;
    File photoFile;
    File dir;
    String directory = "Eventure";
    String fileName = "OCR_Photo.jpg";
    Uri uriImg;
    StrictMode.VmPolicy.Builder builder;
    Context context;
    String MyPREFERENCES = "sharedPrefs"; //name of file for sharedPreferences
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_camera);
        fab = (FloatingActionButton) findViewById(R.id.camButton);
        scannedOutput = (TextView) findViewById(R.id.scannedBlock);
        imageView = (ImageView) findViewById(R.id.imageView);

        context = getApplication().getApplicationContext();
        //Need this in order to read text from image
        textRecognizer = new TextRecognizer.Builder(context).build();

        //Set policy because of API 24 & > because of issues with uri exposure
        //TODO: Delete this
        builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        log.info("HAS PERMISSION?: " + hasPermissionInManifest(context, android.provider.MediaStore.ACTION_IMAGE_CAPTURE));
        log.info("HAS PERMISSION Camera ?: " + hasPermissionInManifest(context,  Manifest.permission.CAMERA));

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //TODO: Fix restoring state when activity is destroyed
        if(savedInstanceState != null){
            log.info("Activity recreated.");
            //set text data back
            String scannedResult = savedInstanceState.getString("scannedText");
            scannedOutput.setText(scannedResult);
            Log.d("output ocr" , scannedResult);
            //Set imageview with bitmap
            Bitmap bm = savedInstanceState.getParcelable("bitmap");
            imageView.setImageBitmap(bm);
        } else {
            log.info("savedInstanceState in activity is null");
        }


        //Gets permission to write to storage. If storage is successful, then call the takePhoto() method
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(OcrCameraActivity.this, new
                        String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_WRITE_PERMISSIONS);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //On rotation, save data for persistence
        if(uriImg != null && scannedOutput != null && bitmap != null){
            outState.putString("scannedText", scannedOutput.getText().toString());
            outState.putParcelable("imgUri", uriImg);
            outState.putParcelable("bitmap",bitmap);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //retrieve back data
        super.onRestoreInstanceState(savedInstanceState);
        //set text data back
        String scannedResult = savedInstanceState.getString("scannedText");
        scannedOutput.setText(scannedResult);
        //Set imageview with bitmap
        Bitmap bm = savedInstanceState.getParcelable("bitmap");
        imageView.setImageBitmap(bm);
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
                    log.info("Permission to write to external storage granted.");
                    //Take a picture
                    takePhoto();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(context, "Sorry, you must enable write permissions to use this feature.", Toast.LENGTH_LONG).show();
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

    /**
     * This method checks if the passed in permision is in the manifest
     * @param context
     * @param permissionName
     * @return
     */
    public boolean hasPermissionInManifest(Context context, String permissionName) {
        final String packageName = context.getPackageName();
        try {
            final PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            final String[] declaredPermisisons = packageInfo.requestedPermissions;
            if (declaredPermisisons != null && declaredPermisisons.length > 0) {
                for (String p : declaredPermisisons) {
                    if (p.equals(permissionName)) {
                        return true;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return false;
    }

    /**
     *  Calls and launches the phone's camera storing the photo data inside the uriImg from
     *  the intent via the MediaStore.EXTRA_OUTPUT constant when returned
     */
    public void takePhoto() {
        //createFile();
        //uriImg = Uri.fromFile(photoFile);
        //uriImg = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", createImageFile());

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            uriImg = FileProvider.getUriForFile(OcrCameraActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    createImageFile());
            log.info("Greater than version M");
        } else {
            uriImg = Uri.fromFile(createImageFile());
            log.info("Less than version M");
        }

        log.info("HAS PERMISSION?: " + hasPermissionInManifest(context, android.provider.MediaStore.ACTION_IMAGE_CAPTURE));
        log.info("HAS PERMISSION Camera ?: " + hasPermissionInManifest(context,  Manifest.permission.CAMERA));

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriImg);
        if(uriImg != null){
            startActivityForResult(intent, PHOTO_MEDIA_REQUEST);
        } else {
            log.info("URI IS NULL");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_MEDIA_REQUEST && resultCode == RESULT_OK) {
            sendMediaIntent();
            try {
                bitmap = decodeBitmapUri(this, uriImg);
                if (textRecognizer.isOperational() && bitmap != null) {
                    //Set the image
                    imageView.setImageBitmap(bitmap);


                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> textBlocks = textRecognizer.detect(frame);
                    String blocks = "";
                    String lines = "";
                    String words = "";
                    ArrayList<String> wordList = new ArrayList<>();
                    for (int index = 0; index < textBlocks.size(); index++) {
                        //extract scanned text blocks here
                        TextBlock tBlock = textBlocks.valueAt(index);
                        blocks += tBlock.getValue() + "\n\n ";
                        for (Text line : tBlock.getComponents()) {
                            Log.d("scanned line:", line.getValue());
                            for (Text element : line.getComponents()) {
                                //extract scanned text words here
                                wordList.add(element.getValue());
                                Log.d("scanned element:", element.getValue());
                            }
                        }
                    }
                    if (textBlocks.size() == 0) {
                        Toast.makeText(context, "Sorry, no text to show", Toast.LENGTH_SHORT).show();
                        log.info("Textblock size is 0");
                    } else {
                        //Show results
                        scannedOutput.setText(blocks);
                        //Persist data for calling activity
                        Gson gson = new Gson();
                        String jsonData = gson.toJson(wordList);
                        editor = sharedpreferences.edit();
                        editor.putString("ocrWordList", jsonData);
                        editor.commit();
                    }
                } else {
                    scannedOutput.setText("Could not set up the detector!");

                }
            } catch (Exception e) {
                Toast.makeText(this, "Failed to load Image", Toast.LENGTH_SHORT)
                        .show();
                Log.e("Check string", e.toString());
            }
        }
    }

    /**
     *  Create the folder directory and file to save photo as.
     */
    public File createImageFile(){
        // get the path to sdcard
        File sdcard = Environment.getExternalStorageDirectory();
        log.info("sd: " + sdcard);
        //Create directory
        dir = new File(sdcard.getAbsolutePath() + "/" + directory + "/");
        dir.mkdir();
        checkFileDir(dir);
        //Create new file
        photoFile = new File(dir, fileName);
        checkFileDir(photoFile);
        log.info("file: " + photoFile);
        return photoFile;
    }

    /**
     * This method just checks if the file or directory exists.
     * If the directory does not exist, make it.
     * @param fileDir a file or directory that is passed in
     */
    public void checkFileDir(File fileDir){
        if(fileDir.isDirectory()){  //check if it is a directory exists
            if(!fileDir.exists()){      //check if directory doesn't exist
                log.info("Directory DNE!");
                fileDir.mkdir();
            } else {
                log.info("Directory exists! : " + fileDir);
            }
        //TODO: delete this part or refix for file creation
        } else if(fileDir.isFile()){    //check if is file
            if(fileDir.exists()){           //check if file exists
                log.info("File Exists: " + fileDir);
            } else {
                log.info("File DNE!");
            }
        }
    }

    /**
     * This function creates the intent with the filled uri and broadcasts to all
     * receivers (apps) that can take a picture
     */
    private void sendMediaIntent() {
        Intent mediaIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaIntent.setData(uriImg);
        //Broadcast intent to any camera app that can handle this request
        this.sendBroadcast(mediaIntent);
    }

    /**
     * This method takes the uri and turns it into a bitmap. Then it rescales the image appropiately
     * to avoid memory overflow
     * @param ctx
     * @param uri
     * @return
     * @throws FileNotFoundException
     */
    private Bitmap decodeBitmapUri(Context ctx, Uri uri) throws FileNotFoundException {
        int targetW = 600;
        int targetH = 600;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        try {
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
        } catch(FileNotFoundException e){
            Log.d("decodeBitMapUri", "Error: " + e.getMessage());
        }
        return BitmapFactory.decodeStream(ctx.getContentResolver()
                .openInputStream(uri), null, bmOptions);
    }

}
