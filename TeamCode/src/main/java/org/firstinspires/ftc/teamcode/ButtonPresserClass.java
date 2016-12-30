package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static java.lang.Thread.sleep;
import static org.firstinspires.ftc.teamcode.Constants.*;
/**
 * Created by Josh on 9/30/2016.
 */
@TeleOp(name = "ButtonPresserZ", group = "Concept")
public class ButtonPresserClass implements ButtonPresserInterface {
    @Override
    public void Start(Servo servo1, Servo servo2) throws InterruptedException{
        Press_Button(servo1, ACTUATOR_RESET_VALUE);
        Press_Button(servo2, ACTUATOR_RESET_VALUE);
        Thread.sleep(ACTUATOR_RESET_TIME);
    }
    /*
    resets the linear actuattors for a set time.
     */
    @Override
    public void Press_Button(Servo servo, double position){
        servo.setDirection(Servo.Direction.FORWARD);
        servo.setPosition(position);
    }
    /*
    extends a servo to a position
     */
    @Override
    public boolean atBeacon(ColorSensor color) {
        if(ColorHelper.getFloorColor(color) == "white"){
            return true;
        }
        else{
            return false;
        }
    }

    /*
    returns a value based on wether of not the white line is beneath the floor sensor
     */
    @Override
    public String getBeaconColor(ColorSensor color) {
        return ColorHelper.getBeaconColor(color);
    }
    /*
    reutnrs a string containing the color that the beacon sensor sees
    */
    @Override
    //public String isTeamColor(String team) {
        //only use "red" and "blue"
      //  return team;
    //}


    public void Respond_If_In_Red_Alliance(ColorSensor color, Servo servo1, Servo servo2) throws InterruptedException{
        if(getBeaconColor(color).equals("red")){
            Press_Button(servo1, ACTUATOR_DISTANCE_TO_HIT_BEACON);
            Thread.sleep(ACTUATOR_TIME_OF_EXTENDING);
            Press_Button(servo1, ACTUATOR_RESET_VALUE);
            Thread.sleep(ACTUATOR_TIME_OF_EXTENDING);
        }
        else// if(getBeaconColor(color).equals("blue"))
        {
            Press_Button(servo2, ACTUATOR_DISTANCE_TO_HIT_BEACON);
            Thread.sleep(ACTUATOR_TIME_OF_EXTENDING);
            Press_Button(servo2, ACTUATOR_RESET_VALUE);
            Thread.sleep(ACTUATOR_TIME_OF_EXTENDING);
        }
        /*else{

        }*/
    }
    /*
    hit the red beacon side on the beacon that the robot is facing
     */
    @Override
    public void Respond_If_In_Blue_Alliance(ColorSensor color, Servo servo1, Servo servo2) throws InterruptedException{
        if(getBeaconColor(color).equals("blue")){
            Press_Button(servo1, ACTUATOR_DISTANCE_TO_HIT_BEACON);
            Thread.sleep(ACTUATOR_TIME_OF_EXTENDING);
            Press_Button(servo1, ACTUATOR_RESET_VALUE);
            Thread.sleep(ACTUATOR_TIME_OF_EXTENDING);
        }
        else
        {
            Press_Button(servo2, ACTUATOR_DISTANCE_TO_HIT_BEACON);
            Thread.sleep(ACTUATOR_TIME_OF_EXTENDING);
            Press_Button(servo2, ACTUATOR_RESET_VALUE);
            Thread.sleep(ACTUATOR_TIME_OF_EXTENDING);
        }
    }

    /*
    hit the blue beacon side on the beacon that the robot is facing
     */

    }

