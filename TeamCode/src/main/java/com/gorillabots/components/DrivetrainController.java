package com.gorillabots.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * @author csoper on 8/26/16.
 */
public class DrivetrainController {

    protected static final int FORWARD = 1;
    protected static final int BACKWARD = 2;
    protected static final int LEFT = 3;
    protected static final int RIGHT = 4;

    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;

    public DrivetrainController(HardwareMap hardwareMap) {
        frontRight = hardwareMap.dcMotor.get("motor1");
        frontLeft = hardwareMap.dcMotor.get("motor2");
        backRight = hardwareMap.dcMotor.get("motor3");
        backLeft = hardwareMap.dcMotor.get("motor4");
        setState("Initialized");

        stop();
    }

    public void move(int direction, double power) {
        switch (direction) {
            case FORWARD:
                frontRight.setPower(power);
                frontLeft.setPower(-power);
                backRight.setPower(power);
                backLeft.setPower(-power);
                setState("Driving Forward");
                break;
            case BACKWARD:
                frontRight.setPower(-power);
                frontLeft.setPower(power);
                backRight.setPower(-power);
                backLeft.setPower(power);
                setState("Driving Backward");
                break;
            case RIGHT:
                frontRight.setPower(power);
                frontLeft.setPower(power);
                backRight.setPower(power);
                backLeft.setPower(power);
                setState("Turning to the right");
                break;
            case LEFT:
                frontRight.setPower(-power);
                frontLeft.setPower(-power);
                backRight.setPower(-power);
                backLeft.setPower(-power);
                setState("Turning to the left");
                break;
        }
    }

    public void stop() {
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
        setState("Stopped");
    }

    private void setState(String state) {
        Robot.telemetry.addData("Drivetrain State", state);
        Robot.telemetry.update();
    }
}
