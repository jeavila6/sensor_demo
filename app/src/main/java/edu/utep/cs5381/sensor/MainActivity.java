package edu.utep.cs5381.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView mTextViewAzimuth;
    private TextView mTextViewPitch;
    private TextView mTextViewRoll;

    private SensorManager mSensorManager;
    private Sensor mRotationVectorSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mRotationVectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        setContentView(R.layout.activity_main);

        mTextViewAzimuth = findViewById(R.id.textViewAzimuth);
        mTextViewPitch = findViewById(R.id.textViewPitch);
        mTextViewRoll = findViewById(R.id.textViewRoll);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] R = new float[16];
        SensorManager.getRotationMatrixFromVector(R, event.values);

        float[] orientation = new float[3];
        SensorManager.getOrientation(R, orientation);

        double azimuth = orientation[0];
        double pitch = orientation[1];
        double roll = orientation[2];

        mTextViewAzimuth.setText(String.format(Locale.getDefault(), "%.3f", azimuth));
        mTextViewPitch.setText(String.format(Locale.getDefault(), "%.3f", pitch));
        mTextViewRoll.setText(String.format(Locale.getDefault(), "%.3f", roll));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mRotationVectorSensor, 75000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
