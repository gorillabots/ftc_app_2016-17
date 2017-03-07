package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//Created by Mikko on 2/18/2017

@Autonomous(name=".Beta", group="Beta")
public class AutoBetaHeader extends LinearOpMode
{
    @Override
    public void runOpMode()
    {
        telemetry.addData("Note", "This is a dummy OpMode, why are you running it?");
        telemetry.update();

        waitForStart();
    }
}
