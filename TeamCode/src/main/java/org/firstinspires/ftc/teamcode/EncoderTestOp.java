package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by mikko on 10/14/16.
 */

@TeleOp(name = "EncoderTestOp", group = "Concept")
public class EncoderTestOp extends OpMode
{
    AutonomousDriveTrain driveTrain;

    public void init()
    {
        driveTrain = new AutonomousDriveTrain(hardwareMap, telemetry);
    }

    public void loop()
    {
        telemetry.addData("Button Pressed?", gamepad1.dpad_up);

        if(gamepad1.dpad_up)
        {
            driveTrain.forwards(5240);
        }

        driveTrain.loop();
    }
}