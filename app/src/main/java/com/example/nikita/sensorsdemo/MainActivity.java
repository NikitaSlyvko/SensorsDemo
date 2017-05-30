package com.example.nikita.sensorsdemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;

    private TextView sensorsLis;
    private ImageView rotatedImage;

    private float currentDegree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentDegree = 0;

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorsLis = (TextView) findViewById(R.id.sensors_list);
        rotatedImage = (ImageView) findViewById(R.id.rotation_image);

        readSensorList();
    }

    private void readSensorList() {
        List<Sensor> list = sensorManager.getSensorList(Sensor.TYPE_ALL);
        String tmp = null;
        for(Sensor sensor : list)
            tmp += sensor.getName() + "\n";

        sensorsLis.setText(tmp);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float degree = (float) Math.toDegrees(event.values[0]);

        RotateAnimation rotateAnimation = new RotateAnimation(
            currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );

        rotateAnimation.setDuration(210);
        rotateAnimation.setFillAfter(true);

        rotatedImage.startAnimation(rotateAnimation);
        currentDegree = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
