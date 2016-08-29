package com.gorillabots.components;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * @author csoper on 8/26/16.
 */
public class Winglets {

    private Servo leftWinglet;
    private Servo rightWinglet;
    private static final double LEFT_WINGLET_UP = 0.7;
    private static final double LEFT_WINGLET_DOWN = 0.2;
    private static final double RIGHT_WINGLET_UP = 0.3;
    private static final double RIGHT_WINGLET_DOWN = 0.8;

    public Winglets (HardwareMap hardwareMap) {
        // Initialize servos
        leftWinglet = hardwareMap.servo.get("servo_1");
        rightWinglet = hardwareMap.servo.get("servo_2");
        setState("Initialized");

        // Set servos to starting position
        retractWinglets();
    }

    /**
     * Extends Winglets for saving climbers
     */
    public void extendWinglets() {
        leftWinglet.setPosition(LEFT_WINGLET_DOWN);
        rightWinglet.setPosition(RIGHT_WINGLET_DOWN);
        setState("Extended");
    }

    /**
     * Retracts Winglets to Starting Position
     */
    public void retractWinglets() {
        leftWinglet.setPosition(LEFT_WINGLET_UP);
        rightWinglet.setPosition(RIGHT_WINGLET_UP);
        setState("Retracted");
    }

    private void setState(String state) {
        Robot.telemetry.addData("Wing State", state);
        Robot.telemetry.update();
    }
}
