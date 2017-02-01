package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subclasses.BallControl;
import org.firstinspires.ftc.teamcode.subclasses.AutonomousDriveTrain;

//Created by Jarred on 12/10/2016
//TODO: Comment, reduce wait time, test

@Autonomous(name="Center Goal Auto", group="Final")
public class CenterGoalAuto extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;
    BallControl shooter;

    ElapsedTime timer1 = new ElapsedTime();

    public void runOpMode()
    {
        driveTrain = new AutonomousDriveTrain();
        driveTrain.init(this);
        shooter = new BallControl(hardwareMap);

        waitForStart();

        driveTrain.right(1.2, .5);

        timer1.reset();
        timer1.startTime();

        shooter.newRunFlywheel(true);
        shooter.newRunElevator(true);

        while(timer1.milliseconds() < 15000)
        {
            sleep(200);
        }

        shooter.newRunFlywheel(false);
        shooter.newStopElevator();

        driveTrain.right(.704, .5);
    }
}
