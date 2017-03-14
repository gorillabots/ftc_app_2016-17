package org.firstinspires.ftc.teamcode.submodules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.LedStates;

/**
 * Created by Jarred on 3/13/2017.
 */

public class LedHelp {
    private DcMotor controlPort;
    Telemetry telemetry;
    private static final double LED_ON = 1.0;
    private static final double LED_OFF = 0.0;
    private ElapsedTime timer = new ElapsedTime();
    private boolean firstRun;

    public LedHelp(HardwareMap map, Telemetry tele) {

        controlPort = map.dcMotor.get("ledcontrol");
        timer.reset();
        timer.startTime();
        firstRun = false;
    }
    public void ledOn(){
        controlPort.setPower(LED_ON);
    }
    public void LedOff(){
        controlPort.setPower(LED_OFF);
    }
    public void flash(){
        if(!firstRun){
            timer.reset();
            timer.startTime();
            firstRun = true;
        }
        if(timer.milliseconds()< 500){
            ledOn();
        }
        if(timer.milliseconds() < 1000 && timer.milliseconds()> 500){
            LedOff();
        }
        if(timer.milliseconds()< 1000 && timer.milliseconds() < 1500){
            ledOn();
        }
        if(timer.milliseconds() < 2000 && timer.milliseconds()> 1500){
            LedOff();
        }
        if(timer.milliseconds()>2000 && timer.milliseconds() < 2500){
            ledOn();
        }
        if(timer.milliseconds() < 3000 && timer.milliseconds()> 3500){
            LedOff();
        }
        if(timer.milliseconds()< 3500 && timer.milliseconds() > 4000){
            ledOn();
        }
        if(timer.milliseconds() < 4000 && timer.milliseconds()> 4500){
            LedOff();
        }
        if(timer.milliseconds()< 4500 && timer.milliseconds() > 5000){
            ledOn();
        }
        if(timer.milliseconds() < 5000 && timer.milliseconds()> 5500){
            LedOff();
        }
        if(timer.milliseconds()< 5500 && timer.milliseconds() > 6000){
            ledOn();
        }
        if(timer.milliseconds() < 6000 && timer.milliseconds()> 6500){
            LedOff();
        }if(timer.milliseconds()< 6500 && timer.milliseconds() > 7000){
            ledOn();
        }
        if(timer.milliseconds() < 7000 && timer.milliseconds()> 7500){
            LedOff();
        }
        if(timer.milliseconds()< 7500 && timer.milliseconds() > 8000){
            ledOn();
        }
        if(timer.milliseconds() < 8000 && timer.milliseconds()> 8500){
            LedOff();
        }
        if(timer.milliseconds()< 8500 && timer.milliseconds() > 9000){
            ledOn();
        }
        if(timer.milliseconds() < 9000 && timer.milliseconds()> 9500){
            LedOff();
        }
        if(timer.milliseconds()< 9500 && timer.milliseconds()>10000){
            ledOn();
        }
        if(timer.milliseconds() < 1000 && timer.milliseconds()> 500){
            LedOff();
        }
    }

    public double getLed(){
        return controlPort.getPower();
    }
}
