package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

import org.firstinspires.ftc.teamcode.TeamColors;
import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrain;
import org.firstinspires.ftc.teamcode.submodules.BallControl;

/**
 * Created by Jarred on 3/2/2017.
 */
@Autonomous(name="ShootFirstRed", group="Beta")
public class ShootFirstRed extends LinearOpMode {
    AutonomousDriveTrain driveTrain;
    BallControl shooter;

    //Sensors
    ColorSensor floorColor;
    ColorSensor beaconColorL;
    ColorSensor beaconColorR;
    ModernRoboticsI2cRangeSensor range;

    public void runOpMode() {
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
        driveTrain.resetGyro();
        waitForStart();

        shooter.newRunFlywheel(true);

        driveTrain.right(.68, .4);
        sleep(1000);
        shooter.newRunElevator(true);
        sleep(2500);

        shooter.newRunFlywheel(false);
        shooter.newStopElevator();

        driveTrain.left(.1, .4);
        driveTrain.frontRight(1.55, .5);

        driveTrain.turnToGyroAny(270, .1 , 5);


        driveTrain.goToDistance(range, 6, .5, .1);
        driveTrain.right(.09,.4);
//        driveTrain.resetGyro();


        driveTrain.left(.1198, .5);


        floorColor.enableLed(true);
        driveTrain.forwardsGyroToLineTimeout(floorColor, .22, 1, .05, 3); //Go to white line 1
        floorColor.enableLed(false);

        driveTrain.back(.02 , .3); //Align color sensors

        driveTrain.goToDistanceGyro(range, 13, 1, .2,5,.2); //Approach beacon

        sleep(100);

        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
        driveTrain.beaconResponse(TeamColors.RED, beaconColorL, beaconColorR); //Press button

        //Second beacon
        driveTrain.left(.02, .25);

        driveTrain.back(.75, .8);

        driveTrain.goToDistanceGyro(range, 20, 1, .2,5,.2);

        floorColor.enableLed(true);
        driveTrain.backGyroToLine(floorColor, .22,1, .05); //Go white line 2
        floorColor.enableLed(false);

        driveTrain.goToDistanceGyro(range, 13, 1, .6, 5,.2); //Approach beacon

        sleep(100);

        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
        driveTrain.beaconResponse(TeamColors.RED, beaconColorL, beaconColorR);
    }
}
