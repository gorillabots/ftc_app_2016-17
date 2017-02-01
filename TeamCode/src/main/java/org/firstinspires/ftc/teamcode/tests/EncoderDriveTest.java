package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subclasses.AutonomousDriveTrain;

/**
 * Created by mikko on 1/15/17.
 */

@Disabled
@Autonomous(name="EncoderDriveTest", group="Tests")
public class EncoderDriveTest extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;

    @Override
    public void runOpMode() throws InterruptedException
    {
        driveTrain = new AutonomousDriveTrain(); //Initialize hardware
        driveTrain.init(this);

        waitForStart();

        driveTrain.resetGyro();

        int frontRightStart = driveTrain.frontRight.getCurrentPosition();
        int backRightStart = driveTrain.backRight.getCurrentPosition();
        int frontLeftStart = driveTrain.frontLeft.getCurrentPosition();
        int backLeftStart = driveTrain.backLeft.getCurrentPosition();

        int frontRightCur;
        int backRightCur;
        int frontLeftCur;
        int backLeftCur;

        double frontRightDelta;
        double backRightDelta;
        double frontLeftDelta;
        double backLeftDelta;

        double max;

        long startTime = System.currentTimeMillis();
        long endTime = startTime + 2000;

        driveTrain.frontRight.setPower(.5);
        driveTrain.backRight.setPower(.5);
        driveTrain.frontLeft.setPower(-.5);
        driveTrain.backLeft.setPower(-.5);

        while(System.currentTimeMillis() < endTime && opModeIsActive())
        {
            frontRightCur = driveTrain.frontRight.getCurrentPosition();
            backRightCur = driveTrain.backRight.getCurrentPosition();
            frontLeftCur = driveTrain.frontLeft.getCurrentPosition();
            backLeftCur = driveTrain.backLeft.getCurrentPosition();

            frontRightDelta = frontRightCur - frontRightStart;
            backRightDelta = backRightCur - backRightStart;
            frontLeftDelta = frontLeftStart - frontLeftCur;
            backLeftDelta = backLeftStart - backLeftCur;

            max = Math.max(Math.max(frontRightDelta, backRightDelta), Math.max(frontLeftDelta, backLeftDelta));

            frontRightDelta = max / frontRightDelta;
            backRightDelta = max / backRightDelta;
            frontLeftDelta = max / frontLeftDelta;
            backLeftDelta = max / backLeftDelta;

            driveTrain.frontRight.setPower(.5 * frontRightDelta);
            driveTrain.backRight.setPower(.5 * backRightDelta);
            driveTrain.frontLeft.setPower(-.5 * frontLeftDelta);
            driveTrain.backLeft.setPower(-.5 * backLeftDelta);


            sleep(5);
        }

        driveTrain.frontRight.setPower(0);
        driveTrain.backRight.setPower(0);
        driveTrain.frontLeft.setPower(0);
        driveTrain.backLeft.setPower(0);

                /*
                frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
                 */
    }
}
