package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Mikko on 12/11/16.
 */

@Autonomous(name="RedBeaconsShoot", group="Comp")
public class RedBeaconsShoot extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;
    ColorSensor floorColor;
    ColorSensor beaconColorL;
    ColorSensor beaconColorR;
    Servo servoSwing;
    BallControl shooter;

    ModernRoboticsI2cRangeSensor range;

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

        floorColor.enableLed(false); //Disable LEDs
        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);

        servoSwing = hardwareMap.servo.get("servoSwing");
        servoSwing.setPosition(.56);

        range = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "range");

        waitForStart();

        driveTrain.resetGyro();

        servoSwing.setPosition(.52);
        //Go to first beacon
        //servoSwing.setPosition(.09);

        driveTrain.backRightGyro(2.5, .8, 1, .1); //First (diagonal) move

        //driveTrain.rightGyroToTouch(.3, 1, .1); //Go to wall slowly
        driveTrain.goToDistance(range, 6, .5, .1);

        servoSwing.setPosition(.52);

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

        //Shooting code follows

        driveTrain.leftGyro(.5, .8, 2, .1);

        driveTrain.turnToGyroAny(250, .2, 5);

        driveTrain.right(.5, .6);

        long startTime = System.currentTimeMillis();
        long target = startTime + 5000;

        shooter.newRunFlywheel(true);
        shooter.newRunElevator(false);

        while(System.currentTimeMillis() < target)
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

        //TODO: Retract touch servo?


        /* Old Code (For Reference)
        telemetry.addData("Finished", "Left");
        telemetry.addData("Now", "TurnToGyro");
        telemetry.update();

        driveTrain.turnToGyro(3, .2);
        //driveTrain.GyroRotation(0e, .2);

        telemetry.addData("Finished", "TurnToGyro");
        telemetry.addData("Now", "EnableLED");
        telemetry.update();

        floorColor.enableLed(true);

        telemetry.addData("Finished", "EnableLED");
        telemetry.addData("Now", "ForwardsToLine");
        telemetry.update();

        driveTrain.forwardsGyroToLine(floorColor, .5, 1, .1);

        telemetry.addData("Finished", "ForwardsToLine");
        telemetry.update();
        */
        /*
        //beacon code //////
        driveTrain.forwardsGyro(.02, .5, 1, .2);
        floorColor.enableLed(false);

        driveTrain.turnToGyro(1, .25);

        driveTrain.right(.200, .5);

        driveTrain.left(.02, .5);
        driveTrain.turnToGyro(1, .25);
        driveTrain.right(.01, .5);
        // driveTrain.turnToGyro();



        if (ColorHelper.getBeaconColor(beaconColor).equals("blue")) {
            driveTrain.right(.1, .5);
            driveTrain.left(.02, .5);
        }
        driveTrain.forwards(.01, .5);
        if (ColorHelper.getBeaconColor(beaconColor).equals("blue")) {
            driveTrain.right(.1, .5);
            driveTrain.left(.02, .5);
        }
        driveTrain.back(.02, .5);
        if (ColorHelper.getBeaconColor(beaconColor).equals("blue")) {
            driveTrain.right(.1, .5);
            driveTrain.left(.02, .5);
        }
        if (ColorHelper.getBeaconColor(beaconColor).equals("blue")) {
            driveTrain.right(.1, .5);
            driveTrain.left(.02, .5);
        }
        */
    }
}
//driveTrain.backwardsGyroToLine(floorColor, .5);

        /*driveTrain.left(.25);
        driveTrain.backwardsGyroToLine(floorColor);


        driveTrain.right(.1); //Changed


        if(ColorHelper.getBeaconColor(beaconColor) == "blue"){
            driveTrain.left(.25);
            driveTrain.right(.1); //Changed
        }

        beaconColor.enableLed(false); //Go to second beacon
        floorColor.enableLed(true);

        driveTrain.back(.25);
        driveTrain.backwardsGyroToLine(floorColor);
        driveTrain.right(.02112);
        beaconColor.enableLed(true);
        driveTrain.right(.352);
        if(ColorHelper.getBeaconColor(beaconColor) == "blue"){
            driveTrain.left(.095);
            driveTrain.right(.352);

        }
        driveTrain.left(.15);



        beaconColor.enableLed(false); //Disable LEDs
        floorColor.enableLed(false);
  //  }
//
//}
*/



