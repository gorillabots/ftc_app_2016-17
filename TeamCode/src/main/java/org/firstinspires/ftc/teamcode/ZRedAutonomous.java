package org.firstinspires.ftc.teamcode;

/**
 * Created by emper on 12/22/2016.
 */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.Constants.BETWEEN_WHITE_LINES;
import static org.firstinspires.ftc.teamcode.Constants.GO_BACKWARD_AFTER_TOUCH_WALL;
import static org.firstinspires.ftc.teamcode.Constants.WALL_TO_WALL_IN_AUTONOMOUS;

@Autonomous(name="RedAutonomous", group="concept")
public class ZRedAutonomous extends LinearOpMode
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
    }
}

