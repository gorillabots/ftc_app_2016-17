package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

//Created by Mikko on 12/11/16

@Autonomous(name="RedBeacons", group="Comp")
public class RedBeacons extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;

    ColorSensor floorColor;
    ColorSensor beaconColorL;
    ColorSensor beaconColorR;

    ModernRoboticsI2cRangeSensor range;

    public void runOpMode()
    {
        driveTrain = new AutonomousDriveTrain(); //Initialize hardware
        driveTrain.init(this);

        floorColor = hardwareMap.colorSensor.get("floorColor");
        beaconColorL = hardwareMap.colorSensor.get("beaconColor");
        beaconColorR = hardwareMap.colorSensor.get("beaconColor2");
        beaconColorL.setI2cAddress(I2cAddr.create8bit(58));
        beaconColorR.setI2cAddress(I2cAddr.create8bit(62));

        floorColor.enableLed(false); //Disable LEDs
        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);

        range = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "range");

        waitForStart();

        driveTrain.resetGyro();

        driveTrain.backRightGyro(2.5, .8, 1, .1); //First (diagonal) move

        //driveTrain.rightGyroToTouch(.3, 1, .1); //Go to wall slowly
        driveTrain.goToDistance(range, 6, .5, .1);

        driveTrain.left(.08, .5);

        floorColor.enableLed(true);
        driveTrain.forwardsGyroToLine(floorColor, .22, 1, .05); //Go to white line 1
        floorColor.enableLed(false);

        driveTrain.back(.06 , .3); //Align color sensors

        driveTrain.goToDistance(range, 11, 1, .2); //Approach beacon

        sleep(100);

        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
        driveTrain.beaconResponse(TeamColors.RED, beaconColorL, beaconColorR); //Press button

        driveTrain.left(.08, .25);

        driveTrain.back(.25, .8);

        floorColor.enableLed(true);
        driveTrain.backGyroToLine(floorColor, .23, 1, .05); //Go white line 2
        floorColor.enableLed(false);

        //driveTrain.back(.095, .3); //Align color sensors

        driveTrain.goToDistance(range, 11, 1, .2); //Approach beacon

        sleep(100);

        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
        driveTrain.beaconResponse(TeamColors.RED, beaconColorL, beaconColorR); //Press button

        //Finishing up

        floorColor.enableLed(false); //Disable LEDs
        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
    }
}


