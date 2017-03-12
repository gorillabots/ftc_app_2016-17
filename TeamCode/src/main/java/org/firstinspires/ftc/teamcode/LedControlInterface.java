package org.firstinspires.ftc.teamcode;

/**
 * Created by emper on 3/5/2017.
 */

public interface LedControlInterface {

    /**
     * Turns the LED on or off until otherwise specified
     * @param state Whether the LED is on or not
     *              Enter either LedStates.ON or LedStates.OFF
     * @param time If the LED is to flash, this is the time it does so
     * @param interval If the LED is to flash, this is its interval of flashing
     */
    void setLedState(LedStates state, Double time, Double interval);


    /**
     * Determines the state of the LED
     * @return LED state as either ON or OFF
     */
    LedStates getState();
}
