package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Josh on 1/13/2017.
 */

@Autonomous(name="RangeTest", group="test")
public class RangeTest extends OpMode
{
    Drivetrain drive;
    UltrasonicSensor range;
    ModernRoboticsI2cGyro gyro;
    public void init()
    {
        drive = new Drivetrain(hardwareMap, telemetry);
        gyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        if (gyro.isCalibrating())
        {
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        range = hardwareMap.ultrasonicSensor.get("range");
    }

    @Override
    public void loop()
    {

            float stickX = (gamepad1.left_stick_x); // Stick position (Absolute heading)
            float stickY = (gamepad1.left_stick_y); // Each is in range -1 to 1
            float stickRot = (gamepad1.right_stick_x / 2f); //Used to rotate the robot;

            if(gamepad1.b == true)
            {
                gyro.resetZAxisIntegrator();
            }

            int rotation = gyro.getHeading();

            telemetry.addData("Range", range.getUltrasonicLevel());
            drive.oneStickLoop(stickX, stickY, stickRot, rotation, gamepad1.back);
    }
}