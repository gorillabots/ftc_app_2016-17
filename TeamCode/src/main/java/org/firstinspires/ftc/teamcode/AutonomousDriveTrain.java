package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
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

    ModernRoboticsI2cGyro gyro;

    public void init(LinearOpMode opMode)
    {
        this.opMode = opMode;

        frontRight = opMode.hardwareMap.dcMotor.get("frontLeft"); //frontRight
        backRight = opMode.hardwareMap.dcMotor.get("frontRight"); //backRight
        frontLeft = opMode.hardwareMap.dcMotor.get("backLeft"); //frontLeft
        backLeft = opMode.hardwareMap.dcMotor.get("backRight"); //backLeft

        wallTouch = opMode.hardwareMap.touchSensor.get("wallTouch");
        gyro = (ModernRoboticsI2cGyro) opMode.hardwareMap.gyroSensor.get("gyro");

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



    public void forwards(double meters)
    {
        double target = getPosFB() + meters * Constants.STRAIGHT_INCREMENTS;

        frontRight.setPower(Constants.MAX_SPEED);
        backRight.setPower(Constants.MAX_SPEED);
        frontLeft.setPower(-Constants.MAX_SPEED);
        backLeft.setPower(-Constants.MAX_SPEED);


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

    public void forwardsGyro(double meters)
    {
        //gyro.resetZAxisIntegrator();

        double target = getPosFB() + meters * Constants.STRAIGHT_INCREMENTS;

        while(getPosFB() < target && opMode.opModeIsActive())
        {
            int heading = gyro.getHeading();
            double turnpow;

            if(heading <= 1 || heading >= 359)
            {
                turnpow = 0;
            }
            else if(heading >= 2 && heading <= 180)
            {
                turnpow = -.3;
            }
            else
            {
                turnpow = .3;
            }

            frontRight.setPower(.7 + turnpow);
            backRight.setPower(.7 + turnpow);
            frontLeft.setPower(-.7 + turnpow);
            backLeft.setPower(-.7 + turnpow);

            opMode.telemetry.addData("Action", "Forwards Gyro");
            opMode.telemetry.addData("Currently", getPosFB());
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.addData("Heading", heading);
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void backwardsGyroToLine(ColorSensor floorColor)
    {
        //gyro.resetZAxisIntegrator();

        while(!ColorHelper.isFloorWhite(floorColor) && opMode.opModeIsActive())
        {
            int heading = gyro.getHeading();
            double turnpow;

            if(heading <= 1 || heading >= 359)
            {
                turnpow = 0;
            }
            else if(heading >= 2 && heading <= 180)
            {
                turnpow = -.05;
            }
            else
            {
                turnpow = .05;
            }

            frontRight.setPower(-.2 + turnpow);
            backRight.setPower(-.2 + turnpow);
            frontLeft.setPower(.2 + turnpow);
            backLeft.setPower(.2 + turnpow);

            opMode.telemetry.addData("Action", "Back Gyro To Line");
            opMode.telemetry.addData("Heading", heading);
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }


    public void forwardsGyroToLine(ColorSensor floorColor)
    {
        //gyro.resetZAxisIntegrator();

        while(!ColorHelper.isFloorWhite(floorColor) && opMode.opModeIsActive())
        {
            int heading = gyro.getHeading();
            double turnpow;

            if(heading <= 1 || heading >= 359)
            {
                turnpow = 0;
            }
            else if(heading >= 2 && heading <= 180)
            {
                turnpow = -.05;
            }
            else
            {
                turnpow = .05;
            }

            frontRight.setPower(.2 + turnpow);
            backRight.setPower(.2 + turnpow);
            frontLeft.setPower(-.2 + turnpow);
            backLeft.setPower(-.2 + turnpow);

            opMode.telemetry.addData("Action", "Back Gyro To Line");
            opMode.telemetry.addData("Heading", heading);
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
        double target = getPosFB() - meters * Constants.STRAIGHT_INCREMENTS;

        frontRight.setPower(-Constants.MAX_SPEED);
        backRight.setPower(-Constants.MAX_SPEED);
        frontLeft.setPower(Constants.MAX_SPEED);
        backLeft.setPower(Constants.MAX_SPEED);


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
        frontRight.setPower(-Constants.SLOW_SPEED);
        backRight.setPower(-Constants.SLOW_SPEED);
        frontLeft.setPower(Constants.SLOW_SPEED);
        backLeft.setPower(Constants.SLOW_SPEED);

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
        double target = getPosRL() + meters * Constants.STRAIGHT_INCREMENTS;

        frontRight.setPower(-Constants.MAX_SPEED);
        backRight.setPower(Constants.MAX_SPEED);
        frontLeft.setPower(-Constants.MAX_SPEED);
        backLeft.setPower(Constants.MAX_SPEED);


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

    public void rightGyroToTouch()
    {
        //gyro.resetZAxisIntegrator();


        while(!wallTouch.isPressed() && opMode.opModeIsActive())
        {
            int heading = gyro.getHeading();
            double turnpow;

            if(heading <= 1 || heading >= 359)
            {
                turnpow = 0;
            }
            else if(heading >= 2 && heading <= 180)
            {
                turnpow = -.05;
            }
            else
            {
                turnpow = .05;
            }

            frontRight.setPower(-.2 + turnpow);
            backRight.setPower(.2 + turnpow);
            frontLeft.setPower(-.2 + turnpow);
            backLeft.setPower(.2 + turnpow);

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
        double target = getPosRL() - meters * Constants.STRAIGHT_INCREMENTS;

        frontRight.setPower(Constants.MAX_SPEED);
        backRight.setPower(-Constants.MAX_SPEED);
        frontLeft.setPower(Constants.MAX_SPEED);
        backLeft.setPower(-Constants.MAX_SPEED);


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
        double target = getPosBLFR() + meters * Constants.DIAGONAL_INCREMENTS;

        backRight.setPower(Constants.MAX_SPEED);
        frontLeft.setPower(-Constants.MAX_SPEED);

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
        double target = getPosBRFL() + meters * Constants.DIAGONAL_INCREMENTS;

        backLeft.setPower(Constants.MAX_SPEED);
        frontRight.setPower(-Constants.MAX_SPEED);

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
        double target = getPosBRFL() - meters * Constants.DIAGONAL_INCREMENTS;

        backLeft.setPower(-Constants.MAX_SPEED);
        frontRight.setPower(Constants.MAX_SPEED);

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
        double target = getPosBLFR() - meters * Constants.DIAGONAL_INCREMENTS;

        backRight.setPower(-Constants.MAX_SPEED);
        frontLeft.setPower(Constants.MAX_SPEED);

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

    void turnToGyro()
    {
        while(true)
        {
            int head = gyro.getHeading();

            if(head >= 350 || head <= 9)
            {
                frontRight.setPower(0);
                backRight.setPower(0);
                frontLeft.setPower(0);
                backLeft.setPower(0);

                break;
            }

            if(head >= 2 && head <= 179)
            {
                frontRight.setPower(Constants.SLOW_SPEED);
                backRight.setPower(Constants.SLOW_SPEED);
                frontLeft.setPower(Constants.SLOW_SPEED);
                backLeft.setPower(Constants.SLOW_SPEED);
            }
            else if(head >= 180 && head <= 358)
            {
                frontRight.setPower(-Constants.SLOW_SPEED);
                backRight.setPower(-Constants.SLOW_SPEED);
                frontLeft.setPower(-Constants.SLOW_SPEED);
                backLeft.setPower(-Constants.SLOW_SPEED);
            }
            else
            {
                opMode.telemetry.addData("Status", "Everything broke :(");
            }

            opMode.sleep(50);
        }


    }

    void turn(int millis)
    {
        frontRight.setPower(.2);
        backRight.setPower(.2);
        frontLeft.setPower(.2);
        backLeft.setPower(.2);

        opMode.sleep(millis);

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
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