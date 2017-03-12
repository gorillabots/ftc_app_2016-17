package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.AnalogInputController;
import com.qualcomm.robotcore.hardware.AnalogOutput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.LedStates;
import org.firstinspires.ftc.teamcode.submodules.Drivetrain;
import org.firstinspires.ftc.teamcode.submodules.ForkLift;
import org.firstinspires.ftc.teamcode.submodules.LedController;

/**
 * Created by Jarred on 3/7/2017.
 */
@TeleOp(name = "Fork Test", group = "Concept")
public class forkTest extends OpMode {
    DcMotor raise;
        LedController led;
    DeviceInterfaceModule cdim;
    AnalogInput stop;
    ForkLift fork;
    static final int LimitSwitchPin = 7;
    Drivetrain drivetrain;
    ModernRoboticsI2cGyro gyro;
    public void init(){
        raise = hardwareMap.dcMotor.get("raise");
        led = new LedController(hardwareMap, telemetry);
        cdim = hardwareMap.deviceInterfaceModule.get("dim");
        stop = hardwareMap.analogInput.get("stop");
        fork = new ForkLift(hardwareMap);
        drivetrain = new Drivetrain(hardwareMap, telemetry);
        gyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (gyro.isCalibrating()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gyro.resetZAxisIntegrator();
    }
    public void loop(){
        led.setLedState(LedStates.ON);
        led.LedFlash(10000, 1000);
        fork.lift(-gamepad2.left_stick_y);
        telemetry.addData("encoder at", raise.getCurrentPosition());
        telemetry.addData("Voltage", stop.getVoltage());
        telemetry.update();
        //.043 == reset position

        float stickX = (gamepad1.left_stick_x); // Stick position (Absolute heading)
        float stickY = (gamepad1.left_stick_y); // Each is in range -1 to 1
        float stickRot = (gamepad1.right_stick_x / 2f); //Used to rotate the robot;

/*
        if(gamepad1.x){
            try
            {
                // make sure the gyro is calibrated.
                while (gyro.isCalibrating())
                {
                    Thread.sleep(50);
                }
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
3
            gyro.resetZAxisIntegrator();
        }


*/



        if (gamepad1.b == true) {
            gyro.resetZAxisIntegrator();
        }

        int rotation = gyro.getHeading();

        drivetrain.oneStickLoop(stickX, stickY, stickRot);
    }
    //256
    //19403
}
