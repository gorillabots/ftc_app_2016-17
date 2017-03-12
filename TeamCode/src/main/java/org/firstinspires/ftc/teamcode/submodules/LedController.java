package org.firstinspires.ftc.teamcode.submodules;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.LedControlInterface;
import org.firstinspires.ftc.teamcode.LedStates;

/**
 * Created by emper on 3/5/2017.
 */

public class LedController implements LedControlInterface {
    private Thread flashingThreadHandle = null;
    private DcMotor controlPort;
    Telemetry telemetry;
    private static final double LED_ON = 1.0;
    private static final double LED_OFF = 0.0;
    private LedStates currentState;
    private double flash_start;
    public LedController(HardwareMap map, Telemetry tele) {
        controlPort = map.dcMotor.get("ledcontrol");
        setLedState(LedStates.OFF, null, null);
    }
    @Override
    public void setLedState(LedStates state, Double time, Double interval) {
        currentState = state;
        switch (state) {
            case ON:
                controlPort.setPower(LED_ON);
                break;
            case OFF:
                controlPort.setPower(LED_OFF);
                break;
            case FLASHING:
                flashingThreadHandle = new Thread(new Flashing(time, interval));
                if(flashingThreadHandle.isAlive()){}
                if(!flashingThreadHandle.isAlive()){
                    flashingThreadHandle.start();
                }
                break;
        }
    }
    @Override
    public LedStates getState() {
        return currentState;
    }
    private class Flashing implements Runnable{
        private double time;
        private double interval;
        public Flashing(double time, double interval){
            this.time = time;
            this.interval = interval;
        }
        public void LedFlash(double time, double interval) {
            if (currentState != LedStates.FLASHING) {
                currentState = LedStates.FLASHING;
                flash_start = System.currentTimeMillis();
            }
            if ((System.currentTimeMillis() - flash_start) < time*1000) {
                if ((System.currentTimeMillis() - flash_start) % (interval * 1000) == 0) {
                    if (getState() == LedStates.ON) {
                        controlPort.setPower(LED_OFF);
                    } else if (getState() == LedStates.OFF) {
                        controlPort.setPower(LED_ON);
                    }
                }
            } else {
                currentState = LedStates.OFF;
                controlPort.setPower(LED_OFF);
            }
        }
        @Override
        public void run() {
            LedFlash(time, interval);
        }
    }
}
