package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

import org.firstinspires.ftc.teamcode.subclasses.ColorHelper;
import org.firstinspires.ftc.teamcode.subclasses.AutonomousDriveTrain;

/**
 * Created by Mikko on 12/11/16.
 */

@Disabled
@Autonomous(name="Motion Test", group="Tests")
public class MovementTEst extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;
    ColorSensor floorColor;
    ColorSensor beaconColor;
    ColorHelper colorHelp = new ColorHelper();

    public void runOpMode()
    {
        driveTrain = new AutonomousDriveTrain(); //Initialize hardware
        driveTrain.init(this);

        floorColor = hardwareMap.colorSensor.get("floorColor");
        beaconColor = hardwareMap.colorSensor.get("beaconColor");
        beaconColor.setI2cAddress(I2cAddr.create8bit(58));
        beaconColor.enableLed(false);
        floorColor.enableLed(false);


        waitForStart();

        driveTrain.resetGyro();

        //driveTrain.forwards(1, .5);

        int frontRightStart = driveTrain.frontRight.getCurrentPosition();
        int backRightStart = driveTrain.backRight.getCurrentPosition();
        int frontLeftStart = driveTrain.frontLeft.getCurrentPosition();
        int backLeftStart = driveTrain.backLeft.getCurrentPosition();

        driveTrain.frontRight.setPower(.5);
        driveTrain.backRight.setPower(.5);
        driveTrain.frontLeft.setPower(-.5);
        driveTrain.backLeft.setPower(-.5);

        driveTrain.forwardsGyro(1,.5, 1,.095);
    }
}

