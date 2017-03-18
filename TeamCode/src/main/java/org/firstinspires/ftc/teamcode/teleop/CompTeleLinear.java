package org.firstinspires.ftc.teamcode.teleop;

//Created by Mikko on 03/18/2017

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.submodules.Drivetrain;

@TeleOp(name = "Comp Tele Linear", group = "Concept")
public class CompTeleLinear extends LinearOpMode
{
    Drivetrain drivetrain;

    DcMotor elevator;
    DcMotor vac;
    DcMotor fly;
    DcMotor raise;

    public void runOpMode()
    {
        initRobot();

        waitForStart();

        while(opModeIsActive())
        {
            updateRobot();
        }

        stopRobot();
    }

    public void initRobot()
    {
        drivetrain = new Drivetrain(hardwareMap, telemetry);
        vac = hardwareMap.dcMotor.get("vac");
        elevator = hardwareMap.dcMotor.get("elevator");
        fly = hardwareMap.dcMotor.get("fly");
        raise = hardwareMap.dcMotor.get("raise");
    }

    public void updateRobot()
    {
        float stickX = (gamepad1.left_stick_x); // Stick position (Absolute heading)
        float stickY = (gamepad1.left_stick_y); // Each is in range -1 to 1
        float stickRot = (gamepad1.right_stick_x / 2f); //Used to rotate the robot;

        if(gamepad1.b)
        {
            drivetrain.resetGyro();
        }

        drivetrain.oneStickLoop(stickX, stickY, stickRot);

        if (gamepad2.right_bumper == true)
        {
            fly.setPower(-1);
        }
        else
        {
            fly.setPower(0);
        }

        if (gamepad2.y)
        {
            elevator.setPower(-.5);
            vac.setPower(-1);
        }
        else
            {
            if (gamepad1.right_bumper == true)
            {
                vac.setPower(1);
            }
            else if (gamepad1.a == true)
            {
                vac.setPower(-1);
            }
            else
            {
                vac.setPower(0);
            }
        }

        if (gamepad2.left_bumper == true)
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

        raise.setPower(-gamepad2.left_stick_y);
    }

    public void stopRobot(){}
}
