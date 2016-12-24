package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by root on 11/13/16.
 */
//Josh was here
@Disabled
@Autonomous(name="BlueBeacons", group="concept")
public class BlueBeaconsOp extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;
    ColorSensor floorColor;
    ColorSensor beaconColor;
    ButtonPresserClass beacon;
    Servo button_presser_1;
    Servo button_presser_2;
    public void runOpMode() throws InterruptedException
    {
        driveTrain = new AutonomousDriveTrain();
        beacon = new ButtonPresserClass();
        driveTrain.init(this);
        button_presser_1 = hardwareMap.servo.get("actuator1");
        button_presser_2 = hardwareMap.servo.get("actuator2");
        floorColor = hardwareMap.colorSensor.get("floorColor");
        beaconColor = hardwareMap.colorSensor.get("beaconColor");
        floorColor.setI2cAddress(I2cAddr.create8bit(58));

        floorColor.enableLed(false);

        waitForStart();

        floorColor.enableLed(true);

        driveTrain.frontRight(1.42);
        driveTrain.rightToTouch();
        driveTrain.left(.08);

        driveTrain.backToLine(floorColor);

        floorColor.enableLed(false);
        beacon.Respond_If_In_Blue_Alliance(beaconColor, button_presser_1, button_presser_2);
        /*while(opModeIsActive())
        {
            ColorHelper.printColorRGB(telemetry, floorColor);
            ColorHelper.printColorHSV(telemetry, floorColor);
            telemetry.update();
            sleep(5);
        }*/
    }
}
