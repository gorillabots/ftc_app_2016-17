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
       // led = new LedController(hardwareMap, telemetry);
        led.setLedState(LedStates.ON);
        ElapsedTime timer = new ElapsedTime();
        //led.LedFlash(30, 0.5);
        waitForStart();
        timer.startTime();
        boolean ledOn = false;
        while(timer.milliseconds()<10000){

            if(timer.milliseconds()%500 == 0){
                if(ledOn){
                    led.setLedState(LedStates.ON);
                    ledOn = true;
                }
                else{
                    led.setLedState(LedStates.OFF);
                    ledOn = false;
                }
            }

        }

    }
}

