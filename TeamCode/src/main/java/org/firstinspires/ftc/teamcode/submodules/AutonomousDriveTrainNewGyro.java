package org.firstinspires.ftc.teamcode.submodules;

import com.kauailabs.navx.ftc.AHRS;
import com.kauailabs.navx.ftc.navXPIDController;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Floorcolor;
import org.firstinspires.ftc.teamcode.TeamColors;

import java.util.concurrent.Callable;

//Created by Mikko on 10/14/16.

public class AutonomousDriveTrainNewGyro
{
    LinearOpMode opMode;
    Telemetry telemetry;

    //Motors
    DcMotor frontRight, backRight, frontLeft, backLeft;

    //Sensors
    ModernRoboticsI2cGyro gyro;
    TouchSensor wallTouch;

    //Navx gyro constants
    private final int NAVX_DIM_I2C_PORT = 5;
    private final byte NAVX_DEVICE_UPDATE_RATE_HZ = 50;
    int NAVX_TIMEOUT_MS = 5000;

    AHRS navx;
    navXPIDController pidController;
    navXPIDController.PIDResult pidResult;

    //Navx Constants
    private final double TOLERANCE_DEGREES = 2.0;
    private final double MIN_MOTOR_OUTPUT_VALUE = -1.0;
    private final double MAX_MOTOR_OUTPUT_VALUE = 1.0;
    private final double YAW_PID_P = 0.005;
    private final double YAW_PID_I = 0.0;
    private final double YAW_PID_D = 0.0;

    double offset;
    double offsetConverted;

    public void init(LinearOpMode opMode, double offset) //Get hardware from hardwareMap
    {
        this.opMode = opMode;
        telemetry = opMode.telemetry;

        //Motors
        frontRight = opMode.hardwareMap.dcMotor.get("frontLeft"); //frontRight
        backRight = opMode.hardwareMap.dcMotor.get("frontRight"); //backRight
        frontLeft = opMode.hardwareMap.dcMotor.get("backLeft"); //frontLeft
        backLeft = opMode.hardwareMap.dcMotor.get("backRight"); //backLeft

        //Sensors
        navx = AHRS.getInstance(opMode.hardwareMap.deviceInterfaceModule.get("dim"),
                NAVX_DIM_I2C_PORT,
                AHRS.DeviceDataType.kProcessedData,
                NAVX_DEVICE_UPDATE_RATE_HZ);

        while(navx.isCalibrating())
        {
            opMode.sleep(5);
        }

        pidController = new navXPIDController(navx, navXPIDController.navXTimestampedDataSource.YAW);
        pidController.setContinuous(true);
        pidController.setOutputRange(MIN_MOTOR_OUTPUT_VALUE, MAX_MOTOR_OUTPUT_VALUE);
        pidController.setTolerance(navXPIDController.ToleranceType.ABSOLUTE, TOLERANCE_DEGREES);
        pidController.setPID(YAW_PID_P, YAW_PID_I, YAW_PID_D);
        pidController.enable(true);

        pidResult = new navXPIDController.PIDResult();

        wallTouch = opMode.hardwareMap.touchSensor.get("wallTouch");

        this.offset = offset;
        offsetConverted = convertHeading(offset);
    }

    public void stop()
    {
        pidController.close();
        navx.close();

    }

    public void resetGyro() //Define the current heading as 0 degrees
    {
        navx.zeroYaw();
    }

    public void updateOffset(double offset)
    {
        this.offset = offset;
        offsetConverted = convertHeading(offset);
    }

