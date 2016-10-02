package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
/**
 * Created by Owner on 9/27/2016.
 */
import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name = "Concept: Compass Calibration", group = "Concept")

//Test
public class Drivetrain {

    LinearOpMode linearOpMode;

    DcMotor frontRight;
    DcMotor backRight;
    DcMotor frontLeft;
    DcMotor backLeft;


    public Drivetrain(LinearOpMode linearOpMode) {
        this.linearOpMode = linearOpMode;


    }

    public void runOpMode() throws InterruptedException {


        oneStickLoop();

    }

    private void oneStickLoop()
    {
        float stickX = (int) linearOpMode.gamepad1.left_stick_y; // Stick position (Absolute heading)
        float stickY = (int) linearOpMode.gamepad1.left_stick_y; // Each is in range -100 to 100

        int facingDeg = 45; //Robot's rotation (possibly multiply by -1)
        double facingRad = Math.toRadians(facingDeg); // Convert to radians

        double cs = Math.cos(facingRad);
        double sn = Math.sin(facingRad);

        double headX = stickX * cs - stickY * sn; //Rotated vertor (Relative heading)
        double headY = stickX * sn + stickY * cs; //Each is in range -100 * root 2 to 100 * root 2

        //Send signals to motors.
    }

    private void twoStickLoop()
    {


        float stickX = (int) linearOpMode.gamepad1.left_stick_x; // Stick position (Absolute heading)
        float stickY = (int) linearOpMode.gamepad1.left_stick_y; // Each is in range -100 to 100




        float stickRotation = (int) linearOpMode.gamepad1.right_stick_x; // Stick position (Absolute heading)




        if(){

            backLeft.setPower();
            frontLeft.setPower();
            frontRight.setPower();
            backRight.setPower();

        }

        else{

        }










    }
}
