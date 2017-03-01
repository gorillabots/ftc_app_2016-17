package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

import org.firstinspires.ftc.teamcode.TeamColors;
import org.firstinspires.ftc.teamcode.submodules.ColorHelper;
import org.firstinspires.ftc.teamcode.submodules.Drivetrain;

/**
 * Created by mikko on 9/30/16.
 */

@Deprecated
@Disabled
@TeleOp(name = "OmniwheelOp", group = "Dead")
public class OmniwheelOp extends OpMode
{
    Drivetrain drivetrain;

//    ModernRoboticsI2cGyro gyro;
    ColorSensor colorSensor;
    ColorSensor colorSensorF;    // Hardware Device Object
    int rotation = 0;

    public void init()
    {
        //drivetrain = new Drivetrain(hardwareMap, telemetry);
  //      gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");
    //    gyro.calibrate();
        colorSensor = hardwareMap.colorSensor.get("BeaconSensor");
        colorSensorF = hardwareMap.colorSensor.get("FloorSensor");
        colorSensorF.setI2cAddress(I2cAddr.create8bit(58));
        try
        {
            // make sure the gyro is calibrated.
      //      while (gyro.isCalibrating())
            {
                Thread.sleep(50);
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        //gyro.resetZAxisIntegrator();

    }

    public void loop()
    {
        float stickX = gamepad1.left_stick_x; // Stick position (Absolute heading)
        float stickY = gamepad1.left_stick_y; // Each is in range -1 to 1

        float stickRot = gamepad1.right_stick_x / 2f; //Used to rotate the robot;

        if(gamepad1.a)
        {
          //  gyro.resetZAxisIntegrator();
        }

        //rotation = gyro.getHeading();

        //drivetrain.oneStickLoop(stickX, stickY, stickRot, rotation);
        TeamColors beaconColor = ColorHelper.getBeaconColor(colorSensor);
        String floorColor = ColorHelper.getFloorColor(colorSensorF);
        telemetry.addData("Beacon color", beaconColor);
        telemetry.addData("Floor color", floorColor);
    }
}