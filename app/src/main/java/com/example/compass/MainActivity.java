package com.example.compass;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    private ImageView imageViewDynamic;

    private TextView textViewDegree;
    private float currentDegree;

    private float[] floatGravity = new float[3];
    private float[] floatGeoMagnetic = new float[3];

    private final float[] floatOrientation = new float[3];
    private final float[] floatRotationMatrix = new float[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageViewDynamic = findViewById(R.id.imageViewDinamic);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        SensorEventListener sensorEventListenerAccelerometer = new SensorEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSensorChanged(SensorEvent event) {
                floatGravity = event.values;

                SensorManager.getRotationMatrix(floatRotationMatrix, null, floatGravity, floatGeoMagnetic);
                SensorManager.getOrientation(floatRotationMatrix, floatOrientation);

                imageViewDynamic.setRotation((float) (-floatOrientation[0]*180/3.14159));

                float floatGravity = Math.round(event.values[0]);

                textViewDegree.setText("Курс к Северу: " + Float.toString(floatGravity) + " грудусы");

                RotateAnimation rotateAnimation = new RotateAnimation(currentDegree, -floatGravity, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(210);
                rotateAnimation.setFillAfter(true);

                imageViewDynamic.startAnimation(rotateAnimation);
                currentDegree = -floatGravity;
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

        SensorEventListener sensorEventListenerMagneticField = new SensorEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSensorChanged(SensorEvent event) {
                floatGeoMagnetic = event.values;

                SensorManager.getRotationMatrix(floatRotationMatrix, null, floatGravity, floatGeoMagnetic);
                SensorManager.getOrientation(floatRotationMatrix, floatOrientation);

                imageViewDynamic.setRotation((float) (-floatOrientation[0]*180/3.14159));

                float floatGeoMagnetic = Math.round(event.values[0]);

                textViewDegree.setText("Курс к Северу: " + Float.toString(floatGeoMagnetic) + " грудусы");

                RotateAnimation rotateAnimation = new RotateAnimation(
                        currentDegree,
                        -floatGeoMagnetic,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(210);
                rotateAnimation.setFillAfter(true);

                imageViewDynamic.startAnimation(rotateAnimation);
                currentDegree = -floatGeoMagnetic;
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        sensorManager.registerListener(sensorEventListenerAccelerometer, sensorAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(sensorEventListenerMagneticField, sensorMagneticField, SensorManager.SENSOR_DELAY_GAME);
//        init();
    }

//    protected void init() {
//         imageViewDynamic = findViewById(R.id.imageViewDynamic);
//         textViewDegree = findViewById(R.id.textViewDegree);
//         sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

//        imageViewDynamic = findViewById(R.id.imageViewDynamic);
//        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//
//        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//
//        SensorEventListener sensorEventListenerAccelerometer = new SensorEventListener() {
//            @Override
//            public void onSensorChanged(SensorEvent event) {
//                floatGravity = event.values;
//
//                SensorManager.getRotationMatrix(floatRotationMatrix, null, floatGravity, floatGeoMagnetic);
//                SensorManager.getOrientation(floatRotationMatrix, floatOrientation);
//
//                imageViewDynamic.setRotation((float) (-floatOrientation[0]*180/3.14159));
//            }
//
//            @Override
//            public void onAccuracyChanged(Sensor sensor, int accuracy) {
//            }
//        };
//
//        SensorEventListener sensorEventListenerMagneticField = new SensorEventListener() {
//            @Override
//            public void onSensorChanged(SensorEvent event) {
//                floatGeoMagnetic = event.values;
//
//                SensorManager.getRotationMatrix(floatRotationMatrix, null, floatGravity, floatGeoMagnetic);
//                SensorManager.getOrientation(floatRotationMatrix, floatOrientation);
//
//                imageViewDynamic.setRotation((float) (-floatOrientation[0]*180/3.14159));
//            }
//
//            @Override
//            public void onAccuracyChanged(Sensor sensor, int accuracy) {
//            }
//        };
//        sensorManager.registerListener(sensorEventListenerAccelerometer, sensorAccelerometer, SensorManager.SENSOR_DELAY_GAME);
//        sensorManager.registerListener(sensorEventListenerMagneticField, sensorMagneticField, SensorManager.SENSOR_DELAY_GAME);
//    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        sensorManager.unregisterListener(this);
//    }
//
//    @SuppressLint("SetTextI18n")
//    public void onSensorChanged(SensorEvent event) {
//        float degree = Math.round(event.values[0]);
//
//        textViewDegree.setText("Курс к Северу: " + Float.toString(degree) + " грудусы");
//
//        RotateAnimation rotateAnimation = new RotateAnimation(currentDegree, -degree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        rotateAnimation.setDuration(210);
//        rotateAnimation.setFillAfter(true);
//
//        imageViewDynamic.startAnimation(rotateAnimation);
//        currentDegree = -degree;
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//    }
}