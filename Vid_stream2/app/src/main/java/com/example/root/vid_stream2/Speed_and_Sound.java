package com.example.root.vid_stream2;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Speed_and_Sound {

    AmpIndicator myAmpObj;
    TextView noise_level_txtview;
    TextView speed_level_txtview;
    TextView lati;
    TextView longi;
    Context context;
//    TextView usb_read_txtview;
//    RelativeLayout mainback;

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    UsbDevice device;
    UsbDeviceConnection connection;
    UsbManager usbManager;
    //    Usb serialPort;
    PendingIntent pendingIntent;

    public Speed_and_Sound(TextView noise_level_txtview, TextView speed_level_txtview, TextView lati, TextView longi
    , Context context) {
        this.noise_level_txtview = noise_level_txtview;
        this.speed_level_txtview = speed_level_txtview;
        this.lati = lati;
        this.longi = longi;
        this.context = context;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);

//        mainback = (RelativeLayout) findViewById(R.id.backlayout);
//        noise_level_txtview = (TextView) findViewById(R.id.soundtext);
//        speed_level_txtview = (TextView) findViewById(R.id.speedtext);
//        lati = (TextView) findViewById(R.id.textView2);
//        longi = (TextView) findViewById(R.id.textView3);
//        usb_read_txtview = (TextView) findViewById(R.id.textView4);

//        //Permission
//        if (ContextCompat.checkSelfPermission(MainActivity.this,
//                USB_SERVICE)
//                != PackageManager.PERMISSION_GRANTED) {
//                int a = 1;
//                ActivityCompat.requestPermissions(MainActivity.this,
//                        new String[]{USB_SERVICE},
//                        a);
//
//        }

    public void mainFunc(){
        ///SPEED
        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                float speed = location.getSpeed();
                Log.d("hi","yo");
                speed *= 18/5.0;
                speed_level_txtview.setText("Speed is: "+speed);
                if(speed<0.5){
                    speed_level_txtview.setText("Stationary");
                }
                else if(speed<5){
                    speed_level_txtview.setText("Walking");
                }
                else if(speed <16){
                    speed_level_txtview.setText("Running");
                }
                else{
                    speed_level_txtview.setText("Too fast to handle!");
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




        ////NOISE

        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            Log.d("PERMI WALA","Nahi mili");
//            ActivityCompat.requestPermissions(, new String[]{Manifest.permission.RECORD_AUDIO}, 29);
        }
        else{
            Log.d("PERMI WALA","Mil gayi");
//            Toast.makeText(MainActivity.this, "Yay permission.", Toast.LENGTH_SHORT).show();
            myAmpObj = new AmpIndicator("MIC");
            myAmpObj.start(noise_level_txtview);
            Handler h = new Handler();
            final int delay = 100;
            noise_level_txtview.setText("Hi Yo");

//            Handler h = new Handler();
//
//            Runnable r = new Runnable() {
//                int il = 0;
//                @Override
//                public void run() {
////                    while(true) {
//                        Log.e("hohohohoho","eiufhjbdefjk");
//                        noise_level_txtview.setText("NL: "+AmpIndicator.getAmp());
//                        il++;
//                        h.postDelayed(r,delay);
////                    }
//                }
//            };
//
//            h.post(r);

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
                    noise_level_txtview.setText("NL: " + amp);

                };
            };

//            handler.postDelayed(runnable,delay);
            Timer  timerUploadTime = new Timer();
            TimerTask timerUploadTimeTask = new TimerTask() {
                @Override
                public void run() {
                    handler.post(runnable);
                }
            };
//            timerUploadTime.schedule(timerUploadTimeTask, 1, Integer.parseInt(Utils.loadStringValue(mycontext, "refresh")));
            timerUploadTime.scheduleAtFixedRate(timerUploadTimeTask,delay,100);
        }




//            Handler h = new Handler();
//
//            Runnable r = new Runnable() {
//                int il = 0;
//                @Override
//                public void run() {
////                    while(true) {
//                        Log.e("hohohohoho","eiufhjbdefjk");
//                        noise_level_txtview.setText("NL: "+AmpIndicator.getAmp());
//                        il++;
//                        h.postDelayed(r,delay);
////                    }
//                }
//            };
//
//            h.post(r);





    }

//    @Override
//    protected void onStart() {
//        super.onStart();
////        // ________________CODE_FOR_USB_COMM________________
////
////        Handler h2 = new Handler();
////        final int delay2 = 100;
////        usb_read_txtview.setText("USB: ");
////        final Handler handler2 = new Handler();
////        final usb_serial_comm usc_obj = new usb_serial_comm(MainActivity.this);
////        final Runnable runnable2=new Runnable(){
////            @Override
////            public void run() {
////                usc_obj.connector_meth(usb_read_txtview);
////            };
////        };
////
////        //            handler.postDelayed(runnable,delay);
////        Timer  timerUploadTime2 = new Timer();
////        TimerTask timerUploadTimeTask2 = new TimerTask() {
////            @Override
////            public void run() {
////                handler2.post(runnable2);
////            }
////        };
//////            timerUploadTime.schedule(timerUploadTimeTask, 1, Integer.parseInt(Utils.loadStringValue(mycontext, "refresh")));
//////        timerUploadTime2.scheduleAtFixedRate(timerUploadTimeTask2,delay2,100);
////        timerUploadTime2.schedule(timerUploadTimeTask2,1);
//
//    }
//
//    @Override
//    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//
//    }







}
