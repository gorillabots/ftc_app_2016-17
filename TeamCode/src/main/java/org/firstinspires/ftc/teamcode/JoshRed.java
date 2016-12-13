package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import static org.firstinspires.ftc.teamcode.Constants.*;
//Josh was here

@Autonomous(name="JoshRed", group="concept")
public class JoshRed extends LinearOpMode
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
        button_presser_1 = hardwareMap.servo.get("butt1");
        button_presser_2 = hardwareMap.servo.get("butt2");
        floorColor = hardwareMap.colorSensor.get("floorColor");
        beaconColor = hardwareMap.colorSensor.get("beaconColor");
        floorColor.setI2cAddress(I2cAddr.create8bit(58));
        beaconColor.enableLed(true);
        floorColor.enableLed(true);
        beacon.Start(button_presser_1, button_presser_2);
        waitForStart();

        floorColor.enableLed(true);

        driveTrain.backRight(WALL_TO_WALL_IN_AUTONOMOUS);
        //
        driveTrain.rightGyroToTouch();
        driveTrain.left(GO_BACKWARD_AFTER_TOUCH_WALL);
        sleep(100);
        driveTrain.backToLine(floorColor);

        floorColor.enableLed(false);
        beacon.Respond_If_In_Red_Alliance(beaconColor, button_presser_1, button_presser_2);
        driveTrain.back(BETWEEN_WHITE_LINES);
        driveTrain.rightToTouch();
        driveTrain.left(GO_BACKWARD_AFTER_TOUCH_WALL);
        sleep(100);
        floorColor.enableLed(true);
        driveTrain.backToLine(floorColor);
        floorColor.enableLed(false);
        beacon.Respond_If_In_Red_Alliance(beaconColor, button_presser_1, button_presser_2);
    }
}
