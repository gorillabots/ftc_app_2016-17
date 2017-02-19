package org.firstinspires.ftc.teamcode;

/**
 * Created by emper on 12/22/2016.
 */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

import org.firstinspires.ftc.teamcode.submodules.AutonomousDriveTrain;

@Deprecated
@Disabled
@Autonomous(name="RedAutonomous", group="concept")
public class ZRedAutonomous extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;
    ColorSensor floorColor;
    ColorSensor beaconColor;
    //ButtonPresserClass beacon;
    public void runOpMode() throws InterruptedException
    {
        driveTrain = new AutonomousDriveTrain();
       // beacon = new ButtonPresserClass();
        driveTrain.init(this);
        floorColor = hardwareMap.colorSensor.get("floorColor");
        beaconColor = hardwareMap.colorSensor.get("beaconColor");
        beaconColor.setI2cAddress(I2cAddr.create8bit(58));
        beaconColor.enableLed(false);
        floorColor.enableLed(false);
        //driveTrain.RetractTouchServo();
        waitForStart();
        //driveTrain.ExtendTouchServo();
        //driveTrain.GyroRotation(45, 0.2, 10000);
    }
}

