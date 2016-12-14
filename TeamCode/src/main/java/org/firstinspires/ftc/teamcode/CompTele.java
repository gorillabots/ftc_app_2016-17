package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by Jarred on 10/18/2016.
 */
@Disabled
@TeleOp(name = "CompTele", group = "beta")
public class CompTele extends OpMode {
    Drivetrain drivetrain;

    ButtonPresserClass buttonPresser;

    ForkLift forkLift;
    DcMotor vac;

    BallControl ballControl;

    Servo butt1;
    Servo butt2;
    ModernRoboticsI2cGyro gyro;


    int rotation = 0;





    public void init()
    {
        drivetrain = new Drivetrain(hardwareMap, telemetry);
        buttonPresser = new ButtonPresserClass();
        forkLift = new ForkLift(hardwareMap, telemetry);
        ballControl = new BallControl(hardwareMap, telemetry );
        butt1 = hardwareMap.servo.get("butt1");
        butt2 = hardwareMap.servo.get("butt2");

       // drivetrain.floorColor.enableLed(true);
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
        drivetrain.oneStickLoop(stickX, stickY, stickRot, rotation,gamepad1.back);
        drivetrain.resetGyro(gamepad1.a);

        forkLift.manipulateLift(gamepad2.left_stick_y);

        ballControl.runCollector(gamepad1.right_bumper, gamepad1.left_bumper);
        ballControl.runElevator(gamepad2.left_bumper, gamepad2.left_trigger);
        ballControl.runFlywheel(gamepad2.a);

        buttonPresser.Press_Button(butt1, (.18 +(gamepad1.left_trigger)*5));
        buttonPresser.Press_Button(butt2, (.18 +(gamepad1.right_trigger)*5));

        //autopilot














    }
}
