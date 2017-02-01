package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subclasses.AutonomousDriveTrain;

/**
 * Created by mikko on 2/1/17.
 */

@Autonomous(name="TurnToGyroAnyTest", group="Tests")
public class TurnToGyroAnyTest extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;

    public void runOpMode()
    {
        driveTrain = new AutonomousDriveTrain();
        driveTrain.init(this);

        waitForStart();

        driveTrain.resetGyro();

        driveTrain.turnToGyroAny(45, .5, 5);
    }
}
