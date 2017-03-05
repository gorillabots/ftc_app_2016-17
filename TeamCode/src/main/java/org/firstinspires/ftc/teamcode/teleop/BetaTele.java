package org.firstinspires.ftc.teamcode.teleop;


//Created by Mikko on 02/28/2017

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

import org.firstinspires.ftc.teamcode.DoubleScale;
import org.firstinspires.ftc.teamcode.submodules.BallControl;
import org.firstinspires.ftc.teamcode.submodules.Drivetrain;
import org.firstinspires.ftc.teamcode.submodules.ForkLift;

@TeleOp(name = "Beta TeleOp", group = "Final")
public class BetaTele extends OpMode
{
    Drivetrain drivetrain;
    BallControl ballControl;
    ForkLift forkLift;

    ColorSensor floorColor;
    ColorSensor beaconColor;

    FlyState flyState;
    DoubleScale flyRamp;

    public void init()
    {
        drivetrain = new Drivetrain(hardwareMap, telemetry);
        ballControl = new BallControl(hardwareMap, telemetry);
        forkLift = new ForkLift(hardwareMap);

        floorColor = hardwareMap.colorSensor.get("floorColor");
        floorColor.enableLed(false);
        floorColor.enableLed(true);

        beaconColor = hardwareMap.colorSensor.get("beaconColor");
        beaconColor.setI2cAddress(I2cAddr.create8bit(58));
        beaconColor.enableLed(false);
        beaconColor.enableLed(true);

        flyState = FlyState.OFF;
        //flyRamp = new DoubleScale(1, 1, 1, 1);
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

        //Flywheel ramping stuff
        {
            boolean trigger = gamepad2.right_bumper;
            double time = System.currentTimeMillis() / 1000d;

            switch(flyState)
            {
                case OFF:
                    if(trigger)
                    {
                        flyRamp = new DoubleScale(time, time + 5, .3, 1);
                        flyState = FlyState.R_UP;
                    }
                    break;
                case R_UP:
                    if(trigger)
                    {
                        if(time > flyRamp.inmax)
                        {
                            ballControl.fly.setPower(1);
                            flyState = FlyState.ON;
                        }
                        else
                        {
                            ballControl.fly.setPower(-Math.sqrt(flyRamp.scale(time)));
                        }
                    }
                    else
                    {
                        ballControl.fly.setPower(0);
                        flyState = FlyState.OFF;
                    }
                    break;
                case ON:
                    if(!trigger)
                    {
                        flyRamp = new DoubleScale(time, time + 5, 1, .3);
                        flyState = FlyState.R_DOWN;
                    }
                    break;
                case R_DOWN:
                    if(trigger)
                    {
                        ballControl.fly.setPower(1);
                        flyState = FlyState.ON;
                    }
                    else
                    {
                        if(time > flyRamp.inmax)
                        {
                            ballControl.fly.setPower(0);
                            flyState = FlyState.OFF;
                        }
                        else
                        {
                            ballControl.fly.setPower(-Math.sqrt(flyRamp.scale(time)));
                        }
                    }
                    break;
                default:
                    //Why would this ever happen?
                    break;
            }
        }

        if(gamepad1.right_bumper)
        {
            ballControl.newRunCollector(true);
        }
        else if(gamepad1.right_trigger >= .6)
        {
            ballControl.newRunCollector(false);
        }
        else
        {
            ballControl.newStopCollector();
        }

        if(gamepad2.left_bumper)
        {
            ballControl.newRunElevator(true);
        }
        else if(gamepad2.left_trigger >= .6)
        {
            ballControl.newRunElevator(false);
        }
        else
        {
            ballControl.newStopElevator();
        }

        float stick2Y = gamepad2.left_stick_y;

        if(Math.abs(stick2Y) >= .2)
        {
            forkLift.lift(stick2Y);
        }
    }

    @Override
    public void stop()
    {
        super.stop();
        floorColor.enableLed(false);
    }

    //Graph: https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Logistic-curve.svg/320px-Logistic-curve.svg.png
    public static double sigmoid(double x)
    {
        return (1 / (1 + Math.pow(Math.E, -x)));
    }
}

enum FlyState
{
    OFF,
    R_UP,
    ON,
    R_DOWN
}
