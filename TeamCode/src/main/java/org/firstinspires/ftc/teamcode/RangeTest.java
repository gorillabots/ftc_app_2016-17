package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
/**
 * Created by emper on 1/13/2017.
 */

@Autonomous(name="RangeTest", group="test")
public class RangeTest extends OpMode{
    Drivetrain drive;
    UltrasonicSensor range;
    ModernRoboticsI2cGyro gyro;
    public void init() {
        drive = new Drivetrain(hardwareMap, telemetry);
        gyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
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
    }
    public void loop() {

            float stickX = (gamepad1.left_stick_x); // Stick position (Absolute heading)
            float stickY = (gamepad1.left_stick_y); // Each is in range -1 to 1
            float stickRot = (gamepad1.right_stick_x / 2f); //Used to rotate the robot;
            if(gamepad1.b == true) {
                gyro.resetZAxisIntegrator();
            }

            int rotation = gyro.getHeading();

            drive.oneStickLoop(stickX, stickY, stickRot, rotation, gamepad1.back);
    }

    }