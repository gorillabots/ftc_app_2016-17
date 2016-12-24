package org.firstinspires.ftc.teamcode;

/**
 * Created by Jarred on 10/30/2016.
 */

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@TeleOp(name = "forkTest", group = "Concept")
public class forkTEst extends OpMode {

    DcMotor motor;
    TouchSensor limit;


    int encode;

    public void init()
    {

        motor = hardwareMap.dcMotor.get("motor");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        limit = hardwareMap.touchSensor.get("limit");

    }



    public void loop()
    {
        //-12278
        telemetry.update();
        telemetry.addData("rotations ", motor.getCurrentPosition());
        telemetry.addData("direction is", motor.getPower());
        //change the checksum ASAP
        if(gamepad1.left_stick_y >.1 &&  limit.isPressed()){
            motor.setPower((Math.abs(gamepad1.left_stick_y))*-1);

        }
        else{
            motor.setPower(gamepad1.left_stick_y);
        }


    }

}

