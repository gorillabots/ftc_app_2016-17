package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Mikko on 12/11/16.
 */

@Autonomous(name="RedBeaconsSlam", group="beta")
public class RedAutoSlam extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;
    ColorSensor floorColor;
    ColorSensor beaconColor;
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
        beaconColor.setI2cAddress(I2cAddr.create8bit(58));
        beaconColor.enableLed(false);
        floorColor.enableLed(false);

        sensorSwing = hardwareMap.servo.get("servoSwing");
        sensorSwing.setPosition(.56);
        waitForStart();

        driveTrain.resetGyro();

        floorColor.enableLed(true);

        while(!driveTrain.wallTouch.isPressed())
        {
            telemetry.addData("Is white?", ColorHelper.isFloorWhite(floorColor));
            telemetry.addData("R:", floorColor.red());
            telemetry.addData("G:", floorColor.green());
            telemetry.addData("B:", floorColor.blue());
            telemetry.update();
            sleep(5);
        }

        floorColor.enableLed(false);

         //Go to first beacon
        sensorSwing.setPosition(.0);
        driveTrain.backRightGyro(1.76); //Go out
        driveTrain.rightGyroToTouch(); //Go to wall slowly
        sensorSwing.setPosition(52
        );
        driveTrain.left(.0352); //Go out
        driveTrain.turnToGyro();
        //driveTrain.GyroRotation(0, .2);
        floorColor.enableLed(true);
        driveTrain.forwardsGyroToLine(floorColor);
        driveTrain.turnToGyro();
        driveTrain.right(.200);

        floorColor.enableLed(false);

        //beaconColor.enableLed(false);


        //driveTrain.right(.49); //Changed


        /*if(ColorHelper.getBeaconColor(beaconColor) == "blue"){
            driveTrain.left(.25);
            driveTrain.right(.40); //Changed
        }

        driveTrain.left(.25);
        driveTrain.backwardsGyroToLine(floorColor);


        driveTrain.right(.1); //Changed


        if(ColorHelper.getBeaconColor(beaconColor) == "blue"){
            driveTrain.left(.25);
            driveTrain.right(.1); //Changed
        }*/

    /*
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
    */
    }
}

