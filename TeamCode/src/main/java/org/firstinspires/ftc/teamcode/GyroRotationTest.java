package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Mikko on 11/13/16.
 */
//Josh was here
@Autonomous(name="GyroRotationTest", group="concept")
public class GyroRotationTest extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;
    public void runOpMode() throws InterruptedException
    {
        driveTrain = new AutonomousDriveTrain();
        driveTrain.init(this);

        waitForStart();

        driveTrain.rightGyroToTouch();

        //driveTrain.turn(1000);
        //sleep(1000);
        //driveTrain.turnToGyro();
    }
}
