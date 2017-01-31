package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.teamcode.TeamColors.RED;

/**
 * Created by mikko on 10/14/16.
 */

/*
 * Implemented functions:
 *  Normal  | Gyro  | GyroLine  | GyroTouch |
 *  -----------------------------------------
 *  F       | Yes   | Yes       | X         |
 *  B       | Yes   | Yes       | X         |
 *  R       | Yes   | Yes       | Yes       |
 *  L       | Yes   |           | X         |
 *  FR      | Yes   |           |           |
 *  FL      | Yes   |           | X         |
 *  BR      | Yes   |           |           |
 *  BL      | Yes   |           | X         |
 *
 *  X - Unnecessary
 *
 *  turnToGyro - turn to 0 degrees
 *
 *  GyroRotation() -  BETA - turn to any degrees
 *
 */

@Autonomous(name="Autonomous Drive Train", group="concept")
public class AutonomousDriveTrain
{
    LinearOpMode opMode; //Declare hardware

    DcMotor frontRight, backRight, frontLeft, backLeft;

    TouchSensor wallTouch;

    ModernRoboticsI2cGyro gyro;
    //Servo touchServo;
    Telemetry telemetry;
    public void init(LinearOpMode opMode) //Get hardware from hardwareMap
    {
        telemetry = opMode.telemetry;
        this.opMode = opMode;

        //Motors
        frontRight = opMode.hardwareMap.dcMotor.get("frontLeft"); //frontRight
        backRight = opMode.hardwareMap.dcMotor.get("frontRight"); //backRight
        frontLeft = opMode.hardwareMap.dcMotor.get("backLeft"); //frontLeft
        backLeft = opMode.hardwareMap.dcMotor.get("backRight"); //backLeft

        //Link about DC Motors / Encoders: https://ftc-tricks.com/dc-motors/

        frontRight.setMode(RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(RunMode.RUN_USING_ENCODER);
        backRight.setMode(RunMode.RUN_USING_ENCODER);
        backLeft.setMode(RunMode.RUN_USING_ENCODER);
        //touchServo = opMode.hardwareMap.servo.get("servoSwing");
        wallTouch = opMode.hardwareMap.touchSensor.get("wallTouch");
        gyro = (ModernRoboticsI2cGyro) opMode.hardwareMap.gyroSensor.get("gyro");

        gyro.calibrate();

        try //Wait for gyro to calibrate
        {
            while (gyro.isCalibrating())
            {
                Thread.sleep(50);
                telemetry.addData("is", "calibrating");
                telemetry.update();
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        gyro.resetZAxisIntegrator(); //Reset heading
    }

    public void resetGyro()
    {
        gyro.resetZAxisIntegrator();
    }

    public void forwards(double meters, double power) //Move forwards by distance
    {
        double pos = getPosFB();
        double target = pos + meters * Constants.STRAIGHT_INCREMENTS;

        frontRight.setPower(power);
        backRight.setPower(power);
        frontLeft.setPower(-power);
        backLeft.setPower(-power);

        while(pos < target && opMode.opModeIsActive())
        {
            opMode.telemetry.addData("Action", "Forwards");
            opMode.telemetry.addData("Currently", pos);
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);

            pos = getPosFB();
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void forwardsGyro(double meters, double power, int accuracy, double turnpower) //Move forward by distance with gyro
    {
        double pos = getPosFB();
        double target = pos + meters * Constants.STRAIGHT_INCREMENTS;

        int heading;
        double turnpow;

        while(pos < target && opMode.opModeIsActive())
        {
            heading = gyro.getHeading();

            if(heading <= accuracy || heading >= 360 - accuracy)
            {
                turnpow = 0;
            }
            else if(heading <= 180)
            {
                turnpow = -turnpower;
            }
            else
            {
                turnpow = turnpower;
            }

            frontRight.setPower(power + turnpow);
            backRight.setPower(power + turnpow);
            frontLeft.setPower(-power + turnpow);
            backLeft.setPower(-power + turnpow);

            opMode.telemetry.addData("Action", "Forwards Gyro");
            opMode.telemetry.addData("Currently", getPosFB());
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.addData("Heading", heading);
            opMode.telemetry.update();
            opMode.sleep(5);

            pos = getPosFB();
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void forwardsToLine(ColorSensor floorColor, double power) //Move forward to line using gyro
    {
        frontRight.setPower(power);
        backRight.setPower(power);
        frontLeft.setPower(-power);
        backLeft.setPower(-power);

        while(!ColorHelper.isFloorWhite(floorColor) && opMode.opModeIsActive())
        {
            opMode.telemetry.addData("Action", "Forwards To Line");
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void forwardsGyroToLine(ColorSensor floorColor, double power, int accuracy, double turnpower) //Move forward to line using gyro
    {
        int heading;
        double turnpow;

        while(!ColorHelper.isFloorWhiteTest(floorColor) && opMode.opModeIsActive())
        {
            heading = gyro.getHeading();

            if(heading <= accuracy || heading >= 360 - accuracy) //In range 1-359
            {
                turnpow = 0;
            }
            else if(heading <= 180)
            {
                turnpow = -turnpower;
            }
            else
            {
                turnpow = turnpower;
            }

            frontRight.setPower(power + turnpow);
            backRight.setPower(power + turnpow);
            frontLeft.setPower(-power + turnpow);
            backLeft.setPower(-power + turnpow);

            opMode.telemetry.addData("Action", "Forwards Gyro To Line");
            opMode.telemetry.addData("Heading", heading);
            ColorHelper.printColorRGB(opMode.telemetry, floorColor);
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void back(double meters, double power) //Move back by distance
    {
        double pos = getPosFB();
        double target = pos - meters * Constants.STRAIGHT_INCREMENTS;

        frontRight.setPower(-power);
        backRight.setPower(-power);
        frontLeft.setPower(power);
        backLeft.setPower(power);


        while(pos > target && opMode.opModeIsActive())
        {
            opMode.telemetry.addData("Action", "Backwards");
            opMode.telemetry.addData("Currently", pos);
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);

            pos = getPosFB();
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void backGyro(double meters, double power, int accuracy, double turnpower) //Move back to line using gyro
    {
        double pos = getPosFB();
        double target = pos - meters * Constants.STRAIGHT_INCREMENTS;


        int heading;
        double turnpow;

        while(getPosFB() > target && opMode.opModeIsActive())
        {
            heading = gyro.getHeading();

            if(heading <= accuracy || heading >= 360 - accuracy)
            {
                turnpow = 0;
            }
            else if(heading <= 180)
            {
                turnpow = -turnpower;
            }
            else
            {
                turnpow = turnpower;
            }

            frontRight.setPower(-power + turnpow);
            backRight.setPower(-power + turnpow);
            frontLeft.setPower(power + turnpow);
            backLeft.setPower(power + turnpow);

            opMode.telemetry.addData("Action", "Back Gyro");
            opMode.telemetry.addData("Currently", pos);
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.addData("Heading", heading);
            opMode.telemetry.update();
            opMode.sleep(5);

            pos = getPosFB();
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void backGyroToLine(ColorSensor floorColor, double power, int accuracy, double turnpower) //Move back to line using gyro
    {
        int heading;
        double turnpow;

        while(!ColorHelper.isFloorWhiteTest(floorColor) && opMode.opModeIsActive())
        {
            heading = gyro.getHeading();

            if(heading <= accuracy || heading >= 360 - accuracy)
            {
                turnpow = 0;
            }
            else if(heading <= 180)
            {
                turnpow = -turnpower;
            }
            else
            {
                turnpow = turnpower;
            }

            frontRight.setPower(-power + turnpow);
            backRight.setPower(-power + turnpow);
            frontLeft.setPower(power + turnpow);
            backLeft.setPower(power + turnpow);

            opMode.telemetry.addData("Action", "Back Gyro To Line");
            opMode.telemetry.addData("Heading", heading);
            opMode.telemetry.addData("Color", ColorHelper.getFloorColor(floorColor));
            opMode.telemetry.addData("line", ColorHelper.isFloorWhite(floorColor));
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }
    public void backGyroToLineDelay(ColorSensor floorColor, double power, int accuracy, double turnpower) //Move back to line using gyro
    {
        int heading;
        double turnpow;
        back(.2, .4);
        while(!ColorHelper.isFloorWhite(floorColor) && opMode.opModeIsActive())
        {
            heading = gyro.getHeading();



            if(heading <= accuracy || heading >= 360 - accuracy)
            {
                turnpow = 0;
            }
            else if(heading <= 180)
            {
                turnpow = -turnpower;
            }
            else
            {
                turnpow = turnpower;
            }

            frontRight.setPower(-power + turnpow);
            backRight.setPower(-power + turnpow);
            frontLeft.setPower(power + turnpow);
            backLeft.setPower(power + turnpow);

            opMode.telemetry.addData("Action", "Back Gyro To Line");
            opMode.telemetry.addData("Heading", heading);
            opMode.telemetry.addData("Color", ColorHelper.getFloorColor(floorColor));
            opMode.telemetry.addData("line", ColorHelper.isFloorWhite(floorColor));
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void right(double meters, double power) //Move right by distance
    {
        double pos = getPosRL();
        double target = pos + meters * Constants.STRAIGHT_INCREMENTS;


        frontRight.setPower(-power);
        backRight.setPower(power);
        frontLeft.setPower(-power);
        backLeft.setPower(power);


        while(pos < target && opMode.opModeIsActive())
        {
            opMode.telemetry.addData("Action", "Right");
            opMode.telemetry.addData("Currently", pos);
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);

            pos = getPosRL();
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void rightGyro(double meters, double power, int accuracy, double turnpower) //Move right by distance using gyro
    {
        double pos = getPosRL();
        double target = pos + meters * Constants.STRAIGHT_INCREMENTS;

        int heading;
        double turnpow;

        while(pos < target && opMode.opModeIsActive())
        {
            heading = gyro.getHeading();

            if(heading <= accuracy || heading >= 360 - accuracy)
            {
                turnpow = 0;
            }
            else if(heading <= 180)
            {
                turnpow = -turnpower;
            }
            else
            {
                turnpow = turnpower;
            }

            frontRight.setPower(-power + turnpow);
            backRight.setPower(power + turnpow);
            frontLeft.setPower(-power + turnpow);
            backLeft.setPower(power + turnpow);

            opMode.telemetry.addData("Action", "Right Gyro");
            opMode.telemetry.addData("Currently", pos);
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.addData("Heading", heading);
            opMode.telemetry.update();
            opMode.sleep(5);

            pos = getPosRL();
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void rightGyroToLine(ColorSensor floorColor, double power, int accuracy, double turnpower) //Move right to line using gyro
    {
        int heading;
        double turnpow;

        while(!ColorHelper.isFloorWhite(floorColor) && opMode.opModeIsActive())
        {
            heading = gyro.getHeading();

            if(heading <= accuracy || heading >= 360 - accuracy)
            {
                turnpow = 0;
            }
            else if(heading <= 180)
            {
                turnpow = -turnpower;
            }
            else
            {
                turnpow = turnpower;
            }

            frontRight.setPower(-power + turnpow);
            backRight.setPower(power + turnpow);
            frontLeft.setPower(-power + turnpow);
            backLeft.setPower(power + turnpow);

            opMode.telemetry.addData("Action", "Right Gyro to Line");
            opMode.telemetry.addData("Heading", heading);
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void rightGyroToTouch(double power, int accuracy, double turnpower) //Move right to touch using gyro
    {
        int heading;
        double turnpow;

        while(!wallTouch.isPressed() && opMode.opModeIsActive())
        {
            heading = gyro.getHeading();

            if(heading <= accuracy || heading >= 360 - accuracy)
            {
                turnpow = 0;
            }
            else if(heading <= 180)
            {
                turnpow = -turnpower;
            }
            else
            {
                turnpow = turnpower;
            }

            frontRight.setPower(-power + turnpow);
            backRight.setPower(power + turnpow);
            frontLeft.setPower(-power + turnpow);
            backLeft.setPower(power + turnpow);

            opMode.telemetry.addData("Action", "Right Gyro to Touch");
            opMode.telemetry.addData("Heading", heading);
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void left(double meters, double power) //Move left by distance
    {
        opMode.telemetry.addData("Starting left", 42);
        opMode.telemetry.update();

        double pos = getPosRL();
        double target = pos - meters * Constants.STRAIGHT_INCREMENTS;

        frontRight.setPower(power);
        backRight.setPower(-power);
        frontLeft.setPower(power);
        backLeft.setPower(-power);

        while(pos > target && opMode.opModeIsActive())
        {
            opMode.telemetry.addData("Action", "Left");
            opMode.telemetry.addData("Currently", pos);
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);

            pos = getPosRL();
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void leftGyro(double meters, double power, int accuracy, double turnpower) //Move left by distance using gyro
    {
        double pos = getPosRL();
        double target = pos - meters * Constants.STRAIGHT_INCREMENTS;

        int heading;
        double turnpow;

        while(pos > target && opMode.opModeIsActive())
        {
            heading = gyro.getHeading();

            if(heading <= accuracy || heading >= 360 - accuracy)
            {
                turnpow = 0;
            }
            else if(heading <= 180)
            {
                turnpow = -turnpower;
            }
            else
            {
                turnpow = turnpower;
            }

            frontRight.setPower(power + turnpow);
            backRight.setPower(-power + turnpow);
            frontLeft.setPower(power + turnpow);
            backLeft.setPower(-power + turnpow);

            opMode.telemetry.addData("Action", "LeftGyro");
            opMode.telemetry.addData("Currently", pos);
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.addData("Heading", heading);
            opMode.telemetry.update();
            opMode.sleep(5);

            pos = getPosRL();
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void frontRight(double meters, double power) //Move front-right by distance
    {
        double pos = getPosBLFR();
        double target = pos + meters * Constants.DIAGONAL_INCREMENTS;

        backRight.setPower(power);
        frontLeft.setPower(-power);

        while(pos < target && opMode.opModeIsActive())
        {
            opMode.telemetry.addData("Action", "FrontRight");
            opMode.telemetry.addData("Currently", pos);
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);

            pos = getPosBLFR();
        }

        backLeft.setPower(0);
        frontRight.setPower(0);
    }

    public void frontRightGyro(double meters, double power, int accuracy, double turnpower) //Move front-right by distance using gyro
    {
        double pos = getPosBLFR();
        double target = pos + meters * Constants.DIAGONAL_INCREMENTS;

        int heading;
        double turnpow;

        while(pos < target && opMode.opModeIsActive())
        {
            heading = gyro.getHeading();

            if(heading <= accuracy || heading >= 360 - accuracy)
            {
                turnpow = 0;
            }
            else if(heading <= 180)
            {
                turnpow = -turnpower;
            }
            else
            {
                turnpow = turnpower;
            }

            backRight.setPower(power + turnpow);
            frontLeft.setPower(-power + turnpow);

            opMode.telemetry.addData("Action", "FrontRight Gyro");
            opMode.telemetry.addData("Currently", pos);
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.addData("Heading", heading);
            opMode.telemetry.update();
            opMode.sleep(5);

            pos = getPosBLFR();
        }

        backRight.setPower(0);
        frontLeft.setPower(0);
    }

    public void backRight(double meters, double power) //Move back and right a specified distance
    {
        double pos = getPosBRFL();
        double target = pos + meters * Constants.DIAGONAL_INCREMENTS;

        backLeft.setPower(power);
        frontRight.setPower(-power);

        while(pos < target && opMode.opModeIsActive())
        {
            opMode.telemetry.addData("Action", "BackRight");
            opMode.telemetry.addData("Currently", pos);
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);

            pos = getPosBRFL();
        }

        backLeft.setPower(0);
        frontRight.setPower(0);
    }

    public void backRightGyro(double meters, double power, int accuracy, double turnpower) //Move back and right a specified distance
    {
        double pos = getPosBRFL();
        double target = pos + meters * Constants.DIAGONAL_INCREMENTS;

        int heading;
        double turnpow;

        while (pos < target && opMode.opModeIsActive())
        {
            heading = gyro.getHeading();

            if (heading <= accuracy || heading >= 360 - accuracy)
            {
                turnpow = 0;
            }
            else if (heading <= 180)
            {
                turnpow = -turnpower;
            }
            else
            {
                turnpow = turnpower;
            }

            backLeft.setPower(power + turnpow);
            frontRight.setPower(-power + turnpow);

            opMode.telemetry.addData("Action", "BackRight Gyro");
            opMode.telemetry.addData("Currently", pos);
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.addData("Heading", heading);
            opMode.telemetry.update();
            opMode.sleep(5);

            pos = getPosBRFL();
        }

        frontRight.setPower(0);
        backLeft.setPower(0);
    }

    public void frontLeft(double meters, double power) //Move forward and left a specified distance
    {
        double pos = getPosBRFL();
        double target = pos - meters * Constants.DIAGONAL_INCREMENTS;

        backLeft.setPower(-power);
        frontRight.setPower(power);

        while(pos > target && opMode.opModeIsActive())
        {
            opMode.telemetry.addData("Action", "FrontLeft");
            opMode.telemetry.addData("Currently", pos);
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);

            pos = getPosBRFL();
        }

        backLeft.setPower(0);
        frontRight.setPower(0);
    }

    public void frontLeftGyro(double meters, double power, int accuracy, double turnpower) //Move forward and left a specified distance
    {
        double pos = getPosBRFL();
        double target = pos - meters * Constants.DIAGONAL_INCREMENTS;

        int heading;
        double turnpow;

        while(pos > target && opMode.opModeIsActive())
        {
            heading = gyro.getHeading();

            if (heading <= accuracy || heading >= 360 - accuracy)
            {
                turnpow = 0;
            }
            else if (heading <= 180)
            {
                turnpow = -turnpower;
            }
            else
            {
                turnpow = turnpower;
            }

            frontRight.setPower(power + turnpow);
            backLeft.setPower(-power + turnpow);

            opMode.telemetry.addData("Action", "FrontLeft Gyro");
            opMode.telemetry.addData("Currently", pos);
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.addData("Heading", heading);
            opMode.telemetry.update();
            opMode.sleep(5);

            pos = getPosBRFL();
        }

        frontRight.setPower(0);
        backLeft.setPower(0);
    }

    public void backLeft(double meters, double power) //Move back and left specified distance
    {
        double pos = getPosBLFR();
        double target = pos - meters * Constants.DIAGONAL_INCREMENTS;

        backRight.setPower(-power);
        frontLeft.setPower(power);

        while(pos > target && opMode.opModeIsActive())
        {
            opMode.telemetry.addData("Action", "BackLeft");
            opMode.telemetry.addData("Currently", pos);
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);

            pos = getPosBLFR();
        }

        backLeft.setPower(0);
        frontRight.setPower(0);
    }

    public void backLeftGyro(double meters, double power, int accuracy, double turnpower) //Move back and left specified distance
    {
        double pos = getPosBLFR();
        double target = pos - meters * Constants.DIAGONAL_INCREMENTS;

        int heading;
        double turnpow;

        while(pos > target && opMode.opModeIsActive())
        {
            heading = gyro.getHeading();

            if (heading <= accuracy || heading >= 360 - accuracy)
            {
                turnpow = 0;
            }
            else if (heading <= 180)
            {
                turnpow = -turnpower;
            }
            else
            {
                turnpow = turnpower;
            }

            backRight.setPower(-power + turnpow);
            frontLeft.setPower(power + turnpow);

            opMode.telemetry.addData("Action", "BackLeft Gyro");
            opMode.telemetry.addData("Currently", pos);
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.addData("Heading", heading);
            opMode.telemetry.update();
            opMode.sleep(5);

            pos = getPosBLFR();
        }

        backRight.setPower(0);
        frontLeft.setPower(0);
    }

    void turnToGyro(int accuracy, double turnpower) //Turn until we are aligned
    {
        int heading;
        double turnpow;

        while(opMode.opModeIsActive())
        {
            heading = gyro.getHeading();

            opMode.telemetry.addData("Action", "Turn to Gyro");
            opMode.telemetry.addData("Heading", heading);

            if(heading <= accuracy || heading >= 360 - accuracy)
            {
                turnpow = 0;
            }
            else if(heading <= 180)
            {
                turnpow = -turnpower;
            }
            else
            {
                turnpow = turnpower;
            }

            frontRight.setPower(turnpow);
            backRight.setPower(turnpow);
            frontLeft.setPower(turnpow);
            backLeft.setPower(turnpow);

            opMode.sleep(5);
        }
    }

    public void right_continuous(double power)
    {
        frontRight.setPower(-power);
        backRight.setPower(power);
        frontLeft.setPower(-power);
        backLeft.setPower(power);
    }

    private double getPosFB() //Get position for use in forwards and backwards movements
    {
        return (frontRight.getCurrentPosition() - frontLeft.getCurrentPosition() +
                backRight.getCurrentPosition() - backLeft.getCurrentPosition()) / 4;
    }

    private double getPosRL() //Get position for use in left and right movements
    {
        return (-frontRight.getCurrentPosition() - frontLeft.getCurrentPosition() +
                backRight.getCurrentPosition() + backLeft.getCurrentPosition()) / 4;
    }

    private double getPosBRFL() //Get position for use in frontLeft and backRight
    {
        return (backLeft.getCurrentPosition() - frontRight.getCurrentPosition()) / 2;
    }

    private double getPosBLFR() //Get position for use in frontRight and backLeft
    {
        return (backRight.getCurrentPosition() - frontLeft.getCurrentPosition()) / 2;
    }

    double getRotation() //Get rotation
    {
        return (frontRight.getCurrentPosition() + backRight.getCurrentPosition() +
                frontLeft.getCurrentPosition() + backLeft.getCurrentPosition()) / 4;
    }
    private void turnleft(double power)
    {
        frontRight.setPower(power);
        backRight.setPower(power);
        frontLeft.setPower(power);
        backLeft.setPower(power);
    }

    private void turnright(double power)
    {
        frontRight.setPower(-power);
        backRight.setPower(-power);
        frontLeft.setPower(-power);
        backLeft.setPower(-power);
    }

    public void GyroRotation(int target, double power)
    {
        if(target > 360 || target < 0 || power < 0 || power > 1)
        {
            throw new IllegalArgumentException();
        }

        while(true)
        {
            int initial_heading = gyro.getHeading(); //TODO: Potential memory leak!
            int degree_rotation = target - initial_heading;
            if(degree_rotation < 0)
            {
                degree_rotation = degree_rotation + 360;
            }

            if (degree_rotation < 180 && degree_rotation > 0)
            {
                while (initial_heading < target)
                {
                    turnright(power);
                }
            }

            if(degree_rotation > 180 && degree_rotation < 360)
            {
                while(initial_heading > target)
                {
                    turnleft(power);
                }
            }

            if(degree_rotation == 0 || degree_rotation == 360)
            {
                break;
            }
        }
    }

    /*public void ExtendTouchServo()
    {
        touchServo.setPosition(0);
    }

    public void RetractTouchServo()
    {
        touchServo.setPosition(255);
    }
    */
    public void goToDistance(ModernRoboticsI2cRangeSensor rangeSensor, double target, double accuracy, double power)
    {
        double min = target - accuracy;
        double max = target + accuracy;

        double range = rangeSensor.cmUltrasonic();

        while((range < min || range > max) && opMode.opModeIsActive())
        {
            if(range > target)
            {
                frontRight.setPower(-power);
                backRight.setPower(power);
                frontLeft.setPower(-power);
                backLeft.setPower(power);
            }
            else
            {
                frontRight.setPower(power);
                backRight.setPower(-power);
                frontLeft.setPower(power);
                backLeft.setPower(-power);
            }

            opMode.telemetry.addData("Action", "GoToDistance");
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.addData("Accuracy", accuracy);
            opMode.telemetry.addData("Current", range);

            opMode.sleep(5);

            range = rangeSensor.cmUltrasonic();
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void beaconResponse(TeamColors desiredColor, ColorSensor sensorL, ColorSensor sensorR)
    {
        //sensorL is left color sensor
        //sensorR is right color sensor
        
        TeamColors colorL = ColorHelper.getBeaconColorTest(sensorL);
        TeamColors colorR = ColorHelper.getBeaconColorTest(sensorR);

        telemetry.addData("l-r", sensorL.red());
        telemetry.addData("l-b", sensorL.blue());
        telemetry.addData("l-c", enumToString(colorL));
        telemetry.addData("r-r", sensorR.red());
        telemetry.addData("r-b", sensorR.blue());
        telemetry.addData("r-c", enumToString(colorR));
        telemetry.update();
        opMode.sleep(1000);

        if(desiredColor == RED)
        {
            //On red side
            if(colorL == RED && colorR == TeamColors.BLUE) //If pressing left is necessary
            {
                pressLeft();
            }
            else if(colorL == TeamColors.BLUE && colorR == RED) //If pressing right is necessary
            {
                pressRight();
            }
            else if(colorL == RED && colorR == RED) //If both are red, do nothing
            {
                //See, nothing!
            }
            else if(colorL == TeamColors.BLUE && colorR == TeamColors.BLUE) //If both are blue, hit any (right is closest)
            {
                pressRight();
            }
            else
            {
                //If any are indecisive, do nothing to be safe
            }
        }

        if(desiredColor == TeamColors.BLUE)
        {
            if(colorL == TeamColors.BLUE && colorR == RED) //If pressing left is necessary
            {
                pressLeft();
            }
            else if(colorL == RED && colorR == TeamColors.BLUE) //If pressing right is necessary
            {
                pressRight();
            }
            else if(colorL == TeamColors.BLUE && colorR == TeamColors.BLUE) //If both are blue, do nothing
            {
                //See, nothing!
            }
            else if(colorL == RED && colorR == RED) //If both are red, hit any (right is closest)
            {
                pressRight();
            }
            else
            {
                //If any are indecisive, do nothing to be safe
            }
        }
    }

    private String enumToString(TeamColors color)
    {
        switch(color)
        {
            case RED:
                return "RED";
            case BLUE:
                return "BLUE";
            case INDECISIVE:
                return "INDECISIVE";

        }

        return "???";
    }

    private void pressLeft()
    {
        forwards(0.15, 0.3); //Align mashy spike plate
        right(0.2, 0.5); //Mash mashy spike plate into left button
        left(0.2, 0.5); //Back away
    }

    private void pressRight()
    {
        right(0.2, 0.5); //Mash mashy spike plate into left button
        left(0.2, 0.5); //Back away
    }
}
