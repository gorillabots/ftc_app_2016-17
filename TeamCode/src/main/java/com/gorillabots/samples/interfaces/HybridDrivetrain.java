package com.gorillabots.samples.interfaces;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * This class is entirely different from the Tank drivetrain but because they both implement
 * the {@link DrivetrainInterface}, they can be used interchangeably in an OpMode
 */
class HybridDrivetrain implements DrivetrainInterface {
    private Telemetry telemetry;
    private HardwareMap hardwareMap;

    HybridDrivetrain(HardwareMap hardwareMap, Telemetry telemetry) throws InterruptedException {
        this.telemetry = telemetry;
        this.hardwareMap = hardwareMap;
    }

    @Override
    public void goForward(Double speed) { }

    @Override
    public void goBackward(Double speed) { }

    @Override
    public void rotate(Double angle) { }

    @Override
    public void stop() { }

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
