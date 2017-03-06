package org.firstinspires.ftc.teamcode;

/**
 * Created by emper on 3/5/2017.
 */

public interface LedControlInterface {

    /**
     * Turns the LED on or off until otherwise specified
     * @param state Whether the LED is on or not
     *              Enter either LedStates.ON or LedStates.OFF
     */
    void setLedState(LedStates state);

    /**
     * Turns the LED on or off for a specific amount of time
     * @param state Whether the LED is on or not
     *              Enter either LedStates.ON or LedStates.OFF
     * @param time  How long the LED is to be on
     */
    void setLedState(LedStates state, long time) throws InterruptedException;

    /**
     * Starts LED flashing, which continues until instructed to stop
     * @param state Whether the LED is flashing or not
     *              Enter either LedStates.ON or LedStates.OFF
     * @param interval How long the LED is on or off before it switches to the other state while flashing
     */
    void LedFlash(LedStates state, long interval);

    /**
     * Flashes LED for a specific amount of time
     * @param time
     */
    void LedFlash(long time, long interval) throws InterruptedException;

    /**
     * Determines the state of the LED
     * @return LED state as either ON or OFF
     */
    LedStates getState();
}
