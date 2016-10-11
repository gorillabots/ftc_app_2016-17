package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by mikko on 10/9/16.
 */

@TeleOp(name = "MRGyroTest", group = "Concept")
public class MRGyroTest extends OpMode
{
    ModernRoboticsI2cGyro gyro;

    public void init()
    {
        gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");

        gyro.calibrate();
    }

    public void loop()
    {
        if(gamepad1.right_bumper)
        {
            gyro.resetZAxisIntegrator();
        }

        telemetry.addData("Heading", gyro.getHeading());
    }
}
