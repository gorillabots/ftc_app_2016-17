package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.LedStates;
import org.firstinspires.ftc.teamcode.submodules.LedController;

/**
 * Created by emper on 3/7/2017.
 */

@Autonomous(name="LedTestOp", group="Comp")
public class LedTestOp extends LinearOpMode {
    LedController led;
    public void runOpMode() {
        led = new LedController(hardwareMap, telemetry);
        led.setLedState(LedStates.ON, null, null);
        waitForStart();
        led.setLedState(LedStates.FLASHING, new Double(10), new Double(0.5));

        }

    }


