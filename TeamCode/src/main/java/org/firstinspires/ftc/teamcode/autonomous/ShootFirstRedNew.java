package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

import org.firstinspires.ftc.teamcode.TeamColors;
import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrain;
import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrainNewGyro;
import org.firstinspires.ftc.teamcode.submodules.BallControl;

/**
 * Created by Jarred on 3/2/2017.
 */
@Autonomous(name="ShootFirstRedNew", group="Beta")
public class ShootFirstRedNew extends LinearOpMode {
    AutonomousDriveTrainNewGyro driveTrain;
    BallControl shooter;

    //Sensors
    ColorSensor floorColor;
    ColorSensor beaconColorL;
    ColorSensor beaconColorR;
    ModernRoboticsI2cRangeSensor range;

    public void runOpMode()
    {

telemetry.addData("state","starting");
            telemetry.update();
        //Initialize Submodules
        driveTrain = new AutonomousDriveTrainNewGyro(); //Initialize hardware
        driveTrain.init(this, 180);
        telemetry.addData("state","driverain init");
            telemetry.update();
        shooter = new BallControl(hardwareMap, telemetry);
        telemetry.addData("state","ball init");
            telemetry.update();
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
        //driveTrain.resetGyro();
        telemetry.addData("state","waiting for start");
            telemetry.update();
        waitForStart();
        telemetry.addData("state","start");
            telemetry.update();

        shooter.newRunFlywheel(true);

        driveTrain.right(.68, .4);
        sleep(1000);
        shooter.newRunElevator(true);
        sleep(2500);

        shooter.newRunFlywheel(false);
        shooter.newStopElevator();

        driveTrain.left(.1, .4);
        driveTrain.frontRight(1.55, .6);

        driveTrain.turn(270, 2, .5);

        /*

        driveTrain.goToDistance(range, 6, .5, .5);
        driveTrain.right(.09,.4);
//        driveTrain.resetGyro();


        driveTrain.left(.1198, .5);


        floorColor.enableLed(true);
        driveTrain.forwardsToLine(floorColor, .22); //Go to white line 1
        floorColor.enableLed(false);

        driveTrain.backwards(.02 , .5); //Align color sensors

        driveTrain.goToDistance(range, 13, 1, .5); //Approach beacon

        sleep(100);

        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
        driveTrain.beaconResponse(TeamColors.RED, beaconColorL, beaconColorR); //Press button

        //Second beacon
        driveTrain.left(.02, .5);

        driveTrain.backwards(.75, .8);

        driveTrain.goToDistance(range, 20, 1, .4);

        floorColor.enableLed(true);
        driveTrain.backToLine(floorColor, .22); //Go white line 2
        floorColor.enableLed(false);

        driveTrain.goToDistance(range, 13, 1, .6); //Approach beacon

        sleep(100);

        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
        driveTrain.beaconResponse(TeamColors.RED, beaconColorL, beaconColorR); */

        driveTrain.stop();
    }
}
