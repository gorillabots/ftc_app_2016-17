package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;

import org.firstinspires.ftc.teamcode.subclasses.DriveTrain;

//Created by Jarred on 10/30/2016
//TODO: Use BallControl stuff, Commenting, Formatting

@TeleOp(name = "Comp Tele", group = "Final")
public class CompTele extends OpMode
{

    private DriveTrain driveTrain;
    private ModernRoboticsI2cGyro gyro;

    private DcMotor elevator;
    private DcMotor vac;
    private DcMotor fly;
    private DcMotor raise;

    public void init()
    {
        driveTrain = new DriveTrain(hardwareMap, telemetry);
        gyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        if (gyro.isCalibrating())
        {
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        vac = hardwareMap.dcMotor.get("vac");
        elevator = hardwareMap.dcMotor.get("elevator");
        fly = hardwareMap.dcMotor.get("fly");
        raise = hardwareMap.dcMotor.get("raise");

        gyro.resetZAxisIntegrator();
    }

    private float stickX;
    private float stickY;
    private float stickRot;
    private int rotation;

    public void loop()
    {
        stickX = (gamepad1.left_stick_x); // Stick position (Absolute heading)
        stickY = (gamepad1.left_stick_y); // Each is in range -1 to 1
        stickRot = (gamepad1.right_stick_x / 2f); //Used to rotate the robot;

        if(gamepad1.b)
        {
            gyro.resetZAxisIntegrator();
        }

        rotation = gyro.getHeading();

        driveTrain.oneStickLoop(stickX, stickY, stickRot, rotation, gamepad1.back);

        if(gamepad2.right_bumper)
        {
            fly.setPower(-1);

        }
        else
        {
            fly.setPower(0);
        }

        if(gamepad2.y)
        {

            elevator.setPower(-.5);
            vac.setPower(-1);

        }
        else
        {
            if(gamepad1.right_bumper)
            {
                vac.setPower(1);
            }
            else if(gamepad1.a)
            {
                vac.setPower(-1);
            } else {
                vac.setPower(0);
            }
        }

        if(gamepad2.left_bumper)
        {
            elevator.setPower(1);
        }
        else if (gamepad2.left_trigger > .5)
        {
            elevator.setPower(-1);
        }
        else
        {
            elevator.setPower(0);
        }


        if (gamepad2.left_stick_y > .1)
        {
            raise.setPower(((Math.abs(gamepad2.left_stick_y))));
        }
        else
        {
            if (gamepad2.left_stick_y < 0)
            {
                raise.setPower(-1 * (gamepad2.left_stick_y / 2));
            }
            else
            {
                raise.setPower(-1 * (gamepad2.left_stick_y / 2));
            }
        }
    }
}
