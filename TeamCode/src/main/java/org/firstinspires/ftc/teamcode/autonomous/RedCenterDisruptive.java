package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrain;
import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrainNewGyro;
import org.firstinspires.ftc.teamcode.submodules.BallControl;

//Created by Mikko on 1/29/17
//turn to +135
@Autonomous(name="Red Center Disruptive", group="Final")
public class RedCenterDisruptive extends LinearOpMode
{
    AutonomousDriveTrainNewGyro driveTrain;
    BallControl shooter;

    ElapsedTime timer1 = new ElapsedTime();

    public void runOpMode()
    {
        driveTrain = new AutonomousDriveTrainNewGyro();
        driveTrain.init(this, 90);
        shooter = new BallControl(hardwareMap, telemetry);

        waitForStart();
        driveTrain.backwards(.05,.7);
        driveTrain.turn(45,2,1);
        driveTrain.right(1.3, .5);
        driveTrain.backRight(.5, .7);

        timer1.reset();
        timer1.startTime();

        while(timer1.milliseconds() < 5000)
        {
            shooter.newRunFlywheel(true);
            shooter.newRunElevator(true);
        }

        shooter.newRunFlywheel(false);
        shooter.newStopElevator();


    }
}
