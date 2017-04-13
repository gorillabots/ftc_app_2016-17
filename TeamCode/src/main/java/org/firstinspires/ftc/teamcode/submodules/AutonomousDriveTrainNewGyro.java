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

    //Navx Constants
    private final double TARGET_ANGLE_DEGREES = 0.0;
    private final double TOLERANCE_DEGREES = 2.0;
    private final double MIN_MOTOR_OUTPUT_VALUE = -1.0;
    private final double MAX_MOTOR_OUTPUT_VALUE = 1.0;
    private final double YAW_PID_P = 0.005;
    private final double YAW_PID_I = 0.0;
    private final double YAW_PID_D = 0.0;

    public void init(LinearOpMode opMode) //Get hardware from hardwareMap
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

        wallTouch = opMode.hardwareMap.touchSensor.get("wallTouch");
    }

    public void stop()
    {
        navx.close();
    }

    public void resetGyro() //Define the current heading as 0 degrees
    {
        navx.zeroYaw();
    }

    public void forwards(double distance, double power) //Move forwards by distance
    {
        navXPIDController pidController = new navXPIDController(navx, navXPIDController.navXTimestampedDataSource.YAW);

        pidController.setSetpoint(TARGET_ANGLE_DEGREES);
        pidController.setContinuous(true);
        pidController.setOutputRange(MIN_MOTOR_OUTPUT_VALUE, MAX_MOTOR_OUTPUT_VALUE);
        pidController.setTolerance(navXPIDController.ToleranceType.ABSOLUTE, TOLERANCE_DEGREES);
        pidController.setPID(YAW_PID_P, YAW_PID_I, YAW_PID_D);
        pidController.enable(true);

        navXPIDController.PIDResult pidResult = new navXPIDController.PIDResult();

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

    public void backwards(double distance, double power) //Move backwards by distance
    {
        navXPIDController pidController = new navXPIDController(navx, navXPIDController.navXTimestampedDataSource.YAW);

        pidController.setSetpoint(TARGET_ANGLE_DEGREES);
        pidController.setContinuous(true);
        pidController.setOutputRange(MIN_MOTOR_OUTPUT_VALUE, MAX_MOTOR_OUTPUT_VALUE);
        pidController.setTolerance(navXPIDController.ToleranceType.ABSOLUTE, TOLERANCE_DEGREES);
        pidController.setPID(YAW_PID_P, YAW_PID_I, YAW_PID_D);
        pidController.enable(true);

        navXPIDController.PIDResult pidResult = new navXPIDController.PIDResult();

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

    public void right(double distance, double power) //Move right by distance
    {
        navXPIDController pidController = new navXPIDController(navx, navXPIDController.navXTimestampedDataSource.YAW);

        pidController.setSetpoint(TARGET_ANGLE_DEGREES);
        pidController.setContinuous(true);
        pidController.setOutputRange(MIN_MOTOR_OUTPUT_VALUE, MAX_MOTOR_OUTPUT_VALUE);
        pidController.setTolerance(navXPIDController.ToleranceType.ABSOLUTE, TOLERANCE_DEGREES);
        pidController.setPID(YAW_PID_P, YAW_PID_I, YAW_PID_D);
        pidController.enable(true);

        navXPIDController.PIDResult pidResult = new navXPIDController.PIDResult();

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
    }

    public void left(double distance, double power) //Move left by distance
    {
        navXPIDController pidController = new navXPIDController(navx, navXPIDController.navXTimestampedDataSource.YAW);

        pidController.setSetpoint(TARGET_ANGLE_DEGREES);
        pidController.setContinuous(true);
        pidController.setOutputRange(MIN_MOTOR_OUTPUT_VALUE, MAX_MOTOR_OUTPUT_VALUE);
        pidController.setTolerance(navXPIDController.ToleranceType.ABSOLUTE, TOLERANCE_DEGREES);
        pidController.setPID(YAW_PID_P, YAW_PID_I, YAW_PID_D);
        pidController.enable(true);

        navXPIDController.PIDResult pidResult = new navXPIDController.PIDResult();

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

                    frontRight.setPower(+pidOutput);
                    backRight.setPower(-power + pidOutput);
                    frontLeft.setPower(+power + pidOutput);
                    backLeft.setPower(-pidOutput);

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
    }

    public void frontRight(double distance, double power) //Move forwards-right by distance
    {
        navXPIDController pidController = new navXPIDController(navx, navXPIDController.navXTimestampedDataSource.YAW);

        pidController.setSetpoint(TARGET_ANGLE_DEGREES);
        pidController.setContinuous(true);
        pidController.setOutputRange(MIN_MOTOR_OUTPUT_VALUE, MAX_MOTOR_OUTPUT_VALUE);
        pidController.setTolerance(navXPIDController.ToleranceType.ABSOLUTE, TOLERANCE_DEGREES);
        pidController.setPID(YAW_PID_P, YAW_PID_I, YAW_PID_D);
        pidController.enable(true);

        navXPIDController.PIDResult pidResult = new navXPIDController.PIDResult();

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
        navXPIDController pidController = new navXPIDController(navx, navXPIDController.navXTimestampedDataSource.YAW);

        pidController.setSetpoint(TARGET_ANGLE_DEGREES);
        pidController.setContinuous(true);
        pidController.setOutputRange(MIN_MOTOR_OUTPUT_VALUE, MAX_MOTOR_OUTPUT_VALUE);
        pidController.setTolerance(navXPIDController.ToleranceType.ABSOLUTE, TOLERANCE_DEGREES);
        pidController.setPID(YAW_PID_P, YAW_PID_I, YAW_PID_D);
        pidController.enable(true);

        navXPIDController.PIDResult pidResult = new navXPIDController.PIDResult();

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
        navXPIDController pidController = new navXPIDController(navx, navXPIDController.navXTimestampedDataSource.YAW);

        pidController.setSetpoint(TARGET_ANGLE_DEGREES);
        pidController.setContinuous(true);
        pidController.setOutputRange(MIN_MOTOR_OUTPUT_VALUE, MAX_MOTOR_OUTPUT_VALUE);
        pidController.setTolerance(navXPIDController.ToleranceType.ABSOLUTE, TOLERANCE_DEGREES);
        pidController.setPID(YAW_PID_P, YAW_PID_I, YAW_PID_D);
        pidController.enable(true);

        navXPIDController.PIDResult pidResult = new navXPIDController.PIDResult();

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

                    frontRight.setPower(pidOutput);
                    backRight.setPower(-power + pidOutput);
                    frontLeft.setPower(+power + pidOutput);
                    backLeft.setPower(pidOutput);

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
        navXPIDController pidController = new navXPIDController(navx, navXPIDController.navXTimestampedDataSource.YAW);

        pidController.setSetpoint(TARGET_ANGLE_DEGREES);
        pidController.setContinuous(true);
        pidController.setOutputRange(MIN_MOTOR_OUTPUT_VALUE, MAX_MOTOR_OUTPUT_VALUE);
        pidController.setTolerance(navXPIDController.ToleranceType.ABSOLUTE, TOLERANCE_DEGREES);
        pidController.setPID(YAW_PID_P, YAW_PID_I, YAW_PID_D);
        pidController.enable(true);

        navXPIDController.PIDResult pidResult = new navXPIDController.PIDResult();

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

                    frontRight.setPower(+power + pidOutput);
                    backRight.setPower(pidOutput);
                    frontLeft.setPower(pidOutput);
                    backLeft.setPower(-power + pidOutput);

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
}
