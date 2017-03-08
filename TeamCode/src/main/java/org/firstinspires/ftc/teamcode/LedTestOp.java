package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by emper on 3/7/2017.
 */

@Autonomous(name="LedTestOp", group="Comp")
public class LedTestOp extends LinearOpMode {
    LedControlFunctionality led;
    public void runOpMode() {
        led = new LedControlFunctionality(this);
        led.LedFlash(30, 0.5);
    }
}

