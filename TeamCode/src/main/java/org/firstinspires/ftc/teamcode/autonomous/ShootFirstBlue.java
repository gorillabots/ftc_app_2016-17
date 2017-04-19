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
@Autonomous(name="ShootFirstBlue", group="Beta") //Observed to be too far right before looking at beacons
public class ShootFirstBlue extends LinearOpMode {
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
        floorColor.setI2cAddress(I2cAddr.create8bit(0x44)); //68 in decimal
        beaconColorL.setI2cAddress(I2cAddr.create8bit(0x3A)); //58 in decimal
        beaconColorR.setI2cAddress(I2cAddr.create8bit(0x3E)); //62 in decimal
        floorColor.enableLed(false);
        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);

        range = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "range");
        driveTrain.resetGyro();
        waitForStart();

        driveTrain.right(.68, .4);

        shooter.newRunFlywheel(true);
        sleep(1000);
        shooter.newRunElevator(true);
        sleep(2500);

        shooter.newRunFlywheel(false);
        shooter.newStopElevator();

        driveTrain.left(.085, .4);

        driveTrain.backRight(1.55, .5);

        driveTrain.turnToGyroAny(90, .2 , 5);

        driveTrain.forwards(.05,.8);
        sleep(500);
        driveTrain.goToDistance(range, 6, .5, .1);
        sleep(500);
        driveTrain.right(.09,.4);
        driveTrain.resetGyro();


        driveTrain.left(.1988, .5);

        floorColor.enableLed(true);
        driveTrain.backGyroToLineTimeout(floorColor, .22, 1, .05,4); //Go to first beacon line
        floorColor.enableLed(false);

        driveTrain.back(.02, .2);

        driveTrain.goToDistanceGyro(range,13,1,.2, 5, .2);

        sleep(100);

        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
        driveTrain.beaconResponse(TeamColors.BLUE, beaconColorL, beaconColorR);

        //Second beacon
        driveTrain.left(.02, .25);
        driveTrain.forwards(.75, .8);

        driveTrain.goToDistanceGyro(range,20,1,.2, 5, .2);
        floorColor.enableLed(true);
        driveTrain.forwardsGyroToLineTimeout(floorColor, .22, 1, .05,4);

        driveTrain.back(.05, .2);

        driveTrain.goToDistanceGyro(range,13,1,.6, 5, 0.2);

        sleep(100);

        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
        driveTrain.beaconResponse(TeamColors.BLUE, beaconColorL, beaconColorR);
    }
}
