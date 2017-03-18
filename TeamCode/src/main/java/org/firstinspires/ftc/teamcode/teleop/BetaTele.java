package org.firstinspires.ftc.teamcode.teleop;


//Created by Mikko on 02/28/2017

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DoubleScale;
import org.firstinspires.ftc.teamcode.submodules.BallControl;
import org.firstinspires.ftc.teamcode.submodules.Drivetrain;
import org.firstinspires.ftc.teamcode.submodules.ForkLift;
import org.firstinspires.ftc.teamcode.submodules.LedHelp;

@TeleOp(name = "Beta TeleOp Broken", group = "Final")
public class BetaTele extends OpMode
{
    Drivetrain drivetrain;
    BallControl ballControl;
    ForkLift forkLift;

    ColorSensor floorColor;
    ColorSensor beaconColor;

    //FlyState flyState;
    //DoubleScale flyRamp;

    ElapsedTime heartBeat;
    LedHelp led;

    Runtime runtime;

    public void init()
    {
        runtime = Runtime.getRuntime();

        drivetrain = new Drivetrain(hardwareMap, telemetry);
        ballControl = new BallControl(hardwareMap, telemetry);
        forkLift = new ForkLift(hardwareMap);

        floorColor = hardwareMap.colorSensor.get("floorColor");
        floorColor.enableLed(false);
        floorColor.enableLed(true);

        beaconColor = hardwareMap.colorSensor.get("beaconColor");
        beaconColor.setI2cAddress(I2cAddr.create8bit(58));
        beaconColor.enableLed(false);
        beaconColor.enableLed(true);
        heartBeat = new ElapsedTime();
        led = new LedHelp(hardwareMap,telemetry);
        //flyState = FlyState.OFF;
        //flyRamp = new DoubleScale(1, 1, 1, 1);
    }

    boolean button;
    boolean buttonLast = false;
    boolean flyActive = false;
    boolean firstCycle = true;

    long allocatedRAM;

    public void loop()
    {
        allocatedRAM = runtime.totalMemory() / 1024;

        if(firstCycle)
        {
            firstCycle = false;
            heartBeat.reset();
        }

        if(heartBeat.milliseconds() > 80000){
           led.flash();
        }
        else{
            led.ledOn();
        }
//90000

        /*
        if(heartBeat.milliseconds() > 0 && heartBeat.milliseconds() <10000){
            if((heartBeat.milliseconds()%500) == 0){
                if(led.getLed() == 0){
                    led.ledOn();
                }
                if(led.getLed() == 1){
                    led.LedOff();
                }
            }
        }*/

        float stickX = (gamepad1.left_stick_x); // Stick position (Absolute heading)
        float stickY = (gamepad1.left_stick_y); // Each is in range -1 to 1
        float stickRot = (gamepad1.right_stick_x / 2f); //Used to rotate the robot;

        if (gamepad1.b)
        {
            drivetrain.resetGyro();
        }

        drivetrain.oneStickLoop(stickX, stickY, stickRot);

        {
            button = gamepad2.right_bumper;

            if(button && !buttonLast)
            {
                flyActive = !flyActive;
            }

            if(flyActive)
            {
                ballControl.newRunFlywheel(true);
            }
            else
            {
                ballControl.newRunFlywheel(false);
            }

            buttonLast = button;
        }

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

        if(gamepad1.right_bumper)
        {
            ballControl.newRunCollector(true);
        }
        else if(gamepad1.right_trigger >= .6)
        {
            ballControl.newRunCollector(false);
        }
        else
        {
            ballControl.newStopCollector();
        }

        if(gamepad2.left_bumper)
        {
            ballControl.newRunElevator(true);
        }
        else if(gamepad2.left_trigger >= .6)
        {
            ballControl.newRunElevator(false);
        }
        else
        {
            ballControl.newStopElevator();
        }

        float stick2Y = -gamepad2.left_stick_y;

        if(Math.abs(stick2Y) >= .2)
        {
            if(gamepad2.dpad_up && stick2Y > 0)
            {
                forkLift.liftOverride(stick2Y);
            }
            else
            {
                forkLift.lift(stick2Y);
            }
        }
        else
        {
            forkLift.stop();
        }

        telemetry.addData("Allocated RAM", allocatedRAM + "KiB");
    }

    @Override
    public void stop()
    {
        super.stop();
        floorColor.enableLed(false);
    }

    //Graph: https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Logistic-curve.svg/320px-Logistic-curve.svg.png
    public static double sigmoid(double x)
    {
        return (1 / (1 + Math.pow(Math.E, -x)));
    }
}
