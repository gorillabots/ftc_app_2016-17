package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by root on 10/21/16.
 */

@TeleOp(name = "RotationTestOp", group = "Concept")
public class RotationTestOp extends OpMode
{
    DcMotor frontRight;

    public void init()
    {
        frontRight = hardwareMap.dcMotor.get("frontRight");
    }

    public void loop()
    {
        frontRight.setPower(.05);
        telemetry.addData("Encoder", frontRight.getCurrentPosition());
    }
}
