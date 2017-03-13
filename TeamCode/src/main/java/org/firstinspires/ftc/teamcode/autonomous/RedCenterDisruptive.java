package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrain;
import org.firstinspires.ftc.teamcode.submodules.BallControl;

//Created by Mikko on 1/29/17

@Autonomous(name="Red Center Disruptive", group="Final")
public class RedCenterDisruptive extends LinearOpMode
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

        while(timer1.milliseconds() < 5000)
        {
            shooter.newRunFlywheel(true);
            shooter.newRunElevator(true);
        }

        shooter.newRunFlywheel(false);
        shooter.newStopElevator();

        sleep(3000);


        driveTrain.backRight(.7, .5);
        driveTrain.left(1, .7);
        driveTrain.right(2, .7);
        driveTrain.forwards(.3,.5);
    }
}
