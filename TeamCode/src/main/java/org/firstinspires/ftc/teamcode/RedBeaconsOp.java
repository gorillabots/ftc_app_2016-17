package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by mikko on 11/13/16.
 */

@Autonomous(name="RedBeacons", group="concept")
public class RedBeaconsOp extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;

    ColorSensor floorColor;

    Servo sensorSwing;

    public void runOpMode()
    {
        driveTrain = new AutonomousDriveTrain(); //Intialize drive train
        driveTrain.init(this);

        floorColor = hardwareMap.colorSensor.get("floorColor"); //Initialize color sensor
        floorColor.setI2cAddress(I2cAddr.create8bit(58));

        sensorSwing = hardwareMap.servo.get("touchServo");
        sensorSwing.setPosition(.56);

        floorColor.enableLed(false); //Disable color sensor LED

        waitForStart();

        floorColor.enableLed(true); //Enable color sensor LED

        //Start actual autonomous

        driveTrain.backRightGyro(1.42, .8, 1, .2);
        driveTrain.rightGyroToTouch(.8, 1, .2);
        driveTrain.leftGyro(.1, .5, 1, .1);
        //sleep(100);

        floorColor.enableLed(false); //Disable color sensor LED
    }
}
