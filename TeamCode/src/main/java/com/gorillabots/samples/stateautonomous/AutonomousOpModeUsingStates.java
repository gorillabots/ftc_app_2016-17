package com.gorillabots.samples.stateautonomous;

import com.gorillabots.samples.interfaces.DrivetrainInterface;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class AutonomousOpModeUsingStates extends LinearOpMode {
    private static final double CRUISE_SPEED = 50.0;
    private static final double CAREFUL_SPEED = 20.0;

    private DrivetrainInterface DRIVETRAIN;
    private STATE currentState;

    private enum STATE {
        WAITING_TO_START,
        DRIVING_TO_CENTER,
        ROTATING_TO_WALL,
        DRIVING_TO_WALL,
        STOPPED_AT_WALL,
    }

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("OpMode State", "Waiting to start");
        telemetry.update();

        waitForStart();
        currentState = STATE.DRIVING_TO_CENTER;
        while (opModeIsActive()) {
            switch (currentState)
            {
                case DRIVING_TO_CENTER:
                    if (DRIVETRAIN.getRearDistanceFromWall() < 65 || DRIVETRAIN.isOverCenterLine()) {
                        DRIVETRAIN.stop();
                        currentState = STATE.ROTATING_TO_WALL;
                    } else {
                        telemetry.addData("OpMode State", "Driving to Center");
                        DRIVETRAIN.goForward(CRUISE_SPEED);
                    }
                    break;
                case ROTATING_TO_WALL:
                    telemetry.addData("OpMode State", "Rotating to face beacon wall");
                    DRIVETRAIN.rotate(90.0);
                    currentState = STATE.DRIVING_TO_WALL;
                    break;
                case DRIVING_TO_WALL:
                    if (DRIVETRAIN.isFrontTouchingWall()) {
                        DRIVETRAIN.stop();
                        currentState = STATE.STOPPED_AT_WALL;
                    } else {
                        telemetry.addData("OpMode State", "Driving towards beacon wall");
                        DRIVETRAIN.goForward(CAREFUL_SPEED);
                    }
                    break;
                case STOPPED_AT_WALL:
                    telemetry.addData("OpMode State", "Stopped at beacon wall");

                default: {
                    telemetry.addData("OpMode State", "WARNING! Stopped Indefinitely in unknown state: "+currentState.toString());
                }
            }
            telemetry.update();
            idle();
        }
    }
}
