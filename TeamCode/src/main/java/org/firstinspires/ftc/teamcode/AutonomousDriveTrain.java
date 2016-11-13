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
public class AutonomousDriveTrain
{
    LinearOpMode opMode;

    final int diagonalIncrements = 5240;
    final int straightIncrements = 3890;

    DcMotor frontRight, backRight, frontLeft, backLeft;

    TouchSensor touch;

    public void init(LinearOpMode opMode)
    {
        this.opMode = opMode;

        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        backRight = opMode.hardwareMap.dcMotor.get("backRight");
        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");

        touch = opMode.hardwareMap.touchSensor.get("wallTouch");
    }

    public void forwards(double meters)
    {
        double target = getPosFB() + meters * straightIncrements;

        frontRight.setPower(1);
        backRight.setPower(1);
        frontLeft.setPower(-1);
        backLeft.setPower(-1);


        while(getPosFB() < target && opMode.opModeIsActive())
        {
            opMode.telemetry.addData("Action", "Forwards");
            opMode.telemetry.addData("Currently", getPosFB());
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);
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


        while(getPosFB() > target && opMode.opModeIsActive())
        {
            opMode.telemetry.addData("Action", "Backwards");
            opMode.telemetry.addData("Currently", getPosFB());
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);
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


        while(getPosRL() < target && opMode.opModeIsActive())
        {
            opMode.telemetry.addData("Action", "Right");
            opMode.telemetry.addData("Currently", getPosRL());
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);
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


        while(!touch.isPressed() && opMode.opModeIsActive())
        {
            opMode.telemetry.addData("Action", "Right to Touch");
            opMode.telemetry.update();
            opMode.sleep(5);
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


        while(getPosRL() > target && opMode.opModeIsActive())
        {
            opMode.telemetry.addData("Action", "Left");
            opMode.telemetry.addData("Currently", getPosRL());
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void frontRight(double meters)
    {
        double target = getPosBLFR() + meters * diagonalIncrements;

        backRight.setPower(1);
        frontLeft.setPower(-1);

        while(getPosBLFR() < target && opMode.opModeIsActive())
        {
            opMode.telemetry.addData("Action", "FrontRight");
            opMode.telemetry.addData("Currently", getPosBLFR());
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        backLeft.setPower(0);
        frontRight.setPower(0);
    }

    public void backRight(double meters)
    {
        double target = getPosBRFL() + meters * diagonalIncrements;

        backLeft.setPower(1);
        frontRight.setPower(-1);

        while(getPosBRFL() < target && opMode.opModeIsActive())
        {
            opMode.telemetry.addData("Action", "BackRight");
            opMode.telemetry.addData("Currently", getPosBRFL());
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        backLeft.setPower(0);
        frontRight.setPower(0);
    }

    public void frontLeft(double meters)
    {
        double target = getPosBRFL() - meters * diagonalIncrements;

        backLeft.setPower(-1);
        frontRight.setPower(1);

        while(getPosBRFL() > target && opMode.opModeIsActive())
        {
            opMode.telemetry.addData("Action", "FrontLeft");
            opMode.telemetry.addData("Currently", getPosBRFL());
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        backLeft.setPower(0);
        frontRight.setPower(0);
    }

    public void backLeft(double meters)
    {
        double target = getPosBLFR() - meters * diagonalIncrements;

        backRight.setPower(-1);
        frontLeft.setPower(1);

        while(getPosBLFR() > target && opMode.opModeIsActive())
        {
            opMode.telemetry.addData("Action", "BackLeft");
            opMode.telemetry.addData("Currently", getPosBLFR());
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        backLeft.setPower(0);
        frontRight.setPower(0);
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

    double getPosBRFL()
    {
        return (backLeft.getCurrentPosition() - frontRight.getCurrentPosition()) / 2;
    }

    double getPosBLFR()
    {
        return (backRight.getCurrentPosition() - frontLeft.getCurrentPosition()) / 2;
    }

    double getRotation()
    {
        double sum = frontRight.getCurrentPosition() + backRight.getCurrentPosition() +
                frontLeft.getCurrentPosition() + backLeft.getCurrentPosition();
        return sum / 4;
    }
}