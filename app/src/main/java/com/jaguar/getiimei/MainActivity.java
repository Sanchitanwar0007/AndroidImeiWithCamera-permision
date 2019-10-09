package com.jaguar.getiimei;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button imei_button;
    private TextView imei_text;
    String IMEI_Number_Holder;
    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imei_button = (Button) findViewById(R.id.imei_buttonID);
        imei_text = (TextView) findViewById(R.id.imei_textViewID);

        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

       int permission_All = 1;
        final String[] Permission = {android.Manifest.permission.CALL_PHONE, android.Manifest.permission.CAMERA,
                android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!hasPermissions(this, Permission)) {
            ActivityCompat.requestPermissions(this, Permission, permission_All);
        }


        imei_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

              hasImeiNo();
               Intent myIntent = new Intent(MainActivity.this, NewActivity.class);
                startActivity(myIntent);



            }

        });

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }


        }
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
   public void hasImeiNo(){
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(MainActivity.this, "You must enable Phone Call Permission to Detect IMEI", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);

            return ;
        }

        IMEI_Number_Holder = telephonyManager.getDeviceId();

        imei_text.setText(IMEI_Number_Holder);
       /*Intent myIntent = new Intent(MainActivity.this, NewActivity.class);
        startActivity(myIntent);*/



    }
}
