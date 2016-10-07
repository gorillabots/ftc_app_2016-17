package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
/**
 * Created by Owner on 9/27/2016.
 */
import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import org.firstinspires.ftc.robotcore.external.Telemetry;


/**
 * Related Links
 *		http://www.simbotics.org/resources/mobility/omnidirectional-drive
 *		http://www.vexforum.com/index.php/12370-holonomic-drives-2-0-a-video-tutorial-by-cody/0
 */
@Autonomous(name = "Concept: Compass Calibration", group = "Concept")
public class Drivetrain {

    OpMode opMode;

    DcMotor frontRight;
    DcMotor backRight;
    DcMotor frontLeft;
    DcMotor backLeft;


    public Drivetrain(OpMode opMode)
    {
        this.opMode = opMode;

        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        backRight = opMode.hardwareMap.dcMotor.get("backRight");
        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");
    }

    public void motorTestLoop()
    {
        float stickX = opMode.gamepad1.left_stick_x;
        float stickY = opMode.gamepad1.left_stick_y;

        frontRight.setPower(stickX);
        backLeft.setPower(-stickX);
        frontLeft.setPower(stickY);
        frontRight.setPower(-stickY);
    }

    public void oneStickLoop()
    {
        float stickX = (int) opMode.gamepad1.left_stick_x; // Stick position (Absolute heading)
        float stickY = (int) opMode.gamepad1.left_stick_y; // Each is in range -100 to 100

        int facingDeg = 45; //Robot's rotation (possibly multiply by -1 to invert)
        double facingRad = Math.toRadians(facingDeg); // Convert to radians

        double cs = Math.cos(facingRad);
        double sn = Math.sin(facingRad);

        double headX = stickX * cs - stickY * sn; //Rotated vector (Relative heading)
        double headY = stickX * sn + stickY * cs; //Each is in range -100 * root 2 to 100 * root 2

        headX /= Math.sqrt(2); //In range -100 to 100
        headY /= Math.sqrt(2);

        opMode.telemetry.addData("headX", headX);
        opMode.telemetry.addData("headY", headY);

        backLeft.setPower(-headX);
        frontRight.setPower(-headX);
        backRight.setPower(headY);
        frontLeft.setPower(headY);
    }

    /*private void twoStickLoop()
    {


        float stickX = (int) opMode.gamepad1.left_stick_x; // Stick position (Absolute heading)
        float stickY = (int) opMode.gamepad1.left_stick_y; // Each is in range -100 to 100




        float stickRotation = (int) opMode.gamepad1.right_stick_x; // Stick position (Absolute heading)




        if(){

            backLeft.setPower();
            frontLeft.setPower();
            frontRight.setPower();
            backRight.setPower();

        }

        else{

        }










    }*/
}
