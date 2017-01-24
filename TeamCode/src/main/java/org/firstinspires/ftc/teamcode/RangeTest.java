package org.firstinspires.ftc.teamcode;

/**
 * Created by Jarred on 10/30/2016.
 */

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "range test", group = "Concept")
public class RangeTest extends OpMode {

    Drivetrain drivetrain;
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

    ModernRoboticsI2cRangeSensor range;
    public void init() {

        drivetrain = new Drivetrain(hardwareMap, telemetry);
        gyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        range = (ModernRoboticsI2cRangeSensor) hardwareMap.ultrasonicSensor.get("range");
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



    public void loop() {
        servoSwing.setPosition(.56);
        float stickX = (gamepad1.left_stick_x); // Stick position (Absolute heading)
        float stickY = (gamepad1.left_stick_y); // Each is in range -1 to 1
        float stickRot = (gamepad1.right_stick_x / 2f); //Used to rotate the robot;




        if (gamepad1.b == true) {
            gyro.resetZAxisIntegrator();
        }

        int rotation = gyro.getHeading();

        drivetrain.oneStickLoop(stickX, stickY, stickRot, rotation, gamepad1.back);
        telemetry.addData(" optical level is ", range.cmOptical());
        telemetry.addData(" ultrasonic level is ", range.cmUltrasonic());
    }

    @Override
    public void stop()
    {
        super.stop();
        floorColor.enableLed(false);
    }


}

