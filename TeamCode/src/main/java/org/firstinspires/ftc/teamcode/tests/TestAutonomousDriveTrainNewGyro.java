package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrainNewGyro;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Chris Soper on 4/17/2017.
 * <p>This __should__ test all functionality of AutonomousDrivetrainNewGyro.java</p>
 * <p>
 * This is a "Unit Test".  Unit tests are for low level testing of code
 * independent of its implementation (in this case, testing the drive train code itself, not the
 * OpMode using the drive train).  This is important in preventing issues where it is unclear if
 * the library, or the code implementing the library which is causing issues.
 * <p>
 *
 * @see <a href="http://softwaretestingfundamentals.com/unit-testing/">Brief intro to Unit Testing</a>
 */
@Disabled
@TeleOp(name = "Test New Autonomous Drivetrain", group = "Test")
public class TestAutonomousDriveTrainNewGyro extends LinearOpMode {
    /**
     * Various states a test case can have
     */
    public enum state {
        NOT_STARTED, SKIPPED, WAITING_TO_START, NOT_IMPLEMENTED, IN_PROGRESS, DONE
    }

    /**
     * All tests
     * <p>(non-implemented tests will be set to state.NOT_IMPLEMENTED when run)</p>
     */
    public enum tests {
        FORWARDS_0_INIT,
        FORWARDS_90_INIT,
        BACK_0_INIT,
        BACK_90_INIT,
        RIGHT_0_INIT,
        RIGHT_90_INIT,
        LEFT_0_INIT,
        LEFT_90_INIT,
        TURN_0_INIT_POS_TARGET,
        TURN_0_INIT_NEG_TARGET,
        TURN_90_INIT_POS_TARGET,
        TURN_90_INIT_NEG_TARGET,
        TURN_0_INIT_VERY_POS_TARGET,
        TURN_0_INIT_VERY_NEG_TARGET,
        TURN_90_INIT_VERY_POS_TARGET,
        TURN_90_INIT_VERY_NEG_TARGET,
    }

    /**
     * variable to prevent accidental resetting of the testStatus map
     */
    private boolean _testStatusMapSetup = false;

    /**
     * Map of all test cases and their current status
     */
    private Map<tests, state> testStatus = new TreeMap<>();

    /**
     * {@inheritDoc}
     *
     * @throws InterruptedException
     */
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime waitTimer = new ElapsedTime();
        // Initialize the map of test statuses
        initTestStatus();
        waitForStart();

