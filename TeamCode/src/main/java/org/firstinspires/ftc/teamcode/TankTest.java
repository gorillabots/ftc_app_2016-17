package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


/**
 * Created by mikko on 9/30/16.
 */

@TeleOp(name = "OmniwheelOpTank", group = "Concept")
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

        float stickX = gamepad1.left_stick_x; // Stick position (Absolute heading)
        float stickY = gamepad1.left_stick_y; // Each is in range -1 to 1

        int facingDeg = 45; //Robot's rotation (possibly multiply by -1 to invert)
        double facingRad = Math.toRadians(facingDeg); // Convert to radians

        double cs = Math.cos(facingRad);
        double sn = Math.sin(facingRad);

        double headX = stickX * cs - stickY * sn; //Rotated vector (Relative heading)
        double headY = stickX * sn + stickY * cs; //Each is in range -1 * root 2 to root 2

        headX /= Math.sqrt(2); //In range -1 to 1
        headY /= Math.sqrt(2);

        if(gamepad1.right_stick_y >= .20 || gamepad1.right_stick_x >= .20 || gamepad1.right_stick_x <= -.20 || gamepad1.right_stick_y <= -.20){


            frontLeft.setPower(headX);
            frontRight.setPower(-headX);
            backLeft.setPower(headY);
            backRight.setPower(-headY);



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
        else{


            if(gamepad1.right_stick_x >.30){
                frontLeft.setPower(100);
                frontRight.setPower(100);
                backLeft.setPower(100);
                backRight.setPower(100);
            }
            if(gamepad1.right_stick_x < -.30){
                frontLeft.setPower(-100);
                frontRight.setPower(-100);
                backLeft.setPower(-100);
                backRight.setPower(-100);
            }

        }



    }
}
