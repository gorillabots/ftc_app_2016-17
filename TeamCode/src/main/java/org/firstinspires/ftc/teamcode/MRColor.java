package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by emper on 11/1/2015.
 */
public class MRColor extends LinearOpMode {
    ColorSensor Leftcolor;
    ColorSensor Floorcolor;
    String teamcolor = "red";
    String notteamcolor = "blue";
    //Servo leftarm;
    //Servo rightarm;
    //Servo screw;
    //DcMotor motor1;
    //DcMotor motor2;
    //DcMotor motor3;
    //DcMotor motor4;
    //DcMotor motor5;
    //DcMotor motor6;
    //String whatColorIsLeft;
    String whatColorIs1Left;
    String whatColorIs2Left;
    String whatColorIsFloor;

    /**
     * team color is red for this program
     */


    public void _init() {
        Floorcolor = hardwareMap.colorSensor.get("Floorcolor_sensor");
        Floorcolor.enableLed(true);
        telemetry.addData("state", "setup color sensors");

    }

    /**
     * returns red or blue or none depending on what color is read from the floor sensor.
     *
     * @return String
     */


    public String getFloorcolor() {
        String currentcolor = "none";

        telemetry.addData("floorcolor-red", Floorcolor.red());
        telemetry.addData("floorcolor-blue", Floorcolor.blue());
        telemetry.addData("floorcolor-green", Floorcolor.green());
        if (Floorcolor.red() > Floorcolor.green() && Floorcolor.red() > Floorcolor.blue() && Floorcolor.green() >= Floorcolor.blue()) {
            currentcolor = "red";
        }
        telemetry.addData("floorcolor-red", Floorcolor.red());
        telemetry.addData("floorcolor-blue", Floorcolor.blue());
        telemetry.addData("floorcolor-green", Floorcolor.green());
        if (Floorcolor.red() < Floorcolor.green() && Floorcolor.blue() < Floorcolor.green() && Floorcolor.red() == Floorcolor.blue()) {
            currentcolor = "blue";
        }
        if (Floorcolor.blue() < Floorcolor.red() && Floorcolor.red() < Floorcolor.green() && Floorcolor.green() < Floorcolor.alpha()) {
            currentcolor = "alpha";
        }
        return currentcolor;
    }

    /**
     * retruns red or blue or none depending on what color is read from the beacon sensor.
     *
     * @return String
     */
    public String getBeaconcolor(ColorSensor color) {
        String currentcolor = "none";

        telemetry.addData("beconcolor-red", color.red());
        telemetry.addData("beconcolor-blue", color.blue());
        telemetry.addData("beconcolor-green", color.green());
        if (color.red() > color.blue() && color.red() > color.green() && color.green() == color.blue()) {
            currentcolor = "red";
        }
        telemetry.addData("beaconcolor-red", color.red());
        telemetry.addData("beconcolor-blue", color.blue());
        telemetry.addData("beconcolor-green", color.green());
        if (color.red() < color.blue() && color.green() < color.blue() && color.blue() > 1) {
            currentcolor = "blue";
        }

        return currentcolor;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("state", "going into init");
        _init();
        telemetry.addData("state", "setting up sensors");
        waitForStart();
        while (opModeIsActive()) {


            //whatColorIsLeft = getBeaconcolor(Leftcolor);
            whatColorIsFloor = getFloorcolor();
            //telemetry.addData("Leftbeacon_color", whatColorIsLeft);
            telemetry.addData("Floor_color", whatColorIsFloor);
        }
    }
}