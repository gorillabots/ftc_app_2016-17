package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Chris on 11/8/2016.
 */

public class ColorHelper {
    static float hsvValues[] = {0F, 0F, 0F};

    public static String getBeaconColor(ColorSensor colorSensor) {
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

        String currentcolor = "none";

        if (hsvValues[0] < 15) {
            currentcolor = "red";
        }
        if (hsvValues[0] > 220) {
            currentcolor = "blue";
        }
        if (hsvValues[0] > 250) {
            currentcolor = "non";
        }
        if (hsvValues[0] < 1) {
            currentcolor = "non";
        }
        return currentcolor;
    }


    public static String getFloorColor(ColorSensor colorSensor) {
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

        String currentcolor = "none";

        if (hsvValues[0] == 60 && hsvValues[1] == 1) {
            currentcolor = "white";
        }

        if (hsvValues[0] < 60) {
            currentcolor = "none";
        }

        if (hsvValues[0] > 60) {
            currentcolor = "none";
        }

        return currentcolor;

    }
}