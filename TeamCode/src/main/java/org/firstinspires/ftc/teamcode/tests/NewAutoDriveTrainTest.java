package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrain;
import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrainNewGyro;

//Created by Mikko on 4/12/2017
@Disabled
@Autonomous(name="NewAutoDriveTrainTest", group="test")
public class NewAutoDriveTrainTest extends LinearOpMode
{
    AutonomousDriveTrainNewGyro driveTrain;

    public void runOpMode()
    {
        driveTrain = new AutonomousDriveTrainNewGyro();
        driveTrain.init(this, 902);

        waitForStart();

        //driveTrain.forwards(.5, .5);
        //driveTrain.right(.5, .5);
        //driveTrain.backwards(.5, .5);
        //driveTrain.left(.5, .5);

        driveTrain.frontRight(.5, .5);
        driveTrain.frontLeft(.5, .5);
        driveTrain.backLeft(.5, .5);
        driveTrain.backRight(.5, .5);

        driveTrain.stop();
    }
}
