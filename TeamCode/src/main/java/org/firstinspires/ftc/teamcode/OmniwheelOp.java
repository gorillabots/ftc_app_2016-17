package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by mikko on 9/30/16.
 */

@TeleOp(name = "OmniwheelOp", group = "Concept")
public class OmniwheelOp extends OpMode
{
    Drivetrain drivetrain;

    ModernRoboticsI2cGyro gyro;

    int rotDefault;
    int rotation = 0;

    public void init()
    {
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

        rotDefault = gyro.getHeading();

    }

    public void loop()
    {
        float stickX = gamepad1.left_stick_x; // Stick position (Absolute heading)
        float stickY = gamepad1.left_stick_y; // Each is in range -1 to 1

        float stickRot = gamepad1.right_stick_x; //Used to rotate the robot;

        rotation = rotDefault - gyro.getHeading();

        if(rotation < 0)
        {
            rotation += 360;
        }

        rotation %= 360;

        drivetrain.oneStickLoop(stickX, stickY, stickRot, rotation);
    }
}
