package org.firstinspires.ftc.teamcode;

/**
 * Created by emper on 12/22/2016.
 */
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.Constants.BETWEEN_WHITE_LINES;
import static org.firstinspires.ftc.teamcode.Constants.GO_BACKWARD_AFTER_TOUCH_WALL;
import static org.firstinspires.ftc.teamcode.Constants.WALL_TO_WALL_IN_AUTONOMOUS;

@Autonomous(name="RedB", group="concept")
public class ZRedAutonomous extends LinearOpMode
{
    BallControl ball;
    AutonomousDriveTrain driveTrain;
    ModernRoboticsI2cRangeSensor range;
    ColorSensor floorColor;
    ColorSensor beaconColorL;
    ColorSensor beaconColorR;
    Servo sensorSwing;
    ElapsedTime t;
    public void runOpMode() throws InterruptedException
    {
        driveTrain = new AutonomousDriveTrain(); //Initialize hardware
        ball = new BallControl(hardwareMap, telemetry);
        driveTrain.init(this);

        floorColor = hardwareMap.colorSensor.get("floorColor");
        beaconColorL = hardwareMap.colorSensor.get("beaconColor");
        beaconColorR = hardwareMap.colorSensor.get("beaconColor2");
        beaconColorL.setI2cAddress(I2cAddr.create8bit(58));
        beaconColorR.setI2cAddress(I2cAddr.create8bit(62));
        range = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "range");
        floorColor.enableLed(false); //Disable LEDs
        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);

        sensorSwing = hardwareMap.servo.get("touchServo");
        sensorSwing.setPosition(.56);

        waitForStart();
        //ball.newStopElevator();
        //ball.newRunFlywheel(false);
        driveTrain.resetGyro();
        /*driveTrain.goToDistance(range, 50, 2, 0.3);
        //Go until reach center-line by corner
        driveTrain.turnToGyro(2, 0.2);
        driveTrain.frontLeft(2, 0.3);
        //Go until reach base of center goal*/
        driveTrain.turnGyro(90, 0.15);
        //Turns to correct angle to shoot balls
        /*for(int i = 0; i < 2; i++) {
            //Run elevator and flywheel for two seconds, stop, and repeat once more just in case we didn't launch all balls
            t.reset();
            t.startTime();
            while (t.milliseconds() < 2000) {
                ball.newRunElevator(true);
                ball.newRunFlywheel(true);
            }
            ball.newStopElevator();
            ball.newRunFlywheel(false);
            sleep(500);
        }
        driveTrain.turnGyro(180, 0.3);
        driveTrain.backRight(1, 0.3);
        driveTrain.turnGyro(180, 0.3);
        driveTrain.resetGyro();
        driveTrain.backRight(1, 0.3);
        driveTrain.turnToGyro(2, 0.3);
        driveTrain.goToDistance(range, 25, 2, 0.3);
        floorColor.enableLed(true);
        driveTrain.forwardsGyroToLine(floorColor, 0.22, 1, 0.05);
        driveTrain.goToDistance(range, 10, 2, 0.3);
        driveTrain.turnToGyro(2, 0.3);
        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
        driveTrain.beaconResponse(TeamColors.RED, beaconColorL, beaconColorR);
        driveTrain.forwards(0.1, 0.3);
        driveTrain.turnToGyro(2, 0.3);
        driveTrain.goToDistance(range, 25, 2, 0.3);
        driveTrain.turnToGyro(2, 0.3);
        floorColor.enableLed(true);
        driveTrain.forwardsGyroToLine(floorColor, 0.22, 1, 0.05);
        driveTrain.turnToGyro(2, 0.3);
        driveTrain.goToDistance(range, 10, 2, 0.3);
        driveTrain.turnToGyro(2, 0.3);
        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
        driveTrain.beaconResponse(TeamColors.RED, beaconColorL, beaconColorR);
        driveTrain.goToDistance(range, 60, 2, 0.3);
        driveTrain.turnToGyro(2, 0.3);
        driveTrain.frontRight(0.75, 0.75);
        driveTrain.right_continuous(0);
        */
    }
}

