package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by emper on 9/30/2016.
 */

public interface ButtonPresserInterface {
    void Press_Button(Servo servo, double position);
    boolean atBeacon();
    String getBeaconColor();
    String isTeamColor(String team);
    void Respond_If_In_Red_Alliance() throws InterruptedException;
    void Respond_If_In_Blue_Alliance() throws InterruptedException;
}
