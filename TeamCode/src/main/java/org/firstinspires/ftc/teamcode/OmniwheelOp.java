package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by mikko on 9/30/16.
 */

@TeleOp(name = "OmniwheelOp", group = "Concept")
public class OmniwheelOp extends OpMode
{
    Drivetrain drivetrain;

    ModernRoboticsI2cGyro gyro;
    ColorSensor colorSensor;    // Hardware Device Object
    int rotation = 0;

    public void init()
    {
        drivetrain = new Drivetrain(hardwareMap, telemetry);
        gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        colorSensor = hardwareMap.colorSensor.get("BeaconSensor");
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

    public void loop()
    {
        float stickX = gamepad1.left_stick_x; // Stick position (Absolute heading)
        float stickY = gamepad1.left_stick_y; // Each is in range -1 to 1

        float stickRot = gamepad1.right_stick_x / 2f; //Used to rotate the robot;

        if(gamepad1.a)
        {
            gyro.resetZAxisIntegrator();
        }

        rotation = gyro.getHeading();

        drivetrain.oneStickLoop(stickX, stickY, stickRot, rotation);
        String beaconColor = ColorHelper.getBeaconColor(colorSensor);
        String floorColor = ColorHelper.getFloorColor(colorSensor);
    }
}
