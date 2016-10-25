package org.firstinspires.ftc.teamcode;

/**
 * Created by emper on 10/14/2016.
 */

public interface Autonomous_Drive_Train_Interface {
    /**
     * Goes forward
     *
     * @param right_side
     * @param left_side
     */
    void goForward(double right_side, double left_side, long time);

    /**
     * Goes backward
     *
     * @param right_side
     * @param left_side
     */
    void goBackward(double right_side, double left_side, long time);
    void goLeft(double front_side, double back_side, long time);
    void goRight(double front_side, double back_side, long time);
    void clockwise(double all_sides, long time);
    void counterclockwise(double all_sides, long time);
}
