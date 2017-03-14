package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrain;
import org.firstinspires.ftc.teamcode.submodules.BallControl;

//Created by Jarred on 12/10/2016.

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
        shooter = new BallControl(hardwareMap, telemetry);

        waitForStart();

        driveTrain.right(1.3, .5);

        timer1.reset();
        timer1.startTime();

        while(timer1.milliseconds() < 15000)
        {
            shooter.newRunFlywheel(true);
            shooter.newRunElevator(true);
        }

        shooter.newRunFlywheel(false);
        shooter.newStopElevator();

        driveTrain.right(.704, .5);
    }
}
