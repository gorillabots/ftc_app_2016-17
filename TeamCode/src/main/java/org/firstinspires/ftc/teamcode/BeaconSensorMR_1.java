package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.util.ElapsedTime;

@Deprecated
@Disabled
@TeleOp(name = "Sensor: MR Color", group = "Sensor")

public class BeaconSensorMR_1 extends LinearOpMode {

    ColorSensor colorSensor;    // Hardware Device Object
    float hsvValues[] = {0F,0F,0F};

    @Override
    public void runOpMode() {


        ElapsedTime opmodeRunTime = new ElapsedTime();
        colorSensor = hardwareMap.colorSensor.get("RightBS");
        colorSensor.setI2cAddress(I2cAddr.create8bit(62));

        waitForStart();
        while (opModeIsActive()) {

            String currentcolor = getRightBeaconColor();

            // send the info back to driver station using telemetry function.
            telemetry.addData("Running Time", opmodeRunTime.seconds());
            telemetry.addData("Clear", colorSensor.alpha());
            telemetry.addData("Red", colorSensor.red());
            telemetry.addData("Green", colorSensor.green());
            telemetry.addData("Blue ", colorSensor.blue());
            telemetry.addData("Hue", hsvValues[0]);
            telemetry.addData("Saturation", hsvValues[1]);
            telemetry.addData("value", hsvValues[2]);
            telemetry.addData("current color", currentcolor);
            telemetry.update();
        }
    }

    public String getRightBeaconColor() {
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
}