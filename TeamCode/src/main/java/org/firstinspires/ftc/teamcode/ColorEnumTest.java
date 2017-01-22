package org.firstinspires.ftc.teamcode;

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
    ColorSensor beaconColorL;
    ColorSensor beaconColorR;

    boolean currentLeftPress;
    boolean currentRightPress;
    boolean lastLeftPress = false;
    boolean lastRightPress = false;
    
    boolean leftLED = false;
    boolean rightLED = false;

    @Override
    public void init()
    {
        beaconColorL = hardwareMap.colorSensor.get("beaconColor");
        beaconColorR = hardwareMap.colorSensor.get("beaconColor2");
        beaconColorL.setI2cAddress(I2cAddr.create8bit(58));
        beaconColorR.setI2cAddress(I2cAddr.create8bit(62));

        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
    }

    @Override
    public void loop()
    {
        currentLeftPress = gamepad1.dpad_left;
        currentRightPress = gamepad1.dpad_right;
        
        if(currentLeftPress && !lastLeftPress)
        {
            leftLED = !leftLED;
            beaconColorL.enableLed(leftLED);
        }

        if(currentRightPress && !lastRightPress)
        {
            rightLED = !rightLED;
            beaconColorR.enableLed(rightLED);
        }
        
        lastLeftPress = currentLeftPress;
        lastRightPress = currentRightPress;
        
        telemetry.addData("Left color", enumToString(ColorHelper.getBeaconColorTest(beaconColorL)));
        telemetry.addData("Right color", enumToString(ColorHelper.getBeaconColorTest(beaconColorR)));
        telemetry.update();
    }

    @Override
    public void stop()
    {
        super.stop();

        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
    }

    private String enumToString(TeamColors color)
    {
        switch(color)
        {
            case RED:
                return "Red";
            case BLUE:
                return "Blue";
            case INDECISIVE:
                return "Indecisive";
        }

        return "Null";
    }
}
