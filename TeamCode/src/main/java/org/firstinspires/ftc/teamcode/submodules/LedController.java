package org.firstinspires.ftc.teamcode.submodules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

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

    public LedController(HardwareMap map, Telemetry tele) {
        controlPort = map.dcMotor.get("ledcontrol");
        setLedState(LedStates.OFF, null, null);
        this.telemetry = tele;
    }

    @Override
    public void setLedState(LedStates state, Double time, Double interval) {
        currentState = state;
        switch (state) {
            case ON:
                currentState = LedStates.ON;
                controlPort.setPower(LED_ON);
                break;
            case OFF:
                currentState = LedStates.OFF;
                controlPort.setPower(LED_OFF);
                break;
            case FLASHING:
                flashingThreadHandle = new Thread(new Flashing(time, interval));
                if (flashingThreadHandle.isAlive()) {
                }
                if (!flashingThreadHandle.isAlive()) {
                    flashingThreadHandle.start();
                }
                break;
        }
    }

    @Override
    public LedStates getState() {
        return currentState;
    }

    private class Flashing implements Runnable {
        private volatile boolean close = false;
        private double time;
        private long interval;

        public Flashing(Double time, Double interval) {
            this.time = time * 1000;
            this.interval = (long) (interval * 1000);
        }

        public void LedFlash(long interval) throws InterruptedException {
            if (currentState != LedStates.FLASHING) {
                currentState = LedStates.FLASHING;
            }
            if (getState() == LedStates.ON) {
                controlPort.setPower(LED_OFF);
                Thread.sleep(interval);
            } else if (getState() == LedStates.OFF) {
                controlPort.setPower(LED_ON);
                Thread.sleep(interval);
            }
        }

        @Override
        public void run() {
            ElapsedTime timer = new ElapsedTime();
            timer.reset();
            while (!close && (timer.milliseconds() < this.time)) {

                try {
                    LedFlash(this.interval);
                } catch (InterruptedException e) {
                    this.close();
                    Thread.currentThread().interrupt();
                }
            }
        }

        public void close() {
            if (flashingThreadHandle.isAlive()) {
                close = true;
                currentState = LedStates.OFF;
                controlPort.setPower(LED_OFF);
                Thread.currentThread().interrupt();
            }
        }
    }
}
