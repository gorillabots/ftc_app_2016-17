package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


/**
 * Created by mikko on 9/30/16.
 */

@TeleOp(name = "+", group = "Concept")
public class TankTest extends OpMode
{
    DcMotor flyOne;
    DcMotor flyTwo;

    CRServo vat;
    int servoGo;
    public void init() {
        flyOne = hardwareMap.dcMotor.get("flyOne");
        flyTwo = hardwareMap.dcMotor.get("flyTwo");
        vat = hardwareMap.crservo.get("vat");
        vat.setPower(0);
        direction = 1;
        int servoGo = 0;
        vat.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    int direction;
    public void loop()
    {

        if(gamepad1.a == true) {
            direction = direction*-1;
        }

        vat.setPower(-100);
            flyOne.setPower(-100 * direction);
            flyTwo.setPower(100*direction);





        }



    }

