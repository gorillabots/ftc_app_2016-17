package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

/**
 * Created by root on 1/22/17.
 */

@TeleOp(name = "ColorEnumTest", group = "Concept")
public class ColorEnumTest extends OpMode
{
    ColorSensor floorColor;

    @Override
    public void init()
    {
        //Initialize hardware
        floorColor = hardwareMap.colorSensor.get("floorColor");
        floorColor.enableLed(false);

        floorColor.enableLed(true);
    }

    public void loop()
    {
        ColorHelper.printColorRGB(telemetry, floorColor);
        telemetry.addData("Is white", ColorHelper.isFloorWhiteTest(floorColor));
        telemetry.update();
    }

    public void stop()
    {
        super.stop();
        floorColor.enableLed(false);
    }
}
