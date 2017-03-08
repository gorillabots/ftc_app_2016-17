package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Jarred on 3/7/2017.
 */

public class forkTest extends OpMode {
    DcMotor raise;
    public void init(){
        raise = hardwareMap.dcMotor.get("raise");
    }
    public void loop(){
        raise.setPower(gamepad1.left_stick_y);
        telemetry.addData("encoder at:  ", raise.getCurrentPosition());
    }
}
