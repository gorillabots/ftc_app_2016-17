package com.gorillabots.components;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by csoper on 8/26/16.
 */
@SuppressWarnings("unused")
public class Robot {
    protected static Telemetry telemetry;

    private DrivetrainController drivetrainController;
    private Winglets winglets;
    private ColorSensor colorSensor;
    private double leftSideSpeed;
    private double rightSideSpeed;

    private double defaultDriveSpeed = 20.0;

    public Robot(OpMode opMode) {
        // Get variables from OpMode
        HardwareMap hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;

        // Set up Sub-Components
        drivetrainController = new DrivetrainController(opMode.hardwareMap);
        winglets = new Winglets(opMode.hardwareMap);
    }

    /**
     * Drive the robot forward
     * @param power Power level sent to the Motors
     * @throws InterruptedException
     */
    public void goForward(Double power) throws InterruptedException {
        drivetrainController.move(DrivetrainController.FORWARD, power);
    }

    /**
     * Drive the robot backwards
     * @param power Power level sent to the Motors
     * @throws InterruptedException
     */
    public void goBackward(Double power) throws InterruptedException  {
        drivetrainController.move(DrivetrainController.BACKWARD, power);
    }

    /**
     * Turn the robot Clockwise
     * @param power Power level sent to the Motors
     * @param degrees Degrees to rotate
     * @throws InterruptedException
     */
    public void turnClockwise(Double power, Double degrees) throws InterruptedException  {
        drivetrainController.move(DrivetrainController.LEFT, power);
        // TODO: Wait for robot to turn desired degrees
        drivetrainController.stop();
    }

    /**
     * Turn the robot Counter-Clockwise
     * @param power Power level sent to the Motors
     * @param degrees Degrees to rotate
     * @throws InterruptedException
     */
    public void turnCounterClockwise(Double power, Double degrees) throws InterruptedException  {
        drivetrainController.move(DrivetrainController.RIGHT, power);
        // TODO: Wait for robot to turn desired degrees
        drivetrainController.stop();
    }

    /**
     * Make the robot stop driving
     */
    public void stop() {
        drivetrainController.stop();
    }

    /**
     * Extend winglets to rescue climbers
     */
    public void extendWings() {
        winglets.extendWinglets();
    }

    /**
     * Retract winglets
     */
    public void retractWings() {
        winglets.retractWinglets();
    }

    /**
     * Extend GraceArm to prepare to release climbers
     */
    public void extendGraceArm() {
        // TODO: Set up GraceArm
    }

    /**
     * Release climbers
     */
    public void releaseClimbers() throws InterruptedException {
        // TODO: Set up corkscrew mechanism
        Thread.sleep(3000);
    }

    /**
     * Put away GraceArm after releasing climbers
     */
    public void retractGraceArm() {
        // TODO: Set up GraceArm
        // TODO: Turn off Corkscrew
    }

    public ColorSensor.COLOR checkFloorColor() {
        // TODO: Call ColorSensor.checkFloorColor();
        return ColorSensor.COLOR.BLUE;
    }

    public double getDistanceToWall() {
        // TODO: Set up sensors
        return 10.0;
    }
}
