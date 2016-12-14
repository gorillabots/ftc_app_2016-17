package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * An op mode that uses the geomagnetic and accelerometer values to calculate device
 * orientation and return those values in telemetry.
 * It makes use of getRotationMatrix() and getOrientation()
 *
 * Based on code written by FTC Team 3941
 *
 *
 * @see "https://github.com/acharraggi/my_ftc_app/blob/master/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/OrientOp.java"
 */
@Disabled
@Autonomous(name = "GyroTest", group = "Concept")
public class GyroTest extends OpMode implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;

    // orientation values
    private float azimuth = 0.0f;      // value in radians
    private float pitch = 0.0f;        // value in radians
    private float roll = 0.0f;         // value in radians

    private float[] mGravity;       // latest sensor value
    private float[] mGeomagnetic;   // latest sensor values

    Drivetrain drivetrain;

    ModernRoboticsI2cGyro gyro;

    int rotation = 0;

    @Override
    public void init() {
        mSensorManager = (SensorManager) hardwareMap.appContext.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        drivetrain = new Drivetrain(hardwareMap, telemetry);

        gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");

        gyro.calibrate();

        try
        {
            // make sure the gyro is calibrated.
            while (gyro.isCalibrating())
            {
                Thread.sleep(50);
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        gyro.resetZAxisIntegrator();
    }

    @Override
    public void start() {
        // Using SENSOR_DELAY_UI as the refresh Delay is ok for telemetry, But consider using
        // SENSOR_DELAY_NORMAL or SENSOR_DELAY_GAME when using this in an actual OpMode
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }
    @Override
    public void loop() {
        float stickX = gamepad1.left_stick_x; // Stick position (Absolute heading)
        float stickY = gamepad1.left_stick_y; // Each is in range -1 to 1

        float stickRot = gamepad1.right_stick_x / 2f; //Used to rotate the robot;

        rotation = gyro.getHeading();

        drivetrain.oneStickLoop(stickX, stickY, stickRot, rotation,gamepad1.back);

        if (mGravity != null && mGeomagnetic != null) {
            telemetry.addData("azimuth", Math.round(Math.toDegrees(azimuth)));
            telemetry.addData("pitch", Math.round(Math.toDegrees(pitch)));
            telemetry.addData("roll", Math.round(Math.toDegrees(roll)));
        }
        else {
            if (mGravity != null) {
                telemetry.addData("note1", "no default accelerometer sensor on phone");
            }
            if (mGeomagnetic != null) {
                telemetry.addData("note2", "no default magnetometer sensor on phone");
            }
        }

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

    /**
     * It is VERY important to unregister the sensor update listener when this OpMode ends because
     * until you do unregister the listener, the OpMode will remain in memory
     *
     * If you forget to deregister the event listener, Eventually the phone will run out of unused
     * memory and will become unstable and/or crash
     */
    @Override
    public void stop() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Required by interface, but we don't need to do anything here.
    }
}
