package com.accelero.kencleng.kencelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private long lastUpdate;
    private float x;
    private float y;
    private Float[] baris;
    public Integer i = 0;
    public String yoyoi = "yoi";
    public TextView sumbuX;
    public TextView sumbuY;
    public  TextView sumbuZ;
    private float z;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sumbuX = (TextView) findViewById(R.id.sumbuX);

        sumbuY = (TextView) findViewById(R.id.sumbuY);

        sumbuZ = (TextView) findViewById(R.id.sumbuZ);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        waktuKirim();
    }

    private class KirimData extends AsyncTask<String, Void, String>{
        private String sumbux, sumbuy, sumbuz;

        public KirimData(String sumbux, String sumbuy, String sumbuz){
            this.sumbux = sumbux;
            this.sumbuy = sumbuy;
            this.sumbuz = sumbuz;
        }
        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://192.168.43.208/cek_noise.php?kode=1&x="+sumbux+"&y="+sumbuy+"&z="+sumbuz).build();
            return null;
        }
    }


    private void waktuKirim(){
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask(){
            public void run(){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String sumbu_x = sumbuX.getText().toString();
                            String sumbu_y = sumbuY.getText().toString();
                            String sumbu_z = sumbuZ.getText().toString();
                            new KirimData(sumbu_x,sumbu_y,sumbu_z).execute();
                        }
                        catch (Exception e){

                        }
                    }
                });
            }
        }
        timer.schedule(doAsynchronousTask, 0, 1);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            getAccelerometer(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void getAccelerometer(SensorEvent event) {
        float[] value = event.values;
        Float alfa = (float) 13;
        x = value[0];
        y = value[1];
        z = value[2];

        String nilaiX = Float.toString(x);

        sumbuX.setText(Float.toString(x));
        sumbuY.setText(Float.toString(y));
        sumbuZ.setText(Float.toString(z));
       // baris = new Float[0];
       // baris[i] = x;
       // i++;
        lastUpdate = event.timestamp;
        //String[] arrayString  =  Arrays.copyOf(baris, baris.length, String[].class);

       // sumbuXX.append(arrayString[i]);
    }


    @Override
    public void onResume() {
        super.onResume();

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
                sensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
