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
    LinearOpMode opMode; //Declare hardware

    DcMotor frontRight, backRight, frontLeft, backLeft;

    TouchSensor wallTouch;

    ModernRoboticsI2cGyro gyro;

    public void init(LinearOpMode opMode) //Get hardware from hardwareMap
    {
        this.opMode = opMode;

        frontRight = opMode.hardwareMap.dcMotor.get("frontLeft"); //frontRight
        backRight = opMode.hardwareMap.dcMotor.get("frontRight"); //backRight
        frontLeft = opMode.hardwareMap.dcMotor.get("backLeft"); //frontLeft
        backLeft = opMode.hardwareMap.dcMotor.get("backRight"); //backLeft

        wallTouch = opMode.hardwareMap.touchSensor.get("wallTouch");
        gyro = (ModernRoboticsI2cGyro) opMode.hardwareMap.gyroSensor.get("gyro");

        gyro.calibrate();

        try //Wait for gyro to calibrate
        {
            while (gyro.isCalibrating())
            {
                Thread.sleep(50);
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        gyro.resetZAxisIntegrator(); //Reset heading
    }


    void forwardEncodeHelp(double meters) {
        double target = getPosFB() + meters * Constants.STRAIGHT_INCREMENTS;
        while(getPosFB() < target && opMode.opModeIsActive()) {
            if (backLeft.getCurrentPosition() > backRight.getCurrentPosition()) {
                frontRight.setPower((Constants.MAX_SPEED) - .1);
                backRight.setPower(Constants.MAX_SPEED);
                frontLeft.setPower(-Constants.MAX_SPEED);
                backLeft.setPower((-Constants.MAX_SPEED) + .1);
            } else if (backLeft.getCurrentPosition() < backRight.getCurrentPosition()) {
                frontRight.setPower(Constants.MAX_SPEED);
                backRight.setPower((Constants.MAX_SPEED) - .1);
                frontLeft.setPower((-Constants.MAX_SPEED) + .1);
                backLeft.setPower(-Constants.MAX_SPEED);
            } else {
                frontRight.setPower(Constants.MAX_SPEED);
                backRight.setPower(Constants.MAX_SPEED);
                frontLeft.setPower(-Constants.MAX_SPEED);
                backLeft.setPower(-Constants.MAX_SPEED);
            }
        }
    }
    public void forwards(double meters) //Move forward specified distance
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

    public void forwardsGyro(double meters) //Move forward specified distance using gyro
    {
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
                turnpow = -.1;
            }
            else
            {
                turnpow = .1;
            }

            frontRight.setPower(.5 + turnpow);
            backRight.setPower(.5 + turnpow);
            frontLeft.setPower(-.5 + turnpow);
            backLeft.setPower(-.5 + turnpow);

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

    public void forwardsGyroToLine(ColorSensor floorColor) //Move forward to line using gyro
    {
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

            frontRight.setPower(.5 + turnpow);
            backRight.setPower(.5 + turnpow);
            frontLeft.setPower(-.5 + turnpow);
            backLeft.setPower(-.5 + turnpow);

            opMode.telemetry.addData("Action", "Forwards Gyro To Line");
            opMode.telemetry.addData("Heading", heading);
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void back(double meters) //Move back specified distance
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

    public void backToLine(ColorSensor floorColor) //Move back to white line
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

    public void backwardsGyroToLine(ColorSensor floorColor) //Move back to line using gyro
    {
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

    public void right(double meters) //Move right specified distance
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

    public void rightToTouch() //Move right until touch sensor is pressed
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

    public void rightGyroToTouch() //Move right until touch sensor is pressed using gyro
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

    public void rightWobbleToTouch() //Move right until touch sensor is pressed using gyro
    {
        //gyro.resetZAxisIntegrator();


        while(!wallTouch.isPressed() && opMode.opModeIsActive())
        {
            int heading = gyro.getHeading();
            double turnpow;

            if(heading == 0)
            {
                turnpow = 0;
            }
            else if(heading <= 180)
            {
                turnpow = -.1;
            }
            else
            {
                turnpow = .1;
            }

            frontRight.setPower(-.1 + turnpow);
            backRight.setPower(.1 + turnpow);
            frontLeft.setPower(-.1 + turnpow);
            backLeft.setPower(.1 + turnpow);

            opMode.telemetry.addData("Action", "Right to Touch");
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void left(double meters) //Move left specified distance
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

    public void frontRight(double meters) //Move forwards and right a specified distance
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

    public void frontRightGyro(double meters) //Move front right a specified distance using gyro
    {
        double target = getPosBLFR() + meters * Constants.DIAGONAL_INCREMENTS;

        while(getPosBLFR() < target && opMode.opModeIsActive())
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

            backRight.setPower(.7 + turnpow);
            frontLeft.setPower(-.7 + turnpow);

            opMode.telemetry.addData("Action", "Front Right Gyro");
            opMode.telemetry.addData("Currently", getPosFB());
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.addData("Heading", heading);
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        backRight.setPower(0);
        frontLeft.setPower(0);
    }

    public void backRight(double meters) //Move back and right a specified distance
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

    public void backRightGyro(double meters) //Move back and right a specified distance
    {
        double target = getPosBRFL() + meters * Constants.DIAGONAL_INCREMENTS;

        while (getPosBRFL() < target && opMode.opModeIsActive()) {
            int heading = gyro.getHeading();
            double turnpow;

            if (heading <= 1 || heading >= 359) {
                turnpow = 0;
            } else if (heading >= 2 && heading <= 180) {
                turnpow = -.2;
            } else {
                turnpow = .2;
            }

            backLeft.setPower(.8 + turnpow);
            frontRight.setPower(-.8 + turnpow);

            opMode.telemetry.addData("Action", "Back Right Gyro");
            opMode.telemetry.addData("Currently", getPosBRFL());
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        backLeft.setPower(0);
        frontRight.setPower(0);
    }

    public void frontLeft(double meters) //Move forward and left a specified distance
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

    public void backLeft(double meters) //Move back and left specified distance
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

    void turnToGyro() //Turn until we are aligned
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

    void turn(int millis) //Turn for a specified amount of time (Unused)
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

    double getPosFB() //Get position for use in forwards and backwards movements
    {
        double sum = frontRight.getCurrentPosition() - frontLeft.getCurrentPosition() +
                backRight.getCurrentPosition() - backLeft.getCurrentPosition();
        return sum / 4;
    }

    double getPosRL() //Get position for use in left and right movements
    {
        double sum = -frontRight.getCurrentPosition() - frontLeft.getCurrentPosition() +
                backRight.getCurrentPosition() + backLeft.getCurrentPosition();
        return sum / 4;
    }

    double getPosBRFL() //Get position for use in frontLeft and backRight
    {
        return (backLeft.getCurrentPosition() - frontRight.getCurrentPosition()) / 2;
    }

    double getPosBLFR() //Get position for use in frontRight and backLeft
    {
        return (backRight.getCurrentPosition() - frontLeft.getCurrentPosition()) / 2;
    }

    double getRotation() //Get rotation
    {
        double sum = frontRight.getCurrentPosition() + backRight.getCurrentPosition() +
                frontLeft.getCurrentPosition() + backLeft.getCurrentPosition();
        return sum / 4;
    }
}