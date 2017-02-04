package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by mikko on 1/29/17.
 */

@Autonomous(name="Red Center Disruptive", group="final")
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

        driveTrain.right(1.2, .5);

        timer1.reset();
        timer1.startTime();
        /*
        while(timer1.milliseconds() < 5000)
        {
            shooter.newRunFlywheel(true);
            shooter.newRunElevator(false);
        }
*/
        shooter.newRunFlywheel(false);
        shooter.newRunElevator(true);

        driveTrain.right(1.2, .5);
        driveTrain.backRight(1, .5);
        driveTrain.forwards(.5,.5);
    }
}
