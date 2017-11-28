package com.example.root.vid_stream2;

/**
 * Created by avdesh on 27/11/17.
 */
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class ProfileActivity extends AppCompatActivity implements LocationListener{


    private TextView textViewUsername, textViewUserEmail;
    private TextView lati,longi;
    private TextView soundy,speedy;
    private Button trigger_btn;
    GPSTracker gpst;
    int locchangenum = 0;
    Location mFusedLocationClient;
    AmpIndicator myAmpObj;
    LocationManager lmngr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewUserEmail = (TextView) findViewById(R.id.textViewUseremail);
        trigger_btn = (Button) findViewById(R.id.trigger_btn);
        lati = (TextView) findViewById(R.id.textView2);
        longi = (TextView) findViewById(R.id.textView3);
        soundy = (TextView) findViewById(R.id.soundtext);
        speedy = (TextView) findViewById(R.id.speedtext);





        //TRIGGER BUTTON CODE

        trigger_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(i);
//                lmngr.requestLocationUpdates();
            }
        });

        /////////////////////////////////////////////////////



        textViewUserEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
        textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());


        //////GPS

//        gpst = new GPSTracker(ProfileActivity.this);
//        updateLocation(gpst.getLocation());
        lmngr = (LocationManager) getSystemService(ProfileActivity.this.LOCATION_SERVICE);



//        LocationListener listener = new LocationListener() {
//            public void onLocationChanged(Location location) {
//                // called when a new location is found by the network location provider.
//                lati.setText(locchangenum);
//                locchangenum++;
//                longi.setText(location.getLongitude()+"");
////                updateLocation(gpst.getLocation());
//            }
//
//            public void onStatusChanged(String provider, int status, Bundle extras) {}
//
//            public void onProviderEnabled(String provider) {}
//
//            public void onProviderDisabled(String provider) {}
//        };
//
//        if(ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            lmngr.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                    100,
//                    1, listener);
//
//        }

        ////SPEED
        LocationManager locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                float speed = location.getSpeed();
                Log.d("HI","LALAL COOCOO");
                speed *= 18/5.0;
                speedy.setText("Speed is: "+speed);
                if(speed<0.5){
                    speedy.setText("Stationary");
                }
                else if(speed<5){
                    speedy.setText("Walking");
                }
                else if(speed <16){
                    speedy.setText("Running");
                }
                else{
                    speedy.setText("Too fast to handle!");
                }
                lati.setText(""+location.getLatitude());
                longi.setText(""+location.getLongitude());



//                Toast.makeText(MainActivity.this, "Current speed:" + location.getSpeed(),
//                        Toast.LENGTH_SHORT).show();

            }

            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);








        ////SOUND
        if(ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            Log.d("PERMI WALA","Nahi mili");
            ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, 29);
        }
        else{
            Log.d("PERMI WALA","Mil gayi");
//            Toast.makeText(MainActivity.this, "Yay permission.", Toast.LENGTH_SHORT).show();
            myAmpObj = new AmpIndicator("MIC");
            myAmpObj.start(soundy);
            Handler h = new Handler();
            final int delay = 100;
            soundy.setText("Hi Yo");

            final Handler handler = new Handler();
            final Runnable runnable=new Runnable(){
                @Override
                public void run() {
//                    Timer t = new Timer();
                    double amp = AmpIndicator.getAmp();
//                    if(amp>7000){
////                            noise_level_txtview.setTextColor(getResources().getColor(R.color.RED));
////                        mainback.setBackgroundColor(getResources().getColor(R.color.RED));
//                    }
//                    else{
////                            noise_level_txtview.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
//                        mainback.setBackgroundColor(getResources().getColor(R.color.YELLOW));
//                    }
                    soundy.setText("NL: " + amp);

                };
            };

//            handler.postDelayed(runnable,delay);
            Timer timerUploadTime = new Timer();
            TimerTask timerUploadTimeTask = new TimerTask() {
                @Override
                public void run() {
                    handler.post(runnable);
                }
            };
//            timerUploadTime.schedule(timerUploadTimeTask, 1, Integer.parseInt(Utils.loadStringValue(mycontext, "refresh")));
            timerUploadTime.scheduleAtFixedRate(timerUploadTimeTask,delay,100);
        }


    }



    private void updateLocation(Location location) {
        if (location == null) {
//            lati.setText("LocNull Monster");
            return;
        }

        // save location details
        lati.setText(String.valueOf(location.getLatitude()));
        longi.setText(String.valueOf(location.getLongitude()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.menuSettings:
                Toast.makeText(this, "You clicked settings", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

//    @Override
//    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        Speed_and_Sound sns = new Speed_and_Sound(soundy,speedy,lati,longi,ProfileActivity.this);
//        sns.mainFunc();
//    }

    @Override
    public void onLocationChanged(Location location) {
//        updateLocation(location);
        lati.setText(location.getLatitude()+"");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
//        Log.d("poopoo","choochoo");
        switch (requestCode) {
            case 29: {
//                Toast.makeText(MainActivity.this, "Hahha", Toast.LENGTH_SHORT).show();
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Home", "Permission Granted");
//                    Toast.makeText(MainActivity.this, "Lolz You must allow permission record audio to your mobile device.", Toast.LENGTH_SHORT).show();
                    AmpIndicator myAmpObj = new AmpIndicator("MIC");
//                    while(true){
//                        noise_level_txtview.setText("Noise Lev: "+myAmpObj.getAmp());
//                    }
                } else {
                    Log.d("Home", "Permission Failed");
//                    Toast.makeText(MainActivity.this, "You must allow permission record audio to your mobile device.", Toast.LENGTH_SHORT).show();
                }
            }
            // Add additional cases for other permissions you may have asked for
        }
    }
}

