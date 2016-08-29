package org.firstinspires.ftc.teamcode;

import com.gorillabots.components.ColorSensor;
import com.gorillabots.components.Robot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is a sample OpMode to demonstrate an OOP Robot in an autonomous routine
 * @author csoper on 8/28/16.
 */
@Autonomous(name = "Sample Autonomous Routine", group = "Samples")
public class SampleAutonomousMode extends LinearOpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    private STAGE stage = STAGE.WALL_TO_CENTER;

    public void runOpMode() throws InterruptedException {
        logState("Waiting to start");
        Robot robot = new Robot(this);

        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {
            switch (stage) {
                case WALL_TO_CENTER:
                    if (robot.checkFloorColor() == ColorSensor.COLOR.BLUE) {
                        logState("At Center Line");
                        robot.stop();
                        stage = STAGE.ROTATE;
                    } else {
                        logState("Driving toward center line");
                        robot.goForward(20.0);
                    }
                    break;
                case ROTATE:
                    logState("Rotating towards beacon");
                    robot.turnClockwise(10.0, 90.0);
                    robot.stop();
                    stage = STAGE.CENTER_TO_BEACON;
                    break;
                case CENTER_TO_BEACON:
                    if (robot.getDistanceToWall() <= 10.0) {
                        logState("Stopped at beacon");
                        robot.stop();
                        stage = STAGE.OPMODE_COMPLETE;
                    } else {
                        logState("Driving toward beacon");
                        robot.goForward(20.0);
                    }
                    break;
                case OPMODE_COMPLETE:
                    logState("OpMode Completed");
                    break;
            }
            telemetry.addData("Current Runtime", runtime.milliseconds() + "Milliseconds");
            telemetry.update();
            idle();
        }
    }

    /**
     * Autonomous routine stages
     */
    private enum STAGE {
        WALL_TO_CENTER, ROTATE, CENTER_TO_BEACON, OPMODE_COMPLETE
    }

    /**
     * Shortcut for adding OpMode State telemetry data
     * @param msg OpMode State Description
     */
    private void logState(String msg) {
        telemetry.addData("OpMode Status", msg);
        telemetry.update();
    }
}
