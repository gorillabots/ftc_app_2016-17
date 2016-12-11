package org.firstinspires.ftc.teamcode;

/**
 * Created by emper on 11/22/2016.
 */

public class Constants {
    public final static double WALL_TO_WALL_IN_AUTONOMOUS = 1.42;
    public final static double GO_BACKWARD_AFTER_TOUCH_WALL = 0.1;
    public final static double ACTUATOR_DISTANCE_TO_HIT_BEACON = 0.5;
    public final static double ACTUATOR_RESET_VALUE = 0.15;
    public final static long ACTUATOR_TIME_OF_EXTENDING = 2500;
    public final static double BETWEEN_WHITE_LINES = 0.9;
    public final static double GO_BACK_AFTER_PRESSING_FIRST = 0.02;
    public final static long ACTUATOR_RESET_TIME = 5000;

    private final static int TESTBED_DIAGONAL_INCREMENTS = 5240;
    private final static int TESTBED_STRAIGHT_INCREMENTS = 3890;

    private final static int COMP_DIAGONAL_INCREMENTS = 5240;
    private final static int COMP_STRAIGHT_INCREMENTS = 3890;

    public final static int DIAGONAL_INCREMENTS = TESTBED_DIAGONAL_INCREMENTS;
    public final static int STRAIGHT_INCREMENTS = TESTBED_STRAIGHT_INCREMENTS;

    public final static double MAX_SPEED = 1;
    public final static double SLOW_SPEED = .3;
}
