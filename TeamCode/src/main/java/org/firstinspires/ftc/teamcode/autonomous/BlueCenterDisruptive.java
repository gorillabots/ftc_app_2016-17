package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrain;
import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrainNewGyro;
import org.firstinspires.ftc.teamcode.submodules.BallControl;

//Created by Mikko on 1/29/17
//turn to +135'
@Autonomous(name="Blue Center Disruptive", group="Final")
public class BlueCenterDisruptive extends LinearOpMode
{
    AutonomousDriveTrainNewGyro driveTrain;
    BallControl shooter;

    ElapsedTime timer1 = new ElapsedTime();

    public void runOpMode()
    {
        driveTrain = new AutonomousDriveTrainNewGyro();
        driveTrain.init(this, 0);
        shooter = new BallControl(hardwareMap, telemetry);

        waitForStart();
        driveTrain.left(1,.6);
        sleep(500);
        driveTrain.turn(-135,2,.7);
        telemetry.addData("done"," done");
        telemetry.update();
        /*
        driveTrain.turn(90,2,.7);
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
        driveTrain.right(.9,.8);
        driveTrain.backwards(.95,.8);
        driveTrain.left(.9,.8);
*/
    }

    public void Stop(){
        driveTrain.stop();
    }


}

