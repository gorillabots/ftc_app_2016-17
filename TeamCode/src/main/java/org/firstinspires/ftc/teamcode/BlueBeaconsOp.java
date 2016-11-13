package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

/**
 * Created by root on 11/13/16.
 */

@Autonomous(name="BlueBeacons", group="concept")
public class BlueBeaconsOp extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;
    ColorSensor floorColor;

    public void runOpMode()
    {
        driveTrain = new AutonomousDriveTrain();
        driveTrain.init(this);

        floorColor = hardwareMap.colorSensor.get("floorColor");
        floorColor.setI2cAddress(I2cAddr.create8bit(58));

        sleep(500);

        floorColor.enableLed(true);

        waitForStart();

        //driveTrain.frontRight(1.42);
        //driveTrain.rightToTouch();
        //driveTrain.left(.08);

        while(opModeIsActive())
        {
            telemetry.addData("color_r",floorColor.red());
            telemetry.addData("color_g",floorColor.green());
            telemetry.addData("color_b",floorColor.blue());
            telemetry.addData("color_a",floorColor.alpha());
            String color = ColorHelper.getFloorColor(floorColor);
            telemetry.addData("Floor color", color);
            telemetry.update();
            sleep(5);
        }
    }
}
