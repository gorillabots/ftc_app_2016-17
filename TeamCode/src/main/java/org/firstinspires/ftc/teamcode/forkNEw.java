package org.firstinspires.ftc.teamcode;

/**
 * Created by Jarred on 10/30/2016.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "forkTest2", group = "Concept")
public class forkNEw extends OpMode {

    Drivetrain drivetrain;
    ModernRoboticsI2cGyro gyro;
    CRServo elevator;
    DcMotor vac;
    DcMotor flyOne;
    DcMotor flyTwo;

    DcMotor raise;
    TouchSensor limit;
    public void init(){

        drivetrain = new Drivetrain(hardwareMap, telemetry);
            gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");
            gyro.calibrate();

        vac = hardwareMap.dcMotor.get("vac");
        elevator = hardwareMap.crservo.get("elevator");
        flyOne = hardwareMap.dcMotor.get("flyOne");
        flyTwo= hardwareMap.dcMotor.get("flyTwo");
        raise = hardwareMap.dcMotor.get("raise");
        limit = hardwareMap.touchSensor.get("limit");


        try
        {
            // make sure the gyro is calibrated.
            //      while (gyro.isCalibrating())
            {
                Thread.sleep(50);
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

    }
    public void loop(){

        float stickX = gamepad1.left_stick_x; // Stick position (Absolute heading)
        float stickY = gamepad1.left_stick_y; // Each is in range -1 to 1

        float stickRot = gamepad1.right_stick_x / 2f; //Used to rotate the robot;


        if(gamepad1.a)
        {
            gyro.resetZAxisIntegrator();
        }

       int rotation = gyro.getHeading();

        drivetrain.oneStickLoop(stickX, stickY, stickRot, rotation);


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

        if(gamepad2.left_stick_y > .1 && limit.isPressed()){
            raise.setPower((Math.abs(gamepad2.left_stick_y))*-1);
        }
        else{

            if(gamepad2.left_stick_y<0){
                raise.setPower(gamepad2.left_stick_y/2);
            }
            else{
                raise.setPower(gamepad2.left_stick_y);
            }

        }

    }

}

