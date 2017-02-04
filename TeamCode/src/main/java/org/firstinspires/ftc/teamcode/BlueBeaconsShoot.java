package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Owner on 1/15/2017.
 */

@Autonomous (name="BlueBeaconsShoot", group="Comp")
public class BlueBeaconsShoot extends LinearOpMode
{

    AutonomousDriveTrain driveTrain;
    ColorSensor floorColor;
    ColorSensor beaconColorL;
    ColorSensor beaconColorR;
    Servo sensorSwing;
    ModernRoboticsI2cRangeSensor range;

    BallControl shooter;

    public void runOpMode()
    {
        shooter = new BallControl(hardwareMap, telemetry);

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
        sensorSwing = hardwareMap.servo.get("servoSwing");
        sensorSwing.setPosition(.56);
        telemetry.addData("init" , "2");
        telemetry.update();

        waitForStart(); //Initialization done!

        driveTrain.resetGyro();

        sensorSwing.setPosition(.52);
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

        sleep(3000);

        driveTrain.beaconResponse(TeamColors.BLUE, beaconColorL, beaconColorR);


        //Finishing up

        driveTrain.left(.05,.25);
        driveTrain.forwards(.2, .8);
        floorColor.enableLed(true);
        driveTrain.forwardsGyroToLine(floorColor, .2,1,.05);
        //driveTrain.turnToGyro(2, .2);
        driveTrain.goToDistance(range,11,1,.2);


        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);

        sleep(3000);

        driveTrain.beaconResponse(TeamColors.BLUE, beaconColorL, beaconColorR);


        //Shooting code follows

        driveTrain.leftGyro(.5, .8, 2, .1);

        driveTrain.turnToGyroAny(110, .2, 5);

        driveTrain.right(.5, .6);

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

        //Finishing up

        floorColor.enableLed(false); //Disable LEDs
        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
    }
}
