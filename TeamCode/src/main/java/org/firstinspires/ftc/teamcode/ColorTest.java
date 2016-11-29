package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by emper on 11/17/2016.
 */
@Autonomous(name="ColorTest", group="concept")
public class ColorTest extends OpMode{
    ColorSensor color;
    ColorSensor color2;
    @Override
    public void init() {
        color = hardwareMap.colorSensor.get("color");
        color2 = hardwareMap.colorSensor.get("color2");
    }

    @Override
    public void loop(){
        while(true){
            color.enableLed(true);
            color2.enableLed(true);
            try {
                Thread.sleep(1000);
            }
            catch(InterruptedException e){

            }
            color.enableLed(false);
            color.enableLed(false);
            try {
                Thread.sleep(1000);
            }
            catch(InterruptedException e){

            }
        }
    }
}
