package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by mikko on 10/14/16.
 */

@Autonomous(name="Autonomous Drive Train", group="concept")
public class AutonomousDriveTrain
{
    LinearOpMode opMode;

    DcMotor frontRight, backRight, frontLeft, backLeft;

    TouchSensor wallTouch;

    public void init(LinearOpMode opMode)
    {
        this.opMode = opMode;

        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        backRight = opMode.hardwareMap.dcMotor.get("backRight");
        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");

        wallTouch = opMode.hardwareMap.touchSensor.get("wallTouch");
    }

    public void forwards(double meters)
    {
        double target = getPosFB() + meters * Constants.TESTBED_STRAIGHT_INCREMENTS;

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

    public void back(double meters)
    {
        double target = getPosFB() - meters * Constants.TESTBED_STRAIGHT_INCREMENTS;

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

    public void backToLine(ColorSensor floorColor)
    {
        frontRight.setPower(-.1);
        backRight.setPower(-.1);
        frontLeft.setPower(.1);
        backLeft.setPower(.1);

        while(!ColorHelper.isFloorWhite(floorColor) && opMode.opModeIsActive())
        {

            opMode.telemetry.addData("Action", "Back to Color");
            opMode.telemetry.addData("Value", ColorHelper.getFloorValue());
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
        double target = getPosRL() + meters * Constants.TESTBED_STRAIGHT_INCREMENTS;

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


        while(!wallTouch.isPressed() && opMode.opModeIsActive())
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
        double target = getPosRL() - meters * Constants.TESTBED_STRAIGHT_INCREMENTS;

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
        double target = getPosBLFR() + meters * Constants.TESTBED_DIAGONAL_INCREMENTS;

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
        double target = getPosBRFL() + meters * Constants.TESTBED_DIAGONAL_INCREMENTS;

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
        double target = getPosBRFL() - meters * Constants.TESTBED_DIAGONAL_INCREMENTS;

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
        double target = getPosBLFR() - meters * Constants.TESTBED_DIAGONAL_INCREMENTS;

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