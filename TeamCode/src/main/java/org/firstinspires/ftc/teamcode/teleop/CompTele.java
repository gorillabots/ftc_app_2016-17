package org.firstinspires.ftc.teamcode.teleop;

/**
 * Created by Jarred on 10/30/2016.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.submodules.Drivetrain;

@TeleOp(name = "Comp Tele", group = "Concept")
public class CompTele extends OpMode {

    Drivetrain drivetrain;
    //ModernRoboticsI2cGyro gyro;

    DcMotor elevator;
    DcMotor vac;
    DcMotor fly;
    DcMotor raise;
    //TouchSensor limit;

    ColorSensor floorColor;
    ColorSensor beaconColor;

    public void init() {

        drivetrain = new Drivetrain(hardwareMap, telemetry);
        //gyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        //gyro.calibrate();
        /*try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (gyro.isCalibrating()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        vac = hardwareMap.dcMotor.get("vac");
        elevator = hardwareMap.dcMotor.get("elevator");
        fly = hardwareMap.dcMotor.get("fly");
        raise = hardwareMap.dcMotor.get("raise");
        //limit = hardwareMap.touchSensor.get("limit");

        //gyro.resetZAxisIntegrator();

        floorColor = hardwareMap.colorSensor.get("floorColor");
        floorColor.enableLed(false);
        floorColor.enableLed(true);

        beaconColor = hardwareMap.colorSensor.get("beaconColor");
        beaconColor.setI2cAddress(I2cAddr.create8bit(58));
        beaconColor.enableLed(false);
        beaconColor.enableLed(true);
    }



    public void loop() {
        float stickX = (gamepad1.left_stick_x); // Stick position (Absolute heading)
        float stickY = (gamepad1.left_stick_y); // Each is in range -1 to 1
        float stickRot = (gamepad1.right_stick_x / 2f); //Used to rotate the robot;

/*
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
3
            gyro.resetZAxisIntegrator();
        }


*/



        /*if (gamepad1.b == true) {
            gyro.resetZAxisIntegrator();
        }

        int rotation = gyro.getHeading();
        */

        if(gamepad1.b)
        {
            drivetrain.resetGyro();
        }

        drivetrain.oneStickLoop(stickX, stickY, stickRot);


        if (gamepad2.right_bumper == true) {
            fly.setPower(-1);

            fly.setPower(0);
        }

        if (gamepad2.y) {

            elevator.setPower(-.5);
            vac.setPower(-1);

        } else {
            if (gamepad1.right_bumper == true) {
                vac.setPower(1);
            } else if (gamepad1.a == true) {
                vac.setPower(-1);
            } else {
                vac.setPower(0);
            }

        }


        if (gamepad2.left_bumper == true) {
            elevator.setPower(1);
        } else if (gamepad2.left_trigger > .5) {
            elevator.setPower(-1);
        } else {
            elevator.setPower(0);
        }


        /*if (gamepad2.left_stick_y > .1 && limit.isPressed())
        {
            raise.setPower(Math.abs(gamepad2.left_stick_y));
        }
        else
        {
            raise.setPower(-gamepad2.left_stick_y);
        }*/

        raise.setPower(-gamepad2.left_stick_y);
    }

    @Override
    public void stop()
    {
        super.stop();
        floorColor.enableLed(false);
    }


}

