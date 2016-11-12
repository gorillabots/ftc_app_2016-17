package org.firstinspires.ftc.teamcode;

import android.text.method.Touch;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsDigitalTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DeviceManager;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by mikko on 10/14/16.
 */

@Autonomous(name="Autonomous Drive Train", group="concept")
public class AutonomousDriveTrain extends LinearOpMode
{

    final int diagonalIncrements = 5240;
    final int straightIncrements = 3890;

    DcMotor frontRight, backRight, frontLeft, backLeft;

    TouchSensor touch;

    public void runOpMode()
    {
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");

        touch = hardwareMap.touchSensor.get("wallTouch");

        waitForStart();

        //forwards(1.04);

        backRight(1.42);
        right(0.5);
        rightToTouch();
        left(.08);

        /*while(opModeIsActive())
        {
            telemetry.addData("Button", touch.isPressed());
            telemetry.update();
        }*/
    }

    public void forwards(double meters)
    {
        double target = getPosFB() + meters * straightIncrements;

        frontRight.setPower(1);
        backRight.setPower(1);
        frontLeft.setPower(-1);
        backLeft.setPower(-1);


        while(getPosFB() < target && opModeIsActive())
        {
            telemetry.addData("Action", "Forwards");
            telemetry.addData("Currently", getPosFB());
            telemetry.addData("Target", target);
            telemetry.update();
            sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void backwards(double meters)
    {
        double target = getPosFB() - meters * straightIncrements;

        frontRight.setPower(-1);
        backRight.setPower(-1);
        frontLeft.setPower(1);
        backLeft.setPower(1);


        while(getPosFB() > target && opModeIsActive())
        {
            telemetry.addData("Action", "Backwards");
            telemetry.addData("Currently", getPosFB());
            telemetry.addData("Target", target);
            telemetry.update();
            sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void right(double meters)
    {
        double target = getPosRL() + meters * straightIncrements;

        frontRight.setPower(-1);
        backRight.setPower(1);
        frontLeft.setPower(-1);
        backLeft.setPower(1);


        while(getPosRL() < target && opModeIsActive())
        {
            telemetry.addData("Action", "Right");
            telemetry.addData("Currently", getPosRL());
            telemetry.addData("Target", target);
            telemetry.update();
            sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void rightToTouch()
    {
        frontRight.setPower(-.2);
        backRight.setPower(.2);
        frontLeft.setPower(-.2);
        backLeft.setPower(.2);


        while(!touch.isPressed() && opModeIsActive())
        {
            telemetry.addData("Action", "Right to Touch");
            telemetry.update();
            sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void left(double meters)
    {
        double target = getPosRL() - meters * straightIncrements;

        frontRight.setPower(1);
        backRight.setPower(-1);
        frontLeft.setPower(1);
        backLeft.setPower(-1);


        while(getPosRL() > target && opModeIsActive())
        {
            telemetry.addData("Action", "Left");
            telemetry.addData("Currently", getPosRL());
            telemetry.addData("Target", target);
            telemetry.update();
            sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void backRight(double meters)
    {
        double target = getPosBLFR() + meters * diagonalIncrements;

        backLeft.setPower(1);
        frontRight.setPower(-1);

        while(getPosBLFR() < target && opModeIsActive())
        {
            telemetry.addData("Action", "BackRight");
            telemetry.addData("Currently", getPosBLFR());
            telemetry.addData("Target", target);
            telemetry.update();
            sleep(5);
        }

        backLeft.setPower(0);
        frontRight.setPower(0);
    }

    public void frontLeft(double meters)
    {
        double target = getPosBLFR() - meters * diagonalIncrements;

        backLeft.setPower(-1);
        frontRight.setPower(1);

        while(getPosBLFR() > target && opModeIsActive())
        {
            telemetry.addData("Action", "FrontLeft");
            telemetry.addData("Currently", getPosBLFR());
            telemetry.addData("Target", target);
            telemetry.update();
            sleep(5);
        }

        backLeft.setPower(0);
        frontRight.setPower(0);
    }

    double getPosBLFR()
    {
        return (backLeft.getCurrentPosition() - frontRight.getCurrentPosition()) / 2;
    }

    double getPosFB()
    {
        double sum = frontRight.getCurrentPosition() - frontLeft.getCurrentPosition() +
                backRight.getCurrentPosition() - backLeft.getCurrentPosition();
        return sum / 4;
    }

    double getPosRL()
    {
        double sum = -frontRight.getCurrentPosition() - frontLeft.getCurrentPosition() +
                backRight.getCurrentPosition() + backLeft.getCurrentPosition();
        return sum / 4;
    }

    double getRotation()
    {
        double sum = frontRight.getCurrentPosition() + backRight.getCurrentPosition() +
                frontLeft.getCurrentPosition() + backLeft.getCurrentPosition();
        return sum / 4;
    }
}