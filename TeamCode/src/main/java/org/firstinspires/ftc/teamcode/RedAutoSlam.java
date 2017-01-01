package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Mikko on 12/11/16.
 */

@Autonomous(name="RedBeaconsSlam", group="beta")
public class RedAutoSlam extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;
    ColorSensor floorColor;
    ColorSensor beaconColor;
    ColorHelper colorHelp = new ColorHelper();
    ButtonPresserClass beacon;
    Servo button_presser_1;
    Servo button_presser_2;

    public void runOpMode()
    {
        driveTrain = new AutonomousDriveTrain(); //Initialize hardware
        driveTrain.init(this);

        beacon = new ButtonPresserClass();
        button_presser_1 = hardwareMap.servo.get("butt1");
        button_presser_2 = hardwareMap.servo.get("butt2");

        floorColor = hardwareMap.colorSensor.get("floorColor");
        beaconColor = hardwareMap.colorSensor.get("beaconColor");
        beaconColor.setI2cAddress(I2cAddr.create8bit(58));
        beaconColor.enableLed(false);
        floorColor.enableLed(false);

        waitForStart();

        floorColor.enableLed(true); //Go to first beacon

        driveTrain.backRight(1.76);
        driveTrain.rightGyroToTouch();
        driveTrain.left(.0352);
        driveTrain.forwardsGyroToLine(floorColor);
        driveTrain.right(.02112);

        beaconColor.enableLed(false);



        driveTrain.back(.4224);
        driveTrain.forwards(.352);

        if(ColorHelper.getBeaconColor(beaconColor) == "blue"){
            driveTrain.back(.4224);
            driveTrain.forwards(.352);
        }

        if(ColorHelper.getBeaconColor(beaconColor) == "blue"){
            driveTrain.back(.4224);
            driveTrain.forwards(.352);
        }
        beaconColor.enableLed(false); //Go to second beacon
        floorColor.enableLed(true);
        driveTrain.left(.0704);
        driveTrain.back(.2816);
        driveTrain.backwardsGyroToLine(floorColor);
        driveTrain.right(.02112);
        beaconColor.enableLed(true);
        driveTrain.back(.4224);
        driveTrain.forwards(.352);
        if(ColorHelper.getBeaconColor(beaconColor) == "blue"){
            driveTrain.back(.4224);
            driveTrain.forwards(.352);
        }

        if(ColorHelper.getBeaconColor(beaconColor) == "blue"){
            driveTrain.back(.4224);
            driveTrain.forwards(.352);
        }

        beaconColor.enableLed(false); //Disable LEDs
        floorColor.enableLed(false);
    }
}

