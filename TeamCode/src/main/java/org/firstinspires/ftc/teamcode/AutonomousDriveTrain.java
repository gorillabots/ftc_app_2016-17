package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by mikko on 10/14/16.
 */

@Autonomous(name="Autonomous Drive Train", group="concept")
public class AutonomousDriveTrain extends LinearOpMode
{

    final int incrementsPerMeter = 5240;

    DcMotor frontRight, backRight, frontLeft, backLeft;

    ModernRoboticsI2cGyro gyro;

    public void runOpMode()
    {
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");

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

        waitForStart();

        //forwardsMeters(2);
        //backwardsMeters(1);

        counterclockwise(90);
        sleep(1000);
        clockwise(90);
    }

    public void forwardsMeters(int meters)
    {
        forwards(meters * incrementsPerMeter);
    }

    public void forwards(double degrees)
    {
        double target = getPosX() + degrees;

        backLeft.setPower(1);
        frontRight.setPower(-1);

        while(getPosX() < target && opModeIsActive())
        {
            telemetry.addData("Action", "Forwards");
            telemetry.addData("Currently", getPosX());
            telemetry.addData("Target", target);
            telemetry.update();
            sleep(5);
        }

        backLeft.setPower(0);
        frontRight.setPower(0);
    }

    public void backwardsMeters(int meters)
    {
        backwards(meters * incrementsPerMeter);
    }

    public void backwards(double degrees)
    {
        double target = getPosX() - degrees;

        backLeft.setPower(-1);
        frontRight.setPower(1);

        while(getPosX() > target && opModeIsActive())
        {
            telemetry.addData("Action", "Backwards");
            telemetry.addData("Currently", getPosX());
            telemetry.addData("Target", target);
            telemetry.update();
            sleep(5);
        }

        backLeft.setPower(0);
        frontRight.setPower(0);
    }

    public void clockwise(double degrees)
    {
        gyro.resetZAxisIntegrator();

        frontRight.setPower(-1);
        backRight.setPower(-1);
        frontLeft.setPower(-1);
        backLeft.setPower(-1);

        int heading = gyro.getHeading();

        while(heading < degrees && opModeIsActive())
        {
            telemetry.addData("Action", "Clockwise");
            telemetry.addData("Currently", gyro.getHeading());
            telemetry.addData("Target", degrees);
            telemetry.update();

            sleep(5);

            heading = gyro.getHeading();
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void counterclockwise(double degrees)
    {
        gyro.resetZAxisIntegrator();

        frontRight.setPower(1);
        backRight.setPower(1);
        frontLeft.setPower(1);
        backLeft.setPower(1);

        int heading = gyro.getHeading();

        while((heading == 0 || heading > (360 - degrees)) && opModeIsActive())
        {
            telemetry.addData("Action", "Clockwise");
            telemetry.addData("Currently", gyro.getHeading());
            telemetry.addData("Target", degrees);
            telemetry.update();

            sleep(5);

            heading = gyro.getHeading();
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    double getPosX()
    {
        return (backLeft.getCurrentPosition() - frontRight.getCurrentPosition()) / 2;
    }

    double getRotation()
    {
        double sum = frontRight.getCurrentPosition() + backRight.getCurrentPosition() +
                frontLeft.getCurrentPosition() + backLeft.getCurrentPosition();
        return sum / 4;
    }
}