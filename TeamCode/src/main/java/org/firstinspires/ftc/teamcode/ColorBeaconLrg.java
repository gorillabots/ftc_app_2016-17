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

/**
 * Created by Owner on 12/23/2016.
 */
@TeleOp(name = "Sensor: Color Beacon", group = "Sensor")
public class ColorBeaconLrg extends LinearOpMode {

    ColorSensor colorSensor;    // Hardware Device Object
    float hsvValues[] = {0F,0F,0F};
    @Override
    public void runOpMode() {

    }
    }