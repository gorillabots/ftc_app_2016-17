package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrain;


/**
 * Created by root on 1/22/17.
 */

@Deprecated
@Disabled
@Autonomous(name = "RangeTestMikko", group = "Concept")
public class RangeTestMikko extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;
    ModernRoboticsI2cRangeSensor range;

    @Override
    public void runOpMode()
    {
        //Initializations
        driveTrain = new AutonomousDriveTrain();
        driveTrain.init(this);

        range = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "range");

        waitForStart();

        //Run stuff

        driveTrain.goToDistance(range, 50, 5, .3);
        sleep(1000);
        driveTrain.goToDistance(range, 10, 3, .3);
        sleep(1000);
        driveTrain.goToDistance(range, 30, 2, .3);
        sleep(1000);
        driveTrain.goToDistance(range, 100, 4, 3);
    }
}
