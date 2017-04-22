package org.firstinspires.ftc.teamcode.teleop;


//Created by Mikko on 02/28/2017

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DoubleScale;
import org.firstinspires.ftc.teamcode.submodules.BallControl;
import org.firstinspires.ftc.teamcode.submodules.Drivetrain;
import org.firstinspires.ftc.teamcode.submodules.DrivetrainNewGyro;
import org.firstinspires.ftc.teamcode.submodules.ForkLift;
import org.firstinspires.ftc.teamcode.submodules.LedHelp;

@TeleOp(name = "Beta TeleOp", group = "Final")
public class BetaTele extends OpMode
{
    DrivetrainNewGyro drivetrain;
    BallControl ballControl;
    ForkLift forkLift;

    ColorSensor floorColor;
    ColorSensor beaconColor;

    FlyState flyState;
    DoubleScale flyRamp;

    public void init()
    {
        telemetry.addData("Status", "Initialization Started");
        telemetry.update();

        drivetrain = new DrivetrainNewGyro(hardwareMap, telemetry);
        ballControl = new BallControl(hardwareMap, telemetry);
        forkLift = new ForkLift(hardwareMap, telemetry);

        telemetry.addData("Status", "Initialized Submodules");
        telemetry.update();

        floorColor = hardwareMap.colorSensor.get("floorColor");
        floorColor.enableLed(false);
        floorColor.enableLed(true);

        telemetry.addData("Status", "Initialized FloorColor");
        telemetry.update();

        beaconColor = hardwareMap.colorSensor.get("beaconColor");
        beaconColor.setI2cAddress(I2cAddr.create8bit(58));
        beaconColor.enableLed(false);
        beaconColor.enableLed(true);

        telemetry.addData("Status", "Initialized BeaconColor");
        telemetry.update();

        //heartBeat = new ElapsedTime();
        //led = new LedHelp(hardwareMap,telemetry);
        flyState = FlyState.OFF;
        //flyRamp = new DoubleScale(1, 1, 1, 1);

        telemetry.addData("Status", "Initialized Fully");
        telemetry.update();
    }

    boolean button;
    boolean buttonLast = false;
    boolean flyActive = false;

    public void loop()
    {
        float stickX = (gamepad1.left_stick_x); // Stick position (Absolute heading)
        float stickY = (gamepad1.left_stick_y); // Each is in range -1 to 1
        float stickRot = (gamepad1.right_stick_x / 2f); //Used to rotate the robot;

        if (gamepad1.b)
        {
            drivetrain.resetGyro();
        }

        drivetrain.oneStickLoop(stickX, stickY, stickRot);

        //Flywheel ramping stuff
        /*{
            button = gamepad2.right_bumper;

            if(button && !buttonLast)
            {
                flyActive = !flyActive;
            }

            telemetry.addData("Button", button);
            telemetry.addData("FlyActive", flyActive);
            telemetry.update();

            double time = System.currentTimeMillis() / 1000d;

            switch(flyState) {
                case OFF:
                    if (flyActive)
                    {
                        ballControl.newRunFlywheel(true);
                        flyState = FlyState.ON;
                    }
                    break;
                case ON:
                    if (!flyActive) {
                        flyRamp = new DoubleScale(time, time + 1, 1, .3);
                        flyState = FlyState.R_DOWN;
                    }
                    break;
                case R_DOWN:
                    if (flyActive) {
                        ballControl.fly.setPower(1);
                        flyState = FlyState.ON;
                    } else {
                        if (time > flyRamp.inmax) {
                            ballControl.fly.setPower(0);
                            flyState = FlyState.OFF;
                        } else {
                            ballControl.fly.setPower(-Math.sqrt(flyRamp.scale(time)));
                        }
                    }
                    break;
                default:
                    //Why would this ever happen?
                    break;
            }

            buttonLast = button;
        }*/

        button = gamepad2.right_bumper;

        if(button && !buttonLast)
        {
            flyActive = !flyActive;
            ballControl.newRunFlywheel(flyActive);
        }

        buttonLast = button;

        if(gamepad1.right_bumper)
        {
            ballControl.newRunCollector(true);
        }
        else if(gamepad1.right_trigger >= .6)
        {
            ballControl.newRunCollector(false);
        }
        else {
            ballControl.newStopCollector();
        }

        /*if (gamepad2.right_trigger >= .6) {

            flyActive = false;
            ballControl.newRunFlywheelopp(true);
        }
        else {
            ballControl.newRunFlywheelopp(false);
        }*/

        if (gamepad2.left_bumper) {
            ballControl.newRunElevator(true);
        } else if (gamepad2.left_trigger >= .6) {
            ballControl.newRunElevator(false);
        } else {
            ballControl.newStopElevator();
        }

        float stick2Y = -gamepad2.left_stick_y;

        if (Math.abs(stick2Y) >= .2) {
            if (gamepad2.a && stick2Y > 0) {
                forkLift.liftOverride(stick2Y);
            } else {
                forkLift.lift(stick2Y);
            }
        } else {
            forkLift.stop();
        }
        ballControl.addTelemetry();
        drivetrain.addTelemetry();
        forkLift.addTelemetry();
        telemetry.update();

    }

    @Override
    public void stop() {
        super.stop();
        floorColor.enableLed(false);
        drivetrain.stop();
    }


    //Graph: https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Logistic-curve.svg/320px-Logistic-curve.svg.png
    public static double sigmoid(double x) {
        return (1 / (1 + Math.pow(Math.E, -x)));
    }
}
