package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrain;
import org.firstinspires.ftc.teamcode.submodules.BallControl;

//Created by Mikko on 1/29/17.

@Autonomous(name="Blue Center Disruptive", group="Final")
public class BlueCenterDisruptive extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;
    BallControl shooter;

    ElapsedTime timer1;

    public void runOpMode()
    {
        //Initializing
        driveTrain = new AutonomousDriveTrain();
        driveTrain.init(this);
        shooter = new BallControl(hardwareMap, telemetry);

        waitForStart();

        //Running
        driveTrain.right(1.2, .5);

        timer1 = new ElapsedTime();
        timer1.reset();
        timer1.startTime();

        //Shooting
        while(timer1.milliseconds() < 5000)
        {
            shooter.newRunFlywheel(true);
            shooter.newRunElevator(false);
        }

        shooter.newRunFlywheel(false);
        shooter.newRunElevator(true);

        //Disrupting
        driveTrain.right(.704, .5);
        driveTrain.frontRight(1, .5);
        driveTrain.forwards(.5,.5);
    }
}
