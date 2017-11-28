package com.example.root.vid_stream2;

import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

public class AmpIndicator {

    static MediaRecorder med_rec;

    AmpIndicator(String src){


    }

    public void start(TextView txtview){
        if(med_rec==null) {
            med_rec = new MediaRecorder();


//        if(src.equals("Cam")){
//            med_rec.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
//        }
//        else {

            med_rec.setAudioSource(MediaRecorder.AudioSource.MIC);
//        }
            try {
                med_rec.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                med_rec.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                med_rec.setOutputFile("/dev/null");
                med_rec.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                med_rec.start();
            }
            catch (IllegalStateException e){
                Log.e("EROOO","jojojoo");
            }
//            new ampSetter().execute(txtview);
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Log.d("Home", "Permission Granted");
//                    initializeView(v);
//                } else {
//                    Log.d("Home", "Permission Failed");
//                    Toast.makeText(getActivity().getBaseContext(), "You must allow permission record audio to your mobile device.", Toast.LENGTH_SHORT).show();
//                    getActivity().finish();
//                }
//            }
//            // Add additional cases for other permissions you may have asked for
//        }
//    }

    public static double getAmp(TextView txtview) {

        if(med_rec != null){
            double x = med_rec.getMaxAmplitude();
            txtview.setText("lolz: "+x);
            return x;
        }
        else{
            txtview.setText("lolz2: "+"-1");
            return -1;
        }
    }
    public static double getAmp() {

        if(med_rec != null){
            double x = med_rec.getMaxAmplitude();
            return x;
        }
        else{
            return -1;
        }
    }

    public void stop(){
        if(med_rec!=null){
            med_rec.stop();
            med_rec.release();
        }
        med_rec = null;
    }

}

class ampSetter extends AsyncTask<TextView, String, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(TextView... txtview) {
        while(true) {
            AmpIndicator.getAmp(txtview[0]);
        }
//        return null;
    }
}


