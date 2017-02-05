package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

//Created by ??? on 1/15/2017

@Autonomous (name="BlueBeacons", group="Comp")
public class BlueBeacons extends LinearOpMode
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

        floorColor.enableLed(false);
        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
        range = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "range");
        telemetry.addData("init" , "1");
        telemetry.update();
        telemetry.addData("init" , "2");
        telemetry.update();

        waitForStart(); //Initialization done!

        driveTrain.resetGyro();

        telemetry.addData("init" , "3");
        telemetry.update();
        driveTrain.frontRightGyro(2.5, .8, 1, .1); //Go out
        //driveTrain.rightGyroToTouch(.3, 1, .1); //Go to wall slowly
        driveTrain.goToDistance(range, 6, .5, .1);
        //sensorSwing.setPosition(.52); //Raise touch arm
        driveTrain.left(.15, .5); //Back away from wall

        floorColor.enableLed(true);
        driveTrain.backGyroToLine(floorColor, .22, 1, .05); //Go to first beacon line
        floorColor.enableLed(false);

        //driveTrain.forwards(.06, .3);
        driveTrain.goToDistance(range, 11, 1, .2);
        driveTrain.beaconResponse(TeamColors.BLUE, beaconColorL, beaconColorR);

        //Finishing up

        driveTrain.left(.05,.25);
        driveTrain.forwards(.2, .8);
        floorColor.enableLed(true);
        driveTrain.forwardsGyroToLine(floorColor, .2,1,.05);
        driveTrain.turnToGyro(2, .2);
        driveTrain.goToDistance(range,11,1,.2);


        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
        driveTrain.beaconResponse(TeamColors.BLUE, beaconColorL, beaconColorR);
    }
}