        // Loop through each test
        for (tests currentTest : testStatus.keySet()) {
            if (!opModeIsActive()) { // Exit the for loop if the OpMode exited early
                break;
            }

            // Wait for input before executing test
            testStatus.put(currentTest, state.WAITING_TO_START);
            telemetry.addData("A=CONTINUE | B=SKIP", currentTest.toString());
            addTestInfo();
            telemetry.update();
            boolean skip = false;
            while (opModeIsActive()) {
                if (gamepad1.a) {
                    break;
                } else if (gamepad1.b) {
                    skip = true;
                    break;
                }
                sleep(10);
            }
            if (skip) {
                testStatus.put(currentTest, state.SKIPPED);
                telemetry.addData("Skipping test", currentTest);
                continue;
            }

            // force tester to wait 1 second (to prevent accidental task execution)
            waitTimer.reset();
            while (waitTimer.milliseconds() <= 1000) {
                // display timer.milliseconds as a percent
                telemetry.addData("Please wait", (waitTimer.milliseconds() / 10) + "%");
                addTestInfo();
                // I hope telemetry sorts by order added, or our prompts will be hidden...
                telemetry.update();
                sleep(100);
            }

            // Execute test
            AutonomousDriveTrainNewGyro drive = new AutonomousDriveTrainNewGyro();
            switch (currentTest) {
                case FORWARDS_0_INIT:
                    drive.init(this, 0);
                    addTestStartInfo(currentTest, 0, "drive.forwards(.5, .3)");
                    drive.forwards(.5, .3);
                    addTestCompleteInfo(currentTest, "moved .5m forward");
                    break;
                case FORWARDS_90_INIT:
                    drive.init(this, 90);
                    addTestStartInfo(currentTest, 90, "drive.forwards(.5, .3)");
                    drive.forwards(.5, .3);
                    addTestCompleteInfo(currentTest, "moved .5m forward");
                    break;
                case BACK_90_INIT:
                    drive.init(this, 90);
                    addTestStartInfo(currentTest, 90, "drive.backwards(.5, .3)");
                    drive.backwards(.5, .3);
                    addTestCompleteInfo(currentTest, "moved .5m back");
                    break;
                case RIGHT_90_INIT:
                    drive.init(this, 90);
                    addTestStartInfo(currentTest, 90, "drive.right(.5, .3)");
                    drive.right(.5, .3);
                    addTestCompleteInfo(currentTest, "moved .5m right");
                    break;
                case LEFT_90_INIT:
                    drive.init(this, 90);
                    addTestStartInfo(currentTest, 90, "drive.left(.5, .3)");
                    drive.left(.5, .3);
                    addTestCompleteInfo(currentTest, "moved .5m left");
                    break;
                case TURN_0_INIT_POS_TARGET:
                    drive.init(this, 0);
                    addTestStartInfo(currentTest, 0, "drive.turn(90, 2, .7)");
                    drive.turn(90, 2, .7);
                    addTestCompleteInfo(currentTest, "turned 90 deg clockwise");
                    break;
                case TURN_0_INIT_NEG_TARGET:
                    drive.init(this, 0);
                    addTestStartInfo(currentTest, 0, "drive.turn(-130, 2, .7)");
                    drive.turn(-130, 2, .7);
                    addTestCompleteInfo(currentTest, "turned 220 deg ~clockwise");
                    break;
                case TURN_90_INIT_POS_TARGET:
                    drive.init(this, 90);
                    addTestStartInfo(currentTest, 9, "drive.turn(90, 2, .7)");
                    drive.turn(90, 2, .7);
                    addTestCompleteInfo(currentTest, "turned 90deg clockwise");
                    break;
                case TURN_90_INIT_NEG_TARGET:
                    drive.init(this, 90);
                    addTestStartInfo(currentTest, 9, "drive.turn(-130, 2, .7)");
                    drive.turn(-130, 2, .7);
                    addTestCompleteInfo(currentTest, "turned 220deg ~clockwise");
                    break;
                case TURN_0_INIT_VERY_POS_TARGET:
                    drive.init(this, 0);
                    addTestStartInfo(currentTest, 0, "drive.turn(1080, 2, .7)");
                    drive.turn(1080, 2, .7);
                    addTestCompleteInfo(currentTest, "spun 2.5 times clockwise");
                    break;
                case TURN_0_INIT_VERY_NEG_TARGET:
                    drive.init(this, 0);
                    addTestStartInfo(currentTest, 0, "drive.turn(-540, 2, .7)");
                    drive.turn(-540, 2, .7);
                    addTestCompleteInfo(currentTest, "spun 1.5 times ~clockwise");
                    break;
                default:
                    testStatus.put(currentTest, state.NOT_IMPLEMENTED);
                    break;
            }
            if (testStatus.get(currentTest) == state.IN_PROGRESS) {
                testStatus.put(currentTest, state.DONE);
            }
            // Run the drivetrain cleanup method after each test
            drive.stop();
        }
    }


    /**
     * Adds pre-test telemetry and sets test state.
     * <p> Call this BEFORE executing your test method </p>
     *
     * @param test         current test
     * @param initial      drivetrain initial angle
     * @param testFunction test method being executed (preferably with parameters)
     */
    private void addTestStartInfo(tests test, int initial, String testFunction) throws InterruptedException {
        if (opModeIsActive()) { // if (...) might not be needed, but we use sleep() so lets be safe
            testStatus.put(test, state.IN_PROGRESS);
            telemetry.addData("Executing test", test);
            telemetry.addData("Initialized drivetrain to", initial);
            telemetry.addData("function", testFunction);
            telemetry.update();
            sleep(3000);
        }
    }

    /**
     * Adds post-test telemetry and sets test state.
     * <p> Call this AFTER executing your test method </p>
     *
     * @param test    current test
     * @param outcome expected outcome
     */
    private void addTestCompleteInfo(tests test, String outcome) throws InterruptedException {
        if (opModeIsActive()) { // if (...) might not be needed, but we use sleep() so lets be safe
            testStatus.put(test, state.DONE);
            telemetry.addData("Concluded test", test);
            telemetry.addData("Expected", outcome);
            telemetry.update();
            sleep(3000);
        }
    }

    /**
     * Initializes testStatue map with every test as NOT_STARTED
     */
    private void initTestStatus() {
        if (!_testStatusMapSetup) {
            for (tests test : tests.values()) {
                testStatus.put(test, state.NOT_STARTED);
            }
            _testStatusMapSetup = true;
        }
    }

    /**
     * Adds telemetry data for the state of all tests
     */
    private void addTestInfo() {
        for (tests testName : testStatus.keySet()) {
            // key = test, value = status
            telemetry.addData(testName.toString(), testStatus.get(testName).toString());
        }
    }
}
