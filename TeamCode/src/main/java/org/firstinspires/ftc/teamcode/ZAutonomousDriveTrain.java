package org.firstinspires.ftc.teamcode;

/**
 * Created by emper on 12/22/2016.
 */

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.submodules.ColorHelper;

@Deprecated
@Disabled
@Autonomous(name="Autonomous Drive Train", group="concept")
public class ZAutonomousDriveTrain {
    LinearOpMode opMode;

    DcMotor frontRight, backRight, frontLeft, backLeft;

    TouchSensor wallTouch;

    ModernRoboticsI2cGyro gyro;
    Servo touch_servo;
    public void init(LinearOpMode opMode) {
        this.opMode = opMode;

        frontRight = opMode.hardwareMap.dcMotor.get("frontLeft"); //frontRight
        backRight = opMode.hardwareMap.dcMotor.get("frontRight"); //backRight
        frontLeft = opMode.hardwareMap.dcMotor.get("backLeft"); //frontLeft
        backLeft = opMode.hardwareMap.dcMotor.get("backRight"); //backLeft
        touch_servo = opMode.hardwareMap.servo.get("touchServo");
        wallTouch = opMode.hardwareMap.touchSensor.get("wallTouch");
        gyro = (ModernRoboticsI2cGyro) opMode.hardwareMap.gyroSensor.get("gyro");

        gyro.calibrate();

        try {
            // make sure the gyro is calibrated.
            while (gyro.isCalibrating()) {
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        gyro.resetZAxisIntegrator();


    }


    public void forwards(double meters, double power) {
        double target = getPosFB() + meters * Constants.STRAIGHT_INCREMENTS;

        frontRight.setPower(power);
        backRight.setPower(power);
        frontLeft.setPower(-power);
        backLeft.setPower(-power);


        while (getPosFB() < target && opMode.opModeIsActive()) {
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

    public void back(double meters, double power) {
        double target = getPosFB() - meters * Constants.STRAIGHT_INCREMENTS;

        frontRight.setPower(-power);
        backRight.setPower(-power);
        frontLeft.setPower(power);
        backLeft.setPower(power);


        while (getPosFB() > target && opMode.opModeIsActive()) {
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

    public void backToLine(ColorSensor floorColor, double power) {
        frontRight.setPower(-power);
        backRight.setPower(-power);
        frontLeft.setPower(power);
        backLeft.setPower(power);

        while (!ColorHelper.isFloorWhite(floorColor) && opMode.opModeIsActive()) {

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

    public void right(double meters, double power) {
        double target = getPosRL() + meters * Constants.STRAIGHT_INCREMENTS;
        frontRight.setPower(-power);
        backRight.setPower(power);
        frontLeft.setPower(-power);
        backLeft.setPower(power);
        while (getPosRL() < target && opMode.opModeIsActive()) {
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
    public void right_continuous(double power) {
        frontRight.setPower(-power);
        backRight.setPower(power);
        frontLeft.setPower(-power);
        backLeft.setPower(power);
    }
    public void rightToTouch(double power) {
        frontRight.setPower(-power);
        backRight.setPower(power);
        frontLeft.setPower(-power);
        backLeft.setPower(power);
        while (!wallTouch.isPressed() && opMode.opModeIsActive()) {
            opMode.telemetry.addData("Action", "Right to Touch");
            opMode.telemetry.update();
            opMode.sleep(5);
        }
        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void left(double meters, double power) {
        double target = getPosRL() - meters * Constants.STRAIGHT_INCREMENTS;

        frontRight.setPower(power);
        backRight.setPower(-power);
        frontLeft.setPower(power);
        backLeft.setPower(-power);


        while (getPosRL() > target && opMode.opModeIsActive()) {
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

    public void frontRight(double meters, double power) {
        double target = getPosBLFR() + meters * Constants.DIAGONAL_INCREMENTS;

        backRight.setPower(power);
        frontLeft.setPower(-power);

        while (getPosBLFR() < target && opMode.opModeIsActive()) {
            opMode.telemetry.addData("Action", "FrontRight");
            opMode.telemetry.addData("Currently", getPosBLFR());
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        backLeft.setPower(0);
        frontRight.setPower(0);
    }

    public void backRight(double meters, double power) {
        double target = getPosBRFL() + meters * Constants.DIAGONAL_INCREMENTS;

        backLeft.setPower(power);
        frontRight.setPower(-power);

        while (getPosBRFL() < target && opMode.opModeIsActive()) {
            opMode.telemetry.addData("Action", "BackRight");
            opMode.telemetry.addData("Currently", getPosBRFL());
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        backLeft.setPower(0);
        frontRight.setPower(0);
    }

    public void frontLeft(double meters, double power) {
        double target = getPosBRFL() - meters * Constants.DIAGONAL_INCREMENTS;

        backLeft.setPower(-power);
        frontRight.setPower(power);

        while (getPosBRFL() > target && opMode.opModeIsActive()) {
            opMode.telemetry.addData("Action", "FrontLeft");
            opMode.telemetry.addData("Currently", getPosBRFL());
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        backLeft.setPower(0);
        frontRight.setPower(0);
    }

    public void backLeft(double meters, double power) {
        double target = getPosBLFR() - meters * Constants.DIAGONAL_INCREMENTS;

        backRight.setPower(-power);
        frontLeft.setPower(power);

        while (getPosBLFR() > target && opMode.opModeIsActive()) {
            opMode.telemetry.addData("Action", "BackLeft");
            opMode.telemetry.addData("Currently", getPosBLFR());
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.update();
            opMode.sleep(5);
        }

        backLeft.setPower(0);
        frontRight.setPower(0);
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

    double getPosFB() {
        double sum = frontRight.getCurrentPosition() - frontLeft.getCurrentPosition() +
                backRight.getCurrentPosition() - backLeft.getCurrentPosition();
        return sum / 4;
    }

    double getPosRL() {
        double sum = -frontRight.getCurrentPosition() - frontLeft.getCurrentPosition() +
                backRight.getCurrentPosition() + backLeft.getCurrentPosition();
        return sum / 4;
    }

    double getPosBRFL() {
        return (backLeft.getCurrentPosition() - frontRight.getCurrentPosition()) / 2;
    }

    double getPosBLFR() {
        return (backRight.getCurrentPosition() - frontLeft.getCurrentPosition()) / 2;
    }

    double getRotation() {
        double sum = frontRight.getCurrentPosition() + backRight.getCurrentPosition() +
                frontLeft.getCurrentPosition() + backLeft.getCurrentPosition();
        return sum / 4;
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
    public void ExtendTouchServo(){
        touch_servo.setPosition(0);
    }
    public void RetractTouchServo(){
        touch_servo.setPosition(255);
    }
    public void DeAccelerator(double deacceleration_rate, double initial_speed, long time){
        //input positive value less than one

    }
}