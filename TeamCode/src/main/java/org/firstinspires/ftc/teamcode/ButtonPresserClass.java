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
 * Created by emper on 9/30/2016.
 */
@TeleOp(name = "ButtonPresserZ", group = "Concept")
public class ButtonPresserClass implements ButtonPresserInterface {
    @Override
    public void Start(Servo servo1, Servo servo2) throws InterruptedException{
        Press_Button(servo1, ACTUATOR_RESET_VALUE);
        Press_Button(servo2, ACTUATOR_RESET_VALUE);
        Thread.sleep(ACTUATOR_RESET_TIME);
    }
    @Override
    public void Press_Button(Servo servo, double position){
        servo.setDirection(Servo.Direction.FORWARD);
        servo.setPosition(position);
    }
    @Override
    public boolean atBeacon(ColorSensor color) {
        if(ColorHelper.getFloorColor(color) == "white"){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String getBeaconColor(ColorSensor color) {
        return ColorHelper.getBeaconColor(color);
    }

    @Override
    public String isTeamColor(String team) {
        //only use "red" and "blue"
        return team;
    }

    @Override
    public void Respond_If_In_Red_Alliance(ColorSensor color, Servo servo1, Servo servo2) throws InterruptedException{
        if(getBeaconColor(color) == isTeamColor("red")){
            Press_Button(servo1, ACTUATOR_DISTANCE_TO_HIT_BEACON);
            Thread.sleep(ACTUATOR_TIME_OF_EXTENDING);
            Press_Button(servo1, ACTUATOR_RESET_VALUE);
            Thread.sleep(ACTUATOR_TIME_OF_EXTENDING);
        }
        else if(getBeaconColor(color) == "blue"){
            Press_Button(servo2, ACTUATOR_DISTANCE_TO_HIT_BEACON);
            Thread.sleep(ACTUATOR_TIME_OF_EXTENDING);
            Press_Button(servo2, ACTUATOR_RESET_VALUE);
            Thread.sleep(ACTUATOR_TIME_OF_EXTENDING);
        }
        else{
                while(true){

                }
        }
    }

    @Override
    public void Respond_If_In_Blue_Alliance(ColorSensor color, Servo servo1, Servo servo2) throws InterruptedException{
        if(getBeaconColor(color) == isTeamColor("blue")){
            Press_Button(servo1, ACTUATOR_DISTANCE_TO_HIT_BEACON);
            Thread.sleep(ACTUATOR_TIME_OF_EXTENDING);
            Press_Button(servo1, ACTUATOR_RESET_VALUE);
            Thread.sleep(ACTUATOR_TIME_OF_EXTENDING);
        }
        else if(getBeaconColor(color) == "red"){
            Press_Button(servo2, ACTUATOR_DISTANCE_TO_HIT_BEACON);
            Thread.sleep(ACTUATOR_TIME_OF_EXTENDING);
            Press_Button(servo2, ACTUATOR_RESET_VALUE);
            Thread.sleep(ACTUATOR_TIME_OF_EXTENDING);
        }
        else{

        }
    }



    }

