package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Jarred on 10/18/2016.
 */
@TeleOp(name = "CompTele", group = "final")
public class CompTele extends OpMode {
    Drivetrain drivetrain;

    ButtonPresserClass buttonPresser;

    LargeBallLifter yogaLift;

    BallControlInterface ballControl;


    ModernRoboticsI2cGyro gyro;

    int rotation = 0;





    public void init()
    {
        drivetrain = new Drivetrain(hardwareMap, telemetry);
        buttonPresser = new ButtonPresserClass();
        drivetrain.floorColor.enableLed(true);
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
        drivetrain.oneStickLoop(stickX, stickY, stickRot, rotation);

        if(gamepad1.b == true){
            drivetrain.backToLine(1,gamepad1.start);
        }
        else if(gamepad1.x == true){
            drivetrain.backToLine(-1,gamepad1.start);
        }

        if(gamepad1.a == true){
            drivetrain.resetGyro();

        }

        buttonPresser.extend_One(gamepad2.left_trigger);
        buttonPresser.extend_Two(gamepad2.right_trigger);








    }
}
