package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Chris on 11/8/2016.
 */

public class ColorHelper {

    //convert to OF for the HSV values
    static float hsvValues[] = {0F, 0F, 0F};
    static float hsvValuesFloor[] = {0F, 0F, 0F};

    public static TeamColors getBeaconColor(ColorSensor colorSensor)
    {

        // convert RGB to HSV values
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

        // setting the current color to Blank
        String currentcolor = "blank";

        // Red if hue is > than 330 or hue < 10 with a saturation > 0.5
        if (hsvValues[0] > 330  && hsvValues[1] > 0.5 || hsvValues[0] < 10 && hsvValues[1] > 0.5) {
            return TeamColors.RED;
        }

        //Blue if 230 < hue < 250 with saturation > 0.5
        else if (hsvValues[0] > 205 && hsvValues[0] < 250 && hsvValues[1] > 0.5){
            return TeamColors.BLUE;
        }

        //No color if saturation < 0.5
        else if (hsvValues[1] < 0.6)
        {
            return TeamColors.INDECISIVE;
        }

        return TeamColors.INDECISIVE;
    }

    public static TeamColors getBeaconColorTest(ColorSensor colorSensor)
    {
        int r = colorSensor.red();
        int g = colorSensor.green();
        int b = colorSensor.blue();

        if(r > b)
        {
            return TeamColors.RED;
        }

        if(r < b)
        {
            return TeamColors.BLUE;
        }

        return TeamColors.INDECISIVE;
    }

    public static String getRightBeaconColor(ColorSensor colorSensor)
    {

        // convert RGB to HSV values
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

        // setting the current color to Blank
        String currentcolor = "blank";

        // Red if hue is > than 330 or hue < 10 with a saturation > 0.5
        if (hsvValues[0] > 330  && hsvValues[1] > 0.5 || hsvValues[0] < 10 && hsvValues[1] > 0.5) {
            currentcolor = "red";
        }

        //Blue if 230 < hue < 250 with saturation > 0.5
        else if (hsvValues[0] > 230 && hsvValues[0] < 250 && hsvValues[1] > 0.5){
            currentcolor = "blue";
        }

        //No color if saturation < 0.5
        else if (hsvValues[1] < 0.6){
            currentcolor = "blank";
        }

        return currentcolor;
    }

    public static String getFloorColor(ColorSensor colorSensor)
    {

        String currentcolor = "not white";

        if (isFloorWhite(colorSensor))
        {
        // white line?
            currentcolor = "white";
        }
        else {
         // Not White?
            currentcolor = "not white";
        }

        return currentcolor;
    }

    public static void printColorRGB(Telemetry telemetry, ColorSensor color)
    {
        // show the values for RGB
        telemetry.addData("R", color.red());
        telemetry.addData("G", color.green());
        telemetry.addData("B", color.blue());
    }

    public static void printColorHSV(Telemetry telemetry, ColorSensor color)
    {
        Color.RGBToHSV(color.red() * 8, color.green() * 8, color.blue() * 8, hsvValuesFloor);

        // show the hsv values
        telemetry.addData("H", hsvValuesFloor[0]);
        telemetry.addData("S", hsvValuesFloor[1]);
        telemetry.addData("V", hsvValuesFloor[2]);

    }

    /**
     * this checks if the floor color is white
     * @param color Bottom colorsensor
     * @return true if floor is white
     */
    public static boolean isFloorWhite(ColorSensor color)
    {
        // convert the RGB values to HSV values
        Color.RGBToHSV(color.red() * 8, color.green() * 8, color.blue() * 8, hsvValuesFloor);

        return hsvValuesFloor[2] > .8;
    }

    public static float getFloorValue()
    {
        return hsvValuesFloor[2];
    }
}