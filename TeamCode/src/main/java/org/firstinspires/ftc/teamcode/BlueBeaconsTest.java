package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Mikko on 12/13/16.
 */

@Autonomous(name="Blue Beacons", group="final")
public class BlueBeaconsTest extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;
    ColorSensor floorColor;
    ColorSensor beaconColor;

    ButtonPresserClass beacon;
    Servo button_presser_1;
    Servo button_presser_2;

    public void runOpMode()
    {
        driveTrain = new AutonomousDriveTrain();
        driveTrain.init(this);

        beacon = new ButtonPresserClass();
        button_presser_1 = hardwareMap.servo.get("butt1");
        button_presser_2 = hardwareMap.servo.get("butt2");

        floorColor = hardwareMap.colorSensor.get("floorColor");
        beaconColor = hardwareMap.colorSensor.get("beaconColor");
        beaconColor.setI2cAddress(I2cAddr.create8bit(58));
        beaconColor.enableLed(false);
        floorColor.enableLed(false);

        waitForStart();

        floorColor.enableLed(true);

        driveTrain.frontRight(2.5);
        driveTrain.rightGyroToTouch();
        driveTrain.left(.05);
        driveTrain.backwardsGyroToLine(floorColor);
        driveTrain.right(.03);

        floorColor.enableLed(false);

        try
        {
            telemetry.addData("color",ColorHelper.getBeaconColor(beaconColor));
            telemetry.update();

            beacon.Respond_If_In_Blue_Alliance(beaconColor, button_presser_1, button_presser_2);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();

            beaconColor.enableLed(false);
            floorColor.enableLed(false);
        }

        /*

        floorColor.enableLed(true);
        //START NEW SEGMENT
        driveTrain.left(.1);
        driveTrain.back(.4);
        driveTrain.backwardsGyroToLine(floorColor);
        driveTrain.right(.03);

        floorColor.enableLed(false);

        try
        {
            beacon.Respond_If_In_Red_Alliance(beaconColor, button_presser_1, button_presser_2);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();

            beaconColor.enableLed(false);
            floorColor.enableLed(false);
        }*/

        beaconColor.enableLed(false);
        floorColor.enableLed(false);
    }
}
