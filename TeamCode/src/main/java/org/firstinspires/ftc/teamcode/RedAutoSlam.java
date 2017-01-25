package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Mikko on 12/11/16.
 */

@Autonomous(name="RedBeaconsBroken", group="beta")
public class RedAutoSlam extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;
    ColorSensor floorColor;
    ColorSensor beaconColorL;
    ColorSensor beaconColorR;
    Servo sensorSwing;

    public void runOpMode()
    {
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


        sensorSwing = hardwareMap.servo.get("touchServo");
        sensorSwing.setPosition(.56);

        waitForStart();

        driveTrain.resetGyro();


        //Go to first beacon
        sensorSwing.setPosition(.09);
        driveTrain.backRightGyro(2.5, .8, 1, .1); //Go out
        driveTrain.rightGyroToTouch(.3, 1, .1); //Go to wall slowly
        sensorSwing.setPosition(52);
        driveTrain.left(.015, .5); //Go out

        floorColor.enableLed(true);
        //driveTrain.forwardsToLine(floorColor, .3);
        driveTrain.forwardsGyroToLine(floorColor, .3, 1, .05);
        floorColor.enableLed(false);

        //driveTrain.turnToGyro(2, .09);

        sleep(100);

        driveTrain.back(.12, .35);
        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
        driveTrain.beaconResponse(TeamColors.RED, beaconColorL, beaconColorR);


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



