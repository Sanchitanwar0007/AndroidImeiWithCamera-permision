package com.jaguar.getiimei;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;


public class NewActivity extends Activity {
  // MainActivity main_activity= new MainActivity();
   private Button camera_button;
   int REQUEST_IMAGE_CAPTURE=1;
   private Button screen_test_Button;
   ConstraintLayout backGround;
    private android.hardware.Camera mCameraDevice;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afterimei_activity);
        backGround=(ConstraintLayout) findViewById(R.id.backgroundId);
         camera_button= (Button) findViewById(R.id.camera_checkID);
         screen_test_Button=(Button) findViewById(R.id.screen_testID);
         screen_test_Button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent myIntent = new Intent(NewActivity.this, ScreenTest.class);
                 startActivity(myIntent);
             }
         });
           camera_button.setOnClickListener(new View.OnClickListener() {
               @Override
              public void onClick(View v) {
                   //openCamera();
                    // startCamera();
                   mCameraDevice = android.hardware.Camera.open();
                   sCam();
                    /*
                   try {
                       mCameraDevice = android.hardware.Camera.open();

                       if(mCameraDevice == null){
                           Toast.makeText(NewActivity.this, "Camera error", Toast.LENGTH_SHORT).show();
                       }
                       else
                       {
                           Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                           intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                   MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getPath());

                           startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                       }
                   } catch (RuntimeException e) {
                       Log.e(TAG, "fail to connect Camera", e);
                       Toast.makeText(NewActivity.this, "Camera error"+e, Toast.LENGTH_SHORT).show();
                       // Throw exception
                   }*/
               }
           });
    }
    android.hardware.Camera camera;
    public  void sCam(){
        android.hardware.Camera.CameraInfo cameraInfo = new android.hardware.Camera.CameraInfo();
        for (int cameraId = 0; cameraId < android.hardware.Camera.getNumberOfCameras(); cameraId++) {
            android.hardware.Camera.getCameraInfo(cameraId, cameraInfo);
            if (cameraInfo.facing == android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT){
                camera = android.hardware.Camera.open(cameraId);
                break;
            }
        }
        if(checkCameraHardware(this)){
            if(android.hardware.Camera.getNumberOfCameras()==2) {
                Toast.makeText(NewActivity.this, "Camera Opening", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(NewActivity.this, "No Back Camera", Toast.LENGTH_SHORT).show();
            }


        }
        else{
            Toast.makeText(NewActivity.this, "Camera error", Toast.LENGTH_SHORT).show();
        }
    }
        private boolean checkCameraHardware(Context context) {
            if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                // this device has a camera
                return true;
            } else {
                // no camera on this device
                return false;
            }
        }
 /*   private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getPath());

            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }*/
    /*   public void openCamera(){
        android.hardware.Camera mCameraDevice;

            try {
                mCameraDevice = android.hardware.Camera.open();
               // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               // intent.putExtra(MediaStore.EXTRA_OUTPUT,
                  //      MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getPath());

             //   startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

            } catch (RuntimeException e) {
                Log.e(TAG, "fail to connect Camera", e);
                // Throw exception
            }
        }
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {

            return false;
        }
    }*/

    final static private int NEW_PICTURE = 1;
    private String mCameraFileName;


    //ImageButton Edit = (ImageButton) findViewById(R.id.internetbrowser4);
void Camera() {
    camera_button.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            // Picture from camera
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

            // This is not the right way to do this, but for some reason, having
            // it store it in
            // MediaStore.Images.Media.EXTERNAL_CONTENT_URI isn't working right.

            Date date = new Date();
            DateFormat df = new SimpleDateFormat("-mm-ss");

            String newPicFile = "Bild" + df.format(date) + ".jpg";
            String outPath = "/sdcard/" + newPicFile;
            File outFile = new File(outPath);

            mCameraFileName = outFile.toString();
            Uri outuri = Uri.fromFile(outFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outuri);


            startActivityForResult(intent, NEW_PICTURE);

        }
    });
}
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == NEW_PICTURE)
        {
            // return from file upload
            if (resultCode == Activity.RESULT_OK)
            {
                Uri uri = null;
                if (data != null)
                {
                    uri = data.getData();
                }
                if (uri == null && mCameraFileName != null)
                {
                    uri = Uri.fromFile(new File(mCameraFileName));
                }
                File file = new File(mCameraFileName);
                if (!file.exists()) {
                    file.mkdir();
                }
            }
        }
    }


}







