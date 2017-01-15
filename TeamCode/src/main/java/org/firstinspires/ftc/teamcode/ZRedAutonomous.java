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
    ZAutonomousDriveTrain driveTrain;
    ColorSensor floorColor;
    ColorSensor beaconColor;

    Servo button_presser_1;
    Servo button_presser_2;
    public void runOpMode() throws InterruptedException
    {
        driveTrain = new ZAutonomousDriveTrain();

        driveTrain.init(this);
        button_presser_1 = hardwareMap.servo.get("la1");
        button_presser_2 = hardwareMap.servo.get("la2");
        floorColor = hardwareMap.colorSensor.get("floorColor");
        beaconColor = hardwareMap.colorSensor.get("beaconColor");
        beaconColor.setI2cAddress(I2cAddr.create8bit(58));
        beaconColor.enableLed(false);
        floorColor.enableLed(false);
        driveTrain.RetractTouchServo();
        waitForStart();
        driveTrain.ExtendTouchServo();
        driveTrain.rightToTouch(1);
    }
}

