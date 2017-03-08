package org.firstinspires.ftc.teamcode.submodules;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.LedControlInterface;
import org.firstinspires.ftc.teamcode.LedStates;

/**
 * Created by emper on 3/5/2017.
 */

public class LedController implements LedControlInterface {
    private DcMotor controlPort;
    Telemetry telemetry;
    private static final double LED_ON = 1.0;
    private static final double LED_OFF = 0.0;
    private LedStates currentState;
    private double flash_start;
    public LedController(OpMode opMode) {
        telemetry = opMode.telemetry;
        controlPort = opMode.hardwareMap.dcMotor.get("ledcontrol");
        setLedState(LedStates.OFF);
    }
    @Override
    public void setLedState(LedStates state) {

        currentState = state;
        switch (state) {
            case ON:
                controlPort.setPower(LED_ON);
                break;
            case OFF:
                controlPort.setPower(LED_OFF);
                break;
            case FLASHING:
                break;
        }
    }

    @Override
    public void LedFlash(double time, double interval) {
        if (currentState != LedStates.FLASHING) {
            currentState = LedStates.FLASHING;
            flash_start = System.currentTimeMillis();
        }
        if ((System.currentTimeMillis() - flash_start) < time*1000) {
            if ((System.currentTimeMillis() - flash_start) % (interval * 1000) == 0) {
                if (this.getState() == LedStates.ON) {
                    controlPort.setPower(LED_OFF);
                } else if (this.getState() == LedStates.OFF) {
                    controlPort.setPower(LED_ON);
                }
            }
        } else {
            currentState = LedStates.OFF;
            controlPort.setPower(LED_OFF);
        }
    }
    @Override
    public LedStates getState() {
        return currentState;
    }
}
