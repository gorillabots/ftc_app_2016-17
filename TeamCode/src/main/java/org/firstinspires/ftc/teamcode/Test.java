package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by emper on 10/14/2016.
 */
@TeleOp(name="Test", group="Concept")
public class Test extends OpMode{
    DcMotor motor;
    public void init(){
        motor = hardwareMap.dcMotor.get("Motor");
    }
    public void loop(){
        motor.setPower(100);
        //Wheel turns clockwise
        //Motor turns counter-clockwise
    }
}
