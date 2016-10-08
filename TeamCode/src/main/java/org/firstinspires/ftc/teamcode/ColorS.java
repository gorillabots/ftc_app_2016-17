package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;

/**
 * Created by Owner on 10/7/2016.
 */

public class ColorS extends OpMode{

    ColorSensor sensorRGB;
    DeviceInterfaceModule cdim;
    static final int LED_CHANNEL = 5;

    @Override
    public void init() {
        boolean bPrevState = false;
        boolean bCurrState = false;

        float hsvValues[] = {0F,0F,0F};
        // F is 15
        boolean bLedOn = true;

        // turn the LED on in the beginning, just so user will know that the sensor is active.
        cdim.setDigitalChannelState(LED_CHANNEL, bLedOn);

        final float values[] = hsvValues;
        if ((bCurrState == true) && (bCurrState != bPrevState))  {
            // button is transitioning to a pressed state. Toggle the LED.
            //bLedOn = !bLedOn;
            //cdim.setDigitalChannelState(LED_CHANNEL, bLedOn);
        }


        bPrevState = bCurrState;

        sensorRGB = hardwareMap.colorSensor.get("color");


        telemetry.addData("LED", bLedOn ? "On" : "Off");
        telemetry.addData("Clear", sensorRGB.alpha());
        telemetry.addData("Red  ", sensorRGB.red());
        telemetry.addData("Green", sensorRGB.green());
        telemetry.addData("Blue ", sensorRGB.blue());
        telemetry.addData("Hue", hsvValues[0]);

        telemetry.update();

    }

    @Override
    public void loop() {


    }


}
