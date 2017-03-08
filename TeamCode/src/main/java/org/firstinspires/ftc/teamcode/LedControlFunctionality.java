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
    public LedControlFunctionality(LinearOpMode opMode) {
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
    public void LedFlash(LedStates state, double interval){
        final long time_init = System.currentTimeMillis();
        if ((System.currentTimeMillis() - time_init) % (interval * 1000) == 0) {
            if (this.getState() == LedStates.ON) {
                controlPort.setPower(LED_OFF);
            } else if (this.getState() == LedStates.OFF) {
                controlPort.setPower(LED_ON);
            }
        }
        if(state == LedStates.OFF){
            if (this.getState() == LedStates.ON) {
                controlPort.setPower(LED_ON);
            } else if (this.getState() == LedStates.OFF) {
                controlPort.setPower(LED_OFF);
            }
        }
    }

    @Override
    public void LedFlash(double time, double interval) {
        final long time_init = System.currentTimeMillis();
        if ((System.currentTimeMillis() - time_init) < time) {
            if ((System.currentTimeMillis() - time_init) % (interval * 1000) == 0) {
                if (this.getState() == LedStates.ON) {
                    controlPort.setPower(LED_OFF);
                } else if (this.getState() == LedStates.OFF) {
                    controlPort.setPower(LED_ON);
                }
            }
        }
    }
    @Override
    public LedStates getState() {
        if(controlPort.getPower() == LED_ON){
            return LedStates.ON;
        }
        else if(controlPort.getPower() == LED_OFF){
            return LedStates.OFF;
        }
        else{
            return null;
        }
    }
}
