package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrain;
import org.firstinspires.ftc.teamcode.submodules.BallControl;
import org.firstinspires.ftc.teamcode.TeamColors;

//Created by ??? on 1/15/2017

@Deprecated
@Autonomous(name="BlueBeaconsShoot", group="Dead")
public class BlueBeaconsShoot extends LinearOpMode
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
        //Initializing Submodules
        driveTrain = new AutonomousDriveTrain();
        driveTrain.init(this);

        shooter = new BallControl(hardwareMap, telemetry);

        //Initializing Sensors
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

        //Running
        driveTrain.resetGyro();

        telemetry.update();
        driveTrain.frontRightGyro(2.5, .8, 1, .1); //Go out
        driveTrain.goToDistance(range, 6, .5, .1);
        driveTrain.left(.15, .5); //Back away from wall

        floorColor.enableLed(true);
        driveTrain.backGyroToLine(floorColor, .22, 1, .05); //Go to first beacon line
        floorColor.enableLed(false);

        driveTrain.goToDistance(range, 11, 1, .2);

        sleep(3000);

        driveTrain.beaconResponse(TeamColors.BLUE, beaconColorL, beaconColorR);

        driveTrain.left(.05,.25);
        driveTrain.forwards(.2, .8);
        floorColor.enableLed(true);
        driveTrain.forwardsGyroToLine(floorColor, .2,1,.05);
        driveTrain.goToDistance(range,11,1,.2);


        sleep(3000);

        driveTrain.beaconResponse(TeamColors.BLUE, beaconColorL, beaconColorR);

        //Go to shoot
        driveTrain.leftGyro(.5, .8, 2, .1);
        driveTrain.turnToGyroAny(110, .2, 5);
        driveTrain.right(.5, .6);

        //Shoot
        long startTime = System.currentTimeMillis();
        long target = startTime + 5000;

        shooter.newRunFlywheel(true);
        shooter.newRunElevator(false);

        while(System.currentTimeMillis() < target && opModeIsActive())
        {
            telemetry.addData("Action", "Shooting");
            telemetry.update();
            sleep(200);
        }

        shooter.newRunFlywheel(false);
        shooter.newStopElevator();

        driveTrain.right(.15, .6);

        //Finishing
        floorColor.enableLed(false); //Disable LEDs
        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
    }
}
