package org.firstinspires.ftc.teamcode;

/**
 * Created by csoper on 9/27/2016.
 */

public interface LargeBallLifter {
    void openLift();
    void closeLift();
    void raiseLift();
    void lowerLift();
    boolean isLiftOpen();
    boolean isLiftRaised();
    double getLiftHeight();

}
