package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by mikko on 9/30/16.
 */

@TeleOp(name = "OmniwheelOp", group = "Concept")
public class TestOpMode extends OpMode
{
    Drivetrain drivetrain;

    public void init()
    {
        drivetrain = new Drivetrain(this);
    }

    public void loop()
    {
        drivetrain.oneStickLoop();
    }
}
