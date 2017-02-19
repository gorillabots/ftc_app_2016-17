package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.BallControl;

/**
 * Created by Jarred on 12/10/2016.
 */
@Autonomous(name="Center Goal Auto", group="final")
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

        driveTrain.right(1.2, .5);

        timer1.reset();
        timer1.startTime();

        while(timer1.milliseconds() < 15000)
        {
            shooter.newRunFlywheel(true);
            shooter.newRunElevator(false);
        }

        shooter.newRunFlywheel(false);
        shooter.newRunElevator(true);

        driveTrain.right(.704, .5);
    }
}
