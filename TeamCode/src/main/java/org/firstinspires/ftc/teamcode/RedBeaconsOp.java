package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

/**
 * Created by root on 11/13/16.
 */
@Disabled
@Autonomous(name="RedBeaconsbad", group="concept")
public class RedBeaconsOp extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;

    ColorSensor floorColor;

    public void runOpMode()
    {
        driveTrain = new AutonomousDriveTrain(); //Intialize drive train
        driveTrain.init(this);

        floorColor = hardwareMap.colorSensor.get("floorColor"); //Initialize color sensor
        floorColor.setI2cAddress(I2cAddr.create8bit(58));

        floorColor.enableLed(false); //Disable color sensor LED

        waitForStart();

        floorColor.enableLed(true); //Enable color sensor LED

        driveTrain.backRight(1.42); //Run autonomous itself
        driveTrain.rightToTouch();
        driveTrain.left(.1);
        sleep(100);


        floorColor.enableLed(false); //Disable color sensor LED
    }
}
