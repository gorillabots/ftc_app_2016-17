package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
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
    Servo touch_servo;
    public void init(LinearOpMode opMode) //Get hardware from hardwareMap
    {
        this.opMode = opMode;

        frontRight = opMode.hardwareMap.dcMotor.get("frontLeft"); //frontRight
        backRight = opMode.hardwareMap.dcMotor.get("frontRight"); //backRight
        frontLeft = opMode.hardwareMap.dcMotor.get("backLeft"); //frontLeft
        backLeft = opMode.hardwareMap.dcMotor.get("backRight"); //backLeft
        touch_servo = opMode.hardwareMap.servo.get("touchServo");
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

    public void resetGyro()
    {
        gyro.resetZAxisIntegrator();
    }

    public void forwards(double meters, double power) //Move forward specified distance with specified power
    {
        double target = getPosFB() + meters * Constants.STRAIGHT_INCREMENTS;

        frontRight.setPower(power);
        backRight.setPower(power);
        frontLeft.setPower(-power);
        backLeft.setPower(-power);


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

    public void forwardsGyro(double meters, double power, int accuracy, double turnpower) //Move forward specified distance using gyro
    {
        double target = getPosFB() + meters * Constants.STRAIGHT_INCREMENTS;

        int heading;
        double turnpow;

        while(getPosFB() < target && opMode.opModeIsActive())
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

        while(!ColorHelper.isFloorWhite(floorColor) && opMode.opModeIsActive())
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
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void back(double meters, double power) //Move back specified distance
    {
        double target = getPosFB() - meters * Constants.STRAIGHT_INCREMENTS;

        frontRight.setPower(-power);
        backRight.setPower(-power);
        frontLeft.setPower(power);
        backLeft.setPower(power);


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

    public void backToLine(ColorSensor floorColor, double power) //Move back to white line
    {
        frontRight.setPower(-power);
        backRight.setPower(-power);
        frontLeft.setPower(power);
        backLeft.setPower(power );

        while(!ColorHelper.isFloorWhite(floorColor) && opMode.opModeIsActive())
        {

            opMode.telemetry.addData("Action", "Back to Line");
            opMode.telemetry.update();
            opMode.sleep(5);
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
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void right(double meters, double power) //Move right specified distance
    {
        double target = getPosRL() + meters * Constants.STRAIGHT_INCREMENTS;

        frontRight.setPower(-power);
        backRight.setPower(power);
        frontLeft.setPower(-power);
        backLeft.setPower(power);


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

    public void rightToTouch(double power) //Move right until touch sensor is pressed
    {
        frontRight.setPower(-power);
        backRight.setPower(power);
        frontLeft.setPower(-power);
        backLeft.setPower(power);


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

    public void rightGyroToTouch(double power, int accuracy, double turnpower) //Move right until touch sensor is pressed using gyro
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

            opMode.telemetry.addData("Action", "Right Gryo to Touch");
            opMode.telemetry.addData("Heading", heading);
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void left(double meters, double power) //Move left specified distance
    {
        double target = getPosRL() - meters * Constants.STRAIGHT_INCREMENTS;

        frontRight.setPower(power);
        backRight.setPower(-power);
        frontLeft.setPower(power);
        backLeft.setPower(-power);


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

    public void leftGyro(double meters, double power, int accuracy, double turnpower) //Move left specified distance using gyro
    {
        double target = getPosRL() - meters * Constants.STRAIGHT_INCREMENTS;

        int heading;
        double turnpow;

        while(getPosRL() > target && opMode.opModeIsActive())
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
            opMode.telemetry.addData("Currently", getPosRL());
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

    public void frontRight(double meters, double power) //Move forwards and right a specified distance
    {
        double target = getPosBLFR() + meters * Constants.DIAGONAL_INCREMENTS;

        backRight.setPower(power);
        frontLeft.setPower(-power);

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

    public void frontRightGyro(double meters, double power, int accuracy, double turnpower) //Move front right a specified distance using gyro
    {
        double target = getPosBLFR() + meters * Constants.DIAGONAL_INCREMENTS;

        while(getPosBLFR() < target && opMode.opModeIsActive())
        {
            int heading = gyro.getHeading();
            double turnpow;

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
            backRight.setPower(power + turnpow);
            frontLeft.setPower(-power + turnpow);
            backLeft.setPower(turnpow);

            opMode.telemetry.addData("Action", "FrontRight Gyro");
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

    public void backRight(double meters, double power) //Move back and right a specified distance
    {
        double target = getPosBRFL() + meters * Constants.DIAGONAL_INCREMENTS;

        backLeft.setPower(power);
        frontRight.setPower(-power);

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

    public void backRightGyro(double meters, double power, int accuracy, double turnpower) //Move back and right a specified distance
    {
        double target = getPosBRFL() + meters * Constants.DIAGONAL_INCREMENTS;

        int heading;
        double turnpow;

        while (getPosBRFL() < target && opMode.opModeIsActive())
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

            frontRight.setPower(turnpow);
            backLeft.setPower(power + turnpow);
            frontRight.setPower(-power + turnpow);
            backLeft.setPower(turnpow);

            opMode.telemetry.addData("Action", "Back Right Gyro");
            opMode.telemetry.addData("Currently", getPosBRFL());
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

    public void frontLeft(double meters, double power) //Move forward and left a specified distance
    {
        double target = getPosBRFL() - meters * Constants.DIAGONAL_INCREMENTS;

        backLeft.setPower(-power);
        frontRight.setPower(power);

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

    public void backLeft(double meters, double power) //Move back and left specified distance
    {
        double target = getPosBLFR() - meters * Constants.DIAGONAL_INCREMENTS;

        backRight.setPower(-power);
        frontLeft.setPower(power);

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

    void turnToGyro(int accuracy, double turnpower) //Turn until we are aligned
    {
        while(opMode.opModeIsActive())
        {
            int heading = gyro.getHeading();

            opMode.telemetry.addData("Action", "Turn to Gyro");
            opMode.telemetry.addData("Heading", heading);

            double turnpow;

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

            opMode.sleep(50);
        }


    }
    public void right_continuous(double power) {
        frontRight.setPower(-power);
        backRight.setPower(power);
        frontLeft.setPower(-power);
        backLeft.setPower(power);
    }

    private double getPosFB() //Get position for use in forwards and backwards movements
    {
        double sum = frontRight.getCurrentPosition() - frontLeft.getCurrentPosition() +
                backRight.getCurrentPosition() - backLeft.getCurrentPosition();
        return sum / 4;
    }

    private double getPosRL() //Get position for use in left and right movements
    {
        double sum = -frontRight.getCurrentPosition() - frontLeft.getCurrentPosition() +
                backRight.getCurrentPosition() + backLeft.getCurrentPosition();
        return sum / 4;
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
        double sum = frontRight.getCurrentPosition() + backRight.getCurrentPosition() +
                frontLeft.getCurrentPosition() + backLeft.getCurrentPosition();
        return sum / 4;
    }
    void turnleft(double power) {
        frontRight.setPower(power);
        backRight.setPower(power);
        frontLeft.setPower(power);
        backLeft.setPower(power);
    }

    void turnright(double power) {
        frontRight.setPower(-power);
        backRight.setPower(-power);
        frontLeft.setPower(-power);
        backLeft.setPower(-power);
    }
    public void GyroRotation(int target, double power){
        if(target > 360 || target < 0 || power < 0 || power > 1){
            throw new IllegalArgumentException();
        }
        while(true){
            int initial_heading = gyro.getHeading();
            int degree_rotation = target - initial_heading;
            if(degree_rotation < 0){
                degree_rotation = degree_rotation + 360;
            }
            if (degree_rotation < 180 && degree_rotation > 0) {
                while (initial_heading < target) {
                    turnright(power);
                }
            }
            if(degree_rotation > 180 && degree_rotation < 360){
                while(initial_heading > target){
                    turnleft(power);
                }
            }
            if(degree_rotation == 0 || degree_rotation == 360){
                break;
            }
        }
    }

    public void ExtendTouchServo()
    {
        touch_servo.setPosition(0);
    }

    public void RetractTouchServo()
    {
        touch_servo.setPosition(255);
    }
    public void DeAccelerator(double deacceleration_rate, double initial_speed, long time)
    {
        //input positive value less than one
    }
}
