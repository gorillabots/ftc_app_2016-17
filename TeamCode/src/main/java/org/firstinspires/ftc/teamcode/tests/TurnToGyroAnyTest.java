package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrain;

/**
 * Created by mikko on 2/1/17.
 */

@Deprecated
@Disabled
@Autonomous(name="TurnToGyroAnyTest", group="Concept")
public class TurnToGyroAnyTest extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;

    public void runOpMode()
    {
        driveTrain = new AutonomousDriveTrain();
        driveTrain.init(this);

        waitForStart();

        driveTrain.resetGyro();

        driveTrain.turnToGyroAny(270, .25, 5);
    }
}
