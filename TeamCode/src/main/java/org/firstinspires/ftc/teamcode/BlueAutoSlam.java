package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Owner on 1/15/2017.
 */
@Autonomous (name="BlueBeaconSlam", group="beta")
public class BlueAutoSlam extends LinearOpMode{

    AutonomousDriveTrain driveTrain;
    ColorSensor floorColor;
    ColorSensor beaconColor;
    ColorSensor RightBS;
    ColorHelper colorHelp = new ColorHelper();
    ButtonPresserClass beacon;
    Servo button_presser_1;
    Servo button_presser_2;
    Servo sensorSwing;
    public void runOpMode()
    {
        driveTrain = new AutonomousDriveTrain(); //Initialize hardware
        driveTrain.init(this);

        beacon = new ButtonPresserClass();
        button_presser_1 = hardwareMap.servo.get("butt1");
        button_presser_2 = hardwareMap.servo.get("butt2");

        floorColor = hardwareMap.colorSensor.get("floorColor");
        beaconColor = hardwareMap.colorSensor.get("beaconColor");
        RightBS = hardwareMap.colorSensor.get("RightBS");
        beaconColor.setI2cAddress(I2cAddr.create8bit(58));
        beaconColor.setI2cAddress(I2cAddr.create8bit(62));
        beaconColor.enableLed(false);
        floorColor.enableLed(false);
        RightBS.enableLed(false);

        sensorSwing = hardwareMap.servo.get("servoSwing");
        sensorSwing.setPosition(.56);
        waitForStart();

        driveTrain.resetGyro();

         /* START COMMENT

        //Go to first beacon
        sensorSwing.setPosition(.0);
        driveTrain.backRightGyro(1.76, .5); //Go out
        driveTrain.rightGyroToTouch(.3); //Go to wall slowly
        sensorSwing.setPosition(52
        );
        driveTrain.right(.0352, .5); //Go out
        driveTrain.turnToGyro(.3);
        //driveTrain.GyroRotation(0e, .2);
        floorColor.enableLed(true);
        driveTrain.forwardsGyroToLine(floorColor, .5);

        //beacon code //////
        driveTrain.forwardsGyro(.02, 5);
        floorColor.enableLed(false);
        sleep(200);
        driveTrain.turnToGyro(.3);
        sleep(200);
        driveTrain.left(.200, .5);
        sleep(200);
        driveTrain.right(.02, .5);
        driveTrain.turnToGyro(.3);
        driveTrain.left(.01, .5);
       // driveTrain.turnToGyro();

        sleep(500);

        if(ColorHelper.getBeaconColor(beaconColor).equals("red"))
        {
            driveTrain.left(.1, .5);
            driveTrain.right(.02, .5);
        }
        driveTrain.forwards(.01, .5);
        if(ColorHelper.getBeaconColor(beaconColor).equals("red"))
        {
            driveTrain.left(.1, .5);
            driveTrain.right(.02, .5);
        }
        driveTrain.back(.02, .5);
        if(ColorHelper.getBeaconColor(beaconColor).equals("red"))
        {
            driveTrain.left(.1, .5);
            driveTrain.right(.02, .5);
        }
        if(ColorHelper.getBeaconColor(beaconColor).equals("red"))
        {
            driveTrain.left(.1, .5);
            driveTrain.right(.02, .5);
        }

        driveTrain.backwardsGyroToLine(floorColor, .5);

        /*driveTrain.right(.25);
        driveTrain.backwardsGyroToLine(floorColor);


        driveTrain.left(.1); //Changed


        if(ColorHelper.getBeaconColor(beaconColor) == "red"){
            driveTrain.right(.25);
            driveTrain.left(.1); //Changed
        }

        beaconColor.enableLed(false); //Go to second beacon
        floorColor.enableLed(true);

        driveTrain.back(.25);
        driveTrain.backwardsGyroToLine(floorColor);
        driveTrain.left(.02112);
        beaconColor.enableLed(true);
        driveTrain.left(.352);
        if(ColorHelper.getBeaconColor(beaconColor) == "red"){
            driveTrain.right(.095);
            driveTrain.left(.352);

        }
        driveTrain.right(.15);

        END COMMENT */

        beaconColor.enableLed(false); //Disable LEDs
        floorColor.enableLed(false);
    }
}
