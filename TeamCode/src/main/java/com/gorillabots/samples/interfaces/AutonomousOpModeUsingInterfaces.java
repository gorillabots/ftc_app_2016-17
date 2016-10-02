package com.gorillabots.samples.interfaces;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * This OpMode demonstrates how to take advantages of
 * interfaces, and build an autonomous mode before any
 * of the actual implementation classes have been written
 */
@Autonomous(name = "Climber Deposit OpMode", group = "Non-Working Samples" )
@Disabled
public class AutonomousOpModeUsingInterfaces extends LinearOpMode {

    // Define variables that will be used throughout the entire OpMode
    private DrivetrainInterface DRIVETRAIN;
    private ClimberDepositInterface CLIMBERDEPOSITOR;

    /**
     * runOpMode() is defined as an 'Abstract Method' in the LinearOpMode class
     * that we extend.  This means is that we MUST implement this method
     * ourselves for the code to compile
     *
     * Specifically, the runOpMode() is called when the init button
     * is pressed on the driver station
     */
    @Override
    public void runOpMode() throws InterruptedException {
        _init();

        telemetry.addData("OpMode State", "Waiting to start");
        telemetry.update();

        waitForStart();

        /*
         * The while() statement is evaluated every time the loop starts over.
         *
         *          !!! DANGER DANGER DANGER DANGER !!!
         * This IS NOT a good autonomous OpMode and is VERY DANGEROUS!
         * The while loop only executes once, which means that even if the
         * stop button is pressed, it will not have any affect until the routine ends
         *
         * Ideally some sort of states with a switch-case statement would be used here
         */
        while (opModeIsActive()) {
            telemetry.addData("OpMode State", "Driving away from start");
            telemetry.update();
            while (DRIVETRAIN.getRearDistanceFromWall() < 65 || DRIVETRAIN.isOverCenterLine()) {
                DRIVETRAIN.goForward(50.0);
                idle();
            }
            DRIVETRAIN.stop();

            telemetry.addData("OpMode State", "Turning towards blue beacon wall");
            telemetry.update();
            DRIVETRAIN.rotate(-90.0);

            telemetry.addData("OpMode State", "Driving towards beacon wall");
            telemetry.update();
            while (!DRIVETRAIN.isFrontTouchingWall()) {
                DRIVETRAIN.goForward(50.0);
                idle();
            }
            DRIVETRAIN.stop();

            telemetry.addData("OpMode State", "Turning parallel to beacon wall");
            telemetry.update();
            DRIVETRAIN.rotate(90.0);


            telemetry.addData("OpMode State", "Driving along wall towards beacon");
            telemetry.update();
            while (!DRIVETRAIN.isOverWhiteLine()) {
                DRIVETRAIN.goForward(25.0);
                idle();
            }
            DRIVETRAIN.stop();

            telemetry.addData("OpMode State", "Rotating to face beacon");
            telemetry.update();
            DRIVETRAIN.rotate(-90.0);

            telemetry.addData("OpMode State", "Extending arm to deposit climbers");
            telemetry.update();
            while (!CLIMBERDEPOSITOR.isReadyToDepositClimbers()) {
                CLIMBERDEPOSITOR.prepareToDepositClimbers();
            }

            telemetry.addData("OpMode State", "Depositing climbers");
            telemetry.update();
            CLIMBERDEPOSITOR.depositClimbers();
            sleep(10000);
            CLIMBERDEPOSITOR.stopDepossitingClimbers();

            telemetry.addData("OpMode State", "Resetting position after climber depositing");
            telemetry.update();
            CLIMBERDEPOSITOR.resetPositionToStart();
        }
    }

    /**
     * This method sets up everything that will be needed in this OpMode.
     * We want to call this method after the init button has been pressed
     * on the driver station but before the play button is pressed
     * (before "waitForStart()" is called)
     */
    private void _init() throws InterruptedException {
        telemetry.addData("OpMode State", "Initializing");
        telemetry.update();

        /*
         * Either of the two commented lines below will work, because they
         * both implement DrivetrainInterface
         *
         * DRIVETRAIN = new HybridDrivetrain(hardwareMap, telemetry);
         * DRIVETRAIN = new TankDrivetrain(hardwareMap, telemetry);
         */
        DRIVETRAIN = new TankDrivetrain(hardwareMap, telemetry);
        CLIMBERDEPOSITOR = new GraceArmClimberDeposit(this);

        telemetry.addData("OpMode State", "Initialized");
        telemetry.update();
    }
}