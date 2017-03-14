package com.accelero.kencleng.kencelerometer;

import android.hardware.Sensor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private long lastUpdate;
    private float x;
    private float y;
    public TextView sumbuX;
    private float z;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            getAccelerometer(event);
        }
    }

    private void getAccelerometer(SensorEvent event){
        float[] value = event.values;

        x = value[0];
        y = value[1];
        z = value[2];

        lastUpdate = event.timestamp;
    }

    @Override
    public void onAccuratyChanged(Sensor sensor, int accuracy){

    }

    @Override
    public void onResume(){
        super.onResume();

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
                sensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
