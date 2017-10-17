package org.firstinspires.ftc.teamcode.submodules;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by mikko on 4/11/17.
 */

@Autonomous(name="New Gyro Turning", group="Test")
public class NewGyroTurningTest extends LinearOpMode
{
    AutonomousDriveTrainNewGyro driveTrain = new AutonomousDriveTrainNewGyro();

    public void runOpMode()
    {
        driveTrain.init(this, 0);

        waitForStart();

        driveTrain.turn(90, 1, 1);
        telemetry.addData("done", "with op");
        telemetry.update();
    }
}
