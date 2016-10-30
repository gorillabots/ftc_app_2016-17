package org.firstinspires.ftc.teamcode;

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
    static final int LED_CHANNEL = 5;
    int count;
    public void init() {


        count = 0;
        // turn the LED on in the beginning, just so user will know that the sensor is active.
        sensorRGB = hardwareMap.colorSensor.get("sensorRGB");
    }
        public void loop(){
            float hsvValues[] = {0F, 0F, 0F};
            final float values[] = hsvValues;
            boolean bCurrState = false;
            boolean bPrevState = false;
            boolean bLedOn = true;

                //bPrevState = bCurrState;
                telemetry.addData("LED", bLedOn ? "On" : "Off");
                telemetry.addData("Clear", sensorRGB.alpha());
                telemetry.addData("Red  ", sensorRGB.red());
                telemetry.addData("Green", sensorRGB.green());
                telemetry.addData("Blue ", sensorRGB.blue());
                telemetry.addData("Hue", hsvValues[0]);
                telemetry.addData("count is ",count);


                //count++;


                telemetry.update();
            }
        }