    public void forwards(double distance, double power) //Move forwards by distance
    {
        pidController.setSetpoint(offsetConverted);

        double pos = getPosFB();

        double target = pos + distance * Constants.STRAIGHT_INCREMENTS;

        double pidOutput;

        try
        {
            while(pos < target && opMode.opModeIsActive())
            {
                if(pidController.waitForNewUpdate(pidResult, NAVX_TIMEOUT_MS))
                {
                    pidOutput = 0;

                    if(!pidController.isOnTarget())
                    {
                        pidOutput = pidResult.getOutput();
                    }

                    frontRight.setPower(+power + pidOutput);
                    backRight.setPower(+power + pidOutput);
                    frontLeft.setPower(-power + pidOutput);
                    backLeft.setPower(-power + pidOutput);

                    telemetry.addData("Status", "Forwards");
                    telemetry.addData("Position", pos);
                    telemetry.addData("Target", target);
                    telemetry.update();

                    opMode.sleep(5);

                    pos = getPosFB();
                }
            }

            frontRight.setPower(0);
            backRight.setPower(0);
            frontLeft.setPower(0);
            backLeft.setPower(0);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void forwardsToLine(ColorSensor floorColor, double power) //Move forwards to white line
    {
        pidController.setSetpoint(offsetConverted);

        double pidOutput;

        telemetry.addData("Status", "Moving to line");
        telemetry.update();

        try
        {
            while(!ColorHelper.isFloorWhiteTest(floorColor) && opMode.opModeIsActive())
            {
                boolean pidUpdated = pidController.waitForNewUpdate(pidResult, NAVX_TIMEOUT_MS);

                telemetry.addData("PID Updated", pidUpdated);
                telemetry.update();

                if(pidUpdated)
                {
                    pidOutput = 0;

                    if(!pidController.isOnTarget())
                    {
                        pidOutput = pidResult.getOutput();
                    }

                    frontRight.setPower(+power + pidOutput);
                    backRight.setPower(+power + pidOutput);
                    frontLeft.setPower(-power + pidOutput);
                    backLeft.setPower(-power + pidOutput);

                    telemetry.addData("Status", "ForwardsToLine");
                    telemetry.addData("ARGB", floorColor.argb());
                    telemetry.addData("R", floorColor.red());
                    telemetry.addData("G", floorColor.green());
                    telemetry.addData("B", floorColor.blue());
                    telemetry.update();

                    opMode.sleep(5);
                }
            }

            frontRight.setPower(0);
            backRight.setPower(0);
            frontLeft.setPower(0);
            backLeft.setPower(0);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void backwards(double distance, double power) //Move backwards by distance
    {
        pidController.setSetpoint(offsetConverted);

        double pos = getPosFB();

        double target = pos - distance * Constants.STRAIGHT_INCREMENTS;

        double pidOutput;

        try
        {
            while(pos > target && opMode.opModeIsActive())
            {
                if(pidController.waitForNewUpdate(pidResult, NAVX_TIMEOUT_MS))
                {
                    pidOutput = 0;

                    if(!pidController.isOnTarget())
                    {
                        pidOutput = pidResult.getOutput();
                    }

                    frontRight.setPower(-power + pidOutput);
                    backRight.setPower(-power + pidOutput);
                    frontLeft.setPower(+power + pidOutput);
                    backLeft.setPower(+power + pidOutput);

                    telemetry.addData("Status", "Backwards");
                    telemetry.addData("Position", pos);
                    telemetry.addData("Target", target);
                    telemetry.update();

                    opMode.sleep(5);

                    pos = getPosFB();
                }
            }

            frontRight.setPower(0);
            backRight.setPower(0);
            frontLeft.setPower(0);
            backLeft.setPower(0);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void backToLine(ColorSensor floorColor, double power) //Move backwards by distance
    {
        pidController.setSetpoint(offsetConverted);

        double pidOutput;

        try
        {
            while(!ColorHelper.isFloorWhiteTest(floorColor) && opMode.opModeIsActive())
            {
                if(pidController.waitForNewUpdate(pidResult, NAVX_TIMEOUT_MS))
                {
                    pidOutput = 0;

                    if(!pidController.isOnTarget())
                    {
                        pidOutput = pidResult.getOutput();
                    }

                    frontRight.setPower(-power + pidOutput);
                    backRight.setPower(-power + pidOutput);
                    frontLeft.setPower(+power + pidOutput);
                    backLeft.setPower(+power + pidOutput);

                    telemetry.addData("Status", "BackwardsToLine");

                    telemetry.update();

                    opMode.sleep(5);
                }
            }

            frontRight.setPower(0);
            backRight.setPower(0);
            frontLeft.setPower(0);
            backLeft.setPower(0);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void right(double distance, double power) //Move right by distance
    {
        pidController.setSetpoint(offsetConverted);

        double pos = getPosRL();

        double target = pos + distance * Constants.STRAIGHT_INCREMENTS;

        double pidOutput;

        try
        {
            while(pos < target && opMode.opModeIsActive())
            {
                if(pidController.waitForNewUpdate(pidResult, NAVX_TIMEOUT_MS))
                {
                    pidOutput = 0;

                    if(!pidController.isOnTarget())
                    {
                        pidOutput = pidResult.getOutput();
                    }

                    frontRight.setPower(-power + pidOutput);
                    backRight.setPower(+power + pidOutput);
                    frontLeft.setPower(-power + pidOutput);
                    backLeft.setPower(+power + pidOutput);

                    telemetry.addData("Status", "Right");
                    telemetry.addData("Position", pos);
                    telemetry.addData("Speed", power);
                    telemetry.addData("Target", target);
                    telemetry.addData("Gyro yaw", navx.getYaw());
                    telemetry.addData("PID Output", pidOutput);
                    telemetry.addData("Offset", offsetConverted);
                    //telemetry.addData("Heading", navx.getYaw());
                    //telemetry.addData("Target", offsetConverted);
                    telemetry.update();

                    opMode.sleep(5);

                    pos = getPosRL();
                }
            }

            frontRight.setPower(0);
            backRight.setPower(0);
            frontLeft.setPower(0);
            backLeft.setPower(0);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void left(double distance, double power) //Move left by distance
    {
        pidController.setSetpoint(offsetConverted);

        double pos = getPosRL();

        double target = pos - distance * Constants.STRAIGHT_INCREMENTS;

        double pidOutput;

        try
        {
            while(pos > target && opMode.opModeIsActive())
            {
                if(pidController.waitForNewUpdate(pidResult, NAVX_TIMEOUT_MS))
                {
                    pidOutput = 0;

                    if(!pidController.isOnTarget())
                    {
                        pidOutput = pidResult.getOutput();
                    }

                    frontRight.setPower(+power + pidOutput);
                    backRight.setPower(-power + pidOutput);
                    frontLeft.setPower(+power + pidOutput);
                    backLeft.setPower(-power + pidOutput);

                    telemetry.addData("Status", "Left");
                    telemetry.addData("Position", pos);
                    telemetry.addData("Target", target);
                    telemetry.update();

                    opMode.sleep(5);

                    pos = getPosRL();
                }
            }

            frontRight.setPower(0);
            backRight.setPower(0);
            frontLeft.setPower(0);
            backLeft.setPower(0);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        pidController.close();
    }

    public void frontRight(double distance, double power) //Move forwards-right by distance
    {
        pidController.setSetpoint(offsetConverted);

        double pos = getPosBLFR();

        double target = pos + distance * Constants.DIAGONAL_INCREMENTS;

        double pidOutput;

        try
        {
            while(pos < target && opMode.opModeIsActive())
            {
                if(pidController.waitForNewUpdate(pidResult, NAVX_TIMEOUT_MS))
                {
                    pidOutput = 0;

                    if(!pidController.isOnTarget())
                    {
                        pidOutput = pidResult.getOutput();
                    }

                    frontRight.setPower(pidOutput);
                    backRight.setPower(+power + pidOutput);
                    frontLeft.setPower(-power + pidOutput);
                    backLeft.setPower(pidOutput);

                    telemetry.addData("Status", "FrontRight");
                    telemetry.addData("Position", pos);
                    telemetry.addData("Target", target);
                    telemetry.update();

                    opMode.sleep(5);

                    pos = getPosBLFR();
                }
            }

            frontRight.setPower(0);
            backRight.setPower(0);
            frontLeft.setPower(0);
            backLeft.setPower(0);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void backRight(double distance, double power) //Move forwards by distance
    {
        pidController.setSetpoint(offsetConverted);

        double pos = getPosBRFL();

        double target = pos + distance * Constants.DIAGONAL_INCREMENTS;

        double pidOutput;

        try
        {
            while(pos < target && opMode.opModeIsActive())
            {
                if(pidController.waitForNewUpdate(pidResult, NAVX_TIMEOUT_MS))
                {
                    pidOutput = 0;

                    if(!pidController.isOnTarget())
                    {
                        pidOutput = pidResult.getOutput();
                    }

                    frontRight.setPower(-power + pidOutput);
                    backRight.setPower(pidOutput);
                    frontLeft.setPower(pidOutput);
                    backLeft.setPower(+power + pidOutput);

                    telemetry.addData("Status", "BackRight");
                    telemetry.addData("Position", pos);
                    telemetry.addData("Target", target);
                    telemetry.update();

                    opMode.sleep(5);

                    pos = getPosBRFL();
                }
            }

            frontRight.setPower(0);
            backRight.setPower(0);
            frontLeft.setPower(0);
            backLeft.setPower(0);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void frontLeft(double distance, double power) //Move forwards by distance
    {
        pidController.setSetpoint(offsetConverted);

        double pos = getPosBRFL();

        double target = pos - distance * Constants.DIAGONAL_INCREMENTS;

        double pidOutput;

        try
        {
            while(pos > target && opMode.opModeIsActive())
            {
                if(pidController.waitForNewUpdate(pidResult, NAVX_TIMEOUT_MS))
                {
                    pidOutput = 0;

                    if(!pidController.isOnTarget())
                    {
                        pidOutput = pidResult.getOutput();
                    }

                    frontRight.setPower(+power + pidOutput);
                    backRight.setPower(pidOutput);
                    frontLeft.setPower(pidOutput);
                    backLeft.setPower(-power + pidOutput);

                    telemetry.addData("Status", "FrontLeft");
                    telemetry.addData("Position", pos);
                    telemetry.addData("Target", target);
                    telemetry.update();

                    opMode.sleep(5);

                    pos = getPosBRFL();
                }
            }

            frontRight.setPower(0);
            backRight.setPower(0);
            frontLeft.setPower(0);
            backLeft.setPower(0);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void backLeft(double distance, double power) //Move forwards by distance
    {
        pidController.setSetpoint(offsetConverted);

        double pos = getPosBLFR();

        double target = pos - distance * Constants.DIAGONAL_INCREMENTS;

        double pidOutput;

        try
        {
            while(pos > target && opMode.opModeIsActive())
            {
                if(pidController.waitForNewUpdate(pidResult, NAVX_TIMEOUT_MS))
                {
                    pidOutput = 0;

                    if(!pidController.isOnTarget())
                    {
                        pidOutput = pidResult.getOutput();
                    }

                    frontRight.setPower(pidOutput);
                    backRight.setPower(-power + pidOutput);
                    frontLeft.setPower(+power + pidOutput);
                    backLeft.setPower(pidOutput);

                    telemetry.addData("Status", "BackLeft");
                    telemetry.addData("Position", pos);
                    telemetry.addData("Target", target);
                    telemetry.update();

                    opMode.sleep(5);

                    pos = getPosBLFR();
                }
            }

            frontRight.setPower(0);
            backRight.setPower(0);
            frontLeft.setPower(0);
            backLeft.setPower(0);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void turn(double target, double accuracy, double speed)
    {
        pidController.setSetpoint(convertHeading(offset + target));

        telemetry.addData("spot", 1);
        telemetry.update();

        double absoluteTarget = convertHeading(offset + target);

        try
        {
            while (!pidResult.isOnTarget() && opMode.opModeIsActive())
            {
                if (pidController.waitForNewUpdate(pidResult, 500))
                {
                    double power = pidResult.getOutput();

                    telemetry.addData("Status", "Turn");
                    telemetry.addData("Offset", offset);
                    telemetry.addData("Heading", navx.getYaw());
                    telemetry.addData("Target", target);
                    telemetry.addData("Absolute target", absoluteTarget);
                    telemetry.addData("Defined Speed", speed);
                    telemetry.addData("PID Speed", power);

                    telemetry.update();

                    if (power > -.15 && power < .15)
                    {
                        if (Math.abs(power) < .05)
                        {
                            break;
                        }
                        if (power > 0 && power < .15)
                        {
                            power = .17;
                        }
                        if (power < 0 && power > -.15)
                        {
                            power = -.17;
                        }
                    }

                    frontRight.setPower(power * speed);
                    backRight.setPower(power * speed);
                    frontLeft.setPower(power * speed);
                    backLeft.setPower(power * speed);

                    opMode.sleep(5);
                }
            }
        }
        catch (InterruptedException ex)
        {

        }

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);

        offset += target;
        offsetConverted = convertHeading(offset);
    }

    public void goToDistance(ModernRoboticsI2cRangeSensor range, double target, double accuracy, double power) //Go to distance using range sensor
    {
        pidController.setSetpoint(offsetConverted);

        double distance = range.cmUltrasonic();

        double pidOutput;

        try
        {
            while(!inRange(target, accuracy, distance) && opMode.opModeIsActive())
            {
                if(pidController.waitForNewUpdate(pidResult, NAVX_TIMEOUT_MS))
                {
                    pidOutput = 0;

                    if(!pidController.isOnTarget())
                    {
                        pidOutput = pidResult.getOutput();
                    }

                    telemetry.addData("Status", "GoToDistance");
                    telemetry.addData("Distance", distance);
                    telemetry.addData("Target", target);

                    if(distance > target) //Too far, move right
                    {
                        frontRight.setPower(-power + pidOutput);
                        backRight.setPower(+power + pidOutput);
                        frontLeft.setPower(-power + pidOutput);
                        backLeft.setPower(+power + pidOutput);

                        telemetry.addData("Going", "Right");
                    }
                    else //Too close, move left
                    {
                        frontRight.setPower(+power + pidOutput);
                        backRight.setPower(-power + pidOutput);
                        frontLeft.setPower(+power + pidOutput);
                        backLeft.setPower(-power + pidOutput);

                        telemetry.addData("Going", "Left");
                    }

                    telemetry.update();

                    opMode.sleep(5);

                    distance = range.cmUltrasonic();
                }
            }

            frontRight.setPower(0);
            backRight.setPower(0);
            frontLeft.setPower(0);
            backLeft.setPower(0);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    boolean lastPressLeft;

    public void beaconResponse(TeamColors desiredColor, ColorSensor sensorL, ColorSensor sensorR) {
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

        if (desiredColor == TeamColors.RED) {
            //On TeamColors.RED side
            if (colorL == TeamColors.RED && colorR == TeamColors.BLUE) //If pressing left is necessary
            {
                pressLeft();
            } else if (colorL == TeamColors.BLUE && colorR == TeamColors.RED) //If pressing right is necessary
            {
                pressRight();
            } else if (colorL == TeamColors.RED && colorR == TeamColors.RED) //If both are TeamColors.RED, do nothing
            {
                //See, nothing!
            } else if (colorL == TeamColors.BLUE && colorR == TeamColors.BLUE) //If both are blue, hit any (right is closest)
            {
                pressRight();
            } else {
                //If any are indecisive, do nothing to be safe
            }
        }

        if (desiredColor == TeamColors.BLUE) {
            if (colorL == TeamColors.BLUE && colorR == TeamColors.RED) //If pressing left is necessary
            {
                pressLeft();
            } else if (colorL == TeamColors.RED && colorR == TeamColors.BLUE) //If pressing right is necessary
            {
                pressRight();
            } else if (colorL == TeamColors.BLUE && colorR == TeamColors.BLUE) //If both are blue, do nothing
            {
                //See, nothing!
            } else if (colorL == TeamColors.RED && colorR == TeamColors.RED) //If both are TeamColors.RED, hit any (right is closest)
            {
                pressRight();
            } else {
                //If any are indecisive, do nothing to be safe
            }
        }
    }

    private String enumToString(TeamColors color) {
        switch (color) {
            case RED:
                return "RED";
            case BLUE:
                return "BLUE";
            case INDECISIVE:
                return "INDECISIVE";

        }

        return "???";
    }

    private void pressLeft() {
        forwards(0.15, 0.3); //Align mashy spike plate
        right(0.2, 0.5);
        forwards(0.02, 0.2);
        backwards(0.02, 0.2);
        //Mash mashy spike plate into left button
        left(0.2, 0.5); //Back away

        lastPressLeft = true;
    }

    private void pressRight() {
        right(0.2, 0.5); //Mash mashy spike plate into left button
        forwards(0.02, 0.2);
        backwards(0.02, 0.2);
        left(0.2, 0.5); //Back away
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

    double convertHeading(double in) //0-360
    {
        if(in < 0) //If it is negative
        {
            in += 720; //Add 720 to make sure it is positive, which will be mostly removed by the mod
        }

        in %= 360; //Modulus

        if(in > 180)
        {
            return in - 180;
        }
        else
        {
            return in;
        }
    }

    boolean inRange(double target, double accuracy, double reading)
    {
        return (Math.abs(target - reading) < accuracy); //If abs of difference is less than accuracy, we are in range
    }
}
