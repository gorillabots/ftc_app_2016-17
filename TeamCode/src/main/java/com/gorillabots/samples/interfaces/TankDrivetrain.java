package com.gorillabots.samples.interfaces;

/**
 * Created by Chris on 10/1/2016.
 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * This class has not been developed, but still works because
 * every method that the OpMode expects it to have (as defined
 * in the interface) has been implemented
 */
class TankDrivetrain implements DrivetrainInterface {
    private Telemetry telemetry;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private GyroSensor gyroSensor;

    /**
     * This method is called a "Constructor" and is called whenever
     * "new TankDrivetrain(hardwareMap, telemetry)" is called
     * @param hardwareMap used to set up motors
     * @param telemetry for logging data
     * @throws InterruptedException allows Thread.sleep() to be used
     */
    TankDrivetrain(HardwareMap hardwareMap, Telemetry telemetry) throws InterruptedException {
        this.telemetry = telemetry;
        this.telemetry.addData("Drivetrain State","Initializing");
        this.telemetry.update();

        frontLeft = hardwareMap.dcMotor.get("motor1");
        frontRight = hardwareMap.dcMotor.get("motor2");
        backLeft = hardwareMap.dcMotor.get("motor3");
        backRight = hardwareMap.dcMotor.get("motor4");

        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        gyroSensor = hardwareMap.gyroSensor.get("gyro");
        gyroSensor.calibrate();
        while (gyroSensor.isCalibrating()) {
            Thread.sleep(500);
        }

        this.telemetry.addData("Drivetrain State","Initialized");
        this.telemetry.update();
    }

    @Override
    public void goForward(Double speed) {
        frontRight.setPower(speed);
        backRight.setPower(speed);
        frontLeft.setPower(speed);
        backLeft.setPower(speed);
    }

    @Override
    public void goBackward(Double speed) {

    }

    @Override
    public void rotate(Double angle) {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isFrontTouchingWall() {
        return false;
    }

    @Override
    public boolean isBeaconDetected() {
        return false;
    }

    @Override
    public boolean isOverWhiteLine() {
        return false;
    }

    @Override
    public boolean isOverCenterLine() {
        return false;
    }

    @Override
    public double getRearDistanceFromWall() {
        return 0;
    }

    @Override
    public double getFrontDistanceFromWall() {
        return 0;
    }

    @Override
    public double getGyroHeader() {
        return 0;
    }
}
