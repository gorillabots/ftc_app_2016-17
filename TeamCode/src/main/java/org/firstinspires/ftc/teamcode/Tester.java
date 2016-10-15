package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
/**
 * Created by mikko on 9/30/16.
 */

@TeleOp(name = "tester", group = "tester")
public class Tester extends OpMode
{

    DcMotor motor1;
    DcMotor motor2;

    public void init(){

        motor1 = hardwareMap.dcMotor.get("motor1");
        motor2 = hardwareMap.dcMotor.get("motor2");

    }
    public void loop(){

        motor1.setPower(gamepad1.left_stick_x);
        motor2.setPower(gamepad1.right_stick_x);

    }
}
