package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

/**
 * Created by mikko on 4/17/17.
 */
@Disabled
@Autonomous(name="ColorTest", group="Test")
public class ColorTest extends LinearOpMode
{
    ColorSensor floorColor;

    public void runOpMode()
    {
        floorColor = hardwareMap.colorSensor.get("floorColor");
        floorColor.setI2cAddress(I2cAddr.create8bit(0x44)); //68 in decimal

        waitForStart();

        floorColor.enableLed(true);

        while(opModeIsActive())
        {
            floorColor.enableLed(gamepad1.a);

            telemetry.addData("R", floorColor.red());
            telemetry.addData("G", floorColor.green());
            telemetry.addData("B", floorColor.blue());
            telemetry.update();
        }

        floorColor.enableLed(false);
    }
}
