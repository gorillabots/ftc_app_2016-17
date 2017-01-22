package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

/**
 * Created by root on 1/22/17.
 */

@TeleOp(name = "ColorEnumTest", group = "Concept")
public class ColorEnumTest extends LinearOpMode
{
    AutonomousDriveTrain driveTrain;

    ColorSensor beaconColorL;
    ColorSensor beaconColorR;

    @Override
    public void runOpMode() throws InterruptedException
    {
        //Initialize hardware
        driveTrain = new AutonomousDriveTrain(); //Initialize hardware
        driveTrain.init(this);

        beaconColorL = hardwareMap.colorSensor.get("beaconColor");
        beaconColorR = hardwareMap.colorSensor.get("beaconColor2");
        beaconColorL.setI2cAddress(I2cAddr.create8bit(58));
        beaconColorR.setI2cAddress(I2cAddr.create8bit(62));

        beaconColorL.enableLed(false);
        beaconColorR.enableLed(true);

        waitForStart(); //Done initializing

        TeamColors l = ColorHelper.getBeaconColorTest(beaconColorL);
        TeamColors r = ColorHelper.getBeaconColorTest(beaconColorR);

        telemetry.addData("Left", enumToString(l));
        telemetry.addData("Right", enumToString(r));
        telemetry.update();

        sleep(1000);

        driveTrain.beaconResponse(TeamColors.RED, beaconColorL, beaconColorR);

        beaconColorL.enableLed(false);
        beaconColorR.enableLed(true);
    }

    private String enumToString(TeamColors color)
    {
        switch(color)
        {
            case RED:
                return "Red";
            case BLUE:
                return "Blue";
            case INDECISIVE:
                return "Indecisive";
        }

        return "Null";
    }
}
