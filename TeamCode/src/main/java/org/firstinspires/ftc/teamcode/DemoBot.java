package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Jarred on 10/30/2016.
 */
@TeleOp(name = "Demo", group = "final")
public class DemoBot extends OpMode {


    ElapsedTime timer;

    Drivetrain drivetrain;


    Servo claw;

    DcMotor rotateArm;
    ModernRoboticsI2cGyro gyro;

    int rotation = 0;
    int clawState = 0;




    float clawOpen =1 ;
    float clawClose = 0;
    void toggleClaw(){

        if(clawState== 1){
            claw.setPosition(clawOpen);
            clawState =2;
            timer.reset();
        }

       else if(clawState == 2){
                claw.setPosition(clawClose);
                clawState = 1;
                timer.reset();
        }
    }
    public void init()
    {


        timer = new ElapsedTime();
        drivetrain = new Drivetrain(hardwareMap, telemetry);

        gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");

        rotateArm = hardwareMap.dcMotor.get("rotateArm");
        claw = hardwareMap.servo.get("claw");

        gyro.calibrate();

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

        timer.startTime();
    }

    public void loop()
    {
        float stickX = gamepad1.left_stick_x; // Stick position (Absolute heading)
        float stickY = gamepad1.left_stick_y; // Each is in range -1 to 1

        float stickRot = gamepad1.right_stick_x / 2f; //Used to rotate the robot;

        if(gamepad1.a)
        {
            gyro.resetZAxisIntegrator();
        }

        rotation = gyro.getHeading();

        drivetrain.oneStickLoop(stickX, stickY, stickRot, rotation);

        if(gamepad1.left_bumper == true){
            rotateArm.setPower(.1);
        }
        else if(gamepad1.left_trigger > .5){
            rotateArm.setPower(-.1);
        }
        else{
            rotateArm.setPower(0);
        }


        if(gamepad1.right_bumper == true){
            claw.setPosition(1.0);

        }

        else if(gamepad1.right_trigger > .5){
            claw.setPosition(.2);

        }



    }

}
