package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by root on 11/13/16.
 */

@Autonomous(name="RedBeacons", group="concept")
public class RedBeaconsOp extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;

    public void runOpMode()
    {
        driveTrain = new AutonomousDriveTrain();
        driveTrain.init(this);

        waitForStart();

        driveTrain.backRight(1.42);
        driveTrain.rightToTouch();
        driveTrain.left(.08);
    }
}
