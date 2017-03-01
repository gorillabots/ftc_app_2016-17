package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

import org.firstinspires.ftc.teamcode.TeamColors;
import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrain;

//Created by ??? on 1/15/2017

@Deprecated
@Autonomous(name="BlueBeacons", group="Dead")
public class BlueBeacons extends LinearOpMode
{
    //Submodules
    AutonomousDriveTrain driveTrain;

    //Sensors
    ColorSensor floorColor;
    ColorSensor beaconColorL;
    ColorSensor beaconColorR;
    ModernRoboticsI2cRangeSensor range;

    public void runOpMode()
    {
        //Initializing Submodules
        driveTrain = new AutonomousDriveTrain(); //Initialize hardware
        driveTrain.init(this);

        //Initializing Sensors
        floorColor = hardwareMap.colorSensor.get("floorColor");
        beaconColorL = hardwareMap.colorSensor.get("beaconColor");
        beaconColorR = hardwareMap.colorSensor.get("beaconColor2");
        beaconColorL.setI2cAddress(I2cAddr.create8bit(58));
        beaconColorR.setI2cAddress(I2cAddr.create8bit(62));
        range = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "range");

        floorColor.enableLed(false);
        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        //Running
        driveTrain.resetGyro();

        driveTrain.frontRightGyro(2.5, .8, 1, .1); //Go out
        driveTrain.goToDistance(range, 6, .5, .1);
        driveTrain.left(.15, .5); //Back away from wall

        floorColor.enableLed(true);
        driveTrain.backGyroToLine(floorColor, .22, 1, .05); //Go to first beacon line
        floorColor.enableLed(false);

        driveTrain.goToDistance(range, 11, 1, .2);
        driveTrain.beaconResponse(TeamColors.BLUE, beaconColorL, beaconColorR);

        driveTrain.left(.05,.25);
        driveTrain.forwards(.2, .8);
        floorColor.enableLed(true);
        driveTrain.forwardsGyroToLine(floorColor, .2,1,.05);
        driveTrain.turnToGyro(2, .2);
        driveTrain.goToDistance(range,11,1,.2);

        driveTrain.beaconResponse(TeamColors.BLUE, beaconColorL, beaconColorR);

        //Finishing up
        floorColor.enableLed(false);
        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
    }
}
