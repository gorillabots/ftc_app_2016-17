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
    ButtonPresserClass buttonPress;
    DcMotor raise;
    TouchSensor limit;
    Servo butt1;
    Servo butt2;
    public void init(){

        drivetrain = new Drivetrain(hardwareMap, telemetry);
            gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");
            gyro.calibrate();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(gyro.isCalibrating()){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        buttonPress = new ButtonPresserClass();
        vac = hardwareMap.dcMotor.get("vac");
        elevator = hardwareMap.crservo.get("elevator");
        flyOne = hardwareMap.dcMotor.get("flyOne");
        flyTwo= hardwareMap.dcMotor.get("flyTwo");
        raise = hardwareMap.dcMotor.get("raise");
        limit = hardwareMap.touchSensor.get("limit");
        butt1 = hardwareMap.servo.get("butt1");
        butt2 = hardwareMap.servo.get("butt2");
        butt1.setPosition(0);
        butt2.setPosition(0);


        gyro.resetZAxisIntegrator();

    }
    public void loop(){

        float stickX = (gamepad1.left_stick_x)*-1; // Stick position (Absolute heading)
        float stickY = (gamepad1.left_stick_y)*-1; // Each is in range -1 to 1
        float stickRot = (gamepad1.right_stick_x / 2f)*-1; //Used to rotate the robot;


        if(gamepad1.x){
            try
            {
                // make sure the gyro is calibrated.
                while (gyro.isCalibrating())
                {
                    Thread.sleep(50);
                }
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }

            gyro.resetZAxisIntegrator();
        }

        if(gamepad1.b == true)
        {
            gyro.resetZAxisIntegrator();
        }

       int rotation = gyro.getHeading();

        drivetrain.oneStickLoop(stickX, stickY, stickRot, rotation);


        if(gamepad2.right_bumper == true) {
            flyOne.setPower(-1);
            flyTwo.setPower(1);

        }
        else{
            flyOne.setPower(0);
            flyTwo.setPower(0);
        }

        if(gamepad1.right_bumper == true) {
            vac.setPower(1);
        }
        else if(gamepad1.a == true){
            vac.setPower(-1);
        }
        else{
            vac.setPower(0);
        }


        if(gamepad2.left_bumper == true) {
            elevator.setPower(1);
        }
        else if(gamepad2.left_trigger >.1){
            elevator.setPower(-1);
        }
        else{
            elevator.setPower(0);
        }

        if(gamepad2.left_stick_y > .1 && limit.isPressed()){
            raise.setPower(((Math.abs(gamepad2.left_stick_y))));
        }
        else{

            if(gamepad2.left_stick_y<0){
                raise.setPower(gamepad2.left_stick_y/2);
            }

            else{
                raise.setPower(gamepad2.left_stick_y/2);
            }

        }



        if(gamepad1.dpad_left == true){
            butt1.setPosition(1);
        }
        else{
            butt1.setPosition(0);
        }
        if(gamepad1.dpad_right == true){
            butt2.setPosition(1);
        }
        else{
            butt1.setPosition(0);
        }




    }

}

