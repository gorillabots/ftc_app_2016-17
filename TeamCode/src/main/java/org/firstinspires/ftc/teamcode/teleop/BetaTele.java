package org.firstinspires.ftc.teamcode.teleop;


//Created by Mikko on 02/28/2017

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

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
