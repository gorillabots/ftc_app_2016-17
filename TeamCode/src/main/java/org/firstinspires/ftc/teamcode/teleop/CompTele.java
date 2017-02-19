package org.firstinspires.ftc.teamcode.teleop;

/**
 * Created by Jarred on 10/30/2016.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.submodules.BallControl;
import org.firstinspires.ftc.teamcode.submodules.Drivetrain;
import org.firstinspires.ftc.teamcode.submodules.ForkLift;

@TeleOp(name = "Main", group = "Final")
public class CompTele extends OpMode
{
    Drivetrain drivetrain;
    BallControl ballControl;
    ForkLift forkLift;

    ColorSensor floorColor;
    ColorSensor beaconColor;

    long startTime;

    public void init()
    {
        drivetrain = new Drivetrain(hardwareMap, telemetry);

        floorColor = hardwareMap.colorSensor.get("floorColor");
        floorColor.enableLed(false);
        floorColor.enableLed(true);

        beaconColor = hardwareMap.colorSensor.get("beaconColor");
        beaconColor.setI2cAddress(I2cAddr.create8bit(58));
        beaconColor.enableLed(false);
        beaconColor.enableLed(true);

        startTime = System.currentTimeMillis();
    }



    public void loop()
    {
        float stickX = (gamepad1.left_stick_x); // Stick position (Absolute heading)
        float stickY = (gamepad1.left_stick_y); // Each is in range -1 to 1
        float stickRot = (gamepad1.right_stick_x / 2f); //Used to rotate the robot;

        if (gamepad1.b)
        {
            drivetrain.resetGyro();
        }

        drivetrain.oneStickLoop(stickX, stickY, stickRot);

        //TODO: Insert code for other buttons here
    }

    @Override
    public void stop()
    {
        super.stop();
        floorColor.enableLed(false);
    }
}

/*

if (gamepad2.right_bumper)
        {
            ballControl.newRunFlywheel(true);
        }
        else
        {
            ballControl.newRunFlywheel(false);
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


        if (gamepad2.left_stick_y > .1 && limit.isPressed())
        {
            raise.setPower(Math.abs(gamepad2.left_stick_y));
        }
        else
        {
            raise.setPower(-gamepad2.left_stick_y);
        }

 */
