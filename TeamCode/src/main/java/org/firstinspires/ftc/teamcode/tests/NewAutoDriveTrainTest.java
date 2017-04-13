package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrain;
import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrainNewGyro;

//Created by Mikko on 4/12/2017

@Autonomous(name="NewAutoDriveTrainTest", group="test")
public class NewAutoDriveTrainTest extends LinearOpMode
{
    AutonomousDriveTrainNewGyro driveTrain;

    public void runOpMode()
    {
        driveTrain = new AutonomousDriveTrainNewGyro();
        driveTrain.init(this);

        waitForStart();

        driveTrain.forwards(1.0, .5);
        driveTrain.right(1.0, .5);
        driveTrain.backwards(1.0, .5);
        driveTrain.left(1.0, .5);

        driveTrain.frontRight(1.0, .5);
        driveTrain.frontLeft(1.0, .5);
        driveTrain.backLeft(1.0, .5);
        driveTrain.backRight(1.0, .5);

        driveTrain.stop();
    }
}
