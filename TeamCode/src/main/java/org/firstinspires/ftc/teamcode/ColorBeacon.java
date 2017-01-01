package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.ColorB;
/**
 * Created by Owner on 12/23/2016.
 */
@TeleOp(name = "Color Beacon", group = "MRI")

public class ColorBeacon extends LinearOpMode {

    ColorBeacon colorBeacon;
    ColorBeacon beacon = new ColorBeacon();
    byte red = 0;     //red value to sent to sensor
    byte green = 0;   //green ...
    byte blue = 0;

    int colorNumber;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


    //    beacon = hardwareMap.MRIColorBeacon.get("ColorBeacon");

        waitForStart();
        while (opModeIsActive()) {
  }
    }
}