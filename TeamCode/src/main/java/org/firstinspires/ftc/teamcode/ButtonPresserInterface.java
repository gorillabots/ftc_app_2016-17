package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by emper on 9/30/2016.
 */

public interface ButtonPresserInterface {
    void Start(Servo servo1, Servo servo2) throws InterruptedException;

    void Press_Button(Servo servo, double position);
    boolean atBeacon(ColorSensor color);
    String getBeaconColor(ColorSensor color);
    //String isTeamColor(String team);
    void Respond_If_In_Red_Alliance(ColorSensor color, Servo servo1, Servo servo2) throws InterruptedException;
    void Respond_If_In_Blue_Alliance(ColorSensor color, Servo servo1, Servo servo2) throws InterruptedException;
}
