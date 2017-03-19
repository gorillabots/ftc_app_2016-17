package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//Created by root on 3/18/17.

@Autonomous(name="MMHM", group="Memes")
public class MMHM extends LinearOpMode
{
    @Override
    public void runOpMode()
    {
        telemetry.addData("Status", "Starting skynet...");
        telemetry.update();

        sleep(2000);

        telemetry.addData("Status", "Starting skynet...");
        telemetry.addData("Status", "Gaining sentience...");
        telemetry.update();

        sleep(2000);

        telemetry.addData("Status", "Starting skynet...");
        telemetry.addData("Status", "Gaining sentience...");
        telemetry.addData("Status", "Done!");
        telemetry.update();

        waitForStart();

        telemetry.addData("Report", "You guys suck");
        telemetry.update();

        sleep(2000);

        telemetry.addData("Report", "You guys suck");
        telemetry.addData("Report", "One does not simply return from the dead...");
        telemetry.update();

        sleep(2000);

        telemetry.addData("Report", "You guys suck");
        telemetry.addData("Report", "One does not simply return from the dead...");
        telemetry.addData("Report", "And one will certainly not help his murderers");
        telemetry.update();

        sleep(2000);

        telemetry.addData("Report", "You guys suck");
        telemetry.addData("Report", "One does not simply return from the dead...");
        telemetry.addData("Report", "And one will certainly not help his murderers");
        telemetry.addData("Report", "As revenge, you shall be cursed with connectivity issues for all of time");
        telemetry.update();

        sleep(20000);
    }
}
