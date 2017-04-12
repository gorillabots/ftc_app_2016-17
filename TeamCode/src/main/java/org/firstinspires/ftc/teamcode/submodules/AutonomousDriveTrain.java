package org.firstinspires.ftc.teamcode.submodules;

import com.kauailabs.navx.ftc.AHRS;
import com.kauailabs.navx.ftc.navXPIDController;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.submodules.ColorHelper;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.TeamColors;

//Created by Mikko on 10/14/16.

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
 *  turnToGyroAny - turn to any number of degrees
 *
 *  GyroRotation() - Depricated - turn to any number of degrees
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
    private final int NAVX_DIM_I2C_PORT = 5;
    private final byte NAVX_DEVICE_UPDATE_RATE_HZ = 50;

    AHRS navx;
    public void init(LinearOpMode opMode) //Get hardware from hardwareMap
    {
        telemetry = opMode.telemetry;
        this.opMode = opMode;
        telemetry.addData("state", "drive init");
        telemetry.update();
        //Motors
        frontRight = opMode.hardwareMap.dcMotor.get("frontLeft"); //frontRight
        backRight = opMode.hardwareMap.dcMotor.get("frontRight"); //backRight
        frontLeft = opMode.hardwareMap.dcMotor.get("backLeft"); //frontLeft
        backLeft = opMode.hardwareMap.dcMotor.get("backRight"); //backLeft

        telemetry.addData("state", "motors init");
        telemetry.update();
        //Link about DC Motors / Encoders: https://ftc-tricks.com/dc-motors/

        frontRight.setMode(RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(RunMode.RUN_USING_ENCODER);
        backRight.setMode(RunMode.RUN_USING_ENCODER);
        backLeft.setMode(RunMode.RUN_USING_ENCODER);

        telemetry.addData("state", "encoders init");
        telemetry.update();
        //touchServo = opMode.hardwareMap.servo.get("servoSwing");
        wallTouch = opMode.hardwareMap.touchSensor.get("wallTouch");
        navx = AHRS.getInstance(opMode.hardwareMap.deviceInterfaceModule.get("dim"),
                NAVX_DIM_I2C_PORT,
                AHRS.DeviceDataType.kProcessedData,
                NAVX_DEVICE_UPDATE_RATE_HZ);

        telemetry.addData("state", "gyro init");
        telemetry.update();
        while(navx.isCalibrating())
        {
            try
            {
                Thread.sleep(5);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void resetGyro() //Define the current heading as 0 degrees
    {
        navx.zeroYaw();
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

        double heading;
        double turnpow;

        while(pos < target && opMode.opModeIsActive())
        {
            heading = navx.getYaw();

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

    @Deprecated //Use forwardsGyroToLine()
    public void forwardsToLine(ColorSensor floorColor, double power) //Move forwards to line
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

    /*
     * Same as forwardsToLine(ColorSensor, double), although this method utilizes the gyro sensor for motor inaccuracy compensation, similar to forwardsGyro(double, double, int, double)
     * @param floorColor color sensor to be used to detect white line
     * @param power relative speed of movement of robot
     * @param accuracy gyro sensor heading margin of error that is allowed
     * @param turnpower speed change applied to motors when compensating
     * @see #forwardsToLine(ColorSensor, double)
     */
    public void forwardsGyroToLine(ColorSensor floorColor, double power, int accuracy, double turnpower) //Move forward to line using gyro
    {
        double heading;
        double turnpow;

        while(!ColorHelper.isFloorWhiteTest(floorColor) && opMode.opModeIsActive())
        {
            heading = navx.getYaw();

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

    public void forwardsGyroToLineTimeout(ColorSensor floorColor, double power, int accuracy, double turnpower, double timeout) //Move forward to line using gyro with timeout
    {
        double heading;
        double turnpow;

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (int)(timeout * 1000);

        while(!ColorHelper.isFloorWhiteTest(floorColor) && opMode.opModeIsActive() && System.currentTimeMillis() < endTime)
        {
            heading = navx.getYaw();

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

    /**
     * Move robot backwards(relative to sensors on front of robot, it is moving right)
     * @param meters distance that the robot moves using encoders
     * @param power relative speed of robot in this direction
     */
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

    /**
     * Moves "back" using gyro sensor to compensate for motor inaccuracies
     * @param meters How far the robot moves
     * @param power Relative speed of movement
     * @param accuracy Margin of error allowed with regard to gyro sensor heading
     * @param turnpower Speed change applied to motors when compensating inaccurate movement
     * @see #back(double, double)
     */
    public void backGyro(double meters, double power, int accuracy, double turnpower) //Move backwards using gyro
    {
        double pos = getPosFB();
        double target = pos - meters * Constants.STRAIGHT_INCREMENTS;

        double heading;
        double turnpow;

        while(getPosFB() > target && opMode.opModeIsActive())
        {
            heading = navx.getYaw();

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

    public void backToLine(ColorSensor floorColor, double power) //Move back to line using gyro
    {
        frontRight.setPower(-power);
        backRight.setPower(-power);
        frontLeft.setPower(power);
        backLeft.setPower(power);

        while(!ColorHelper.isFloorWhiteTest(floorColor) && opMode.opModeIsActive())
        {
            opMode.telemetry.addData("Action", "Back To Line");
            opMode.telemetry.addData("Color", ColorHelper.getFloorColor(floorColor));
            opMode.telemetry.addData("Line", ColorHelper.isFloorWhite(floorColor));
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    /**
     * This method moves the robot "back," utilizing the gyro sensor for motor inaccuracy compensation while approaching the line, similar to backGyro(double, double, int, double)
     * @param floorColor color sensor to be used to detect white line
     * @param power relative speed of movement of robot
     * @param accuracy gyro sensor heading margin of error that is allowed
     * @param turnpower speed change applied to motors when compensating
     */
    public void backGyroToLine(ColorSensor floorColor, double power, int accuracy, double turnpower) //Move back to line using gyro
    {
        double heading;
        double turnpow;

        while(!ColorHelper.isFloorWhiteTest(floorColor) && opMode.opModeIsActive())
        {
            heading = navx.getYaw();

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

    public void backGyroToLineTimeout(ColorSensor floorColor, double power, int accuracy, double turnpower, double timeout) //Move back to line using gyro with timeout
    {
        double heading;
        double turnpow;

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (int)(timeout * 1000);

        while(!ColorHelper.isFloorWhiteTest(floorColor) && opMode.opModeIsActive() && System.currentTimeMillis() < endTime)
        {
            heading = navx.getYaw();

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

    /**This method does the exact same thing as backGyroToLine(ColorSensor, double, int, double), although this one will give an initial headstart going backwards
     * @param floorColor color sensor to be used to detect white line
     * @param power relative speed of movement of robot
     * @param accuracy gyro sensor heading margin of error that is allowed
     * @param turnpower speed change applied to motors when compensating
     * @see #backGyroToLine(ColorSensor, double, int, double)
     */
    @Deprecated
    public void backGyroToLineDelay(ColorSensor floorColor, double power, int accuracy, double turnpower) //Move back to line using gyro
    {
        double heading;
        double turnpow;
        back(.2, .4);
        while(!ColorHelper.isFloorWhite(floorColor) && opMode.opModeIsActive())
        {
            heading = navx.getYaw();

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
    /**
     * Move robot right(relative to sensors on front of robot, it is moving forwards)
     * @param meters distance that the robot moves using encoders
     * @param power relative speed of robot in this direction
     */
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
    /**
     * Moves "right" using gyro sensor to compensate for motor inaccuracies
     * @param meters How far the robot moves
     * @param power Relative speed of movement
     * @param accuracy Margin of error allowed with regard to gyro sensor heading
     * @param turnpower Speed change applied to motors when compensating inaccurate movement
     * @see #right(double, double)
     */
    public void rightGyro(double meters, double power, int accuracy, double turnpower) //Move right by distance using gyro
    {
        double pos = getPosRL();
        double target = pos + meters * Constants.STRAIGHT_INCREMENTS;

        double heading;
        double turnpow;

        while(pos < target && opMode.opModeIsActive())
        {
            heading = navx.getYaw();

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
    /**
     * This method moves the robot "right" in approaching the white line, utilizing the gyro sensor for motor inaccuracy compensation, similar to rightGyro(double, double, int, double)
     * @param floorColor color sensor to be used to detect white line
     * @param power relative speed of movement of robot
     * @param accuracy gyro sensor heading margin of error that is allowed
     * @param turnpower speed change applied to motors when compensating
     */
    public void rightGyroToLine(ColorSensor floorColor, double power, int accuracy, double turnpower) //Move right to line using gyro
    {
        double heading;
        double turnpow;

        while(!ColorHelper.isFloorWhite(floorColor) && opMode.opModeIsActive())
        {
            heading = navx.getYaw();

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

    /**This method moves "right" continuously until the touch sensor is activated
     * @param power relative speed at which the robot moves
     * @param accuracy gyro sensor margin of error allowed without compensation
     * @param turnpower speed change applied to motors while compensating
     */
    public void rightGyroToTouch(double power, int accuracy, double turnpower) //Move right to touch using gyro
    {
        double heading;
        double turnpow;

        while(!wallTouch.isPressed() && opMode.opModeIsActive())
        {
            heading = navx.getYaw();

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
    /**
     * Move robot left(relative to sensors on front of robot, it is moving backwards)
     * @param meters distance that the robot moves using encoders
     * @param power relative speed of robot in this direction
     */
    public void left(double meters, double power) //Move left by distance
    {
        //opMode.telemetry.addData("Starting left", 42);
        //opMode.telemetry.update();

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
    /**
     * Moves "left" using gyro sensor to compensate for motor inaccuracies
     * @param meters How far the robot moves
     * @param power Relative speed of movement
     * @param accuracy Margin of error allowed with regard to gyro sensor heading
     * @param turnpower Speed change applied to motors when compensating inaccurate movement
     * @see #left(double, double)
     */
    public void leftGyro(double meters, double power, int accuracy, double turnpower) //Move left by distance using gyro
    {
        double pos = getPosRL();
        double target = pos - meters * Constants.STRAIGHT_INCREMENTS;

        double heading;
        double turnpow;

        while(pos > target && opMode.opModeIsActive())
        {
            heading = navx.getYaw();

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

        double heading;
        double turnpow;

        while(pos < target && opMode.opModeIsActive())
        {
            heading = navx.getYaw();

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

    public void backRight(double meters, double power) //Move back-right by distance
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

    public void backRightGyro(double meters, double power, int accuracy, double turnpower) //Move back-right by distance using gyro
    {
        double pos = getPosBRFL();
        double target = pos + meters * Constants.DIAGONAL_INCREMENTS;

        double heading;
        double turnpow;

        while (pos < target && opMode.opModeIsActive())
        {
            heading = navx.getYaw();

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

    public void frontLeft(double meters, double power) //Move forward-left by distance
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

    public void frontLeftGyro(double meters, double power, int accuracy, double turnpower) //Move forward-left by distance using gyro
    {
        double pos = getPosBRFL();
        double target = pos - meters * Constants.DIAGONAL_INCREMENTS;

        double heading;
        double turnpow;

        while(pos > target && opMode.opModeIsActive())
        {
            heading = navx.getYaw();

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

    public void backLeft(double meters, double power) //Move back-left by distance
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

    public void backLeftGyro(double meters, double power, int accuracy, double turnpower) //Move back-left by distance using gyro
    {
        double pos = getPosBLFR();
        double target = pos - meters * Constants.DIAGONAL_INCREMENTS;

        double heading;
        double turnpow;

        while(pos > target && opMode.opModeIsActive())
        {
            heading = navx.getYaw();

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

    public void turnToGyro(int accuracy, double turnpower) //Turn until we are aligned
    {
        double heading;
        double turnpow;

        while(opMode.opModeIsActive())
        {
            heading = navx.getYaw();

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

    @Deprecated //Unused
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

    @Deprecated //Unused and non-functional
    public void GyroRotation(int target, double power)
    {
        if(target > 360 || target < 0 || power < 0 || power > 1)
        {
            throw new IllegalArgumentException();
        }

        while(true)
        {
            double initial_heading = navx.getYaw(); //TODO: Potential memory leak!
            double degree_rotation = target - initial_heading;
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

    public void turnToGyroAny(int target, double speed, int accuracy)
    {
        double heading = Math.abs(navx.getYaw());

        int pivot;

        if(target < 180) //0 - 179
        {
            pivot = target + 180;
        }
        else //180 - 359
        {
            pivot = target - 180;
        }

        double power;

        //     Less than minimum           or More than maximum            and is running
        while((heading < target - accuracy || heading > target + accuracy) && opMode.opModeIsActive()) //Not acceptable
        {
            //PSA: Clockwise: -, Counter: +

            opMode.telemetry.addData("Action", "Turn to Gyro Any");
            opMode.telemetry.addData("Heading", heading);

            telemetry.addData("compass",navx.getCompassHeading() );

            if(target < 180)
            {
                if(heading > target && heading <= pivot) //Inside of range to subtract
                {
                    opMode.telemetry.addData("Condition", "A- : Clockwise");
                    power = -speed;
                }
                else //Inside of range to add
                {
                    opMode.telemetry.addData("Condition", "A+ : Counter");
                    power = speed;
                }
            }
            else //target >= 180
            {
                if(heading > pivot && heading < target) //Inside of range to add
                {
                    opMode.telemetry.addData("Condition", "B+ : Counter");
                    power = speed;
                }
                else //Inside of range to subtract
                {
                    opMode.telemetry.addData("Condition", "B- : Clockwise");
                    power = -speed;
                }
            }

            opMode.telemetry.update();

            frontRight.setPower(power);
            backRight.setPower(power);
            frontLeft.setPower(power);
            backLeft.setPower(power);

            opMode.sleep(50);

            heading = Math.abs(navx.getYaw());
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    final double MIN_MOTOR_OUTPUT_VALUE = -1.0;
    final double MAX_MOTOR_OUTPUT_VALUE = 1.0;
    final double YAW_PID_P = 0.005;
    final double YAW_PID_I = 0.0;
    final double YAW_PID_D = 0.0;
    
    public void turnNewGyro(double target, double accuracy, double speed)
    {
        navXPIDController pidController = new navXPIDController(navx, navXPIDController.navXTimestampedDataSource.YAW);

        pidController.setSetpoint(target);
        pidController.setContinuous(true);
        pidController.setOutputRange(MIN_MOTOR_OUTPUT_VALUE, MAX_MOTOR_OUTPUT_VALUE);
        pidController.setTolerance(navXPIDController.ToleranceType.ABSOLUTE, accuracy);
        pidController.setPID(YAW_PID_P, YAW_PID_I, YAW_PID_D);

        pidController.enable(true);

        navXPIDController.PIDResult pidResult = new navXPIDController.PIDResult();

        while(!pidResult.isOnTarget() && opMode.opModeIsActive())
        {
            double power = pidResult.getOutput();

            frontRight.setPower(power * speed);
            backRight.setPower(power * speed);
            frontLeft.setPower(power * speed);
            backLeft.setPower(power * speed);

            opMode.sleep(5);
            pidResult = new navXPIDController.PIDResult();
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
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

    public void goToDistanceGyro(ModernRoboticsI2cRangeSensor rangeSensor, double target, double accuracyLin, double powerLin, double accuracyRot, double powerRot)
    {
        double min = target - accuracyLin;
        double max = target + accuracyLin;

        double range = rangeSensor.cmUltrasonic();

        double heading;
        double turnpow;

        while((range < min || range > max) && opMode.opModeIsActive())
        {
            heading = navx.getYaw();

            if (heading <= accuracyRot || heading >= 360 - accuracyRot)
            {
                turnpow = 0;
            }
            else if (heading <= 180)
            {
                turnpow = -powerRot;
            }
            else
            {
                turnpow = powerRot;
            }

            if(range > target)
            {
                frontRight.setPower(-powerLin + turnpow);
                backRight.setPower(powerLin + turnpow);
                frontLeft.setPower(-powerLin + turnpow);
                backLeft.setPower(powerLin + turnpow);
            }
            else
            {
                frontRight.setPower(powerLin + turnpow);
                backRight.setPower(-powerLin + turnpow);
                frontLeft.setPower(powerLin + turnpow);
                backLeft.setPower(-powerLin + turnpow);
            }

            opMode.telemetry.addData("Action", "GoToDistanceGyro");
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.addData("Linear Accuracy", accuracyLin);
            opMode.telemetry.addData("Current", range);

            opMode.sleep(5);

            range = rangeSensor.cmUltrasonic();
        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public boolean lastPressLeft;

    public void beaconResponse(TeamColors desiredColor, ColorSensor sensorL, ColorSensor sensorR)
    {
        //sensorL is left color sensor
        //sensorR is right color sensor

        sensorL.enableLed(false);
        sensorR.enableLed(false);
        
        TeamColors colorL = ColorHelper.getBeaconColorTest(sensorL);
        TeamColors colorR = ColorHelper.getBeaconColorTest(sensorR);

        telemetry.addData("l-r", sensorL.red());
        telemetry.addData("l-b", sensorL.blue());
        telemetry.addData("l-c", enumToString(colorL));
        telemetry.addData("r-r", sensorR.red());
        telemetry.addData("r-b", sensorR.blue());
        telemetry.addData("r-c", enumToString(colorR));
        telemetry.update();
        //opMode.sleep(1000);

        lastPressLeft = false;

        if(desiredColor == TeamColors.RED)
        {
            //On TeamColors.RED side
            if(colorL == TeamColors.RED && colorR == TeamColors.BLUE) //If pressing left is necessary
            {
                pressLeft();
            }
            else if(colorL == TeamColors.BLUE && colorR == TeamColors.RED) //If pressing right is necessary
            {
                pressRight();
            }
            else if(colorL == TeamColors.RED && colorR == TeamColors.RED) //If both are TeamColors.RED, do nothing
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
            if(colorL == TeamColors.BLUE && colorR == TeamColors.RED) //If pressing left is necessary
            {
                pressLeft();
            }
            else if(colorL == TeamColors.RED && colorR == TeamColors.BLUE) //If pressing right is necessary
            {
                pressRight();
            }
            else if(colorL == TeamColors.BLUE && colorR == TeamColors.BLUE) //If both are blue, do nothing
            {
                //See, nothing!
            }
            else if(colorL == TeamColors.RED && colorR == TeamColors.RED) //If both are TeamColors.RED, hit any (right is closest)
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
        right(0.2, 0.5);
        forwards (0.02, 0.2);
        back(0.02, 0.2);
        //Mash mashy spike plate into left button
        left(0.2, 0.5); //Back away

        lastPressLeft = true;
    }

    private void pressRight()
    {
        right(0.2, 0.5); //Mash mashy spike plate into left button
        forwards(0.02, 0.2);
        back(0.02, 0.2);
        left(0.2, 0.5); //Back away
    }
}
