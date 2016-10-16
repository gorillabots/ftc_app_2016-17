package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by emper on 11/1/2015.
 */
@Autonomous(name = "Sensor: MRColor", group = "Sensor")
public class MRColor extends OpMode {
    ColorSensor color;
    ColorSensor Floorcolor;
    String teamcolor = "red";
    String notteamcolor = "blue";
    String whatColorIs1Left;
    String whatColorIs2Left;
    String whatColorIsFloor;
    ElapsedTime poemElapsed = new ElapsedTime();
    boolean bPrevState = false;
    boolean bLedOn = false;

    /**
     * team color is red
     */

    @Override
    public void init() {

        Floorcolor = hardwareMap.colorSensor.get("Floorcolor_sensor");
        boolean bLedOn = true;
        // to count seconds of elapsed time
        poemElapsed = new ElapsedTime();
    }

    public String getFloorcolor() {
        String currentcolor = "none";
        //find out color
        telemetry.addData("floorcolor-red", Floorcolor.red());
        telemetry.addData("floorcolor-blue", Floorcolor.blue());
        telemetry.addData("floorcolor-green", Floorcolor.green());
        if (Floorcolor.red() > Floorcolor.green() && Floorcolor.red() > Floorcolor.blue() && Floorcolor.green() >= Floorcolor.blue()) {
            currentcolor = "red";
        }
        if (Floorcolor.red() < Floorcolor.green() && Floorcolor.blue() < Floorcolor.green() && Floorcolor.red() == Floorcolor.blue()) {
            currentcolor = "blue";
        }
        if (Floorcolor.blue() < Floorcolor.red() && Floorcolor.red() < Floorcolor.green() && Floorcolor.green() < Floorcolor.alpha()) {
            currentcolor = "alpha";
        }
        return currentcolor;
    }

    @Override
    public void loop() {

        telemetry.addData("floorcolor-red", Floorcolor.red());
        telemetry.addData("floorcolor-blue", Floorcolor.blue());
        telemetry.addData("floorcolor-green", Floorcolor.green());
        telemetry.addData("time",poemElapsed.seconds());
//        whatColorIsFloor = getFloorcolor();
//        telemetry.addData("Floor_color", whatColorIsFloor);
        telemetry.update();

        //turn on Led on sensor
        if ((poemElapsed.seconds() >= 4.5) && (bLedOn == false)){
            bLedOn = true;
            Floorcolor.enableLed(bLedOn);

        }
        boolean X = gamepad1.x;
        // check for button-press state transitions.
        if ((X == true) && (X != bPrevState))  {
            // button is transitioning to a pressed state. Toggle the LED.
            bLedOn = !bLedOn;
            Floorcolor.enableLed(bLedOn);
        }

        // update previous state variable.
        bPrevState = X;


    }
}