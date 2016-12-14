package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Jarred on 10/18/2016.
 */
@TeleOp(name = "shooter", group = "test")
public class BallShooter extends OpMode {

    DcMotor vac;
    DcMotor flyOne;
    DcMotor flyTwo;
    CRServo elevator;

    int go;
    int goTwo;
    int goThree;

    public void init()
    {
        vac = hardwareMap.dcMotor.get("vac");
        flyOne = hardwareMap.dcMotor.get("flyOne");
        flyTwo = hardwareMap.dcMotor.get("flyTwo");
        elevator = hardwareMap.crservo.get("elevator");



    }

    public void loop()
    {

        if(gamepad1.left_bumper == true) {
            flyOne.setPower(1);
            flyTwo.setPower(-1);

        }
        else{
            flyOne.setPower(0);
            flyTwo.setPower(0);
        }

        if(gamepad1.right_bumper == true) {
            vac.setPower(1);
        }
        else{
            vac.setPower(0);
        }


        if(gamepad1.a == true) {
            elevator.setPower(1);
        }
        else{
            elevator.setPower(0);
        }





    }
}
