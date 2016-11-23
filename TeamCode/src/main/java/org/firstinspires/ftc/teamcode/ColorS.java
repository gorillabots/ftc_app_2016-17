package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannelController;

/**
 * Created by Owner on 10/7/2016.
 */

@TeleOp(name = "Sensor: sensorRGB", group = "Sensor")
public class ColorS extends OpMode {

    ColorSensor sensorRGB;
    DeviceInterfaceModule cdim;
    static final int LED_CHANNEL = 5;
    int count;
    boolean bLedOn = false;
int recounter = 0;

    public void init() {
        count = 0;
        cdim = hardwareMap.deviceInterfaceModule.get("dim");
        cdim.setDigitalChannelMode(LED_CHANNEL, DigitalChannelController.Mode.OUTPUT);
        sensorRGB = hardwareMap.colorSensor.get("sensorRGB");
        cdim.setDigitalChannelState(LED_CHANNEL, bLedOn);
    }

    public void loop() {
        float hsvValues[] = {0F, 0F, 0F};

        // final float values[] = hsvValues;
        boolean bCurrState = false;
        boolean bPrevState = false;

        Color.RGBToHSV((sensorRGB.red() * 255) / 800, (sensorRGB.green() * 255) / 800, (sensorRGB.blue() * 255) / 800, hsvValues);

        //bPrevState = bCurrState;
        telemetry.addData("LED", bLedOn ? "On" : "Off");
        telemetry.addData("Clear", sensorRGB.alpha());
        telemetry.addData("Red  ", sensorRGB.red());
        telemetry.addData("Green", sensorRGB.green());
        telemetry.addData("Blue ", sensorRGB.blue());
        telemetry.addData("Hue", hsvValues[0]);
        telemetry.addData("Saturation", hsvValues[1]);
        telemetry.addData("count is ", count);
        //telemetry.addData("number of reads", recounter);

        telemetry.update();

        recounter ++;
    }
    @Override
    public void stop(){
        telemetry.addData("number of reads", recounter);
        telemetry.update();
    }
}

