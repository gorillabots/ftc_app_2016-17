package org.firstinspires.ftc.teamcode.tests;

/**
 * Created by Jarred on 10/30/2016.
 */

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.subclasses.DriveTrain;

@Disabled
@TeleOp(name = "range test", group = "Tests")
public class RangeTest extends OpMode {

    DriveTrain driveTrain;
    ModernRoboticsI2cGyro gyro;

    DcMotor elevator;
    DcMotor vac;
    DcMotor fly;
    DcMotor raise;
    TouchSensor limit;

    ColorSensor floorColor;
    ColorSensor beaconColor;
    Servo servoSwing;
    long startTime;
    long time;

    ModernRoboticsI2cRangeSensor rangeSensor;
    public void init() {

        driveTrain = new DriveTrain(hardwareMap, telemetry);
        gyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "range");
        gyro.calibrate();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (gyro.isCalibrating()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        gyro.resetZAxisIntegrator();


    }
//22 US for touch sensor wall


    public void loop() {

        float stickX = (gamepad1.left_stick_x); // Stick position (Absolute heading)
        float stickY = (gamepad1.left_stick_y); // Each is in range -1 to 1
        float stickRot = (gamepad1.right_stick_x / 2f); //Used to rotate the robot;




        if (gamepad1.b == true) {
            gyro.resetZAxisIntegrator();
        }

        int rotation = gyro.getHeading();

        driveTrain.oneStickLoop(stickX, stickY, stickRot, rotation, gamepad1.back);
        telemetry.addData("raw ultrasonic", rangeSensor.rawUltrasonic());
        telemetry.addData("raw optical", rangeSensor.rawOptical());
        telemetry.addData("cm optical", "%.2f cm", rangeSensor.cmOptical());
        telemetry.addData("cm", "%.2f cm", rangeSensor.getDistance(DistanceUnit.CM));
        telemetry.update();
    }

    @Override
    public void stop()
    {
        super.stop();
        floorColor.enableLed(false);
    }


}

