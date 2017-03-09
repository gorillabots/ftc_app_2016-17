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
     * Flashes LED for a specific amount of time
     * @param time
     * @param interval Length between flashes in seconds
     */
    void LedFlash(double time, double interval);

    /**
     * Determines the state of the LED
     * @return LED state as either ON or OFF
     */
    LedStates getState();
}
