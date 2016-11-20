package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

/**
 * Created by root on 11/13/16.
 */

@Autonomous(name="RedBeacons", group="concept")
public class RedBeaconsOp extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;

    ColorSensor floorColor;

    public void runOpMode()
    {
        driveTrain = new AutonomousDriveTrain();
        driveTrain.init(this);

        floorColor = hardwareMap.colorSensor.get("floorColor");
        floorColor.setI2cAddress(I2cAddr.create8bit(58));

        floorColor.enableLed(false);

        waitForStart();

        floorColor.enableLed(true);

        driveTrain.backRight(1.42);
        driveTrain.rightToTouch();
        driveTrain.left(.1);
        sleep(100);
        driveTrain.backToLine(floorColor);

        floorColor.enableLed(false);
    }
}
