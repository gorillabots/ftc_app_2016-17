package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subclasses.BallControl;
import org.firstinspires.ftc.teamcode.subclasses.DriveTrain;
import org.firstinspires.ftc.teamcode.subclasses.ForkLift;

//Created by Jarred on 10/18/2016.

@Disabled
@Deprecated
@TeleOp(name = "CompTeleDeprecated", group = "Depricated")
public class CompTeleDeprecated extends OpMode {
    DriveTrain driveTrain;

    ForkLift forkLift;
    DcMotor vac;

    BallControl ballControl;

    Servo butt1;
    Servo butt2;
    ModernRoboticsI2cGyro gyro;


    int rotation = 0;





    public void init()
    {
        driveTrain = new DriveTrain(hardwareMap, telemetry);
        forkLift = new ForkLift(hardwareMap);
        ballControl = new BallControl(hardwareMap );
        butt1 = hardwareMap.servo.get("butt1");
        butt2 = hardwareMap.servo.get("butt2");

       // driveTrain.floorColor.enableLed(true);
        gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");

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

    }

    public void loop()
    {
        float stickX = gamepad1.left_stick_x; // Stick position (Absolute heading)
        float stickY = gamepad1.left_stick_y; // Each is in range -1 to 1
        float stickRot = gamepad1.right_stick_x / 2f; //Used to rotate the robot;
        rotation = gyro.getHeading();
        driveTrain.oneStickLoop(stickX, stickY, stickRot, rotation,gamepad1.back);
        driveTrain.resetGyro(gamepad1.a);

        forkLift.manipulateLift(gamepad2.left_stick_y);

        ballControl.runCollector(gamepad1.right_bumper, gamepad1.left_bumper);
        ballControl.runElevator(gamepad2.left_bumper, gamepad2.left_trigger);
        ballControl.runFlywheel(gamepad2.a);

        //autopilot

    }
}
