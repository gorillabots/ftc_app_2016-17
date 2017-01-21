package org.firstinspires.ftc.teamcode;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Sensor: MR Color", group = "Sensor")

public class Floorcolor extends LinearOpMode {

    ColorSensor colorSensor;    // Hardware Device Object
    float hsvValues[] = {0F, 0F, 0F};

    @Override
    public void runOpMode() {


        ElapsedTime opmodeRunTime = new ElapsedTime();
        colorSensor = hardwareMap.colorSensor.get("floorColor");

        waitForStart();
        while (opModeIsActive()) {

            String currentcolor = ColorHelper.getFloorColor(colorSensor);

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
}