package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

import org.firstinspires.ftc.teamcode.TeamColors;
import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrain;
import org.firstinspires.ftc.teamcode.submodules.BallControl;

//Created by ??? on 1/15/2017


@Autonomous (name="Blue Beacons Shoot Utica", group="Backup")
public class BlueBeaconsShootUtica extends LinearOpMode
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

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart(); //Initialization done!

        driveTrain.resetGyro();

        //First beacon
        driveTrain.frontRightGyro(2.5, .8, 1, .1); //Go out
        driveTrain.goToDistance(range, 6, .5, .1);
        driveTrain.left(.1, .5); //Back away from wall

        floorColor.enableLed(true);
        driveTrain.backGyroToLine(floorColor, .22, 1, .05); //Go to first beacon line
        floorColor.enableLed(false);

        driveTrain.back(.02, .2);

        driveTrain.goToDistance(range, 11, 1, .2);

        sleep(100);

        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
        driveTrain.beaconResponse(TeamColors.BLUE, beaconColorL, beaconColorR);

        //Second beacon
        driveTrain.left(.02, .25);
        driveTrain.forwards(.2, .8);
        floorColor.enableLed(true);
        driveTrain.forwardsGyroToLine(floorColor, .2,1,.05);

        driveTrain.back(.05, .2);

        driveTrain.goToDistance(range,11,1,.2);

        sleep(100);

        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
        driveTrain.beaconResponse(TeamColors.BLUE, beaconColorL, beaconColorR);


        if(!driveTrain.lastPressLeft)
        {
            driveTrain.forwards(0.15, 0.3);
        }

        //Go to shoot
        driveTrain.left(.2, .8);

        driveTrain.turnToGyroAny(100, .17, 5);

        shooter.newRunFlywheel(true);

        driveTrain.right(1, .6);

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

        driveTrain.right(.5, .6);

        //Finishing up
        floorColor.enableLed(false);
        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
    }
}
