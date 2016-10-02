package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by mikko on 9/30/16.
 */

public class TestOpMode extends LinearOpMode
{
    public void runOpMode()
    {
        _init();
    }

    public void _init()
    {
        Drivetrain drivetrain = new Drivetrain(this);
    }
}
