package com.example.moon.locationtracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    Button dbbutton;
    ToggleButton tb;
    private NMapView mMapView;// 지도 화면 View
    private final String CLIENT_ID = "Rfiu1FCN4mw9nHdVEqR4";// 애플리케이션 클라이언트 아이디 값
    private static final String TAG = "MainActivity";
    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        200);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        tv = (TextView) findViewById(R.id.textView2);
        dbbutton = (Button)findViewById(R.id.showDB);
        tv.setText("위치정보 미수신중");
        tb = (ToggleButton)findViewById(R.id.toggle1);
        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent locationServiceIntent = new Intent(MainActivity.this , MyLocationService.class);
                    if(tb.isChecked()){
                        tv.setText("수신중..");
                        startService(locationServiceIntent);
                    }else{
                        stopService(locationServiceIntent);
                        tv.setText("위치정보 미수신중");
                    }
                }catch(SecurityException ex){
                }
            }
        });
        dbbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),NMapViewer.class);
                startActivity(intent);
                List<dataFrame> dbList =  db.getAlldataFrames();
                Log.e(TAG,"size: "+dbList.size());
                for(int i=0;i<dbList.size();i++){
                   Log.d(TAG,"DB : "+dbList.get(i).getTime()+" 위도: "+dbList.get(i).getLongitude() +" 경도: "+dbList.get(i).getLatitude()+"\n");
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 200: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
