package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

/**
 * Created by root on 12/11/16.
 */
@Disabled
@Autonomous(name="ColorTestOp", group="concept")
public class ColorSensorTest extends LinearOpMode
{
    ColorSensor floorColor;
    ColorSensor beaconColor;

    @Override
    public void runOpMode() throws InterruptedException
    {
        floorColor = hardwareMap.colorSensor.get("floorColor");
        beaconColor = hardwareMap.colorSensor.get("beaconColor");
        beaconColor.setI2cAddress(I2cAddr.create8bit(58));
        beaconColor.enableLed(false);
        floorColor.enableLed(false);

        waitForStart();

        while(opModeIsActive())
        {
            floorColor.enableLed(true);
            sleep(200);
            floorColor.enableLed(false);
            sleep(200);
        }
    }
}
