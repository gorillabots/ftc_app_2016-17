package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Chris on 11/8/2016.
 */

public class ColorHelper {
    static float hsvValues[] = {0F, 0F, 0F};

    static float hsvValuesFloor[] = {0F, 0F, 0F};


    public static String getBeaconColor(ColorSensor colorSensor) {
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);


        String currentcolor = "blank";

        if (hsvValues[0] > 330  && hsvValues[1] > 0.5 || hsvValues[0] < 10 && hsvValues[1] > 0.5) {
            currentcolor = "red";
        }
        else if (hsvValues[0] > 230 && hsvValues[0] < 250 && hsvValues[1] > 0.5){
            currentcolor = "blue";
        }
        else if (hsvValues[1] < 0.6){
            currentcolor = "blank";
        }
        return currentcolor;
    }


    public static String getFloorColor(ColorSensor colorSensor) {
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValuesFloor);


        String currentcolor = "not white";

        if (hsvValuesFloor[1] < .3 && hsvValuesFloor[2] > .8) {
            currentcolor = "white";
        }

        else {
            currentcolor = "not white";
        }


        return currentcolor;

    }

    public static void printColorRGB(Telemetry telemetry, ColorSensor color)
    {
        telemetry.addData("R", color.red());
        telemetry.addData("G", color.green());
        telemetry.addData("B", color.blue());
    }

    public static void printColorHSV(Telemetry telemetry, ColorSensor color)
    {
        Color.RGBToHSV(color.red() * 8, color.green() * 8, color.blue() * 8, hsvValuesFloor);

        telemetry.addData("H", hsvValuesFloor[0]);
        telemetry.addData("S", hsvValuesFloor[1]);
        telemetry.addData("V", hsvValuesFloor[2]);
    }

    public static boolean isFloorWhite(ColorSensor color)
    {
        Color.RGBToHSV(color.red() * 8, color.green() * 8, color.blue() * 8, hsvValuesFloor);

        return hsvValuesFloor[2] > .8;
    }

    public static float getFloorValue()
    {
        return hsvValuesFloor[2];
    }
}