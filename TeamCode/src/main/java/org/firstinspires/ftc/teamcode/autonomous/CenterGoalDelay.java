package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrain;
import org.firstinspires.ftc.teamcode.submodules.BallControl;

//Created by Jarred on 12/10/2016

@Autonomous(name="Center Goal Auto Delay", group="Final")
public class CenterGoalDelay extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;
    BallControl shooter;

    ElapsedTime timer1 = new ElapsedTime();

    public void runOpMode()
    {
        driveTrain = new AutonomousDriveTrain();
        driveTrain.init(this);
        shooter = new BallControl(hardwareMap, telemetry);

        waitForStart();

        float mark2 = (float) .9;
        sleep(10000);
        driveTrain.right(1.1987, .5);
        timer1.reset();
        timer1.startTime();

        while(timer1.milliseconds() < 5000)
        {
            shooter.runFlywheel(true);
            shooter.runElevator(false, mark2 );
        }

        shooter.runFlywheel(false);
        shooter.runElevator(true, mark2);

        driveTrain.right(.6, .5);
    }
}
