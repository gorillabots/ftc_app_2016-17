package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Owner on 11/4/2016. (0x3a)
 */
@TeleOp(name = "Sensor: MR Color", group = "Sensor")
public class SensorMRColorFloor extends LinearOpMode{

        ColorSensor colorSensor;    // Hardware Device Object

        @Override
        public void runOpMode() {
            float hsvValues[] = {0F,0F,0F};

            // values is a reference to the hsvValues array.
            final float values[] = hsvValues;

            ElapsedTime opmodeRunTime = new ElapsedTime();
            colorSensor = hardwareMap.colorSensor.get("BeaconSensor");

            waitForStart();
            while (opModeIsActive()) {

                Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

                String currentcolor = "none";

                if (hsvValues[0] <= 8 && hsvValues[0] > 6){
                        currentcolor = "white";
                }

                // send the info back to driver station using telemetry function.
                telemetry.addData("Running Time", opmodeRunTime.seconds());
                telemetry.addData("Clear", colorSensor.alpha());
                telemetry.addData("Red", colorSensor.red());
                telemetry.addData("Green", colorSensor.green());
                telemetry.addData("Blue ", colorSensor.blue());
                telemetry.addData("Hue", hsvValues[0]);
                telemetry.addData("Saturation", hsvValues[1]);
                telemetry.addData("floor color",currentcolor);
                telemetry.addData("value", hsvValues[2]);
                telemetry.update();


            }
        }

    }