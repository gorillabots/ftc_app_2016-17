package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subclasses.BallControl;
import org.firstinspires.ftc.teamcode.subclasses.AutonomousDriveTrain;

//Created by mikko on 1/29/17
//TODO: Commenting

@Autonomous(name="Red Center Disruptive", group="Final")
public class RedCenterDisruptive extends LinearOpMode
{
    private AutonomousDriveTrain driveTrain;
    private BallControl shooter;

    private ElapsedTime timer1 = new ElapsedTime();

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

        while(timer1.milliseconds() < 5000)
        {
            sleep(200);
        }

        shooter.newRunFlywheel(false);
        shooter.newStopElevator();

        driveTrain.right(1.2, .5);
        driveTrain.backRight(1, .5);
    }
}
