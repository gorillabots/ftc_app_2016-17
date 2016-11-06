package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by emper on 9/30/2016.
 */

public interface ButtonPresserInterface {
    void Press_Button(Servo servo, double position);
    void Was_Button_Pressed();
    boolean Is_Left_Side_Teamcolor();
    boolean Is_Right_Side_Teamcolor();
    void Get_Left_Color();

    void Get_Right_Color();
    boolean Light_Sensor();
}
