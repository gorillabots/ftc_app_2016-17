package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by emper on 3/5/2017.
 */

public class LedControlFunctionality implements LedControlInterface {
    private DcMotor controlPort;
    LinearOpMode opMode;
    Telemetry telemetry;
    private static final double LED_ON = 1.0;
    private static final double LED_OFF = 0.0;
    public void init(LinearOpMode opMode) {
        telemetry = opMode.telemetry;
        this.opMode = opMode;
        controlPort = opMode.hardwareMap.dcMotor.get("ledcontrol");
    }
    @Override
    public void setLedState(LedStates state) {
        switch (state) {
            case ON:
                controlPort.setPower(LED_ON);
                break;
            default:
                controlPort.setPower(LED_OFF);
                break;
        }
    }

    @Override
    public void setLedState(LedStates state, long time) throws InterruptedException{
        switch (state) {
            case ON:
                controlPort.setPower(LED_ON);
                Thread.sleep(time);
                controlPort.setPower(LED_OFF);
                break;
            default:
                controlPort.setPower(LED_OFF);
                Thread.sleep(time);
                controlPort.setPower(LED_ON);
                break;
        }
    }

    @Override
    public void LedFlash(LedStates state, long interval) throws InterruptedException{
        switch (state) {
            case ON:
                if (this.getState() == LedStates.ON) {
                    controlPort.setPower(LED_OFF);
                    Thread.sleep(interval);
                } else if (this.getState() == LedStates.OFF) {
                    controlPort.setPower(LED_ON);
                    Thread.sleep(interval);
                }
            default:
                if (this.getState() == LedStates.ON) {
                    controlPort.setPower(LED_ON);
                } else if (this.getState() == LedStates.OFF) {
                    controlPort.setPower(LED_OFF);
                }
                break;
        }
    }

    @Override
    public void LedFlash(long time, long interval) throws InterruptedException{
        final long time_init = System.currentTimeMillis();
        while(System.currentTimeMillis()-time_init < time){
            if (this.getState() == LedStates.ON) {
                controlPort.setPower(LED_OFF);
                Thread.sleep(interval);
            } else if (this.getState() == LedStates.OFF) {
                controlPort.setPower(LED_ON);
                Thread.sleep(interval);
            }
        }
    }

    @Override
    public LedStates getState() {
        if(controlPort.getPower() == LED_ON){
            return LedStates.ON;
        }
        if ()
    }
}
