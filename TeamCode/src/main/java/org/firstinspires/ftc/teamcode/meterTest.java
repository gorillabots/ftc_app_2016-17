package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Mikko on 12/11/16.
 */

@Autonomous(name="Meter Test", group="beta")
public class meterTest extends LinearOpMode
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
        
        sleep(2000);

        driveTrain.frontRight.setPower(0);
        driveTrain.backRight.setPower(0);
        driveTrain.frontLeft.setPower(0);
        driveTrain.backLeft.setPower(0);

        int frontRightEnd = driveTrain.frontRight.getCurrentPosition();
        int backRightEnd = driveTrain.backRight.getCurrentPosition();
        int frontLeftEnd = driveTrain.frontLeft.getCurrentPosition();
        int backLeftEnd = driveTrain.backLeft.getCurrentPosition();

        int frontRightDelta = frontRightEnd - frontRightStart;
        int backRightDelta = backRightEnd - backRightStart;
        int frontLeftDelta = frontLeftEnd - frontLeftStart;
        int backLeftDelta = backLeftEnd - backLeftStart;
        
        telemetry.addData("Front Right Start", frontRightStart);
        telemetry.addData("Back Right Start", backRightStart);
        telemetry.addData("Front Left Start", frontLeftStart);
        telemetry.addData("Back Left Start", backLeftStart);

        telemetry.addData("Front Right End", frontRightEnd);
        telemetry.addData("Back Right End", backRightEnd);
        telemetry.addData("Front Left End", frontLeftEnd);
        telemetry.addData("Back Left End", backLeftEnd);

        telemetry.addData("Front Right Delta", frontRightDelta);
        telemetry.addData("Back Right Delta", backRightDelta);
        telemetry.addData("Front Left Delta", frontLeftDelta);
        telemetry.addData("Back Left Delta", backLeftDelta);

        telemetry.update();

        sleep(10000);
    }
}

