package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


/**
 * Created by mikko on 9/30/16.
 */

@TeleOp(name = "OmniwheelOp", group = "Concept")
public class TankTest extends OpMode
{
    DcMotor frontRight;
    DcMotor backRight;
    DcMotor frontLeft;
    DcMotor backLeft;

    public void init() {
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
    }

    public void loop()
    {




        if(gamepad1.right_stick_y >= 20 || gamepad1.right_stick_x >= 20 || gamepad1.right_stick_x <= -20 || gamepad1.right_stick_y <= -20){


            frontLeft.setPower();
            frontRight.setPower();
            backLeft.setPower();
            backRight.setPower();



            /*
            if(gamepad1.left_stick_y > 0 && gamepad1.left_stick_x > 0){

                frontLeft.setPower();//ip
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower();
            }

            if(gamepad1.left_stick_y < 0 && gamepad1.left_stick_x > 0){

                frontLeft.setPower(0);
                frontRight.setPower();
                backLeft.setPower();
                backRight.setPower(0);
            }
            if(gamepad1.left_stick_y > 0 && gamepad1.left_stick_x < 0){

                frontLeft.setPower(0);
                frontRight.setPower();
                backLeft.setPower();
                backRight.setPower(0);
            }
            if(gamepad1.left_stick_y < 0 && gamepad1.left_stick_x < 0){

                frontLeft.setPower();
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower();
            }

*/

        }


    }
}
