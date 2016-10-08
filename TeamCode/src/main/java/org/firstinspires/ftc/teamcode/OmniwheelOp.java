package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by mikko on 9/30/16.
 */

@TeleOp(name = "OmniwheelOp", group = "Concept")
public class OmniwheelOp extends OpMode implements SensorEventListener
{
    Drivetrain drivetrain;

    int rotation;

    public void init()
    {
        drivetrain = new Drivetrain(this);

        mSensorManager = (SensorManager) hardwareMap.appContext.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public void loop()
    {
        drivetrain.oneStickLoop(rotation);
    }

    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;

    // orientation values
    private float azimuth = 0.0f;      // value in radians
    private float pitch = 0.0f;        // value in radians
    private float roll = 0.0f;         // value in radians

    private float[] mGravity;       // latest sensor value
    private float[] mGeomagnetic;   // latest sensor values

    @Override
    public void start()
    {
        // Using SENSOR_DELAY_UI as the refresh Delay is ok for telemetry, But consider using
        // SENSOR_DELAY_NORMAL or SENSOR_DELAY_GAME when using this in an actual OpMode
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Both sensor values are required to calculate orientation. Only one value will have
        // changed each time this method is called, we assume we can still use the other value.
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = sensorEvent.values;
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mGeomagnetic = sensorEvent.values;
        }
        if (mGravity != null && mGeomagnetic != null) {  //make sure we have both before calling getRotationMatrix
            float R[] = new float[9];
            float I[] = new float[9];

            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimuth = orientation[0];
                pitch = orientation[1];
                roll = orientation[2];
            }
        }
    }

    @Override
    public void stop() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Required by interface, but we don't need to do anything here.
    }
}
