package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrain;


/**
 * Created by Mikko on 3/12/17
 */

@Autonomous(name = "Range Test Gyro", group = "Test")
public class RangeTestGyro extends LinearOpMode
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

        driveTrain.goToDistanceGyro(range, 100, 5, .3, 5, .2);
    }
}
