package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

import org.firstinspires.ftc.teamcode.TeamColors;
import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrain;
import org.firstinspires.ftc.teamcode.submodules.BallControl;

//Created by Mikko on ???

@Autonomous(name="Red Beacons Shoot Utica", group="Backup")
public class RedBeaconsShootUtica extends LinearOpMode
{
    //Submodules
    AutonomousDriveTrain driveTrain;
    BallControl shooter;

    //Sensors
    ColorSensor floorColor;
    ColorSensor beaconColorL;
    ColorSensor beaconColorR;
    ModernRoboticsI2cRangeSensor range;

    public void runOpMode()
    {
        //Initialize Submodules
        driveTrain = new AutonomousDriveTrain(); //Initialize hardware
        driveTrain.init(this);

        shooter = new BallControl(hardwareMap, telemetry);

        //Initialize Sensors
        floorColor = hardwareMap.colorSensor.get("floorColor");
        beaconColorL = hardwareMap.colorSensor.get("beaconColor");
        beaconColorR = hardwareMap.colorSensor.get("beaconColor2");
        beaconColorL.setI2cAddress(I2cAddr.create8bit(58));
        beaconColorR.setI2cAddress(I2cAddr.create8bit(62));
        floorColor.enableLed(false);
        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);

        range = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "range");

        waitForStart();

        driveTrain.resetGyro();

        //First beacon
        driveTrain.backRightGyro(2.5, .8, 1, .1); //First (diagonal) move

        driveTrain.goToDistance(range, 6, .5, .1);

        driveTrain.left(.08, .5);

        floorColor.enableLed(true);
        driveTrain.forwardsGyroToLine(floorColor, .22, 1, .05); //Go to white line 1
        floorColor.enableLed(false);

        driveTrain.back(.04 , .3); //Align color sensors

        driveTrain.goToDistance(range, 11, 1, .2); //Approach beacon

        sleep(100);

        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
        driveTrain.beaconResponse(TeamColors.RED, beaconColorL, beaconColorR); //Press button

        //Second beacon
        driveTrain.left(.05, .25);

        driveTrain.back(.25, .8);

        floorColor.enableLed(true);
        driveTrain.backGyroToLine(floorColor, .23, 1, .05); //Go white line 2
        floorColor.enableLed(false);

        driveTrain.goToDistance(range, 11, 1, .2); //Approach beacon

        sleep(100);

        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
        driveTrain.beaconResponse(TeamColors.RED, beaconColorL, beaconColorR); //Press button

        if(!driveTrain.lastPressLeft)
        {
            driveTrain.forwards(0.15, 0.3);
        }

        //Go to shoot

        driveTrain.leftGyro(.14, .8, 2, .1);

        driveTrain.turnToGyroAny(236, .2, 5);

        shooter.newRunFlywheel(true);

        driveTrain.right(.92, .6);

        long startTime = System.currentTimeMillis();
        long target = startTime + 5000;

        //Shoot
        shooter.newRunElevator(true);

        while(System.currentTimeMillis() < target && opModeIsActive())
        {
            telemetry.addData("Action", "Shooting");
            telemetry.update();
            sleep(200);
        }

        shooter.newRunFlywheel(false);
        shooter.newStopElevator();

        driveTrain.right(.4, .6);

        //Finishing up
        floorColor.enableLed(false);
        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
    }
}