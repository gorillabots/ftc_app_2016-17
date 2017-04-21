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
        // drivetrain.offset = 90
        shooter = new BallControl(hardwareMap, telemetry);

        waitForStart();
        driveTrain.left(.1,.3);
        driveTrain.turn(-135,2,.7);
        // drivetrain.offset = 135
       // driveTrain.updateOffset(135);
        driveTrain.right(.81, .8);

        timer1.reset();
        timer1.startTime();

        while(timer1.milliseconds() < 5000)
        {
            shooter.newRunFlywheel(true);
            shooter.newRunElevator(true);
        }

        shooter.newRunFlywheel(false);
        shooter.newStopElevator();

        driveTrain.turn(-45,2,.7);
        // drivetrain.offset = 90
        driveTrain.right(.9,.8);
        driveTrain.backwards(.95,.8);
        driveTrain.left(.9,.8);
        driveTrain.forwards(.1, 1);

        driveTrain.stop();
    }

    public void Stop(){driveTrain.stop();}
}
