package com.gorillabots.samples.interfaces;

/**
 * Created by Chris on 10/1/2016.
 *
 */

public interface ClimberDepositInterface {

    void prepareToDepositClimbers();
    void resetPositionToStart();
    void depositClimbers();
    void stopDepossitingClimbers();
    boolean isReadyToDepositClimbers();
    boolean haveClimbersBeenDepositted();
}
